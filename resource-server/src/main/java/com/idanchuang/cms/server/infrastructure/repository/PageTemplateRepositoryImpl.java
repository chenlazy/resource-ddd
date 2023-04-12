package com.idanchuang.cms.server.infrastructure.repository;

import com.alibaba.fastjson.JSON;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.idanchuang.cms.api.response.ShareJsonModelDTO;
import com.idanchuang.cms.server.domain.model.cms.CmsCorePageList;
import com.idanchuang.cms.server.domain.model.cms.CmsPage;
import com.idanchuang.cms.server.domain.model.cms.CmsPageContainer;
import com.idanchuang.cms.server.domain.model.cms.ContainerRender;
import com.idanchuang.cms.server.domain.model.cms.PageRender;
import com.idanchuang.cms.server.domain.model.cms.schema.SchemaCode;
import com.idanchuang.cms.server.domain.model.cms.schema.SchemaId;
import com.idanchuang.cms.server.domain.repository.CmsPageContainerRepository;
import com.idanchuang.cms.server.domain.repository.CmsPageRepository;
import com.idanchuang.cms.server.domain.repository.CmsPageSchemaRepository;
import com.idanchuang.cms.server.domain.repository.PageTemplateRepository;
import com.idanchuang.cms.server.infrastructure.persistence.model.CmsPageSchemaListDO;
import com.idanchuang.component.redis.util.RedisUtil;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author lei.liu
 * @date 2021/9/13
 */
@Repository
@Slf4j
public class PageTemplateRepositoryImpl implements PageTemplateRepository {

    private static final String PAGE_STYLE_CURRENT_KEY = "page.current:";

    private static final String PAGE_STYLE_VERSION_KEY = "page.version:";

    @Resource
    private CmsPageSchemaRepository cmsPageSchemaRepository;

    @Resource
    private CmsPageRepository cmsPageRepository;

    @Resource
    private CmsPageContainerRepository cmsPageContainerRepository;

    /**
     * 获取页面的当前的样式
     * @param id    页面实例ID
     * @return  渲染的页面
     */

    LoadingCache<Integer, PageRender> loadingCache = Caffeine.newBuilder()
            .maximumSize(10_000)
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .refreshAfterWrite(1, TimeUnit.SECONDS)
            .build(key -> getCurrentStyle(key));

    @Override
    public PageRender getInfoById(Integer id) {


        return loadingCache.get(id);
    }

    @Override
    public PageRender getInfoBySchemaCode(SchemaCode schemaCode) {

         SchemaId schemaId = cmsPageSchemaRepository.getPageSchemaIdByCode(schemaCode);

        if(schemaId == null){
            return null;
        }
        return getInfoById((int) schemaId.getValue());
    }

    /**
     * 根据页面定义ID，获取所有页面实例列表
     *
     * @param id 页面定义ID
     * @return 页面实例列表
     */
    @Override
    public List<PageRender> getInfoList(Integer id) {
        List<CmsPage> pageList = cmsPageRepository.getCmsPageList(id);
        if (CollectionUtils.isEmpty(pageList)) {
            return null;
        }

        List<PageRender> pageRenderList = pageList.stream().map(this::convertOf).collect(Collectors.toList());

        for (PageRender pageRender : pageRenderList) {
            List<String> sort = PageRender.getSortArray(pageRender.getSort());
            List<CmsPageContainer> pageContainers = cmsPageContainerRepository.getByIds(sort.stream().map(Long::parseLong).collect(Collectors.toList()));


            if (!CollectionUtils.isEmpty(pageContainers)) {
                pageRender.setContainers(pageContainers.stream().map(this::convertOf).collect(Collectors.toList()));
            }
        }

        return pageRenderList;
    }

    @Override
    public List<PageRender> getInfoListBySchemaCode(SchemaCode schemaCode) {
        SchemaId schemaId = cmsPageSchemaRepository.getPageSchemaIdByCode(schemaCode);

        if(schemaId == null){
            return Lists.emptyList();
        }
        return getInfoList((int) schemaId.getValue());
    }

    /**
     * 构建页面生效样式
     *
     * @param id 页面定义ID
     */
    @Override
    public void buildPageVersion(Integer id) {
        if (id == null) {
            return;
        }
        buildCurrentVersion(id);
    }

    @Override
    public void buildAllPageVersion() {
        List<CmsPageSchemaListDO> pageSchemaList = cmsPageSchemaRepository.queryByCondition(new CmsCorePageList());
        if (CollectionUtils.isEmpty(pageSchemaList)) {
            return;
        }

        for (CmsPageSchemaListDO pageSchema : pageSchemaList) {
            if (pageSchema.getId() != null) {
                buildCurrentVersion(pageSchema.getId().intValue());
            }
        }
    }

    /**
     * 删除页面样式缓存
     * @param pageId    页面ID
     */
    @Override
    public void deletePageVersion(Integer pageId) {
        RedisUtil.getInstance().delObj(getCurrentKey(pageId));
        RedisUtil.getInstance().delObj(getVersionKey(pageId));
    }

    /**
     * 删除页面某个版本的样式缓存
     * @param id        页面定义ID
     * @param version   版本号
     */
    @Override
    public void deletePageVersion(Integer id, String version) {
        if (id == null || StringUtils.isEmpty(version)) {
            return;
        }
        buildPageVersion(id);
    }

    @Override
    public void deleteAllPageVersion() {
        List<CmsPageSchemaListDO> pageList = cmsPageSchemaRepository.queryByCondition(new CmsCorePageList());
        if (CollectionUtils.isEmpty(pageList)) {
           return;
        }

        for (CmsPageSchemaListDO page : pageList) {
            if (page.getId() != null) {
                deletePageVersion(page.getId().intValue());
            }
        }
    }

    @Override
    public PageRender getCachePageVersion(Integer pageId) {
        String key = getCurrentKey(pageId);
        return RedisUtil.getInstance().getObj(key);
    }


    private PageRender getCurrentStyle(Integer pageId) {
        String key = getCurrentKey(pageId);
        PageRender pageRender = null;

        try {
            pageRender = RedisUtil.getInstance().getObj(key);
        } catch (Exception e) {
            log.warn("pageRender缓存获取失败",e);
        }

        if (pageRender == null) {
            buildPageVersion(pageId);
            pageRender = RedisUtil.getInstance().getObj(key);
        }

        if (pageRender != null) {
            pageRender = newPageVersion(pageRender);
            if (pageRender == null || pageRender.getId() == null || !pageRender.isCurrent()) {
                return null;
            }
        }
        return pageRender;
    }

    private PageRender newPageVersion(PageRender pageRender) {

        if (pageRender.isOver()) {
            return buildCurrentVersion(pageRender.getId());
        }

        return pageRender;
    }

    private void saveCurrentCache(Integer id, PageRender pageRender) {
        RedisUtil.getInstance().setObj(getCurrentKey(id), pageRender);
    }

    private PageRender buildCurrentVersion(Integer id) {
        if (id == null) {
            return null;
        }

        List<CmsPage> pageList = cmsPageRepository.getCmsPageListForActive(Collections.singletonList(id));
        if (CollectionUtils.isEmpty(pageList)) {
            return null;
        }

        List<PageRender> pageRenderList = pageList.stream().map(this::convertOf).collect(Collectors.toList());
        sort(pageRenderList);

        PageRender pageRender = null;
        int size = pageRenderList.size();
        int index = -1;
        for (int i = 0; i < size; i++) {
            PageRender temp = pageRenderList.get(i);
            if (LocalDateTime.now().compareTo(temp.getStartTime()) >= 0) {
                pageRender = temp;
                index = i;
                break;
            }
        }

        if (pageRender == null) {
            index = size - 1;
            pageRender = pageRenderList.get(index);
        }

        // 下一个版本的启用类型，0：过去时间的一个版本作为下一版本；1：未来时间的一个版本作为下一个版本
        int oldOrFuture = 1;

        // 下一个版本
        PageRender nextPageRender = null;
        if (index > 0) {
            // 存在未来时间的下一个版本
            nextPageRender = pageRenderList.get(index - 1);

            if (pageRender.getEndTime() != null && nextPageRender != null) {
                if (pageRender.getEndTime().compareTo(nextPageRender.getStartTime()) < 0) {
                    if (index < size - 1) {
                        PageRender oldPageRender = pageRenderList.get(index + 1);
                        if (oldPageRender.getEndTime() == null || oldPageRender.getEndTime().compareTo(pageRender.getStartTime()) > 0) {
                            nextPageRender = oldPageRender;
                            oldOrFuture = 0;
                        }
                    }
                }
            }
        }

        if (nextPageRender != null) {
            pageRender.setNextVersion(nextPageRender.getVersion());
            if (oldOrFuture == 0) {
                pageRender.setVersionEndTime(pageRender.getEndTime());
            } else {
                pageRender.setVersionEndTime(nextPageRender.getStartTime());
            }
        }

        setPageStyle(pageRender);
        saveCurrentCache(id, pageRender);
        return pageRender;
    }

    //多容器下根据page sort字段直接过滤
    private void setPageStyle(PageRender pageRender) {
        //历史逻辑
//        List<CmsPageContainer> pageContainerList = cmsPageContainerRepository.queryPageContainer(pageRender.getPageId());
//        if (!CollectionUtils.isEmpty(pageContainerList)) {
//            pageRender.setContainers(pageContainerList.stream().filter(e -> NumberUtils.isCreatable(pageRender.getSort()) && e.getId().equals(Long.parseLong(pageRender.getSort()))).map(this::convertOf).collect(Collectors.toList()));
//        }

        List<String> containerIds = PageRender.getSortArray(pageRender.getSort());

        if(CollectionUtils.isEmpty(containerIds)){
            return;
        }

        List<CmsPageContainer> pageContainers = cmsPageContainerRepository.getByIds(containerIds.stream().map(Long::parseLong).collect(Collectors.toList()));
        if (!CollectionUtils.isEmpty(pageContainers)) {
            pageRender.setContainers(pageContainers.stream().map(this::convertOf).collect(Collectors.toList()));
        }


    }

    private void sort(List<PageRender> pageList) {
        if (pageList == null || pageList.size() <= 1) {
            return;
        }

        pageList.sort((o1, o2) -> {
            if (o2.getStartTime() == null) {
                return -1;
            }
            if (o2.getStartTime().compareTo(o1.getStartTime()) == 0) {
                return o2.getPageId().compareTo(o1.getPageId());
            }
            return o2.getStartTime().compareTo(o1.getStartTime());
        });
    }

    private String getCurrentKey(Integer pageId) {
        return PAGE_STYLE_CURRENT_KEY + pageId;
    }

    private String getVersionKey(Integer id) {
        return PAGE_STYLE_VERSION_KEY + id;
    }

    private PageRender convertOf(CmsPage source) {
        String shareJson = source.getShareJson();
        List<ShareJsonModelDTO> shareList = null;
        if (source.getShareFlag() == 1 && StringUtils.isNotBlank(shareJson)) {
            shareList = JSON.parseArray(shareJson, ShareJsonModelDTO.class);
        }
        return PageRender.builder()
                .id(source.getPageSchemaId())
                .tagId(source.getTagId() == null ? null : source.getTagId().intValue())
                .platform(source.getPlatform())
                .pageId(source.getId())
                .version(source.getId() + "" + source.getVersion() + "")
                .pageTitle(source.getPageName())
                .backEndTitle(source.getBackEndTitle())
                .aliasTitle(source.getAliasTitle())
                .shareFlag(source.getShareFlag())
                .shareList(shareList)
                .startTime(source.getStartTime())
                .endTime(source.getEndTime())
                .goodsEnable(source.getGoodsEnable())
                .versionEndTime(source.getEndTime())
                .sort(JsonUtil.toJsonString(source.getSort()))
                .build();
    }

    private ContainerRender convertOf(CmsPageContainer source) {
        return ContainerRender.builder()
                .containerId(source.getId())
                .styleContent(source.getPageStyle())
                .containerCode(source.getContainerCode() != null ? source.getContainerCode().getValue() : "")
                .build();
    }
}
