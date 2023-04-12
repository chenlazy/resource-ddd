package com.idanchuang.cms.server.domainNew.model.cms.component;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.idanchuang.cms.api.common.constants.ModelNameConstant;
import com.idanchuang.cms.api.model.CmsGoods;
import com.idanchuang.cms.api.model.LuckyComponentInfo;
import com.idanchuang.cms.api.model.TaskComponentInfo;
import com.idanchuang.cms.server.application.constant.PageStyleConstant;
import com.idanchuang.cms.server.application.enums.ErrorEnum;
import com.idanchuang.cms.server.application.remote.RemoteCircleGoodsService;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.PageCode;
import com.idanchuang.cms.server.domainNew.model.cms.container.Container;
import com.idanchuang.cms.server.domainNew.model.cms.container.ContainerId;
import com.idanchuang.cms.server.domainNew.model.cms.external.equity.EquityType;
import com.idanchuang.cms.server.domainNew.model.cms.external.goodsPrice.GoodsId;
import com.idanchuang.cms.server.domainNew.model.cms.external.goodsPrice.GoodsPrice;
import com.idanchuang.cms.server.domainNew.model.cms.external.goodsPrice.GoodsPriceFactory;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateId;
import com.idanchuang.cms.server.domainNew.model.cms.number.SequenceNumberId;
import com.idanchuang.cms.server.domainNew.model.cms.external.equity.EquityKey;
import com.idanchuang.cms.server.domainNew.shard.OperatorId;
import com.idanchuang.cms.server.domainNew.shard.parse.GoodsDetail;
import com.idanchuang.cms.server.domainNew.shard.parse.GoodsDetailWrapper;
import com.idanchuang.component.base.exception.core.ExDefinition;
import com.idanchuang.component.base.exception.core.ExType;
import com.idanchuang.component.base.exception.exception.BusinessException;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-21 17:05
 * @Desc: 组件工厂类
 * @Copyright VTN Limited. All rights reserved.
 */
@Slf4j
@org.springframework.stereotype.Component
public class ComponentFactory {

    private static RemoteCircleGoodsService remoteCircleGoodsService;

    private ComponentFactory(RemoteCircleGoodsService remoteCircleGoodsService) {
        ComponentFactory.remoteCircleGoodsService = remoteCircleGoodsService;
    }

    /**
     * 生成组件信息
     * @param component
     * @param containerId
     * @param pageCode
     * @param masterplateId
     * @param operatorId
     * @param goodsIds
     * @param goodsPrices
     * @param equityIdMap
     * @return
     */
    public static Component createComponent(Object component, ContainerId containerId, PageCode pageCode,
                                            MasterplateId masterplateId, OperatorId operatorId,
                                            List<GoodsId> goodsIds, List<GoodsPrice> goodsPrices,
                                            Map<EquityKey, List<GoodsId>> equityIdMap) {

        String compStr = JsonUtil.toJsonString(component);

        //解析组件字段
        Map<String, Object> compMap = JsonUtil.toMap(compStr, Map.class);
        String type = (String) compMap.get(PageStyleConstant.COMP_TYPE);
        Object goodsDetail = compMap.get(PageStyleConstant.COMP_GOODS_INFO);
        Object componentIdObj = compMap.get(PageStyleConstant.COMPONENT_ID);
        //判断组件id是否存在
        if (null == componentIdObj || (Long)componentIdObj <= 0) {
            throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.COMPONENT_ID_NOT_EXIST_ERROR.getCode(),
                    ErrorEnum.COMPONENT_ID_NOT_EXIST_ERROR.getMsg()));
        }

        EquityKey equityKey = null;
        //拼接权益key
        if (ModelNameConstant.EQUITY_GOODS.equals(type)) {
            Long equityId = compMap.get(PageStyleConstant.EQUITY_ID) != null ? Long.parseLong(String.valueOf(compMap.get(PageStyleConstant.EQUITY_ID))) : null;
            Integer equityType = compMap.get(PageStyleConstant.LAYOUT_TYPE) != null ? Integer.parseInt(String.valueOf(compMap.get(PageStyleConstant.LAYOUT_TYPE))) : EquityType.EQUITY_ACTIVITY.getVal();

            if (null != equityId) {
                equityKey = new EquityKey();
                equityKey.setEquityId(equityId);
                equityKey.setEquityType(equityType);
            }
        }
        ComponentId componentId = new ComponentId((Long)componentIdObj);

        String bizJson = "";
        if (null != goodsDetail) {
            //解析商品模型
            bizJson = convertGoodsDetail(goodsDetail, goodsIds, goodsPrices, operatorId, type, componentId, equityIdMap, equityKey);
        } else if (ModelNameConstant.COMP_TASK_COMPONENT.equals(type)) {
            //解析任务组件
            bizJson = convertTaskComp(component);
        } else if (ModelNameConstant.COMP_LUCKY_COMPONENT.equals(type)) {
            //抽奖九宫格组件
            bizJson = convertLuckyComp(compMap);
        }

        String group = (String) compMap.get(PageStyleConstant.COMP_GROUP);
        ComponentBusinessType businessType = ComponentBusinessType.getBusinessType(type);
        ComponentType componentType = PageStyleConstant.GROUP_BASE_TYPE.equals(group) ? ComponentType.BASE_TYPE : ComponentType.BUSINESS_TYPE;

        return new Component(componentId, containerId, pageCode, masterplateId, componentType, businessType, bizJson, operatorId, 0L);
    }


    /**
     * 创建基础组件对象
     * @param sequenceNumberId
     * @param container
     * @param masterplateId
     * @param domain
     * @return
     */
    public static Component createBasicsComponent(SequenceNumberId sequenceNumberId, Container container, MasterplateId masterplateId, Component domain) {
        return new Component(new ComponentId(sequenceNumberId.getValue()), container.getId(),
                domain.getPageCode(), masterplateId, domain.getComponentType(), domain.getComponentBusinessType(),
                domain.getBizJson(), domain.getOperatorId(), 0L);
    }

    /**
     * 获取商品模型
     * @param goodsDetail
     * @param goodsIds
     * @param goodsPrices
     * @param operatorId
     * @param type
     * @param componentId
     * @param equityIdMap
     * @return
     */
    private static String convertGoodsDetail(Object goodsDetail, List<GoodsId> goodsIds, List<GoodsPrice> goodsPrices,
                                             OperatorId operatorId, String type, ComponentId componentId,
                                             Map<EquityKey, List<GoodsId>> equityIdMap, EquityKey equityKey) {

        String bizJson = "";
        String goodsDetailInfo = null != goodsDetail ? JsonUtil.toJsonString(goodsDetail) : "";
        if (StringUtils.isNotEmpty(goodsDetailInfo)) {

            GoodsDetailWrapper detailWrapper;
            detailWrapper = JsonUtil.toObject(goodsDetailInfo, GoodsDetailWrapper.class);
            List<GoodsDetail> goodsDetails = null != detailWrapper ? null != detailWrapper.getGoodsList() ? detailWrapper.getGoodsList() : Lists.newArrayList() : Lists.newArrayList();
            List<GoodsId> goodsIdList = goodsDetails.stream().map(p -> new GoodsId(p.getGoodsId())).collect(Collectors.toList());

            goodsIds.addAll(goodsIdList);
            if (detailWrapper != null && !CollectionUtils.isEmpty(detailWrapper.getSpuIds())) {
                //商品分类组件关联商品集合
                goodsIds.addAll(detailWrapper.getSpuIds().stream().map(GoodsId::new).collect(Collectors.toList()));
                //权益组件关联商品
                if (ModelNameConstant.EQUITY_GOODS.equals(type) && null != equityKey) {
                    if (!CollectionUtils.isEmpty(equityIdMap.get(equityKey))) {
                        List<GoodsId> goodsList = equityIdMap.get(equityKey);
                        goodsList.addAll(detailWrapper.getSpuIds().stream().map(GoodsId::new).collect(Collectors.toList()));
                    } else {
                        equityIdMap.put(equityKey, detailWrapper.getSpuIds().stream().map(GoodsId::new).collect(Collectors.toList()));
                    }
                }
            }
            //查询圈品id关联商品
            if (null != detailWrapper && null != detailWrapper.getBizId()) {
                List<Long> spuIdListByBizId = remoteCircleGoodsService.getSpuIdListByBizId(detailWrapper.getBizId());
                if (!CollectionUtils.isEmpty(spuIdListByBizId)) {
                    goodsIds.addAll(spuIdListByBizId.stream().map(GoodsId::new).collect(Collectors.toList()));
                }
            }

            //转化商品模型类
            List<CmsGoods> cmsGoodsList = new ArrayList<>();

            for (GoodsDetail goodsInfo : goodsDetails) {
                CmsGoods cmsGoods = new CmsGoods();
                cmsGoods.setGoodsId(goodsInfo.getGoodsId());
                cmsGoods.setGiftImage(goodsInfo.getGiftImage());
                //默认自定义
                Integer goodsNameEnable = null != goodsInfo.getGoodsNameEnable() ? goodsInfo.getGoodsNameEnable() : 2;
                int goodsImageEnable = null != goodsInfo.getGoodsImageEnable() ? goodsInfo.getGoodsImageEnable() : 2;
                int goodsSubTitleEnable = null != goodsInfo.getGoodsSubTitleEnable() ? goodsInfo.getGoodsSubTitleEnable() : 2;
                cmsGoods.setGoodsNameEnable(goodsNameEnable);

                cmsGoods.setGoodsNameEnable(goodsNameEnable);
                cmsGoods.setGoodsImageEnable(goodsImageEnable);
                cmsGoods.setGoodsSubTitleEnable(goodsSubTitleEnable);
                cmsGoods.setGoodsImage(goodsImageEnable == 2 ? goodsInfo.getGoodsImage() : null);
                cmsGoods.setGoodsName(goodsNameEnable == 2 ? goodsInfo.getGoodsName() : null);
                cmsGoods.setGoodsSubTitle(goodsSubTitleEnable == 2 ? goodsInfo.getGoodsSubTitle() : null);

                String textBgImage = StringUtils.isNotEmpty(goodsInfo.getTextBackgroundImage()) ? goodsInfo.getTextBackgroundImage() : "";
                String salePoint = !CollectionUtils.isEmpty(goodsInfo.getSalesPoint()) ? JsonUtil.toJsonString(goodsInfo.getSalesPoint()) : "";
                cmsGoods.setTextBgImage(textBgImage);
                cmsGoods.setCreateTime(goodsInfo.getCreateTime());
                cmsGoods.setLimitNum(goodsInfo.getLimitNum());
                cmsGoods.setOperatorId(null != operatorId ? operatorId.getValue() : null);
                cmsGoods.setSalesPoint(salePoint);
                cmsGoods.setSwellPercentage(goodsInfo.getSwellPercentage());
                cmsGoods.setComponentId(goodsInfo.getComponentId());
                cmsGoods.setUpdateTime(LocalDateTime.now());
                if (null != cmsGoods.getSubjectGoodsType()) {
                    cmsGoods.setSubjectGoodsType(cmsGoods.getSubjectGoodsType());
                } else {
                    Integer subjectGoodsType = ModelNameConstant.COMP_INTEGRAL_GOOD.equals(type) ? 2 : 1;
                    cmsGoods.setSubjectGoodsType(subjectGoodsType);
                }

                //设置日常价
                if (!CollectionUtils.isEmpty(goodsInfo.getNormalPrice())) {
                    GoodsPrice goodsPrice = GoodsPriceFactory.createGoodsPrice(null, operatorId, new GoodsId(goodsInfo.getGoodsId()),
                            goodsInfo.getNormalPrice(), componentId);
                    goodsPrices.add(goodsPrice);
                }
                cmsGoodsList.add(cmsGoods);
            }
            bizJson = !CollectionUtils.isEmpty(cmsGoodsList) ? JsonUtil.toJsonString(cmsGoodsList) : "";

        }
        return bizJson;
    }

    private static String convertTaskComp(Object taskCompInfo) {

        String taskCompStr = null != taskCompInfo ? JsonUtil.toJsonString(taskCompInfo) : "";

        if (StringUtils.isNotEmpty(taskCompStr)) {
            TaskComponentInfo componentInfo = JsonUtil.toObject(taskCompStr, TaskComponentInfo.class);
            return JsonUtil.toJsonString(componentInfo);
        }
        return "";
    }

    private static String convertLuckyComp(Map<String, Object> compMap) {

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
                return JSON.toJSONString(luckyComponentInfo);
            }
        }
        return "";
    }
}
