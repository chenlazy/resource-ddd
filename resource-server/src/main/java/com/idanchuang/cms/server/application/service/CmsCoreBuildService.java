package com.idanchuang.cms.server.application.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.idanchuang.abmio.feign.dto.FileInfoDTO;
import com.idanchuang.cms.api.common.constants.ModelNameConstant;
import com.idanchuang.cms.api.common.enums.ModelTypeEnum;
import com.idanchuang.cms.api.model.CmsGoods;
import com.idanchuang.cms.api.model.LuckyComponentInfo;
import com.idanchuang.cms.api.model.TaskComponentInfo;
import com.idanchuang.cms.api.response.CmsGoodsDTO;
import com.idanchuang.cms.api.response.ShareImgDTO;
import com.idanchuang.cms.server.application.config.SystemConfig;
import com.idanchuang.cms.server.application.constant.PageStyleConstant;
import com.idanchuang.cms.server.application.constant.RedisKeyConstant;
import com.idanchuang.cms.server.application.enums.ContainerStatusEnum;
import com.idanchuang.cms.server.application.enums.ErrorEnum;
import com.idanchuang.cms.server.application.enums.PageStatusEnum;
import com.idanchuang.cms.server.application.remote.RemoteFileService;
import com.idanchuang.cms.server.application.remote.RemoteGoodsTagService;
import com.idanchuang.cms.server.application.remote.RemoteSsoService;
import com.idanchuang.cms.server.domain.model.cms.*;
import com.idanchuang.cms.server.domain.model.cms.factory.*;
import com.idanchuang.cms.server.domain.model.cms.schema.SchemaCode;
import com.idanchuang.cms.server.domain.model.cms.schema.SchemaId;
import com.idanchuang.cms.server.domain.repository.CmsPageContainerRepository;
import com.idanchuang.cms.server.domain.repository.CmsPageRepository;
import com.idanchuang.cms.server.domain.repository.CmsPageSchemaRepository;
import com.idanchuang.cms.server.domain.repository.ContainerComponentRepository;
import com.idanchuang.cms.server.infrastructure.transfer.CmsGoodsTransfer;
import com.idanchuang.cms.server.interfaces.app.dto.ContainerDTO;
import com.idanchuang.component.base.exception.core.ExDefinition;
import com.idanchuang.component.base.exception.core.ExType;
import com.idanchuang.component.base.exception.exception.BusinessException;
import com.idanchuang.component.base.page.PageData;
import com.idanchuang.resource.server.infrastructure.utils.CacheUtil;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import com.idanchuang.sso.model.dto.system.UserDetailDTO;
import com.idanchuang.trade.goods.hulk.api.constant.tag.GoodsTagBizTypeEnum;
import com.idanchuang.trade.goods.hulk.api.constant.tag.GoodsTagTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-07 13:42
 * @Desc: cms核心流程服务类
 * @Copyright VTN Limited. All rights reserved.
 */
@Service
@Slf4j
public class CmsCoreBuildService {

    @Resource
    private CmsPageSchemaService pageSchemaService;

    @Resource
    private CmsPageService cmsPageService;

    @Resource
    private ContainerComponentService containerComponentService;

    @Resource
    private PageTagService pageTagService;

    @Resource
    private ComponentGoodsPriceService componentGoodsPriceService;

    @Resource
    private RemoteSsoService remoteSsoService;

    @Resource
    private RemoteFileService remoteFileService;

    @Resource
    private RemoteGoodsTagService remoteGoodsTagService;

    @Resource
    private CacheUtil cacheUtil;

    @Resource
    private PageTemplateService pageTemplateService;

    @Resource
    private ContainerComponentRepository containerComponentRepository;

    @Resource
    private CmsPageContainerRepository pageContainerRepository;

    @Resource
    private CmsPageSchemaRepository pageSchemaRepository;

    @Resource
    private CmsPageRepository cmsPageRepository;


    public Boolean createPageContainer(CmsCoreCreateOrUpdate cmsCoreCreateOrUpdate) {


        return createOrUpdatePageContainer(cmsCoreCreateOrUpdate);
    }

    public Integer createPageSchema(Long operateId) {

        CmsPageSchema cmsPageSchema = PageSchemaFactory.createPageSchema(null, null, operateId.intValue(), 0L,
                PageStatusEnum.PAGE_STATUS_DRAFT.getStatus(),"0",
                "", null, null, null);

        Integer pageSchema = pageSchemaService.createPageSchema(cmsPageSchema);
        if (pageSchema > 0) {
            return cmsPageSchema.getId();
        }
        return 0;
    }


    public PageData<CmsPageSchemaList> pageList(CmsCorePageList corePageList) {
        PageData<CmsPageSchemaList> pageData = pageSchemaService.queryPageSchemaList(corePageList);
        List<CmsPageSchemaList> records = pageData.getRecords();
        if (!CollectionUtils.isEmpty(records)) {
            List<Integer> tagIds = records.stream().map(CmsPageSchemaList::getTagId).collect(Collectors.toList());
            List<PageTag> tags = pageTagService.selectList(tagIds);

            List<Long> operatorIds = records.stream().map(CmsPageSchemaList::getOperatorId).collect(Collectors.toList());
            List<UserDetailDTO> detailDTOS = remoteSsoService.getUsers(operatorIds);
            Map<Long, UserDetailDTO> usersMap = !CollectionUtils.isEmpty(detailDTOS) ?
                    detailDTOS.stream().collect(Collectors.toMap(UserDetailDTO::getId, p -> p, (e1, e2) -> e1)) : null;

            Map<Integer, PageTag> pageTagMap = !CollectionUtils.isEmpty(tags) ?
                    tags.stream().collect(Collectors.toMap(PageTag::getId, p -> p, (e1, e2) -> e1)) : null;

            records.forEach(p -> {
                PageTag pageTag = null != pageTagMap ? pageTagMap.get(p.getTagId()) : null;
                p.setTag(null != pageTag ? pageTag.getName() : "");
                UserDetailDTO detailDTO = null != usersMap ? usersMap.get(p.getOperatorId()) : null;
                p.setOperator(null != detailDTO ? detailDTO.getRealName() : "");
            });
        }
        return pageData;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean removePageContainer(Integer id, Long operator) {

        //查询页面定义信息
        CmsPageSchema pageSchema = pageSchemaService.getPageSchema(id);
        if (null == pageSchema) {
            throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.PAGE_SCHEMA_ERROR.getCode(),
                    ErrorEnum.PAGE_SCHEMA_ERROR.getMsg()));
        }

        //查询所有关联的实例模版进行删除
        List<CmsPage> cmsPageList = cmsPageService.getCmsPageList(id);

        for (CmsPage cmsPage : cmsPageList) {
            //删除页面下的所有实例
            removePageTemplate(cmsPage.getId(), operator, false);
        }

        //删除页面定义
        Boolean removeRes = pageSchemaService.removePageSchema(id, operator);
        // 删除页面渲染数据
        if(removeRes) {
            pageTemplateService.deletePageVersion(id);
        }
        return removeRes;
    }

    public CmsPageDetail getDetailById(Integer id, Integer templateId) {
        CmsPageDetail detail = pageSchemaService.getDetailById(id, templateId);

        if (null == detail) {
            return null;
        }

        //设置具体的图片信息
        List<CmsCoreShareImg> shareImgs = detail.getShareImgs();

        List<Long> imageIds = new ArrayList<>();

        if (!CollectionUtils.isEmpty(shareImgs)) {
            shareImgs.forEach(p -> {
                ShareImgDTO shareImg = p.getShareImg();
                ShareImgDTO sharePoster = p.getSharePoster();
                Long shareImgId = null != shareImg ? shareImg.getId() : 0;
                Long sharePosterId = null != sharePoster ? sharePoster.getId() : 0;
                imageIds.add(shareImgId);
                imageIds.add(sharePosterId);
            });

            Map<Long, FileInfoDTO> infoDTOMap = null;
            List<Long> images = imageIds.stream().filter(p -> p != null && p != 0).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(images)) {
                infoDTOMap = remoteFileService.batchQuery(images);
            }
            for (CmsCoreShareImg cmsCoreShareImg : shareImgs) {
                ShareImgDTO shareImg = cmsCoreShareImg.getShareImg();
                if (null != shareImg && null != infoDTOMap && infoDTOMap.get(shareImg.getId()) != null) {
                    FileInfoDTO fileInfoDTO = infoDTOMap.get(shareImg.getId());
                    shareImg.setFileUrl(null != fileInfoDTO ? fileInfoDTO.getUrl() : "");
                }
                ShareImgDTO sharePoster = cmsCoreShareImg.getSharePoster();
                if (null != sharePoster && null != infoDTOMap &&infoDTOMap.get(sharePoster.getId()) != null) {
                    FileInfoDTO fileInfoDTO = infoDTOMap.get(sharePoster.getId());
                    sharePoster.setFileUrl(null != fileInfoDTO ? fileInfoDTO.getUrl() : "");
                }
            }
        }
        //查询页面实例下的容器信息  支持多容器
        List<String> sort = detail.getSort();
        List<CmsPageContainer> pageContainers = pageContainerRepository.getByIds(sort.stream().map(Long::parseLong).collect(Collectors.toList()));
        if (!CollectionUtils.isEmpty(pageContainers)) {

            //后面逻辑应该收拢到facade todo
            List<ContainerDTO> containerDTOS = pageContainers.stream().map(cmsPageContainer ->
                    new ContainerDTO(cmsPageContainer.getId(),
                            cmsPageContainer.getContainerCode()!= null?cmsPageContainer.getContainerCode().getValue() : "",
                            cmsPageContainer.getContainerName(), cmsPageContainer.getPageStyle())).collect(Collectors.toList());

            detail.setPageStyle(JsonUtil.toJsonString(containerDTOS));
        }
        return detail;
    }

    public PageData<CmsGoodsDTO> listCmsGoods(CmsGoodsList cmsGoodsList) {

        Long componentId = cmsGoodsList.getComponentId();
        Integer pageSize = Optional.ofNullable(cmsGoodsList.getLimit()).orElse(50);
        ContainerComponent component = containerComponentService.selectById(componentId);

        if (null == component) {
            return new PageData<>(Lists.newArrayList(), 1, pageSize, 0);
        }

        Integer modelType = component.getModelType();

        if (ModelTypeEnum.MODEL_TYPE_GOODS.getModelType().equals(modelType)) {
            String bizJson = component.getBizJson();

            try {
                List<CmsGoods> goodsList = JsonUtil.toList(bizJson, new TypeReference<List<CmsGoods>>() {
                });

                List<Long> goodsIds = goodsList.stream().map(CmsGoods::getGoodsId).collect(Collectors.toList());

                if (!CollectionUtils.isEmpty(goodsIds)) {
                    //查询组件下的商品价格信息
                    List<ComponentGoodsPrice> goodsPrices = componentGoodsPriceService.selectListByComponentId(componentId, 1);
                    List<CmsGoodsDTO> cmsGoodsDTOS = convertCmsGoods(componentId, goodsList, goodsPrices);
                    return new PageData<>(cmsGoodsDTOS, 1, pageSize, cmsGoodsDTOS.size());
                }
            } catch (Exception e) {
                log.error("listCmsGoods parse goods error, bizJson:{}, e:{}", bizJson, e);
            }
        }
        return new PageData<>(Lists.newArrayList(), 1, pageSize, 0);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean removePageTemplate(Integer templateId, Long operateId, Boolean needRemind) {

        if (templateId != 0) {

            CmsPageQuery pageQuery = new CmsPageQuery();
            pageQuery.setId(templateId);
            CmsPage cmsPage = cmsPageService.selectCmsPage(pageQuery);

            if (null == cmsPage) {
                throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.PAGE_TEMPLATE_ERROR.getCode(),
                        ErrorEnum.PAGE_TEMPLATE_ERROR.getMsg()));
            }

            //判断是否是最后一个模版，如果是就不允许删除
            int pageCount = cmsPageService.getCmsPageCount(cmsPage.getPageSchemaId());

            if (pageCount <= 1 && needRemind) {
                throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.PAGE_VALID_ERROR.getCode(),
                        ErrorEnum.PAGE_VALID_ERROR.getMsg()));
            }

            //失效快照标签，先失效后删除
            expiredOldTag(templateId);
            //删除快照版本
            cmsPageService.removeCmsPage(templateId, operateId);
            //失效缓存
            pageTemplateService.deletePageVersion(cmsPage.getPageSchemaId(), templateId.toString());
            return true;
        }
        return false;
    }

    /**
     * 发布页面模版
     * @param pageId        模版ID
     * @param startTime     生效时间
     * @param operateId     操作人ID
     * @return  true：成功；false：失败
     */

    public boolean publishPageTemplate(Integer pageId, LocalDateTime startTime, Long operateId) {
        if (pageId != null && pageId != 0) {

            CmsPageQuery pageQuery = new CmsPageQuery();
            pageQuery.setId(pageId);
            CmsPage cmsPage = cmsPageService.selectCmsPage(pageQuery);

            if (null == cmsPage) {
                throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.PAGE_TEMPLATE_ERROR.getCode(),
                        ErrorEnum.PAGE_TEMPLATE_ERROR.getMsg()));
            }

            return cmsPageService.updateStartTimeById(pageId, startTime, operateId);
        }
        return false;
    }

    private List<CmsGoodsDTO> convertCmsGoods(Long componentId, List<CmsGoods> goodsList,
                                              List<ComponentGoodsPrice> goodsPrices) {

        List<CmsGoodsDTO> cmsGoodsDTOS = new ArrayList<>();

        Map<Long, ComponentGoodsPrice> priceMap = !CollectionUtils.isEmpty(goodsPrices) ? goodsPrices.stream()
                .collect(Collectors.toMap(ComponentGoodsPrice::getComponentGoodsId, p -> p, (e1, e2) -> e1)) : Maps.newHashMap();

        for (CmsGoods cmsGoods : goodsList) {
            CmsGoodsDTO cmsGoodsDTO = new CmsGoodsDTO();
            cmsGoodsDTO.setComponentId(componentId);
            cmsGoodsDTO.setGiftImage(cmsGoods.getGiftImage());
            cmsGoodsDTO.setCreateTime(cmsGoods.getCreateTime());
            cmsGoodsDTO.setGoodsImage(cmsGoods.getGoodsImage());
            cmsGoodsDTO.setGoodsImageEnable(cmsGoods.getGoodsImageEnable());
            cmsGoodsDTO.setGoodsName(cmsGoods.getGoodsName());
            cmsGoodsDTO.setGoodsNameEnable(cmsGoods.getGoodsNameEnable());
            cmsGoodsDTO.setGoodsSubTitle(cmsGoods.getGoodsSubTitle());
            cmsGoodsDTO.setGoodsSubTitleEnable(cmsGoods.getGoodsSubTitleEnable());
            cmsGoodsDTO.setGoodsId(cmsGoods.getGoodsId());
            cmsGoodsDTO.setLimitNum(cmsGoods.getLimitNum());
            cmsGoodsDTO.setSubjectGoodsType(cmsGoods.getSubjectGoodsType());
            cmsGoodsDTO.setOperatorId(cmsGoods.getOperatorId());
            String salesPoint = cmsGoods.getSalesPoint();
            if (!StringUtils.isEmpty(salesPoint)) {
                List<String> salesList = JsonUtil.toList(salesPoint, new TypeReference<List<String>>() {});
                cmsGoodsDTO.setSalesPoint(salesList);
            }
            ComponentGoodsPrice goodsPrice = priceMap.get(cmsGoods.getGoodsId());

            if (null != goodsPrice) {
                String priceData = goodsPrice.getPriceData();
                List<PriceData> priceDataList = JsonUtil.toList(priceData, new TypeReference<List<PriceData>>() {
                });
                List<CmsGoodsDTO.PriceData> prices = priceDataList.stream().map(p -> {
                    CmsGoodsDTO.PriceData data = new CmsGoodsDTO.PriceData();
                    data.setAmountPromotionPrice(p.getAmountPromotionPrice());
                    data.setCashValue(p.getCashValue());
                    data.setPointValue(p.getPointValue());
                    data.setPrice(p.getPrice());
                    data.setType(p.getType());
                    return data;
                }).collect(Collectors.toList());

                cmsGoodsDTO.setNormalPrice(prices);
            }
            cmsGoodsDTOS.add(cmsGoodsDTO);
        }
        return cmsGoodsDTOS;
    }

    private List<GoodsInfo> convertGoodsComponent( List<ContainerComponent> goodsComponents) {
        if (CollectionUtils.isEmpty(goodsComponents)) {
            return Lists.newArrayList();
        }

        List<GoodsInfo> cmsGoodsList = new ArrayList<>();

        for (ContainerComponent containerComponent : goodsComponents) {
            String bizJson = containerComponent.getBizJson();

            if (!StringUtils.isEmpty(bizJson)) {
                List<GoodsInfo> goodsInfoList = JsonUtil.toList(bizJson, new TypeReference<List<GoodsInfo>>() {});
                goodsInfoList.forEach(p -> p.setComponentId(containerComponent.getId()));
                cmsGoodsList.addAll(goodsInfoList);
            }

        }

        return cmsGoodsList;
    }

    /**
     * 创建或更新专题页面信息
     * @param coreCreateOrUpdate
     * @return
     */
    public Boolean createOrUpdatePageContainer(CmsCoreCreateOrUpdate coreCreateOrUpdate) {

        //解析组件的json样式
        String pageStyle = coreCreateOrUpdate.getPageStyle();

        //支持多容器方式
        PageContainerData pageContainerData = JsonUtil.toObject(pageStyle,PageContainerData.class);

        if(pageContainerData == null){
            return false;
        }

        List<CmsCompData> cmsCompDates = pageContainerData.getCompDataList();

        if (CollectionUtils.isEmpty(cmsCompDates)) {
            return false;
        }

        long distinctSize = cmsCompDates.stream().map(CmsCompData::getContainerCode).distinct().count();

        if(cmsCompDates.size() > distinctSize){
            throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.CONTAINER_CODE_REPEAT.getCode(),
                    ErrorEnum.CONTAINER_CODE_REPEAT.getMsg()));
        }

        //操作员id
        Integer operatorId = Optional.ofNullable(coreCreateOrUpdate.getOperatorId()).orElse(0);

        //全部商品,商品价格信息
        List<Long> allGoodsIdList = new ArrayList<>();
        List<ComponentGoodsPrice> allPriceList = new ArrayList<>();


        //遍历容器插入
        List<Long> containerIds = Lists.newArrayList();
        for (int i = 0; i < cmsCompDates.size(); i++) {

            //组件信息
            CmsCompData cmsCompData = cmsCompDates.get(i);

            Map<String, Object> metaDataMap = JsonUtil.toMap(pageStyle, Map.class);
            //过滤容器字段
            metaDataMap.remove(PageStyleConstant.COMP_DATA_LIST);


            //获取具体的组件数据
            Object componentData = cmsCompData.getComponentJsonData();
            String dataJson = JsonUtil.toJsonString(componentData);
            List<Object> components = JsonUtil.toList(dataJson, Object.class);


            List<Long> goodsIdList = new ArrayList<>();
            Map<Integer, List<ComponentGoodsPrice>> goodsPricesMap = new HashMap<>();

            //解析组件列表信息
            List<ContainerComponent> containerComponents = getContainerComponents(components, goodsIdList, goodsPricesMap, operatorId);

            allGoodsIdList.addAll(goodsIdList);

            //批量插入容器组件信息
            List<Long> componentIds = containerComponentRepository.insertBatch(containerComponents);

            List<ComponentGoodsPrice> priceList = new ArrayList<>();

            //重新填充组件id到各个模型json
            String metaData = setComponentIdByList(components, priceList, componentIds, metaDataMap, goodsPricesMap);

            allPriceList.addAll(priceList);


            CmsPageContainer cmsPageContainer = ContainerFactory.createContainer(0, cmsCompData.getContainerCode(), ContainerStatusEnum.CONTAINER_STATUS_PUBLISH.getStatus(),
                    metaData, coreCreateOrUpdate.getPageTitle(), operatorId, null);

            pageContainerRepository.createPageContainer(cmsPageContainer);

            //更新组件容器id
            containerComponentRepository.updateContainerId(componentIds, cmsPageContainer.getId(), operatorId);

            containerIds.add(cmsPageContainer.getId());
        }


        //生成页面实例
        CmsPage cmsPage = createCmsPage(coreCreateOrUpdate, operatorId, coreCreateOrUpdate.getId(), containerIds);
        Long tagId = cmsPage.getTagId();

        //定义表版本号更新为最新的快照主键id
        CmsPageSchema pageSchema = pageSchemaService.getPageSchema(coreCreateOrUpdate.getId());
        if (null == pageSchema) {
            throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.PAGE_SCHEMA_ERROR.getCode(),
                    ErrorEnum.PAGE_SCHEMA_ERROR.getMsg()));
        }

        //判断页面code是否冲突
        SchemaCode schemaCode =null;
        if(!StringUtils.isEmpty(coreCreateOrUpdate.getPageCode())){
            schemaCode = new SchemaCode(coreCreateOrUpdate.getPageCode());
            SchemaId schemaId = pageSchemaRepository.getPageSchemaIdByCode(schemaCode);

            if(schemaId != null && schemaId.getValue() != pageSchema.getId()){
                throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.SCHEMA_CODE_EXIST.getCode(),
                        ErrorEnum.SCHEMA_CODE_EXIST.getMsg()));
            }
        }


        //创建页面定义
        String describe = !StringUtils.isEmpty(coreCreateOrUpdate.getBackEndTitle()) ? coreCreateOrUpdate.getBackEndTitle() : "";


        CmsPageSchema cmsPageSchema = PageSchemaFactory.createPageSchema(coreCreateOrUpdate.getId(), schemaCode,
                operatorId, tagId, PageStatusEnum.PAGE_STATUS_PUBLISH.getStatus(), cmsPage.getId().toString(),
                describe, null, null, null);

        pageSchemaRepository.updatePageSchema(cmsPageSchema);

        //更新容器的页面实例id
        pageContainerRepository.updateContainerPage(cmsPage.getId(), operatorId, containerIds);


        //处理额外的商品信息
        dealExtraGoodsInfo(allPriceList, allGoodsIdList, cmsPage, tagId);

        //保存模版配置文件
        pageTemplateService.buildPageVersion(cmsPageSchema.getId());
        return true;
    }

    /**
     * 生成页面实例
     * @param coreCreateOrUpdate
     * @param operatorId
     * @param pageSchemaId
     * @param cmsPageContainerIds
     * @return
     */
    private CmsPage createCmsPage(CmsCoreCreateOrUpdate coreCreateOrUpdate, Integer operatorId,
                                  Integer pageSchemaId, List<Long> cmsPageContainerIds) {

        CmsPage cmsPage = PageFactory.createPage(coreCreateOrUpdate, operatorId, pageSchemaId, cmsPageContainerIds);

        if (coreCreateOrUpdate.getTemplateId() != null) {
            cmsPageRepository.updateCmsPage(cmsPage);
        } else {
            cmsPageRepository.saveCmsPage(cmsPage);
        }
        return cmsPage;
    }


    /**
     * 解析组件列表信息
     * @param components
     * @param allGoodsIdList
     * @param goodsPricesMap
     * @param operatorId
     * @return
     */
    private List<ContainerComponent> getContainerComponents(List<Object> components, List<Long> allGoodsIdList,
                                                            Map<Integer, List<ComponentGoodsPrice>> goodsPricesMap, Integer operatorId) {

        List<ContainerComponent> containerComponents = new ArrayList<>();

        List<ContainerComponent> tabContainerComponents = new ArrayList<>();

        int tabComponentId = components.size();
        for (int m = 0; m < components.size(); m++) {

            Object component = components.get(m);

            //构建组件信息
            ContainerComponent componentInfo = buildComponentInfo(component, allGoodsIdList, goodsPricesMap, operatorId, m);
            containerComponents.add(componentInfo);

            //解析tab
            if (ModelNameConstant.COMP_TAB.equals(componentInfo.getType()) || ModelNameConstant.NEW_COMP_TAB.equals(componentInfo.getType())) {

                String detailJson = JsonUtil.toJsonString(componentInfo.getDetails());
                List<Object> detailList = JsonUtil.toList(detailJson, Object.class);
                if (!CollectionUtils.isEmpty(detailList)) {
                    for (Object obm : detailList) {
                        String obmStr = JsonUtil.toJsonString(obm);

                        //解析每个数组字段
                        Map<String, Object> obmMap = JsonUtil.toMap(obmStr, Map.class);
                        Object list = obmMap.get(PageStyleConstant.LIST);
                        List<Object> tabComponents = JsonUtil.toList(JSONObject.toJSONString(list), Object.class);
                        //创建tab的容器id
                        for (Object tabComponent : tabComponents) {
                            ContainerComponent containerComponent = buildComponentInfo(tabComponent, allGoodsIdList, goodsPricesMap, operatorId, tabComponentId);
                            tabContainerComponents.add(containerComponent);
                            //标记位置自增
                            tabComponentId++;
                        }
                    }
                }
            }
        }

        //添加tab里面的组件信息
        if (!CollectionUtils.isEmpty(tabContainerComponents)) {
            containerComponents.addAll(tabContainerComponents);
        }
        return containerComponents;
    }

    /**
     * 构建组件对象
     * @param component
     * @param allGoodsIdList
     * @param goodsPricesMap
     * @return
     */
    private ContainerComponent buildComponentInfo(Object component, List<Long> allGoodsIdList, Map<Integer,
            List<ComponentGoodsPrice>> goodsPricesMap, Integer operatorId, int m) {

        String compStr = JsonUtil.toJsonString(component);
        //解析每个数组字段
        Map<String, Object> compMap = JsonUtil.toMap(compStr, Map.class);
        String group = (String)compMap.get(PageStyleConstant.COMP_GROUP);
        String type = (String)compMap.get(PageStyleConstant.COMP_TYPE);
        Integer modelType = ModelTypeEnum.getModelType(type);
        Object details = compMap.get(PageStyleConstant.DETAIL);

        Object goodsInfo = compMap.get(PageStyleConstant.COMP_GOODS_INFO);
        String bizJson = "";
        if (null != goodsInfo) {
            //解析商品模型
            bizJson = convertGoodsJson(goodsInfo, allGoodsIdList, goodsPricesMap, m, operatorId, type);
        } else if (ModelNameConstant.COMP_TASK_COMPONENT.equals(type)) {
            //解析任务组件模型
            bizJson = convertTaskCompJson(component);
        } else if (ModelNameConstant.COMP_LUCKY_COMPONENT.equals(type)) {
            //抽奖九宫格组件
            bizJson = convertLuckyCompJson(compMap);
        }

        return ComponentFactory.createComponent(type, modelType, details, group, compStr, bizJson, 0L, operatorId);
    }

    /**
     * 重新设置组件id到组件列表
     * @param components
     * @param allPriceList
     * @param componentIds
     * @param metaDataMap
     * @param goodsPricesMap
     * @return
     */
    private String setComponentIdByList(List<Object> components, List<ComponentGoodsPrice> allPriceList, List<Long> componentIds,
                                        Map<String, Object> metaDataMap, Map<Integer, List<ComponentGoodsPrice>> goodsPricesMap) {

        //重新填充组件id到各个模型json
        List<Object> newComponents = new ArrayList<>();

        int tabComId = components.size();
        for (int i = 0; i < components.size(); i++) {
            Object component = components.get(i);
            Long componentId = componentIds.get(i);

            //给每个组件设置组件id
            Map<String, Object> compMap = getComponentMap(allPriceList, goodsPricesMap, component, componentId, i);
            String type = (String)compMap.get(PageStyleConstant.COMP_TYPE);
            //判断是否是tab类型的组件
            if (ModelNameConstant.COMP_TAB.equals(type) || ModelNameConstant.NEW_COMP_TAB.equals(type)) {
                Object details = compMap.get(PageStyleConstant.DETAIL);
                String detailJson = JsonUtil.toJsonString(details);
                List<Object> detailList = JsonUtil.toList(detailJson, Object.class);

                List<Object> newDetailList = new ArrayList<>();
                if (!CollectionUtils.isEmpty(detailList)) {
                    for (Object obm : detailList) {

                        //解析每个数组字段
                        String obmStr = JsonUtil.toJsonString(obm);
                        Map<String, Object> obmMap = JsonUtil.toMap(obmStr, Map.class);
                        List<Object> tabComponents = JsonUtil.toList(JSONObject.toJSONString(obmMap.get(PageStyleConstant.LIST)), Object.class);

                        List<Object> newTabComponents = new ArrayList<>();
                        for (Object tabComponent : tabComponents) {
                            //获取组件id
                            Long tabCompId = componentIds.get(tabComId);

                            //给每个组件设置组件id
                            Map<String, Object> tabCompMap = getComponentMap(allPriceList, goodsPricesMap, tabComponent, tabCompId, tabComId);
                            String compMapStr = JsonUtil.toJsonString(tabCompMap);
                            Object object = JsonUtil.toObject(compMapStr, Object.class);
                            newTabComponents.add(object);
                            //获取组件标记位置+1
                            tabComId++;
                        }
                        obmMap.put(PageStyleConstant.LIST, newTabComponents);
                        String obmMapStr = JsonUtil.toJsonString(obmMap);
                        Object object = JsonUtil.toObject(obmMapStr, Object.class);
                        newDetailList.add(object);
                    }
                    compMap.put(PageStyleConstant.DETAIL, newDetailList);
                }
            }

            String compMapStr = JsonUtil.toJsonString(compMap);
            Object object = JsonUtil.toObject(compMapStr, Object.class);
            newComponents.add(object);
        }

        //重新设置json对象
        metaDataMap.put(PageStyleConstant.COMPONENT_JSON_DATA, newComponents);
        return JsonUtil.toJsonString(metaDataMap);
    }

    /**
     * 获取对应的map值
     * @param component
     * @param componentId
     * @return
     */
    private Map<String, Object> getComponentMap(List<ComponentGoodsPrice> allPriceList, Map<Integer,
            List<ComponentGoodsPrice>> goodsPricesMap, Object component, Long componentId, int i) {

        //对商品的价格列表设置组件id
        List<ComponentGoodsPrice> priceList = goodsPricesMap.get(i);
        if (!CollectionUtils.isEmpty(priceList)) {
            //设置组件价格id
            priceList.forEach(p -> p.setComponentId(componentId));
            allPriceList.addAll(priceList);
        }

        String compStr = JsonUtil.toJsonString(component);
        //解析每个数组字段
        Map<String, Object> compMap = JsonUtil.toMap(compStr, Map.class);

        //设置组件id信息
        Object goodsInfo = compMap.get(PageStyleConstant.COMP_GOODS_INFO);
        if (null != goodsInfo) {
            GoodsInfoWrapper infoWrapper = JsonUtil.toObject(JsonUtil.toJsonString(goodsInfo), GoodsInfoWrapper.class);
            if (null != infoWrapper) {
                //设置组件id为最新，商品里面的组件id不刷新，提醒时候需要使用
                infoWrapper.setCombineGoodsId(componentId);
            }
            compMap.put(PageStyleConstant.COMP_GOODS_INFO, infoWrapper);
        }

        //设置组件id
        compMap.put(PageStyleConstant.COMPONENT_ID, componentId);

        return compMap;
    }

    private List<ComponentGoodsPrice> convertGoodsPrice(List<GoodsInfo> goodsInfos, Integer operatorId) {

        List<ComponentGoodsPrice> goodsPriceList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(goodsInfos)) {
            for (GoodsInfo goods : goodsInfos) {
                //设置日常价
                if (!CollectionUtils.isEmpty(goods.getNormalPrice())) {
                    ComponentGoodsPrice goodsPrice = GoodsPriceFactory.createGoodsPrice(null, operatorId, goods.getGoodsId(), 1,
                            JsonUtil.toJsonString(goods.getNormalPrice()), null, null, null);
                    goodsPriceList.add(goodsPrice);
                }
            }
        }
        return goodsPriceList;
    }

    private void createGoodsTag(List<Long> goodsList, CmsPage cmsPage, GoodsTagTypeEnum typeEnum, String remark) {

        if (!CollectionUtils.isEmpty(goodsList)) {
            String key = format(RedisKeyConstant.SUBJECT_GOODS_MARK_LIST_KEY, cmsPage.getId());
            cacheUtil.set(key, JsonUtil.toJsonString(goodsList));
            //创建标签
            Long goodsTag = remoteGoodsTagService.createGoodsTag(cmsPage.getPageName(), cmsPage.getStartTime(), cmsPage.getEndTime(), typeEnum, remark);
            if (null != goodsTag) {
                String tagKey = format(RedisKeyConstant.SUBJECT_GOODS_TAG_ID_KEY, cmsPage.getId());
                cacheUtil.set(tagKey, goodsTag.toString());
                //打标
                List<Long> tags = Lists.newArrayList();
                tags.add(goodsTag);
                List<String> goodsListStr = goodsList.stream().map(String::valueOf).collect(Collectors.toList());
                List<List<String>> lists = Lists.partition(goodsListStr, 100);
                lists.parallelStream().forEach(p -> remoteGoodsTagService.bindTag(tags, p, GoodsTagBizTypeEnum.SPU));

                //如果是福三下的猜你喜欢商品，则刷新标签的固定排序
                if (GoodsTagTypeEnum.ON_SALE_WED_TAG.equals(typeEnum)) {
                    remoteGoodsTagService.freshRecommendList(tags);
                }
            } else {
                log.error("createGoodsTag add 创建标签失败 pageShcemaId:{}",cmsPage.getId());
            }
        }
    }

    private String convertGoodsJson(Object goodsInfo,  List<Long> allGoodsIdList, Map<Integer,
            List<ComponentGoodsPrice>> goodsPricesMap, Integer m, Integer operatorId, String type) {

        String goodsInfoStr = null != goodsInfo ? JsonUtil.toJsonString(goodsInfo) : "";
        if (!StringUtils.isEmpty(goodsInfoStr)) {
            GoodsInfoWrapper infoWrapper = JsonUtil.toObject(goodsInfoStr, GoodsInfoWrapper.class);
            List<GoodsInfo> goodsInfoList = null != infoWrapper ? infoWrapper.getGoodsList() : Lists.newArrayList();
            List<Long> goodsIdList = goodsInfoList.parallelStream().map(GoodsInfo::getGoodsId).collect(Collectors.toList());
            allGoodsIdList.addAll(goodsIdList);
            //转化成商品模型类
            List<CmsGoods> cmsGoodsList = CmsGoodsTransfer.convertGoodsInfo(goodsInfoList, type);
            String bizJson = !CollectionUtils.isEmpty(cmsGoodsList) ? JsonUtil.toJsonString(cmsGoodsList) : "";

            //设置日常价格
            List<ComponentGoodsPrice> priceList = convertGoodsPrice(goodsInfoList, operatorId);
            goodsPricesMap.put(m, priceList);
            return bizJson;
        }
        return "";
    }

    private String convertTaskCompJson(Object taskCompInfo) {

        String taskCompStr = null != taskCompInfo ? JsonUtil.toJsonString(taskCompInfo) : "";

        if (!StringUtils.isEmpty(taskCompStr)) {
            TaskComponentInfo componentInfo = JsonUtil.toObject(taskCompStr, TaskComponentInfo.class);
            return JsonUtil.toJsonString(componentInfo);
        }
        return "";
    }

    private String convertLuckyCompJson(Map<String, Object> compMap) {

        String bizJson = "";
        if (compMap.get(PageStyleConstant.LAYOUT_TYPE) != null) {
            String layoutType = (String) compMap.get(PageStyleConstant.LAYOUT_TYPE);
            if (PageStyleConstant.GRID_VIEW.equals(layoutType)) {
                LuckyComponentInfo luckyComponentInfo = new LuckyComponentInfo();
                if (compMap.get(PageStyleConstant.ACTIVITY_ID) != null) {
                    String activityIdStr = (String) compMap.get(PageStyleConstant.ACTIVITY_ID);
                    if (NumberUtils.isCreatable(activityIdStr)) {
                        luckyComponentInfo.setActivityId(Long.parseLong(activityIdStr));
                    }
                }
                bizJson = JSON.toJSONString(luckyComponentInfo);
            }
        }
        return bizJson;
    }

    private void dealExtraGoodsInfo(List<ComponentGoodsPrice> allPriceList, List<Long> allGoodsIdList,
                                    CmsPage cmsPage, Long tagId) {
        //批量插入日常价格信息
        if (!CollectionUtils.isEmpty(allPriceList)) {
            componentGoodsPriceService.batchInsert(allPriceList);
        }

        //如果存在老的商品标签，进行去标
        expiredOldTag(cmsPage.getId());

        //特定标签商品信息打标
        if (!CollectionUtils.isEmpty(allGoodsIdList)) {
            if (SystemConfig.getInstance().getSubjectTagId().equals(tagId)) {
                createGoodsTag(allGoodsIdList, cmsPage, GoodsTagTypeEnum.ON_SALE_WED_TAG, "VTN-福三频道");
            } else if (SystemConfig.getInstance().getSubjectPreferenceTagId().equals(tagId)) {
                createGoodsTag(allGoodsIdList, cmsPage, GoodsTagTypeEnum.SPECIAL_OFFER_TAG, "VTN-特惠团频道");
            } else if (SystemConfig.getInstance().getSubjectNewYearTagId().equals(tagId)) {
                createGoodsTag(allGoodsIdList, cmsPage, GoodsTagTypeEnum.FULL_DRAW_TAG, "VTN-年货节频道");
            } else if (SystemConfig.getInstance().getCelebrationNewYearTagId().equals(tagId)) {
                createGoodsTag(allGoodsIdList, cmsPage, GoodsTagTypeEnum.CELEBRATION_NEW_YEAR, "VTN-庆开年频道");
            }
        }
    }

    private void expiredOldTag(Integer pageId) {

        if (pageId == 0) {
            return;
        }

        CmsPageQuery pageQuery = new CmsPageQuery();
        pageQuery.setId(pageId);
        CmsPage cmsPage = cmsPageService.selectCmsPage(pageQuery);
        if (null != cmsPage) {
            String tagKey = format(RedisKeyConstant.SUBJECT_GOODS_TAG_ID_KEY, cmsPage.getId());
            Long tag = cacheUtil.get(tagKey, Long.class);
            if (null != tag) {
                remoteGoodsTagService.unbindTagAll(Lists.newArrayList(tag), GoodsTagBizTypeEnum.SPU);
            }
        }
    }
}
