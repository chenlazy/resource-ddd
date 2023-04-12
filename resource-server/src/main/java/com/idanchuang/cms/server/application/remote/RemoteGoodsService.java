package com.idanchuang.cms.server.application.remote;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.idanchuang.cms.server.application.enums.ErrorEnum;
import com.idanchuang.cms.server.domain.model.cms.GoodsSkuQuery;
import com.idanchuang.cms.server.domain.model.cms.GoodsComponentSku;
import com.idanchuang.cms.server.domain.model.cms.GoodsSpuQuery;
import com.idanchuang.cms.server.domain.model.cms.GoodsComponentSpu;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.component.base.exception.core.ExDefinition;
import com.idanchuang.component.base.exception.core.ExType;
import com.idanchuang.resource.server.infrastructure.common.exception.BusinessException;
import com.idanchuang.trade.goods.hulk.api.constant.SkuQueryModule;
import com.idanchuang.trade.goods.hulk.api.constant.SpuQueryModule;
import com.idanchuang.trade.goods.hulk.api.constant.SpuStatusEnum;
import com.idanchuang.trade.goods.hulk.api.entity.dto.UserAddressDTO;
import com.idanchuang.trade.goods.hulk.api.entity.dto.goods.*;
import com.idanchuang.trade.goods.hulk.api.entity.param.goods.DefaultSkuQuery;
import com.idanchuang.trade.goods.hulk.api.entity.param.goods.SimpleBatchGoodsPriceQuery;
import com.idanchuang.trade.goods.hulk.api.entity.param.goods.SkuSearchQuery;
import com.idanchuang.trade.goods.hulk.api.entity.param.goods.SpuSearchQuery;
import com.idanchuang.trade.goods.hulk.api.entity.param.goods.UpDownMsgParam;
import com.idanchuang.trade.goods.hulk.api.service.goods.SkuQueryApi;
import com.idanchuang.trade.goods.hulk.api.service.goods.SpuQueryApi;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品服务实现
 * @author lei.liu
 * @date 2021/4/20
 */
@Service
@Slf4j
public class RemoteGoodsService {

    @Resource
    private SpuQueryApi spuQueryApi;

    @Resource
    private SkuQueryApi skuQueryApi;

    public GoodsComponentSpu querySpuById(Long spuId) {
        GoodsSpuQuery spuQueryRequest = new GoodsSpuQuery();
        spuQueryRequest.setSpuIdList(Arrays.asList(spuId));
        List<GoodsComponentSpu> goodsSpuList = queryGoodsSpuList(spuQueryRequest);
        if (CollectionUtils.isEmpty(goodsSpuList)) {
            return null;
        }
        return goodsSpuList.get(0);
    }

    public List<GoodsComponentSpu> querySpuList(GoodsSpuQuery query) {
        return queryGoodsSpuList(query);
    }

    public Map<Long, GoodsComponentSpu> querySpuMap(GoodsSpuQuery query) {
        List<GoodsComponentSpu> goodsSpuList = queryGoodsSpuList(query);
        if (CollectionUtils.isEmpty(goodsSpuList)) {
            return new HashMap<>();
        }
        return goodsSpuList.stream().collect(Collectors.toMap(GoodsComponentSpu::getSpuId, t -> t, (a, b) -> a));
    }

    public List<GoodsComponentSku> querySkuList(GoodsSkuQuery query) {
        return queryGoodsSkuList(query);
    }

    public Map<Long, GoodsComponentSku> querySkuMap(GoodsSkuQuery query) {
        List<GoodsComponentSku> goodsSkuList = queryGoodsSkuList(query);
        if (CollectionUtils.isEmpty(goodsSkuList)) {
            return new HashMap<>();
        }
        return goodsSkuList.stream().collect(Collectors.toMap(GoodsComponentSku::getSkuId, t -> t, (a, b) -> a));
    }

    public List<GoodsComponentSku> queryGoodsSkuList(GoodsSkuQuery query) {
        List<GoodsComponentSku> goodsSkuList = new ArrayList<>();

        List<List<Long>> skuIdPartition = Lists.partition(query.getSkuIdList(), 20);
        for (List<Long> skuIdList : skuIdPartition) {
            SkuSearchQuery skuSearchQuery = new SkuSearchQuery();
            skuSearchQuery.setAllowLevelFlag(1);
            skuSearchQuery.setSpuStatusList(Lists.newArrayList(SpuStatusEnum.PUT_OFF.getCode(), SpuStatusEnum.PUF_ON.getCode(), SpuStatusEnum.DRAFT.getCode()));
            skuSearchQuery.setSkuIds(new HashSet<>(skuIdList));
            if (query.getQueryPrice() == 1) {
                skuSearchQuery.setSkuModule(SkuQueryModule.PRICE_SEARCH_BIT);
                if (query.getUserLevel() != null) {
                    //skuSearchQuery.setFilterUserLevel(query.getUserLevel());
                }

                if (query.getGoodsAddress() != null) {
                    SimpleBatchGoodsPriceQuery priceQuery = new SimpleBatchGoodsPriceQuery();
                    priceQuery.setUserLevel(query.getUserLevel());

                    UserAddressDTO userAddress = new UserAddressDTO();
                    userAddress.setCountry(query.getGoodsAddress().getCountry());
                    userAddress.setProvince(query.getGoodsAddress().getProvince());
                    userAddress.setCity(query.getGoodsAddress().getCity());
                    userAddress.setDistrict(query.getGoodsAddress().getDistrict());
                    userAddress.setUserId(query.getUserId());
                    priceQuery.setUserAddressDto(userAddress);
                    skuSearchQuery.setBatchGoodsPriceDto(priceQuery);
                }
            }

            log.info("入参商品数据", JSON.toJSONString(skuSearchQuery));
            JsonResult<List<SkuInfoDTO>> result = skuQueryApi.querySkuList(skuSearchQuery);
            if (!result.isSuccess()) {
                log.error(result.getMsg());
                throw new BusinessException(result.getCode(), result.getMsg());
            }

            log.info("查询商品信息, 参数: {}, 结果: {}", JSON.toJSONString(skuSearchQuery), JSON.toJSONString(result.getData()));
            if (CollectionUtils.isEmpty(result.getData())) {
                continue;
            }

            List<GoodsSkuCode> goodsSkuCodeList = new ArrayList<>();
            GoodsComponentSku goodsSku = null;
            for (SkuInfoDTO skuInfo : result.getData()) {
                goodsSku = new GoodsComponentSku();

                BaseSpuInfoDTO baseSpuInfo = skuInfo.getBaseSpuInfo();
                if (baseSpuInfo != null) {
                    goodsSku.setName(baseSpuInfo.getName());
                    goodsSku.setSubTitle(baseSpuInfo.getSubTitle());
                    goodsSku.setStatus(baseSpuInfo.getStatus());
                    goodsSku.setActivity(baseSpuInfo.getActivity());
                    goodsSku.setInCart(baseSpuInfo.getInCart());
                    goodsSku.setRecomLabelName(baseSpuInfo.getRecomLabelName());
                    goodsSku.setActivityLevel(baseSpuInfo.getActivityLevel());
                }

                BaseSkuInfoDTO baseSkuInfo = skuInfo.getBaseSkuInfoDTO();
                if (baseSkuInfo != null) {
                    goodsSku.setSkuId(baseSkuInfo.getId());
                    goodsSku.setSpuId(baseSkuInfo.getSpuId());
                    goodsSku.setMarketPrice(baseSkuInfo.getMarketPrice());
                    goodsSku.setGoodsCode(baseSkuInfo.getSkuCode() != null ? baseSkuInfo.getSkuCode().toString() : null);

                    if (baseSkuInfo.getImageFile() != null) {
                        goodsSku.setImage(baseSkuInfo.getImageFile().getId());
                        goodsSku.setImageUrl(baseSkuInfo.getImageFile().getFileUrl());
                    }
                }

                PriceDTO goodsPrice = skuInfo.getGoodsPriceVO();
                if (goodsPrice != null) {
                    goodsSku.setAmountPrice(goodsPrice.getAmountPrice());
                    goodsSku.setOriginalAmountPrice(goodsPrice.getOriginalAmountPrice());
                    goodsSku.setFinalPriceTitle(goodsPrice.getFinalPriceTitle());
                    goodsSku.setOriginalAmountPriceTitle(goodsPrice.getOriginalAmountPriceTitle());
                }

                goodsSkuList.add(goodsSku);
                GoodsSkuCode goodsSkuCode = new GoodsSkuCode();
                goodsSkuCode.setSkuId(goodsSku.getSkuId());
                goodsSkuCode.setGoodsCode(goodsSku.getGoodsCode());
                goodsSkuCode.setActivity(goodsSku.getActivity());
                goodsSkuCode.setSubSkuList(skuInfo.getSubSkuList());
                goodsSkuCodeList.add(goodsSkuCode);
            }

            // 商品库存
//            if (query.getQueryStock() == 1) {
//                Map<Long, Integer> goodsStockMap = queryGoodsStockMap(goodsSkuCodeList);
//                for (GoodsSkuResponse item : goodsSkuList) {
//                    item.setStock(goodsStockMap.get(item.getSkuId()));
//                }
//            }
        }
        return goodsSkuList;
    }

    private List<GoodsComponentSpu> queryGoodsSpuList(GoodsSpuQuery query) {
        SpuSearchQuery spuSearchQuery = new SpuSearchQuery();
        spuSearchQuery.setSpuStatusList(Lists.newArrayList(SpuStatusEnum.PUT_OFF.getCode(), SpuStatusEnum.PUF_ON.getCode(), SpuStatusEnum.DRAFT.getCode()));
        spuSearchQuery.setSpuIds(new HashSet<>(query.getSpuIdList()));
        spuSearchQuery.setAllowLevelFlag(1);

        if (query.getQueryPrice() == 1) {
            spuSearchQuery.setSpuModule(SpuQueryModule.searchDefaultSku);
            spuSearchQuery.setSkuModule(SkuQueryModule.PRICE_SEARCH_BIT);

            if (query.getGoodsAddress() != null) {
                SimpleBatchGoodsPriceQuery priceQuery = new SimpleBatchGoodsPriceQuery();
                priceQuery.setPromotionType(0);
                priceQuery.setUserLevel(query.getUserLevel());

                UserAddressDTO userAddress = new UserAddressDTO();
                userAddress.setCountry(query.getGoodsAddress().getCountry());
                userAddress.setProvince(query.getGoodsAddress().getProvince());
                userAddress.setCity(query.getGoodsAddress().getCity());
                userAddress.setDistrict(query.getGoodsAddress().getDistrict());
                userAddress.setUserId(query.getUserId());
                priceQuery.setUserAddressDto(userAddress);
                spuSearchQuery.setBatchGoodsPriceDto(priceQuery);
            }
        }

        JsonResult<List<SpuInfoDTO>> result = spuQueryApi.querySpuList(spuSearchQuery);
        if (!result.isSuccess()) {
            log.error("调用商品查询服务异常。Msg: {}", result.getMsg());
            throw new BusinessException(result.getCode(), result.getMsg());
        }

        List<SpuInfoDTO> spuInfoList = result.getData();
        if (CollectionUtils.isEmpty(spuInfoList)) {
            return null;
        }

        List<GoodsComponentSpu> goodsSpuList = new ArrayList<>();
        GoodsComponentSpu goodsSpu = null;
        for (SpuInfoDTO spuInfo : spuInfoList) {
            goodsSpu = new GoodsComponentSpu();

            BaseSpuInfoDTO baseSpuInfo = spuInfo.getBaseSpuInfo();
            if (baseSpuInfo != null) {
                goodsSpu.setSpuId(baseSpuInfo.getId());
                goodsSpu.setName(baseSpuInfo.getName());
                goodsSpu.setSubTitle(baseSpuInfo.getSubTitle());
                goodsSpu.setStatus(baseSpuInfo.getStatus());
                goodsSpu.setInCart(baseSpuInfo.getInCart());
                if (baseSpuInfo.getImageFile() != null) {
                    goodsSpu.setImage(baseSpuInfo.getImageFile().getId());
                    goodsSpu.setImageUrl(baseSpuInfo.getImageFile().getFileUrl());
                }
            }

            if (query.getQueryPrice() == 1) {
                // 商品价格
                SkuInfoDTO skuInfo = spuInfo.getDefaultSku();
                if (skuInfo != null) {
                    goodsSpu.setDefaultSkuId(skuInfo.getId());
                    if (skuInfo.getGoodsPriceVO() != null) {
                        PriceDTO goodsPrice = skuInfo.getGoodsPriceVO();
                        goodsSpu.setAmountPrice(goodsPrice.getAmountPrice());
                        goodsSpu.setMarketPrice(goodsPrice.getMarketPrice());
                        goodsSpu.setGoodsPrice(goodsPrice.getGoodsPrice());
                    }
                } else {
                    log.info("调用商品接口，spu无默认sku信息。spuId: " + spuInfo.getId());
                }
            }
            goodsSpuList.add(goodsSpu);
        }
        return goodsSpuList;
    }

    /**
     * 商品下架
     * @param spuIds
     * @param name
     * @param status
     * @return
     */
    public UpDownMsgDTO upOrDownGoods(List<Long> spuIds, String name, Integer status) {
        UpDownMsgParam up = new UpDownMsgParam();
        up.setSpuIds(spuIds);
        up.setStatus(status);
        up.setSource(name);
        JsonResult<UpDownMsgDTO> upDownMsgDTOJsonResult = spuQueryApi.upOrDownGoods(up);
        if (upDownMsgDTOJsonResult == null || !upDownMsgDTOJsonResult.checkSuccess()) {
            log.error("RemoteGoodsService upOrDownGoods error spuIds:{} name:{} status:{} upDownMsgDTOJsonResult:{}", spuIds, name, status, upDownMsgDTOJsonResult);
            return null;
        }
        return upDownMsgDTOJsonResult.getData();
    }

    public Map<Long, SpuInfoDTO> queryNewSpuInfoBySpuIdList(List<Long> spuIdList, Integer spuModule, Integer skuModule, DefaultSkuQuery defaultSkuQuery, Integer level, Integer type) {
        List<List<Long>> lists = Lists.partition(spuIdList, 20);
        List<SpuInfoDTO> data = Lists.newArrayList();
        for (List<Long> list : lists) {
            SpuSearchQuery request = new SpuSearchQuery();
            Set<Long> spuIds = new HashSet<>(list);
            request.setSpuIds(spuIds);
            request.setSpuModule(spuModule);
            request.setSkuModule(skuModule);
            request.setAllowLevelFlag(1);
            request.setSpuStatusList(Lists.newArrayList(1, 2));
            request.setRequireSpuTag(0);
            if (defaultSkuQuery != null) {
                request.setDefaultSkuQuery(defaultSkuQuery);
                SimpleBatchGoodsPriceQuery batchGoodsPriceQuery = new SimpleBatchGoodsPriceQuery();
                batchGoodsPriceQuery.setUserAddressDto(defaultSkuQuery.getUserAddressDto());
                batchGoodsPriceQuery.setUserLevel(level);
                //专题商品类型与商品端类型不一致 历史原因
                if (type == 1) {
                    batchGoodsPriceQuery.setPromotionType(0);
                } else {
                    batchGoodsPriceQuery.setPromotionType(type);
                }
                request.setBatchGoodsPriceDto(batchGoodsPriceQuery);
            }
            log.info("RemoteGoodsSpuInfoService queryNewSpuInfoBySpuIdList req:{}", JSON.toJSONString(request));
            JsonResult<List<SpuInfoDTO>> listJsonResult;
            try {
                listJsonResult = spuQueryApi.querySpuList(request);
            } catch (Exception e) {
                log.error("获取商品基本信息接口出现异常 request:{} e:{}", request, e);
                throw new com.idanchuang.component.base.exception.exception.BusinessException(new ExDefinition(ExType.BUSINESS_ERROR,
                        ErrorEnum.GOODS_INFO_ERROR.getCode(), ErrorEnum.GOODS_INFO_ERROR.getMsg()));
            }
            if (!listJsonResult.isSuccess() || listJsonResult.getData() == null) {
                log.error("获取商品基本信息接口出现异常 request:{} listJsonResult:{}", request, listJsonResult);
                throw new com.idanchuang.component.base.exception.exception.BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, listJsonResult.getCode(),
                        listJsonResult.getMsg()));
            }
            data.addAll(listJsonResult.getData());
        }
        if (org.apache.commons.collections.CollectionUtils.isEmpty(data)) {
            return Maps.newHashMap();
        }
        return data.stream().collect(Collectors.toMap(e -> e.getId(), e -> e, (k1, k2) -> k1));
    }

    @Data
    public static class GoodsSkuCode {
        private Long skuId;
        private String goodsCode;
        /**
         * 0-非套餐, 1-套餐
         */
        private Integer activity;
        /**
         * 套餐sku的芯子
         */
        private List<SubSkuDTO> subSkuList;
    }
}
