package com.idanchuang.cms.server.infrastructureNew.external.feign;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.idanchuang.cms.server.application.config.SystemConfig;
import com.idanchuang.cms.server.application.constant.RedisKeyConstant;
import com.idanchuang.cms.server.domain.model.cms.CmsCompData;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPageId;
import com.idanchuang.cms.server.domainNew.model.cms.external.equity.EquityKey;
import com.idanchuang.cms.server.domainNew.model.cms.external.equity.EquityType;
import com.idanchuang.cms.server.domainNew.model.cms.external.goods.AssignTagEnableEnum;
import com.idanchuang.cms.server.domainNew.model.cms.external.goods.GoodsTagMessage;
import com.idanchuang.cms.server.domainNew.model.cms.external.goods.GoodsTagService;
import com.idanchuang.cms.server.domainNew.model.cms.external.goodsPrice.GoodsId;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.Masterplate;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateId;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateRepository;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.component.redis.util.RedisUtil;
import com.idanchuang.resource.server.infrastructure.common.constant.ErrorEnum;
import com.idanchuang.resource.server.infrastructure.common.exception.BusinessException;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import com.idanchuang.resource.server.infrastructure.utils.ListUtils;
import com.idanchuang.trade.goods.hulk.api.constant.tag.GoodsTagBizTypeEnum;
import com.idanchuang.trade.goods.hulk.api.constant.tag.GoodsTagStatusEnum;
import com.idanchuang.trade.goods.hulk.api.constant.tag.GoodsTagTypeEnum;
import com.idanchuang.trade.goods.hulk.api.entity.param.goods.SpuRecommendRefreshQuery;
import com.idanchuang.trade.goods.hulk.api.entity.param.tag.BindTagParam;
import com.idanchuang.trade.goods.hulk.api.entity.param.tag.CreateGoodsTagParam;
import com.idanchuang.trade.goods.hulk.api.entity.param.tag.UnbindTagAllParam;
import com.idanchuang.trade.goods.hulk.api.entity.param.tag.UnbindTagParam;
import com.idanchuang.trade.goods.hulk.api.service.GoodsTagApi;
import com.idanchuang.trade.goods.hulk.api.service.goods.SpuSceneQueryApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;


/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-22 16:26
 * @Desc: 商品打标服务
 * @Copyright VTN Limited. All rights reserved.
 */
@Slf4j
@Service
public class GoodsTagExternalService implements GoodsTagService {

    @Resource
    private GoodsTagApi goodsTagApi;

    @Resource
    private SpuSceneQueryApi spuSceneQueryApi;

    @Resource
    private MasterplateRepository masterplateRepository;

    @Override
    public Long createGoodsTag(GoodsTagMessage goodsTagMessage) {

        //特定标签商品打标
        List<GoodsId> goodsIds = goodsTagMessage.getGoodsIds();
        if (!CollectionUtils.isEmpty(goodsIds)) {
            if (goodsTagMessage.getAssignTagEnable() == AssignTagEnableEnum.NOT_ASSIGN) {
                if (SystemConfig.getInstance().getSubjectTagId().equals(goodsTagMessage.getPageId().getValue())) {
                    createGoodsTag(goodsIds, goodsTagMessage, GoodsTagTypeEnum.ON_SALE_WED_TAG, "VTN-福三频道");
                } else if (SystemConfig.getInstance().getSubjectPreferenceTagId().equals(goodsTagMessage.getPageId().getValue())) {
                    createGoodsTag(goodsIds, goodsTagMessage, GoodsTagTypeEnum.SPECIAL_OFFER_TAG, "VTN-特惠团频道");
                } else if (SystemConfig.getInstance().getSubjectNewYearTagId().equals(goodsTagMessage.getPageId().getValue())) {
                    createGoodsTag(goodsIds, goodsTagMessage, GoodsTagTypeEnum.FULL_DRAW_TAG, "VTN-年货节频道");
                } else if (SystemConfig.getInstance().getCelebrationNewYearTagId().equals(goodsTagMessage.getPageId().getValue())) {
                    createGoodsTag(goodsIds, goodsTagMessage, GoodsTagTypeEnum.CELEBRATION_NEW_YEAR, "VTN-庆开年频道");
                } else if (SystemConfig.getInstance().getGoddessTagId().equals(goodsTagMessage.getPageId().getValue())) {
                    createGoodsTag(goodsIds, goodsTagMessage, GoodsTagTypeEnum.GODDESS_OF_THE_LABEL, "VTN-女神年频道");
                }
            } else {
                return createGoodsTag(goodsIds, goodsTagMessage, GoodsTagTypeEnum.getEnumByType(goodsTagMessage.getTagType()), goodsTagMessage.getTagMsg());
            }
        }
        return null;
    }

    @Override
    public void expireOldTag(MasterplateId masterplateId) {

        if (null == masterplateId || masterplateId.getValue() == 0) {
            return;
        }

        Masterplate masterplate = masterplateRepository.getMasterplateById(masterplateId);

        if (null != masterplate) {
            //普通标签
            String tagKey = format(RedisKeyConstant.SUBJECT_GOODS_TAG_ID_KEY, masterplateId.getValue());
            String key = format(RedisKeyConstant.SUBJECT_GOODS_MARK_LIST_KEY, masterplateId.getValue());
            Integer tag = RedisUtil.getInstance().getObj(tagKey);
            String goodsIdsStr = RedisUtil.getInstance().get(key);
            List<Long> goodsIds = JsonUtil.toList(goodsIdsStr, new TypeReference<List<Long>>() {
            });

            //权益标签
            String equityTagKey = format(RedisKeyConstant.SUBJECT_EQUITY_GOODS_TAG_ID_KEY, masterplateId.getValue());
            RedisUtil.getInstance().del(equityTagKey);
            if (null != tag && !CollectionUtils.isEmpty(goodsIds)) {
                //失效普通标签
                unbindTag(Lists.newArrayList(tag.longValue()), goodsIds);
            }

            //失效权益活动标签
            String equityActivityKey = format(RedisKeyConstant.SUBJECT_EQUITY_ID_KEY, masterplate.getId());
            Map<Long, Long> equityActivityMap = RedisUtil.getInstance().getObj(equityActivityKey);
            if (MapUtils.isNotEmpty(equityActivityMap)) {
                for (Map.Entry<Long, Long> entry : equityActivityMap.entrySet()) {
                    String tagStr = format(RedisKeyConstant.SUBJECT_GOODS_EQUITY_MARK_LIST_KEY, masterplate.getId(), entry.getKey());
                    String tagGoodsIdsStr = RedisUtil.getInstance().get(tagStr);
                    List<Long> equityGoodsIds = JsonUtil.toList(tagGoodsIdsStr, new TypeReference<List<Long>>() {
                    });
                    unbindTag(Lists.newArrayList(entry.getValue()), equityGoodsIds);
                }
            }

            //失效权益标签
            String equityKey = format(RedisKeyConstant.SUBJECT_EQUITY_KEY, masterplate.getId());
            Map<Long, Long> equityMap = RedisUtil.getInstance().getObj(equityKey);
            if (MapUtils.isNotEmpty(equityMap)) {
                for (Map.Entry<Long, Long> entry : equityMap.entrySet()) {
                    String tagStr = format(RedisKeyConstant.SUBJECT_GOODS_EQUITY_LIST_KEY, masterplate.getId(), entry.getKey());
                    String tagGoodsIdsStr = RedisUtil.getInstance().get(tagStr);
                    List<Long> equityGoodsIds = JsonUtil.toList(tagGoodsIdsStr, new TypeReference<List<Long>>() {
                    });
                    unbindTag(Lists.newArrayList(entry.getValue()), equityGoodsIds);
                }
            }
        }
    }

    @Override
    public Map<EquityKey, Long> pageExpireAndCreateTag(Masterplate masterplate, List<GoodsId> goodsIds, ClientPageId pageId, Map<EquityKey, List<GoodsId>> equityIdMap) {
        //如果存在老的商品标签，先进行去标
        expireOldTag(masterplate.getId());
        //创建商品标签
        GoodsTagMessage goodsTagMessage = new GoodsTagMessage(goodsIds, pageId, masterplate.getId(),
                masterplate.getMasterplateName(), masterplate.getStartTime(), masterplate.getEndTime(), AssignTagEnableEnum.NOT_ASSIGN, null, "", null);
        createGoodsTag(goodsTagMessage);

        Map<EquityKey, Long> equityTagMap = Maps.newHashMap();
        if (MapUtils.isNotEmpty(equityIdMap)) {
            for (Map.Entry<EquityKey, List<GoodsId>> entry : equityIdMap.entrySet()) {
                //创建商品权益标签
                GoodsTagMessage message = new GoodsTagMessage(entry.getValue(), pageId, masterplate.getId(), masterplate.getMasterplateName(), masterplate.getStartTime(), masterplate.getEndTime(), AssignTagEnableEnum.ASSIGN, GoodsTagTypeEnum.RIGHTS_TAG.getTagType(), GoodsTagTypeEnum.RIGHTS_TAG.getTagTypeDesc(), entry.getKey());
                Long goodsTag = createGoodsTag(message);
                if (null != goodsTag) {
                    equityTagMap.put(entry.getKey(), goodsTag);
                }
            }
        }
        return equityTagMap;
    }


    /**
     * 创建商品标签
     *
     * @param goodsIds
     * @param message
     * @param typeEnum
     * @param remark
     */
    private Long createGoodsTag(List<GoodsId> goodsIds, GoodsTagMessage message, GoodsTagTypeEnum typeEnum, String remark) {
        if (null == typeEnum) {
            log.error("createGoodsTag error goodIds:{} message:{}", goodsIds, message);
            throw new BusinessException(ErrorEnum.GOODS_TAG_ERROR);
        }
        if (!CollectionUtils.isEmpty(goodsIds)) {
            Set<Long> goodsList = goodsIds.stream().map(GoodsId::getValue).collect(Collectors.toSet());
            String key;
            Long goodsTag;
            AssignTagEnableEnum assignTagEnable = message.getAssignTagEnable();
            if (assignTagEnable == AssignTagEnableEnum.NOT_ASSIGN) {
                key = format(RedisKeyConstant.SUBJECT_GOODS_MARK_LIST_KEY, message.getMasterplateId().getValue());
            } else {
                EquityKey equityKey = message.getEquityKey();
                if (null == equityKey) {
                    throw new BusinessException(ErrorEnum.EQUITY_NOT_EXIST);
                }
                Long equityId = equityKey.getEquityId();
                if (EquityType.EQUITY_ACTIVITY.getVal().equals(equityKey.getEquityType())) {
                    key = format(RedisKeyConstant.SUBJECT_GOODS_EQUITY_MARK_LIST_KEY, message.getMasterplateId().getValue(), equityId);
                } else{
                    key = format(RedisKeyConstant.SUBJECT_GOODS_EQUITY_LIST_KEY, message.getMasterplateId().getValue(), equityId);
                }
            }
            goodsTag = createGoodsTag(message.getMasterplateName(), message.getStartTime(), message.getEndTime(), typeEnum, remark);
            //保存打标的商品列表
            RedisUtil.getInstance().set(key, JsonUtil.toJsonString(goodsList));
            if (null != goodsTag) {
                String tagKey;
                if (assignTagEnable == AssignTagEnableEnum.NOT_ASSIGN) {
                    tagKey = format(RedisKeyConstant.SUBJECT_GOODS_TAG_ID_KEY, message.getMasterplateId().getValue());
                    //保存标签
                    RedisUtil.getInstance().setObj(tagKey, goodsTag);
                } else {
                    tagKey = format(RedisKeyConstant.SUBJECT_EQUITY_GOODS_TAG_ID_KEY, message.getMasterplateId().getValue());
                    //保存标签
                    RedisUtil.getInstance().setObj(tagKey, setRedisList(tagKey, goodsTag));
                }
                List<String> goodsListStr = goodsList.stream().map(String::valueOf).collect(Collectors.toList());
                List<List<String>> partition = Lists.partition(goodsListStr, 100);
                partition.parallelStream().forEach(p -> bindTag(Lists.newArrayList(goodsTag), p));

                //如果是福三下的猜你喜欢商品，则刷新标签的固定排序
                if (GoodsTagTypeEnum.ON_SALE_WED_TAG.equals(typeEnum)) {
                    freshRecommendList(Lists.newArrayList(goodsTag));
                }

                //权益标签通知会员
                if (assignTagEnable == AssignTagEnableEnum.ASSIGN) {
                    return goodsTag;
                }

            }
        }
        return null;
    }

    private List<Long> setRedisList(String tagKey, Long goodsTag) {
        List<Long> tagIds = Lists.newArrayList();
        List<Long> tagList = RedisUtil.getInstance().getObj(tagKey);
        if (CollectionUtils.isEmpty(tagList)) {
            tagIds.add(goodsTag);
        } else {
            if (!tagList.contains(goodsTag)) {
                tagIds.add(goodsTag);
            }
            tagIds.addAll(tagList);
        }
        return tagIds;
    }

    private Long createGoodsTag(String name, LocalDateTime startTime, LocalDateTime endTime, GoodsTagTypeEnum tagType, String remark) {

        JsonResult<Long> goodsTag;
        CreateGoodsTagParam createGoodsTagParam = new CreateGoodsTagParam();
        createGoodsTagParam.setTagName(name);
        createGoodsTagParam.setRemark(remark);
        createGoodsTagParam.setTagType(tagType.getTagType());
        createGoodsTagParam.setTagStartTime(startTime);
        createGoodsTagParam.setTagEndTime(endTime);
        createGoodsTagParam.setTagScope(GoodsTagBizTypeEnum.SPU.getBizType());
        createGoodsTagParam.setStatus(GoodsTagStatusEnum.NORMAL.getTagStatus());
        try {
            goodsTag = goodsTagApi.createGoodsTag(createGoodsTagParam);
        } catch (Exception e) {
            log.error("GoodsTagExternalService createGoodsTag error req:{} e:{}", JSONObject.toJSONString(createGoodsTagParam), e);
            throw new BusinessException(ErrorEnum.GOODS_TAG_ERROR);
        }
        if (goodsTag == null || !goodsTag.isSuccess()) {
            log.warn("GoodsTagExternalService createGoodsTag warn req:{} goodsTag:{}", JSONObject.toJSONString(createGoodsTagParam), JSONObject.toJSONString(goodsTag));
            throw new BusinessException(ErrorEnum.GOODS_TAG_ERROR);
        } else {
            return goodsTag.getData();
        }
    }

    private void freshRecommendList(List<Long> tagList) {

        SpuRecommendRefreshQuery refreshQuery = new SpuRecommendRefreshQuery();
        refreshQuery.setTagIdList(tagList);
        JsonResult<Void> result = spuSceneQueryApi.refreshSpuRecommendList(refreshQuery);

        if (!result.isSuccess()) {
            log.error("GoodsTagExternalService freshRecommendList refresh fail, tagList:{}, result:{}", tagList, JSONObject.toJSONString(result));
            throw new BusinessException(result.getCode(), result.getMsg());
        }
    }

    private void bindTag(List<Long> ids, List<String> goods) {
        JsonResult<Void> result;
        BindTagParam bindTagParam = new BindTagParam();
        bindTagParam.setBizIdList(goods);
        bindTagParam.setTagIdList(ids);
        bindTagParam.setBizTypeEnum(GoodsTagBizTypeEnum.SPU);
        try {
            result = goodsTagApi.bindTag(bindTagParam);
        } catch (Exception e) {
            log.error("GoodsTagExternalService bindTag error req:{} e:{}", JSONObject.toJSONString(bindTagParam), e);
            throw new BusinessException(ErrorEnum.GOODS_TAG_ERROR);
        }
        if (result == null || !result.isSuccess()) {
            log.warn("GoodsTagExternalService bindTag warn req:{}", JSONObject.toJSONString(bindTagParam));
        }

    }

    private void unbindTagAll(List<Long> ids) {
        JsonResult<Void> result;
        UnbindTagAllParam unbindTagAllParam = new UnbindTagAllParam();
        unbindTagAllParam.setBizTypeEnum(GoodsTagBizTypeEnum.SPU);
        unbindTagAllParam.setTagIdList(ids);
        try {
            result = goodsTagApi.unbindTagAll(unbindTagAllParam);
        } catch (Exception e) {
            log.error("GoodsTagExternalService unbindTagAll error req:{} e:{}", JSONObject.toJSONString(unbindTagAllParam), e);
            throw new BusinessException(ErrorEnum.GOODS_TAG_ERROR);
        }

        if (null == result || !result.isSuccess()) {
            log.warn("GoodsTagExternalService unbindTagAll warn req:{}", JSONObject.toJSONString(unbindTagAllParam));
        }
    }

    private void unbindTag(List<Long> ids, List<Long> goodsIds) {
        List<List<Long>> split = ListUtils.split(goodsIds, 100);
        for (List<Long> goods : split) {
            JsonResult<Void> result;
            UnbindTagParam unbindTagAllParam = new UnbindTagParam();
            unbindTagAllParam.setBizTypeEnum(GoodsTagBizTypeEnum.SPU);
            unbindTagAllParam.setTagIdList(ids);
            unbindTagAllParam.setBizIdList(goods.stream().map(Object::toString).collect(Collectors.toList()));
            try {
                result = goodsTagApi.unbindTag(unbindTagAllParam);
            } catch (Exception e) {
                log.error("GoodsTagExternalService unbindTagAll error req:{} e:{}", JSONObject.toJSONString(unbindTagAllParam), e);
                throw new BusinessException(ErrorEnum.GOODS_TAG_ERROR);
            }

            if (null == result || !result.isSuccess()) {
                log.warn("GoodsTagExternalService unbindTagAll warn req:{}", JSONObject.toJSONString(unbindTagAllParam));
            }
        }
    }
}
