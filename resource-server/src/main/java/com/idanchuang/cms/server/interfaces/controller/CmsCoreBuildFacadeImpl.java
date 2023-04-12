package com.idanchuang.cms.server.interfaces.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.idanchuang.abmio.feign.dto.FileInfoDTO;
import com.idanchuang.cms.api.facade.CmsCoreBuildFacade;
import com.idanchuang.cms.api.request.CmsCorePageReq;
import com.idanchuang.cms.api.request.CmsCoreUpdateReq;
import com.idanchuang.cms.api.request.CmsPageTemplateReq;
import com.idanchuang.cms.api.response.CmsPageDetailDTO;
import com.idanchuang.cms.api.response.CmsPageListDTO;
import com.idanchuang.cms.api.response.CmsPageTemplateDTO;
import com.idanchuang.cms.api.response.PageVersionDTO;
import com.idanchuang.cms.api.response.SelectInfoDTO;
import com.idanchuang.cms.server.application.constant.PageStyleConstant;
import com.idanchuang.cms.server.application.constant.RedisKeyConstant;
import com.idanchuang.cms.server.application.enums.ErrorEnum;
import com.idanchuang.cms.server.application.remote.RemoteFileService;
import com.idanchuang.cms.server.application.remote.RemoteSsoService;
import com.idanchuang.cms.server.applicationNew.service.CatalogueService;
import com.idanchuang.cms.server.applicationNew.service.MasterplateService;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.Catalogue;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueId;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueRepository;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPage;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPageId;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPageQueryForm;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPageRepository;
import com.idanchuang.cms.server.domainNew.model.cms.container.Container;
import com.idanchuang.cms.server.domainNew.model.cms.container.ContainerRepository;
import com.idanchuang.cms.server.domainNew.model.cms.external.selectInfo.SelectTypeEnum;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.Masterplate;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateId;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateRepository;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateShareForm;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateUpsertForm;
import com.idanchuang.cms.server.domainNew.shard.OperatorId;
import com.idanchuang.cms.server.interfaces.assember.CmsCoreBuildDtoAssembler;
import com.idanchuang.cms.server.interfaces.assember.InterfaceCatalogueAssembler;
import com.idanchuang.cms.server.interfaces.assember.InterfaceMasterplateAssembler;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.component.base.exception.core.ExDefinition;
import com.idanchuang.component.base.exception.core.ExType;
import com.idanchuang.component.base.exception.exception.BusinessException;
import com.idanchuang.component.base.page.PageData;
import com.idanchuang.resource.server.infrastructure.utils.CacheUtil;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import com.idanchuang.sso.model.dto.system.UserDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-07 10:15
 * @Desc: cms核心流程控制类
 * @Copyright VTN Limited. All rights reserved.
 */
@RestController
@Slf4j
public class CmsCoreBuildFacadeImpl implements CmsCoreBuildFacade {

    @Resource
    private MasterplateRepository masterplateRepository;

    @Resource
    private RemoteSsoService remoteSsoService;

    @Resource
    private CatalogueRepository catalogueRepository;

    @Resource
    private ClientPageRepository clientPageRepository;

    @Resource
    private ContainerRepository containerRepository;

    @Resource
    private MasterplateService masterplateService;

    @Resource
    private CatalogueService catalogueService;

    @Resource
    private RemoteFileService remoteFileService;

    @Resource
    private CacheUtil cacheUtil;

    @Override
    public JsonResult<PageData<CmsPageListDTO>> catalogueList(CmsCorePageReq cmsCorePageReq) {

        PageData<Catalogue> pageData = catalogueRepository.queryCatalogueByPage(InterfaceCatalogueAssembler.dtoToDomain(cmsCorePageReq));

        if (CollectionUtils.isEmpty(pageData.getRecords())) {
            return JsonResult.success(new PageData<>());
        }

        //获取页面名称
        List<ClientPageId> clientPageIds = pageData.getRecords().stream().filter(p -> p.getPageId() != null &&  p.getPageId().getValue() > 0)
                .map(Catalogue::getPageId).collect(Collectors.toList());
        ClientPageQueryForm queryForm = new ClientPageQueryForm();
        queryForm.setIds(clientPageIds);
        List<ClientPage> clientPages = clientPageRepository.list(queryForm);
        Map<Long, ClientPage> clientPageMap = CollectionUtils.isNotEmpty(clientPages) ? clientPages.stream()
                .collect(Collectors.toMap(p -> p.getId().getValue(), p -> p, (e1, e2) -> e1)) : null;

        //获取用户信息
        List<Long> operatorIds = pageData.getRecords().stream().map(p -> p.getOperatorId().getValue()).collect(Collectors.toList());
        List<UserDetailDTO> detailDTOS = remoteSsoService.getUsers(operatorIds);
        Map<Long, UserDetailDTO> usersMap = !CollectionUtils.isEmpty(detailDTOS) ?
                detailDTOS.stream().collect(Collectors.toMap(UserDetailDTO::getId, p -> p, (e1, e2) -> e1)) : null;

        //获取当前目录下的模版信息
        List<CatalogueId> catalogueIds = pageData.getRecords().stream().map(Catalogue::getId).collect(Collectors.toList());
        List<Masterplate> masterplateList = masterplateRepository.getBatchMasterplate(catalogueIds);
        Map<CatalogueId, List<Masterplate>> catalogueMap = masterplateList.stream().collect(Collectors.groupingBy(Masterplate::getCatalogueId));

        List<CmsPageListDTO> pageListDTOS = pageData.getRecords().stream().map(p -> InterfaceCatalogueAssembler.entityToDTO(p, clientPageMap, catalogueMap, usersMap)).collect(Collectors.toList());

        return JsonResult.success(new PageData<>(pageListDTOS, pageData.getCurrent(), pageData.getSize(), pageData.getTotal()));
    }

    @Override
    public JsonResult<Integer> createCatalogue(Long operateId, Long tagId) {

        if (null == tagId || tagId == 0) {
            return JsonResult.success(0);
        }
        CatalogueId catalogue = catalogueService.createCatalogue(new OperatorId(operateId), new ClientPageId(tagId));
        return JsonResult.success(null != catalogue ? (int) catalogue.getValue() : 0);
    }

    @Override
    public JsonResult<Boolean> upsertCatalogue(@Valid CmsCoreUpdateReq coreUpdateReq) {
        MasterplateUpsertForm upsertForm = InterfaceMasterplateAssembler.dtoToDomain(coreUpdateReq);
        masterplateService.upsertMasterplate(upsertForm);
        return JsonResult.success(true);
    }

    @Override
    public JsonResult<Boolean> removeCatalogue(Integer id, Long operateId) {
        catalogueService.removeCatalogue(new CatalogueId(id), new OperatorId(operateId));
        return JsonResult.success(true);
    }

    @Override
    public JsonResult<CmsPageDetailDTO> getMasterplateDetail(Integer catalogueId, Integer masterplateId, Integer pageTempId) {

        if (null == catalogueId) {
            throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.CATALOGUE_ID_EXIST_ERROR.getCode(),
                    ErrorEnum.CATALOGUE_ID_EXIST_ERROR.getMsg()));
        }

        //获取目录详情
        Catalogue catalogue = catalogueRepository.getCatalogueById(new CatalogueId(catalogueId));

        Catalogue newCatalogue = null;
        if (null != pageTempId) {
            newCatalogue = catalogueRepository.getCatalogueById(new CatalogueId(pageTempId));
            if (null == newCatalogue) {
                throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.CATALOGUE_EXIST_ERROR.getCode(),
                        ErrorEnum.CATALOGUE_EXIST_ERROR.getMsg()));
            }
        }

        if (null == catalogue) {
            throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.CATALOGUE_EXIST_ERROR.getCode(),
                    ErrorEnum.CATALOGUE_EXIST_ERROR.getMsg()));
        }

        ClientPage clientPage = clientPageRepository.query(null != newCatalogue ? newCatalogue.getPageId() : catalogue.getPageId());

        if (null == clientPage) {
            throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.CLIENT_PAGE_NOT_EXIST_ERROR.getCode(),
                    ErrorEnum.CLIENT_PAGE_NOT_EXIST_ERROR.getMsg()));
        }

        //如果模版id为0，则获取优先展示的模版信息
        Masterplate masterplate;
        if (null == masterplateId) {
            List<Masterplate> masterplateList = masterplateRepository.getMasterplateList(new CatalogueId(catalogueId));
            masterplate = catalogue.fetchPriorityMasterplate(masterplateList);
        } else {
            masterplate = masterplateRepository.getMasterplateById(new MasterplateId(masterplateId));
        }

        if (null == masterplate) {
            CmsPageDetailDTO detailDTO = new CmsPageDetailDTO();
            detailDTO.setTagId(clientPage.getId().getValue());
            detailDTO.setTagName(clientPage.getName());
            detailDTO.setPageCode(null != clientPage.getPageCode() ? clientPage.getPageCode().getValue() : "");
            return JsonResult.success(detailDTO);
        }

        if (masterplate.getCatalogueId().getValue() != catalogueId) {
            throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.PAGE_TEMPLATE_ERROR.getCode(),
                    ErrorEnum.PAGE_TEMPLATE_ERROR.getMsg()));
        }

        //获取模版下的容器列表
        List<Container> containers = containerRepository.queryContainerList(masterplate.getId());

        //获取分享信息的图片id集合
        List<MasterplateShareForm> shareForms = masterplate.getShareForms();

        List<Long> imageIds = new ArrayList<>();

        //图片id列表
        if (CollectionUtils.isNotEmpty(shareForms)) {
            shareForms.forEach(p -> {
                Long shareImg = p.getShareImg();
                Long sharePoster = p.getSharePoster();
                imageIds.add(shareImg);
                imageIds.add(sharePoster);
            });
        }

        //获取图片map信息
        List<Long> imageIdList = imageIds.stream().filter(p -> p != null && p != 0).collect(Collectors.toList());

        Map<Long, FileInfoDTO> imageMap = null;
        if (CollectionUtils.isNotEmpty(imageIdList)) {
            imageMap = remoteFileService.batchQuery(imageIdList);
        }

        List<SelectInfoDTO> selectInfo = this.getSelectInfo(masterplate.getId());
        CmsPageDetailDTO detailDTO = InterfaceMasterplateAssembler.domainToDto(catalogue, clientPage, masterplate, containers, imageMap, selectInfo);
        return JsonResult.success(detailDTO);
    }

    private List<SelectInfoDTO> getSelectInfo(MasterplateId id) {
        List<SelectInfoDTO> selectInfoDTOList = Lists.newArrayList();
        long pageId = id.getValue();
        SelectTypeEnum[] values = SelectTypeEnum.values();
        if (values == null || values.length <= 0) {
            return selectInfoDTOList;
        }
        for (SelectTypeEnum v : values) {
            String pageKey = format(RedisKeyConstant.SUBJECT_PAGE_TO_SELECT, v.getModelType(), pageId);
            String forString = cacheUtil.getForString(pageKey);
            List<Integer> list = StringUtils.isEmpty(forString) ? Lists.newArrayList() : JsonUtil.toList(forString, new TypeReference<List<Integer>>() {
            });
            if (!CollectionUtils.isEmpty(list)) {
                List<SelectInfoDTO> collect = list.stream().map(u -> {
                    SelectInfoDTO selectInfoDTO = new SelectInfoDTO();
                    selectInfoDTO.setSelectType(v.getModelType());
                    selectInfoDTO.setSelectId(u);
                    return selectInfoDTO;
                }).collect(Collectors.toList());
                selectInfoDTOList.addAll(collect);
            }
        }
        return selectInfoDTOList;
    }

    @Override
    public JsonResult<PageData<CmsPageTemplateDTO>> listPageTemplate(CmsPageTemplateReq cmsPageTemplateReq) {


        PageData<Masterplate> pageData = masterplateRepository.queryMasterplateByPage(InterfaceMasterplateAssembler.dtoToDomain(cmsPageTemplateReq));


        if (CollectionUtils.isEmpty(pageData.getRecords())) {
            return JsonResult.success(new PageData<>());
        }
        List<Long> userIds = Lists.newArrayList();
        List<Long> operatorIds = pageData.getRecords().stream().map(p -> p.getOperatorId().getValue()).collect(Collectors.toList());
        List<Long> createIds = pageData.getRecords().stream().map(p -> p.getCreateId().getValue()).collect(Collectors.toList());
        userIds.addAll(operatorIds);
        userIds.addAll(createIds);
        List<UserDetailDTO> detailDTOS = remoteSsoService.getUsers(userIds);
        Map<Long, UserDetailDTO> usersMap = CollectionUtils.isNotEmpty(detailDTOS) ? detailDTOS.stream()
                .collect(Collectors.toMap(UserDetailDTO::getId, p -> p, (e1, e2) -> e1)) : null;

        List<CmsPageTemplateDTO> templateDTOS = pageData.getRecords().stream().map(masterplate -> InterfaceMasterplateAssembler.entityToDTO(masterplate, usersMap)).collect(Collectors.toList());
        return JsonResult.success(new PageData<>(templateDTOS, pageData.getCurrent(), pageData.getSize(), pageData.getTotal()));
    }

    @Override
    public JsonResult<Boolean> removePageTemplate(Integer templateId, Long operateId) {
        masterplateService.removeMasterplate(new MasterplateId(templateId), new OperatorId(operateId), true);
        return JsonResult.success(true);
    }

    /**
     * 发布页面模版
     *
     * @param templateId 模版ID
     * @param operateId  操作人ID
     * @return true：成功；false：失败
     */
    @Override
    public JsonResult<Boolean> publishPageTemplate(Integer templateId, Long operateId) {
        masterplateService.publishMasterplate(new MasterplateId(templateId), LocalDateTime.now(), new OperatorId(operateId));
        return JsonResult.success(true);
    }

    @Override
    public JsonResult<Boolean> editPageTemplate(@Valid CmsCoreUpdateReq coreUpdateReq) {
        MasterplateUpsertForm upsertForm = InterfaceMasterplateAssembler.dtoToDomain(coreUpdateReq);
        masterplateService.upsertMasterplate(upsertForm);
        return JsonResult.success(true);
    }


    @Override
    public JsonResult<Boolean> change(Long id) {
        return JsonResult.success(catalogueService.changeType(new CatalogueId(id)));
    }

    @Override
    public JsonResult<List<PageVersionDTO>> historyTemplateList(Integer templateId, Integer containCurrent) {

        Masterplate masterplate = checkTemplateParam(templateId);

        //查询当前模版的历史快照数据
        List<Masterplate> masterplateSnapList = masterplateRepository.getMasterplateSnapList(masterplate.getId());


        if (CollectionUtils.isEmpty(masterplateSnapList) || masterplateSnapList.size() <= 1) {
            throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.HISTORY_PAGE_DATA_NOT_EXIST.getCode(),
                    ErrorEnum.HISTORY_PAGE_DATA_NOT_EXIST.getMsg()));
        }

        //设置模版快照版本号
        Integer version = masterplate.getVersion();
        for (Masterplate snapMasterplate : masterplateSnapList) {
            if (version >= 0) {
                snapMasterplate.setVersion(version);
            } else {
                snapMasterplate.setVersion(0);
            }
            version--;
        }


        //删除当前版本的快照数据
        if (null == containCurrent || containCurrent != 1) {
            masterplateSnapList.remove(0);
        }

        List<PageVersionDTO> pageVersionDTOS = masterplateSnapList.stream().map(CmsCoreBuildDtoAssembler::entityToDTO).collect(Collectors.toList());

        if (null != containCurrent && containCurrent == 1) {
            PageVersionDTO versionDTO = pageVersionDTOS.get(0);
            versionDTO.setCreateTime("current");
        }

        return JsonResult.success(pageVersionDTOS);
    }

    @Override
    public JsonResult<String> querySnapTemplate(Integer templateId) {

        //校验参数
        Masterplate masterplate = checkTemplateParam(templateId);

        //获取快照容器数据
        List<Container> oldContainerList = containerRepository.querySnapContainerList(masterplate.getId());

        if (CollectionUtils.isEmpty(oldContainerList)) {
            return JsonResult.success();
        }

        //获取页面配置信息
        Container container = oldContainerList.get(0);
        String pageStyle = container.getPageStyle();

        Map<String, Object> metaDataMap = JsonUtil.toMap(pageStyle, Map.class);
        //过滤容器字段
        metaDataMap.remove(PageStyleConstant.COMPONENT_JSON_DATA);

        List<JSONObject> containerStyles = new ArrayList<>();
        oldContainerList.forEach(p -> {
            JSONObject containerStyle = new JSONObject();
            Map<String, Object> containerMap = JsonUtil.toMap(p.getPageStyle(), Map.class);
            containerStyle.put(PageStyleConstant.CONTAINER_ID, p.getId().getValue());
            containerStyle.put(PageStyleConstant.CONTAINER_CODE, null != p.getContainerCode() ? p.getContainerCode().getValue() : "");
            containerStyle.put(PageStyleConstant.CONTAINER_NAME, p.getContainerName());
            containerStyle.put(PageStyleConstant.COMPONENT_JSON_DATA, containerMap.get(PageStyleConstant.COMPONENT_JSON_DATA));
            containerStyles.add(containerStyle);
        });

        metaDataMap.put(PageStyleConstant.COMP_DATA_LIST, containerStyles);

        return JsonResult.success(JsonUtil.toJsonString(metaDataMap));
    }

    private Masterplate checkTemplateParam(Integer templateId) {

        if (null == templateId || templateId < 0) {
            throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.BAD_REQUEST.getCode(),
                    ErrorEnum.BAD_REQUEST.getMsg()));
        }

        //记录想要查找的历史模版id
        Masterplate masterplate = masterplateRepository.getMasterplateById(new MasterplateId(templateId));

        if (null == masterplate) {
            throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.PAGE_TEMPLATE_ERROR.getCode(),
                    ErrorEnum.PAGE_TEMPLATE_ERROR.getMsg()));
        }

        return masterplate;
    }
}
