package com.idanchuang.cms.server.applicationNew.service;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.idanchuang.cms.api.response.PageRenderDTO;
import com.idanchuang.cms.server.domainNew.model.cms.aggregation.render.PageRender;
import com.idanchuang.cms.server.domainNew.model.cms.aggregation.render.PageRenderRepository;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.Catalogue;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueId;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueRepository;
import com.idanchuang.cms.server.domainNew.model.cms.container.Container;
import com.idanchuang.cms.server.domainNew.model.cms.container.ContainerRepository;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-23 11:24
 * @Desc: 页面聚合服务
 * @Copyright VTN Limited. All rights reserved.
 */
@Service
@Slf4j
public class PageRenderService {

    @Resource
    private PageRenderRepository pageRenderRepository;

    @Resource
    private ContainerRepository containerRepository;

    @Resource
    private CatalogueRepository catalogueRepository;

    /**
     * 删除所有页面聚合信息
     * @return
     */
    public Boolean deleteAllPageVersion() {
        List<Catalogue> catalogues = catalogueRepository.queryAllCatalogueList();

        if (CollectionUtils.isEmpty(catalogues)) {
            return false;
        }

        //依次删除页面聚合缓存
        catalogues.forEach(p -> deletePageRender(p.getId()));
        return true;
    }

    /**
     * 构造所有的页面聚合信息
     * @return
     */
    public Boolean buildAllPageVersion() {
        List<Catalogue> catalogues = catalogueRepository.queryAllCatalogueList();

        if (CollectionUtils.isEmpty(catalogues)) {
            return false;
        }

        //依次构造页面聚合缓存
        catalogues.forEach(p -> generateCurrentVersion(p.getId()));
        return true;
    }

    /**
     * 获取目录下的当前生效的页面聚合信息
     * @param catalogueId
     * @return
     */
    public PageRender generateCurrentVersion(CatalogueId catalogueId) {

        //获取待生效和生效中的模版
        List<PageRender> pageRenders = pageRenderRepository.getPageRenderListForActive(catalogueId);

        //模版列表按照特定规则排序
        sortRenders(pageRenders);

        //获取当前页面聚合信息
        PageRender pageRender = fetchCurrentRender(pageRenders);

        //更新缓存
        pageRenderRepository.storePageRender(pageRender);

        return pageRender;
    }

    LoadingCache<CatalogueId, PageRender> loadingCache = Caffeine.newBuilder()
            .maximumSize(10_000)
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .refreshAfterWrite(1, TimeUnit.SECONDS)
            .build(key -> getCurrentPageStyle(key));

    public PageRender getCurrentStyleByCache(CatalogueId catalogueId) {
        if (null != catalogueId) {
            return loadingCache.get(catalogueId);
        }
        return null;
    }

    /**
     * 获取当前展示的页面信息
     * @param catalogueId
     * @return
     */
    public PageRender getCurrentPageStyle(CatalogueId catalogueId) {

        if (null == catalogueId) {
            return null;
        }

        PageRender pageRender = pageRenderRepository.getPageRenderByCatalogueId(catalogueId);

        //如果当前模版为空或者到达失效时间，重新构造缓存
        if (null == pageRender || pageRender.isOver()) {
            pageRender = generateCurrentVersion(catalogueId);
        }

        //重新构造出来的没有数据或者是待生效版本返回空
        if (null == pageRender || !pageRender.isCurrent()) {
            return null;
        }

        LocalDateTime nextStartTime = pageRender.getNextStartTime();
        if (nextStartTime != null && LocalDateTime.now().isAfter(nextStartTime)) {
            pageRender = generateCurrentVersion(catalogueId);
        }

        return pageRender;
    }

    /**
     * 删除该目录的聚合信息
     * @param catalogueId
     */
    public void deletePageRender(CatalogueId catalogueId) {
        pageRenderRepository.removePageRender(catalogueId);
    }

    /**
     * 对页面聚合列表进行排序（先按照开始时间倒叙，开始时间相同按照模版id倒叙）
     * @param pageRenders
     */
    public void sortRenders(List<PageRender> pageRenders) {

        if (CollectionUtils.isEmpty(pageRenders)) {
            return;
        }
        pageRenders.sort((o1, o2) -> {
            if (o2.getStartTime().equals(o1.getStartTime())) {
                return Long.compare(o2.getMasterplateId().getValue(), o1.getMasterplateId().getValue());
            }
            return o2.getStartTime().compareTo(o1.getStartTime());
        });
    }

    /**
     * 获取页面聚合版本
     * @param pageRenders
     * @return
     */
    private PageRender fetchCurrentRender(List<PageRender> pageRenders) {

        if (CollectionUtils.isEmpty(pageRenders)) {
            return null;
        }

        PageRender currentPageRender = null;
        PageRender nextPageRender = null;

        //存在生效中的页面
        for (int i = 0; i < pageRenders.size(); i++) {
            PageRender pageRender = pageRenders.get(i);
            if (LocalDateTime.now().compareTo(pageRender.getStartTime()) >= 0) {
                currentPageRender = pageRender;
                if (i > 0) {
                    nextPageRender = pageRenders.get(i - 1);
                }
                break;
            }
        }


        //如果不存在当前生效的就获取下一个待生效版本
        if (null == currentPageRender) {
            currentPageRender = pageRenders.get(pageRenders.size() - 1);
            if (pageRenders.size() >= 2) {
                nextPageRender = pageRenders.get(pageRenders.size() - 2);
            }
        }

        if (null != nextPageRender) {
            currentPageRender.setNextStartTime(nextPageRender.getStartTime());
        }

        //获取具体的结构样式
        List<Container> containers = containerRepository.queryContainerList(currentPageRender.getMasterplateId());

        //添加容器列表
        currentPageRender.addContainers(containers);

        return currentPageRender;
    }
}
