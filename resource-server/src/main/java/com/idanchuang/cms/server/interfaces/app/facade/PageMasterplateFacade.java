package com.idanchuang.cms.server.interfaces.app.facade;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.idanchuang.abmio.feign.dto.FileInfoDTO;
import com.idanchuang.cms.server.application.config.SystemConfig;
import com.idanchuang.cms.server.application.remote.RemoteFileService;
import com.idanchuang.cms.server.application.remote.RemoteUserRoleService;
import com.idanchuang.cms.server.applicationNew.service.PageRenderService;
import com.idanchuang.cms.server.domainNew.model.cms.aggregation.render.PageRender;
import com.idanchuang.cms.server.domainNew.model.cms.aggregation.render.PageRenderRepository;
import com.idanchuang.cms.server.domainNew.model.cms.aggregation.render.PageRenderStatus;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.Catalogue;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueId;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueRepository;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueType;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.PageCode;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateShareForm;
import com.idanchuang.cms.server.domainNew.shard.PlatformCode;
import com.idanchuang.cms.server.infrastructure.adcontentservice.util.DateTimeUtils;
import com.idanchuang.cms.server.interfaces.app.dto.PageRenderDTO;
import com.idanchuang.cms.server.interfaces.app.dto.PageVersionDiffForm;
import com.idanchuang.cms.server.interfaces.app.facade.assembler.PageMasterplateAssembler;
import com.idanchuang.cms.server.interfaces.web.config.GatewayUserDTO;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.component.core.Dc;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-01-04 10:27
 * @Desc: 页面模版对c接口
 * @Copyright VTN Limited. All rights reserved.
 */
@Component
public class PageMasterplateFacade {

    @Resource
    private CatalogueRepository catalogueRepository;

    @Resource
    private PageRenderRepository pageRenderRepository;

    @Resource
    private PageRenderService pageRenderService;

    @Resource
    private RemoteUserRoleService remoteUserRoleService;

    @Resource
    private RemoteFileService remoteFileService;

    /**
     * c端获取页面详情
     * @param pageVersionDiffForm
     * @param device
     * @param appVersion
     * @return
     */
    public JsonResult<PageRenderDTO> diff(PageVersionDiffForm pageVersionDiffForm, String device, String appVersion, GatewayUserDTO userDTO) {

        PageRender pageRender = null;

        if (StringUtils.isEmpty(pageVersionDiffForm.getPageCode()) && StringUtils.isEmpty(pageVersionDiffForm.getAliasTitle())) {
            return JsonResult.success(PageRenderDTO.builder().status(PageRenderStatus.PAGE_STATUS_DEFAULT.getVal()).build());
        }
        LocalDateTime previewTime = LocalDateTime.now();

        CatalogueId catalogueId = null;
        if (StringUtils.isNotEmpty(pageVersionDiffForm.getPageCode())) {
            Catalogue catalogue = catalogueRepository.getCatalogueByCode(new PageCode(pageVersionDiffForm.getPageCode()), CatalogueType.CLIENT_PAGE);
            if (null == catalogue) {
                return JsonResult.success(PageRenderDTO.builder().status(PageRenderStatus.PAGE_STATUS_DEFAULT.getVal()).build());
            }
            catalogueId = catalogue.getId();
        }

        if (StringUtils.isNotBlank(pageVersionDiffForm.getAliasTitle())) {
            Integer id = pageVersionDiffForm.aliasTitleToId(pageVersionDiffForm.getAliasTitle());
            catalogueId = null != id && id > 0 ? new CatalogueId(id) : null;
        }

        //时光机逻辑
        if (Dc.isTimeTraveller()) {

            previewTime = Dc.nowLocalDateTime();

            //获取页面详情列表
            List<PageRender> pageRenderList;
            if (StringUtils.isNotEmpty(pageVersionDiffForm.getPageCode())) {
                //客户端获取
                pageRenderList = pageRenderRepository.getPageRenderListByPageCode(new PageCode(pageVersionDiffForm.getPageCode()), CatalogueType.CLIENT_PAGE);
            } else {
                //专题页获取
                pageRenderList = pageRenderRepository.getPageRenderListById(catalogueId);
            }

            if (CollectionUtils.isNotEmpty(pageRenderList)) {

                //过滤出预览时间生效的模版
                LocalDateTime finalPreviewTime = previewTime;
                pageRenderList = pageRenderList.stream().filter(e -> (e.getStartTime() == null || e.getStartTime().compareTo(finalPreviewTime) <= 0)
                        && (e.getEndTime() == null || e.getEndTime().compareTo(finalPreviewTime) > 0)).collect(Collectors.toList());

                //对模版列表进行排序
                if (CollectionUtils.isNotEmpty(pageRenderList)) {
                    pageRenderService.sortRenders(pageRenderList);
                    pageRender = pageRenderList.get(0);
                }
            }
        } else {
            //降级逻辑
            if (SystemConfig.getInstance().checkDegrade(device, appVersion)) {
                return JsonResult.success(PageRenderDTO.degrade());
            }

            pageRender = pageRenderService.getCurrentStyleByCache(catalogueId);
        }

        //如果不存在当前生效中的模版
        if (null == pageRender) {
            //校验当前缓存里面保持的模版状态
            PageRender pageCache = pageRenderRepository.getPageRenderByCatalogueId(catalogueId);

            if (null == pageCache) {
                return JsonResult.success(PageRenderDTO.builder().status(PageRenderStatus.PAGE_STATUS_DEFAULT.getVal()).userIdCode(userDTO.getIdCode()).build());
            }

            //记录开始时间和页面标题
            String pageTitle = pageCache.getPageTitle();
            String platform = PlatformCode.simplyPlatform(pageCache.getPlatform());
            String startTime = DateTimeUtils.localDateTime2String(pageCache.getStartTime(), "yyyy.MM.dd HH:mm:ss");
            String endTime = DateTimeUtils.localDateTime2String(pageCache.getEndTime(), "yyyy.MM.dd HH:mm:ss");
            if (pageCache.isOver()) {
                return JsonResult.success(PageRenderDTO.builder().status(PageRenderStatus.PAGE_STATUS_END.getVal())
                        .pageTitle(pageTitle).userIdCode(userDTO.getIdCode()).platform(platform).endTime(endTime).build());
            } else {
                return JsonResult.success(PageRenderDTO.builder().status(PageRenderStatus.PAGE_STATUS_NO_START.getVal())
                        .pageTitle(pageTitle).userIdCode(userDTO.getIdCode()).platform(platform).startTime(startTime).build());
            }
        }

        //返回页面相关的信息
        MasterplateShareForm shareForm = pageRender.getShareInfoByLevel(userDTO.getBrandProviderLevel());
        Map<Long, FileInfoDTO> imageMap = new HashMap<>();
        if (null != shareForm) {
            imageMap = remoteFileService.batchQuery(Lists.newArrayList(shareForm.getShareImg(), shareForm.getSharePoster()));
        }
        //获取用户角色相关信息
        Integer userRole = null;
        if (null != pageRender.getPlatform() && PlatformCode.ABM.getDesc().equals(pageRender.getPlatform().getDesc())) {
            userRole = remoteUserRoleService.getAbmLevelByIdCode(userDTO.getIdCode().longValue(), true);
            userRole = userRole == null || userRole == -1 ? 1 : userRole;
        }

        return JsonResult.success(PageMasterplateAssembler.domainToDto(pageRender, userDTO, userRole, shareForm,
                imageMap, previewTime, pageVersionDiffForm.getCurrentVersion()));
    }


    /**
     * 获取分享信息
     * @param catalogueId
     * @param userDTO
     * @return
     */
    public JsonResult<PageRenderDTO> share(Integer catalogueId, GatewayUserDTO userDTO) {

        if (null == catalogueId || catalogueId <= 0) {
            return JsonResult.success(null);
        }

        PageRender pageRender = pageRenderService.getCurrentStyleByCache(new CatalogueId(catalogueId));

        if (null == pageRender) {
            return JsonResult.success(null);
        }

        //获取用户角色相关信息
        Integer userRole = null;
        if (PlatformCode.ABM.getDesc().equals(pageRender.getPlatform().getDesc())) {
            userRole = remoteUserRoleService.getAbmLevelByIdCode(userDTO.getIdCode().longValue(), true);
            userRole = userRole == null || userRole == -1 ? 1 : userRole;
        }

        //返回h5页面相关的信息
        MasterplateShareForm shareForm = pageRender.getShareInfoByLevel(userDTO.getBrandProviderLevel());
        Map<Long, FileInfoDTO> imageMap = Maps.newHashMap();
        if (null != shareForm) {
            imageMap = remoteFileService.batchQuery(Lists.newArrayList(shareForm.getShareImg(), shareForm.getSharePoster()));
        }
        PageRenderDTO pageRenderDTO = PageMasterplateAssembler.domainToDto(pageRender, userDTO, userRole, shareForm, imageMap, LocalDateTime.now(), "");
        //清空容器数组
        if (null != pageRenderDTO) {
            pageRenderDTO.setContainers(Lists.newArrayList());
        }
        return JsonResult.success(pageRenderDTO);
    }
}
