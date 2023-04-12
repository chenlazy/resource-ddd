package com.idanchuang.cms.server.application.service;

import com.google.common.collect.Lists;
import com.idanchuang.cms.api.response.GoodsMetaDataDTO;
import com.idanchuang.cms.api.response.PriceDataDTO;
import com.idanchuang.cms.server.application.enums.UserLevelEnum;
import com.idanchuang.cms.server.application.remote.RemoteGoodsService;
import com.idanchuang.cms.server.application.remote.RemotePointGoodsService;
import com.idanchuang.cms.server.application.remote.RemoteUserAddressService;
import com.idanchuang.cms.server.interfaces.assember.UserAddressAssembler;
import com.idanchuang.member.point.api.entity.dto.PointGoodsCouponDTO;
import com.idanchuang.member.point.api.entity.dto.PointGoodsCouponDetailsDTO;
import com.idanchuang.trade.goods.hulk.api.constant.SkuQueryModule;
import com.idanchuang.trade.goods.hulk.api.constant.SpuQueryModule;
import com.idanchuang.trade.goods.hulk.api.entity.dto.ConditionDTO;
import com.idanchuang.trade.goods.hulk.api.entity.dto.OutBrandDTO;
import com.idanchuang.trade.goods.hulk.api.entity.dto.SpuWarmUpConfigDTO;
import com.idanchuang.trade.goods.hulk.api.entity.dto.goods.SpuInfoDTO;
import com.idanchuang.trade.goods.hulk.api.entity.param.goods.DefaultSkuQuery;
import com.idanchuang.trade.member.api.entity.dto.UserAddressDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-12 17:10
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Service
@Slf4j
public class GoodsMetaDataService {

    @Resource
    private RemoteGoodsService remoteGoodsService;

    @Resource
    private RemotePointGoodsService remotePointGoodsService;

    @Resource
    private RemoteUserAddressService remoteUserAddressService;

    public List<GoodsMetaDataDTO> getList(List<Long> goodsIdList, List<String> userLevelList,
                                          Integer subjectGoodsType, Boolean priceEnable,
                                          Map<Long, SpuInfoDTO> maps) {
        List<GoodsMetaDataDTO> dataList = new LinkedList<>();
        for (Long goodsId : goodsIdList) {
            SpuInfoDTO dto = maps.get(goodsId);
            if (dto == null) {
                continue;
            }
            GoodsMetaDataDTO vo = new GoodsMetaDataDTO();
            vo.setGoodsId(dto.getId());
            vo.setGoodsImage(dto.getBaseSpuInfo().getImage());
            vo.setGoodsImageUrl(dto.getBaseSpuInfo().getImageFile().getFileUrl());
            if (StringUtils.isEmpty(dto.getBaseSpuInfo().getShortTitle())) {
                vo.setGoodsName(dto.getBaseSpuInfo().getName());
            } else {
                vo.setGoodsName(dto.getBaseSpuInfo().getShortTitle());
            }
            vo.setGoodsSubTitle(dto.getBaseSpuInfo().getSubTitle());
            //后台默认设置上架和有库存
            vo.setStatus(1);
            vo.setStock(1);
            vo.setGoodsInCar(dto.getBaseSpuInfo().getInCart());
            vo.setMarketPrice(dto.getDefaultSku().getBaseSkuInfoDTO().getMarketPrice());
            vo.setSkuId(dto.getDefaultSku().getId());
            Boolean containShipping = dto.getSpuExtInfoDTO().getGoodsContainShipping();
            vo.setGoodsContainShipping(containShipping);
            vo.setShippingText(null != containShipping ? (containShipping ? "包邮" : "") : "");
            OutBrandDTO outBrandDTO = dto.getOutBrandDTO();
            if (null != outBrandDTO) {
                if (null != outBrandDTO.getLogoImage()) {
                    vo.setBrandUrl(outBrandDTO.getLogoImage().getFileUrl());
                }
            }

            //是否限购 0 不限制 1 限制
            if (dto.getSpuExtInfoDTO().getEnableUserBuyLimit() == 1) {
                vo.setUserUpperLimit(dto.getSpuExtInfoDTO().getUserBuyLimit().getUserUpperLimit());
                vo.setUserLimitStartAt(dto.getSpuExtInfoDTO().getUserBuyLimit().getUserLimitStartAt());
                vo.setUserLimitEndAt(dto.getSpuExtInfoDTO().getUserBuyLimit().getUserLimitEndAt());
            }
            //是否开启预热 0-否 1-全部，2-部分
            if (dto.getSpuExtInfoDTO().getEnableWarmUpConfig() == 0) {
                vo.setEnableWarmUpSet(dto.getSpuExtInfoDTO().getEnableWarmUpConfig());
            } else {
                this.enableWarmUpSet(vo, dto, 86L);
            }
            if (priceEnable) {
                setPointGoodsInfo(vo);
            }
            //组件默认为实时
            vo.setGoodsImageEnable(1);
            vo.setGoodsNameEnable(1);
            vo.setGoodsSubTitleEnable(1);
            dataList.add(vo);
        }
        if (priceEnable) {
            this.setGoodsPriceBatch(userLevelList, dataList, subjectGoodsType, goodsIdList);
        }
        return dataList;
    }

    private void enableWarmUpSet(GoodsMetaDataDTO subjectGoodsDTO, SpuInfoDTO spuDTO, Long countryId) {
        List<SpuWarmUpConfigDTO> warmUpConfigList = spuDTO.getSpuExtInfoDTO().getWarmUpConfigList();
        String defaultSkuDeliveryType = spuDTO.getDefaultSkuDeliveryType();
        //预热条件 1 全部 2 部分
        if (spuDTO.getSpuExtInfoDTO().getEnableWarmUpConfig() == 1) {
            if (org.apache.commons.collections.CollectionUtils.isNotEmpty(warmUpConfigList)) {
                subjectGoodsDTO.setEnableWarmUpSet(1);
                subjectGoodsDTO.setWarmUpSaleAt(warmUpConfigList.get(0).getWarmUpSaleAt());
                subjectGoodsDTO.setWarmUpPrepareAt(warmUpConfigList.get(0).getWarmUpPrepareAt());
            } else {
                subjectGoodsDTO.setEnableWarmUpSet(0);
            }
        } else {
            if (org.apache.commons.collections.CollectionUtils.isNotEmpty(warmUpConfigList)) {
                for (SpuWarmUpConfigDTO spuWarmUpConfigDTO : warmUpConfigList) {
                    if (org.apache.commons.collections.CollectionUtils.isNotEmpty(spuWarmUpConfigDTO.getConditions())) {
                        for (ConditionDTO conditionDTO : spuWarmUpConfigDTO.getConditions()) {
                            if (org.apache.commons.collections.CollectionUtils.isNotEmpty(conditionDTO.getCountries())) {
                                if (conditionDTO.getCountries().contains(countryId) && defaultSkuDeliveryType.equals(conditionDTO.getDeliveryType())) {
                                    subjectGoodsDTO.setEnableWarmUpSet(1);
                                    subjectGoodsDTO.setWarmUpSaleAt(spuWarmUpConfigDTO.getWarmUpSaleAt());
                                    subjectGoodsDTO.setWarmUpPrepareAt(spuWarmUpConfigDTO.getWarmUpPrepareAt());
                                    return;
                                }
                            }
                        }
                    }
                }
            }
            subjectGoodsDTO.setEnableWarmUpSet(0);
        }
    }

    public Map<Long, SpuInfoDTO> getGoodsByGoodsId(List<Long> goodsIds, Integer subjectGoodsType, Boolean priceEnable) {
        DefaultSkuQuery defaultSkuQuery = new DefaultSkuQuery();
        defaultSkuQuery.setUserLevel(1);
        UserAddressDTO userDefaultAddress = remoteUserAddressService.getDefaultAddress();
        defaultSkuQuery.setUserAddressDto(UserAddressAssembler.memberDTOToDTO(userDefaultAddress));
        Map<Long, SpuInfoDTO> longSpuInfoDTOMap = remoteGoodsService.queryNewSpuInfoBySpuIdList(goodsIds, SpuQueryModule.searchDefaultSku, SkuQueryModule.PRICE_SEARCH_BIT, defaultSkuQuery, UserLevelEnum.REGISTER.getId(), subjectGoodsType);
        return longSpuInfoDTOMap;
    }

    public PointGoodsCouponDTO getPointGoodsByGoodsId(Long goodsId) {
        return remotePointGoodsService.getPointGoodsDetailQuery(goodsId);
    }

    /**
     * 根据当前用户信息，设置商品价格
     *
     * @param dataList
     */
    private void setGoodsPriceBatch(List<String> userLevelList, List<GoodsMetaDataDTO> dataList, Integer subjectGoodsType, List<Long> goodsIdList) {
        for (String userLevel : userLevelList) {
            DefaultSkuQuery defaultSkuQuery = new DefaultSkuQuery();
            defaultSkuQuery.setUserLevel(1);
            UserAddressDTO userDefaultAddress = remoteUserAddressService.getDefaultAddress();
            defaultSkuQuery.setUserAddressDto(UserAddressAssembler.memberDTOToDTO(userDefaultAddress));
            Map<Long, SpuInfoDTO> longSpuInfoDTOMap = remoteGoodsService.queryNewSpuInfoBySpuIdList(goodsIdList, SpuQueryModule.searchDefaultSku, SkuQueryModule.PRICE_SEARCH_BIT, defaultSkuQuery, Integer.parseInt(userLevel), subjectGoodsType);
            BigDecimal pointValue = null;
            BigDecimal cashValue = null;
            BigDecimal amountPromotionPrice = null;
            for (GoodsMetaDataDTO goods : dataList) {
                SpuInfoDTO goodsPrice = longSpuInfoDTOMap.get(goods.getGoodsId());
                if (goodsPrice != null) {
                    if (goods.getPrice() == null) {
                        goods.setPrice(new ArrayList<>());
                    }
                    PointGoodsCouponDetailsDTO pointGoods = this.getCashValue(goods.getGoodsId(), Integer.parseInt(userLevel));
                    if (pointGoods != null) {
                        pointValue = pointGoods.getPointValue();
                        cashValue = pointGoods.getCashValue();
                        amountPromotionPrice = goodsPrice.getDefaultSku().getGoodsPriceVO().getPresentAmountPrice();
                    }
                    goods.getPrice().add(new PriceDataDTO(userLevel, goodsPrice.getDefaultSku().getGoodsPriceVO().getPresentAmountPrice(), pointValue, cashValue, amountPromotionPrice));

                    //设置日常价
                    if (goods.getNormalPrice() == null) {
                        goods.setNormalPrice(Lists.newArrayList());
                    }

                    BigDecimal amountPrice = goodsPrice.getDefaultSku().getGoodsPriceVO().getAmountPrice();
                    goods.getNormalPrice().add(new PriceDataDTO(userLevel, amountPrice, null, null, null));
                }
            }
        }
    }

    private void setPointGoodsInfo(GoodsMetaDataDTO vo) {
        PointGoodsCouponDTO pointGoods = remotePointGoodsService.getPointGoodsDetailQuery(vo.getGoodsId());
        if (pointGoods == null) {
            return;
        }
        vo.setPointStatus(pointGoods.getStatus());
        vo.setPointBeginTime(pointGoods.getBeginTime());
        vo.setPointEndTime(pointGoods.getEndTime());
        vo.setPayType(pointGoods.getPayType());
    }

    private PointGoodsCouponDetailsDTO getCashValue(Long goodsId, Integer level) {
        PointGoodsCouponDTO pointGoods = remotePointGoodsService.getPointGoodsDetailQuery(goodsId);
        if (pointGoods == null) {
            return null;
        }
        List<PointGoodsCouponDetailsDTO> detailList = pointGoods.getDetails();
        if (CollectionUtils.isEmpty(detailList)) {
            return null;
        }
        for (PointGoodsCouponDetailsDTO dto : detailList) {
            if (level.equals(dto.getMemberLevel())) {
                return dto;
            }
        }
        return null;
    }
}
