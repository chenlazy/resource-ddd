package com.idanchuang.cms.server.application.remote;

import com.alibaba.fastjson.JSONObject;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.resource.server.infrastructure.common.constant.ErrorEnum;
import com.idanchuang.resource.server.infrastructure.common.exception.BusinessException;
import com.idanchuang.trade.goods.hulk.api.constant.tag.GoodsTagBizTypeEnum;
import com.idanchuang.trade.goods.hulk.api.constant.tag.GoodsTagStatusEnum;
import com.idanchuang.trade.goods.hulk.api.constant.tag.GoodsTagTypeEnum;
import com.idanchuang.trade.goods.hulk.api.entity.param.goods.SpuRecommendRefreshQuery;
import com.idanchuang.trade.goods.hulk.api.entity.param.tag.BindTagParam;
import com.idanchuang.trade.goods.hulk.api.entity.param.tag.CreateGoodsTagParam;
import com.idanchuang.trade.goods.hulk.api.entity.param.tag.ModifyGoodsTagParam;
import com.idanchuang.trade.goods.hulk.api.entity.param.tag.UnbindTagAllParam;
import com.idanchuang.trade.goods.hulk.api.entity.param.tag.UnbindTagParam;
import com.idanchuang.trade.goods.hulk.api.service.GoodsTagApi;
import com.idanchuang.trade.goods.hulk.api.service.goods.SpuSceneQueryApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-16 11:10
 * @Desc: 商品打标服务
 * @Copyright VTN Limited. All rights reserved.
 */
@Slf4j
@Service
public class RemoteGoodsTagService {

    @Resource
    private GoodsTagApi goodsTagApi;

    @Resource
    private SpuSceneQueryApi spuSceneQueryApi;

    public Long createGoodsTag(String name, LocalDateTime startTime, LocalDateTime endTime, GoodsTagTypeEnum tagType, String remark) {
        JsonResult<Long> goodsTag;
        CreateGoodsTagParam createGoodsTagParam = new CreateGoodsTagParam();
        createGoodsTagParam.setTagName(name);
        createGoodsTagParam.setTagType(tagType.getTagType());
        createGoodsTagParam.setTagScope(GoodsTagBizTypeEnum.SPU.getBizType());
        createGoodsTagParam.setTagStartTime(startTime);
        createGoodsTagParam.setTagEndTime(endTime);
        createGoodsTagParam.setStatus(GoodsTagStatusEnum.NORMAL.getTagStatus());
        createGoodsTagParam.setRemark(remark);
        try {
            goodsTag = goodsTagApi.createGoodsTag(createGoodsTagParam);
        } catch (Exception e) {
            log.error("RemoteGoodsTagService createGoodsTag error req:{} e:{}", JSONObject.toJSONString(createGoodsTagParam), e);
            throw new BusinessException(ErrorEnum.GOODS_TAG_ERROR);
        }
        if (goodsTag == null || !goodsTag.isSuccess()) {
            log.warn("RemoteGoodsTagService createGoodsTag warn req:{} goodsTag:{}", JSONObject.toJSONString(createGoodsTagParam), JSONObject.toJSONString(goodsTag));
            throw new BusinessException(ErrorEnum.GOODS_TAG_ERROR);
        } else {
            return goodsTag.getData();
        }
    }

    public void modifyGoodsTag(Long id, String tagName, LocalDateTime startTime, LocalDateTime endTime, GoodsTagStatusEnum tagStatusEnum, GoodsTagTypeEnum tagType, String remark) {
        JsonResult<Void> voidJsonResult;
        ModifyGoodsTagParam modifyGoodsTagParam = new ModifyGoodsTagParam();
        modifyGoodsTagParam.setId(id);
        modifyGoodsTagParam.setTagName(tagName);
        modifyGoodsTagParam.setTagStartTime(startTime);
        modifyGoodsTagParam.setTagEndTime(endTime);
        modifyGoodsTagParam.setRemark(remark);
        try {
            voidJsonResult = goodsTagApi.modifyGoodsTag(modifyGoodsTagParam);
        } catch (Exception e) {
            log.error("RemoteGoodsTagService modifyGoodsTag error req:{} e:{}", JSONObject.toJSONString(modifyGoodsTagParam), e);
            throw new BusinessException(ErrorEnum.GOODS_TAG_ERROR);
        }
        if (voidJsonResult == null || !voidJsonResult.isSuccess()) {
            log.warn("RemoteGoodsTagService modifyGoodsTag warn req:{} voidJsonResult:{}", JSONObject.toJSONString(modifyGoodsTagParam), JSONObject.toJSONString(voidJsonResult));
            throw new BusinessException(ErrorEnum.GOODS_TAG_ERROR);
        }

    }

    public void freshRecommendList(List<Long> tagList) {

        SpuRecommendRefreshQuery refreshQuery = new SpuRecommendRefreshQuery();
        refreshQuery.setTagIdList(tagList);
        JsonResult<Void> result = spuSceneQueryApi.refreshSpuRecommendList(refreshQuery);

        if (!result.isSuccess()) {
            log.error("freshRecommendList refresh fail, tagList:{}, result:{}", tagList, JSONObject.toJSONString(result));
            throw new BusinessException(result.getCode(), result.getMsg());
        }
    }

    public void bindTag(List<Long> ids, List<String> goods, GoodsTagBizTypeEnum bizTypeEnum) {
        JsonResult<Void> voidJsonResult;
        BindTagParam bindTagParam = new BindTagParam();
        bindTagParam.setTagIdList(ids);
        bindTagParam.setBizIdList(goods);
        bindTagParam.setBizTypeEnum(bizTypeEnum);
        try {
            voidJsonResult = goodsTagApi.bindTag(bindTagParam);
        } catch (Exception e) {
            log.error("RemoteGoodsTagService bindTag error req:{} e:{}", JSONObject.toJSONString(bindTagParam), e);
            throw new BusinessException(ErrorEnum.GOODS_TAG_ERROR);
        }
        if (voidJsonResult == null || !voidJsonResult.isSuccess()) {
            log.warn("RemoteGoodsTagService bindTag warn req:{}", JSONObject.toJSONString(bindTagParam));
        }

    }

    public void unbindTag(List<Long> ids, List<String> goods, GoodsTagBizTypeEnum bizTypeEnum) {
        JsonResult<Void> voidJsonResult;
        UnbindTagParam unbindTagParam = new UnbindTagParam();
        unbindTagParam.setTagIdList(ids);
        unbindTagParam.setBizIdList(goods);
        unbindTagParam.setBizTypeEnum(bizTypeEnum);
        try {
            voidJsonResult = goodsTagApi.unbindTag(unbindTagParam);
        } catch (Exception e) {
            log.error("RemoteGoodsTagService unbindTag error req:{} e:{}", JSONObject.toJSONString(unbindTagParam), e);
            throw new BusinessException(ErrorEnum.GOODS_TAG_ERROR);
        }
        if (voidJsonResult == null || !voidJsonResult.isSuccess()) {
            log.warn("RemoteGoodsTagService unbindTag warn req:{}", JSONObject.toJSONString(unbindTagParam));
        }
    }

    public void unbindTagAll(List<Long> ids, GoodsTagBizTypeEnum bizTypeEnum) {
        JsonResult<Void> voidJsonResult;
        UnbindTagAllParam unbindTagAllParam = new UnbindTagAllParam();
        unbindTagAllParam.setTagIdList(ids);
        unbindTagAllParam.setBizTypeEnum(bizTypeEnum);
        try {
            voidJsonResult = goodsTagApi.unbindTagAll(unbindTagAllParam);
        } catch (Exception e) {
            log.error("RemoteGoodsTagService unbindTagAll error req:{} e:{}", JSONObject.toJSONString(unbindTagAllParam), e);
            throw new BusinessException(ErrorEnum.GOODS_TAG_ERROR);
        }

        if (voidJsonResult == null || !voidJsonResult.isSuccess()) {
            log.warn("RemoteGoodsTagService unbindTagAll warn req:{}", JSONObject.toJSONString(unbindTagAllParam));
        }
    }
}
