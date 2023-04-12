package com.idanchuang.cms.server.interfaces.controller;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.idanchuang.cms.api.facade.PageRenderFacade;
import com.idanchuang.cms.api.response.ContainerRenderDTO;
import com.idanchuang.cms.api.response.PageRenderDTO;
import com.idanchuang.cms.api.response.ShareJsonModelDTO;
import com.idanchuang.cms.server.application.config.SystemConfig;
import com.idanchuang.cms.server.application.service.GoodsComponentService;
import com.idanchuang.cms.server.applicationNew.service.PageRenderService;
import com.idanchuang.cms.server.domainNew.model.cms.aggregation.render.ContainerRender;
import com.idanchuang.cms.server.domainNew.model.cms.aggregation.render.PageRender;
import com.idanchuang.cms.server.domainNew.model.cms.aggregation.render.PageRenderRepository;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueId;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateShareForm;
import com.idanchuang.component.base.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 页面渲染
 * @author lei.liu
 * @date 2021/9/13
 */
@RestController
@RequestMapping("/cms/page/render")
@Slf4j
public class PageRenderFacadeImpl implements PageRenderFacade {

    @Resource
    private GoodsComponentService goodsComponentService;

    @Resource
    private PageRenderService pageRenderService;

    @Resource
    private PageRenderRepository pageRenderRepository;

    LoadingCache<Integer, PageRenderDTO> loadingCache = Caffeine.newBuilder()
            .maximumSize(10_000)
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .refreshAfterWrite(1, TimeUnit.SECONDS)
            .build(key -> getCurrentStyle(key));

    @Override
    public JsonResult<PageRenderDTO> getInfoById(Integer id) {
        PageRenderDTO pageRenderDTO = loadingCache.get(id);
        return JsonResult.success(pageRenderDTO);
    }

    private PageRenderDTO getCurrentStyle(Integer id) {

        if (id == null || id <= 0) {
            return null;
        }
        PageRender pageRender = pageRenderService.getCurrentPageStyle(new CatalogueId(id));

        return pageRender == null ? null : convertOf(pageRender);
    }

    @Override
    public JsonResult<PageRenderDTO> getInfoByIdAliasTitle(String id) {
        Integer idInt = ifAliasTitle2Id(id);
        return idInt == null ? null : getInfoById(idInt);
    }

    /**
     * 根据页面定义ID，获取所有页面实例
     *
     * @param id 页面定义ID
     * @return 页面实例列表
     */
    @Override
    public JsonResult<List<PageRenderDTO>> getInfoList(Integer id) {

        if (id == null || id <= 0) {
            return null;
        }

        List<PageRender> pageRenders = pageRenderRepository.getPageRenderListById(new CatalogueId(id));
        return JsonResult.success(CollectionUtils.isEmpty(pageRenders) ? null : pageRenders.stream().map(this::convertOf).collect(Collectors.toList()));
    }

    @Override
    public JsonResult<Void> deleteAllPageVersion() {
        pageRenderService.deleteAllPageVersion();
        return JsonResult.success();
    }

    @Override
    public JsonResult<PageRenderDTO> getCachePageVersion(Integer pageId) {
        PageRender pageRender = pageRenderRepository.getPageRenderByCatalogueId(new CatalogueId(pageId));
        return JsonResult.success(pageRender == null ? null : convertOf(pageRender));
    }

    @Override
    public JsonResult<Void> buildAllPageVersion() {
        pageRenderService.buildAllPageVersion();
        return JsonResult.success();
    }

    @Override
    public JsonResult<Void> buildPageVersion(Integer id) {
        pageRenderService.generateCurrentVersion(new CatalogueId(id));
        return JsonResult.success();
    }

    private Integer ifAliasTitle2Id(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        if (!NumberUtils.isCreatable(value)) {
            int index = value.indexOf("_");
            if (index <= 0) {
                return null;
            }
            value = value.substring(index + 1);
            if (!NumberUtils.isCreatable(value)) {
                return null;
            }
        }
        return Integer.parseInt(value);
    }

    private PageRenderDTO convertOf(PageRender source) {

        Long goodsTagId = null;
        if (null != source.getMasterplateId() && null != source.getClientPageId() &&
                SystemConfig.getInstance().getSubjectTagId().equals(source.getClientPageId().getValue())) {
            goodsTagId = goodsComponentService.getGoodsTagBySubjectId(source.getMasterplateId().getValue());
        }

        List<MasterplateShareForm> shareListForm = source.getShareList();

        List<ShareJsonModelDTO> shareList = Lists.newArrayList();

        if (!CollectionUtils.isEmpty(shareListForm)) {
            shareListForm.forEach(p -> {
                ShareJsonModelDTO modelDTO = new ShareJsonModelDTO();
                modelDTO.setShareScope(p.getShareScope());
                modelDTO.setShareTitle(p.getShareTitle());
                modelDTO.setShareImg(p.getShareImg());
                modelDTO.setLevel(p.getLevel());
                modelDTO.setSharePoster(p.getSharePoster());
                modelDTO.setShareDesc(p.getShareDesc());
                shareList.add(modelDTO);
            });
        }

        return PageRenderDTO.builder()
                .id(null != source.getCatalogueId() ? (int)source.getCatalogueId().getValue() : 0)
                .pageId(null != source.getMasterplateId() ? (int)source.getMasterplateId().getValue() : 0)
                .tagId(null != source.getClientPageId() ? (int)source.getClientPageId().getValue() : 0)
                .pageTitle(source.getPageTitle())
                .backEndTitle(source.getBackEndTitle())
                .aliasTitle(source.getAliasTitle())
                .shareFlag(null != source.getShareFlag() ? source.getShareFlag().getVal() : 0)
                .shareList(shareList)
                .startTime(source.getStartTime())
                .endTime(source.getEndTime())
                .version(source.getVersion())
                .platform(null != source.getPlatform() ? source.getPlatform().getDesc() : "")
                .goodsEnable(null != source.getGoodsEnable() ? source.getGoodsEnable().getVal() : 0)
                .containerList(CollectionUtils.isEmpty(source.getContainers()) ? null : source.getContainers().stream().map(this::convertOf).collect(Collectors.toList()))
                .goodsTagId(goodsTagId)
                .build();
    }

    private ContainerRenderDTO convertOf(ContainerRender source) {
        return ContainerRenderDTO.builder()
                .containerId(source.getContainerId().getValue())
                .styleContent(source.getStyleContent())
                .build();
    }

}
