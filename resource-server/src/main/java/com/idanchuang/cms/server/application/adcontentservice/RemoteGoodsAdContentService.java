package com.idanchuang.cms.server.application.adcontentservice;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.idanchuang.cms.server.application.adcontentservice.transfer.UserAddressTransfer;
import com.idanchuang.cms.server.application.remote.RemoteUserAddressService;
import com.idanchuang.cms.server.infrastructure.adcontentservice.util.BusinessException;
import com.idanchuang.cms.server.infrastructure.adcontentservice.util.ErrorEnum;
import com.idanchuang.cms.server.infrastructure.adcontentservice.util.ListUtils;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.trade.goods.hulk.api.constant.SkuQueryModule;
import com.idanchuang.trade.goods.hulk.api.constant.SpuQueryModule;
import com.idanchuang.trade.goods.hulk.api.entity.dto.goods.SpuInfoDTO;
import com.idanchuang.trade.goods.hulk.api.entity.param.goods.DefaultSkuQuery;
import com.idanchuang.trade.goods.hulk.api.entity.param.goods.SimpleBatchGoodsPriceQuery;
import com.idanchuang.trade.goods.hulk.api.entity.param.goods.SpuSearchQuery;
import com.idanchuang.trade.goods.hulk.api.service.goods.SpuQueryApi;
import com.idanchuang.trade.member.api.entity.dto.UserAddressDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: jww <weiwei.ji@beibei.com>
 * @Date: 2020-12-10 20:44
 * @Desc:
 * @Copyright Beicang Limited. All rights reserved.
 */
@Service
@Slf4j
public class RemoteGoodsAdContentService {

    @Resource
    private SpuQueryApi spuQueryApi;
    @Resource
    private RemoteUserAddressService remoteUserAddressService;

    public Map<Long, SpuInfoDTO> queryNewSpuInfoBySpuIdList(List<Long> spuIdList, Integer spuModule, Integer skuModule, DefaultSkuQuery defaultSkuQuery, Integer level, Integer type) {
        List<List<Long>> lists = ListUtils.split(spuIdList, 20);
        List<SpuInfoDTO> data = Lists.newArrayList();
        for (List<Long> list : lists) {
            SpuSearchQuery request = new SpuSearchQuery();
            Set<Long> spuIds = new HashSet<>(list);
            request.setSpuIds(spuIds);
            request.setSpuModule(spuModule);
            request.setSkuModule(skuModule);
            request.setAllowLevelFlag(1);
            request.setSpuStatusList(Lists.newArrayList(1, 2));
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
                throw new BusinessException(ErrorEnum.GOODS_INFO_ERROR);
            }
            if (!listJsonResult.isSuccess() || listJsonResult.getData() == null) {
                log.error("获取商品基本信息接口出现异常 request:{} listJsonResult:{}", request, listJsonResult);
                throw new BusinessException(listJsonResult.getCode(), listJsonResult.getMsg());
            }
            data.addAll(listJsonResult.getData());
        }
        if (CollectionUtils.isEmpty(data)) {
            return Maps.newHashMap();
        }
        return data.stream().collect(Collectors.toMap(e -> e.getId(), e -> e, (k1, k2) -> k1));
    }

    public <R> Map<Long, R> getSpuMapByIdList(List<Long> spuIdList, Function<SpuInfoDTO, R> mapper) {
        UserAddressDTO defaultAddress = remoteUserAddressService.getDefaultAddress();
        if (defaultAddress == null) {
            return Maps.newHashMap();
        }
        SpuSearchQuery spuSearchQuery = new SpuSearchQuery();
        spuSearchQuery.setSpuIds(Sets.newHashSet(spuIdList));
        spuSearchQuery.setSpuModule(SpuQueryModule.searchDefaultSku);
        spuSearchQuery.setSkuModule(SkuQueryModule.BASE_SEARCH_BIT);
        spuSearchQuery.setAllowLevelFlag(1);
        spuSearchQuery.setSpuStatusList(Lists.newArrayList(1, 2));
        DefaultSkuQuery defaultSkuQuery = new DefaultSkuQuery();
        defaultSkuQuery.setUserAddressDto(UserAddressTransfer.memberDTOToDTO(defaultAddress));
        spuSearchQuery.setDefaultSkuQuery(defaultSkuQuery);
        log.info("根据参数批量查询spu，参数：{}", JSON.toJSONString(spuSearchQuery));
        JsonResult<List<SpuInfoDTO>> listJsonResult;
        try {
            listJsonResult = spuQueryApi.querySpuList(spuSearchQuery);
        } catch (Exception e) {
            log.error("获取商品基本信息接口出现异常 getSpuMapByIdList request:{} e:{}", JSON.toJSONString(spuSearchQuery), e);
            throw new BusinessException(ErrorEnum.GOODS_INFO_ERROR);
        }

        if (listJsonResult == null || !listJsonResult.isSuccess()) {
            return Maps.newHashMap();
        }
        return listJsonResult.getData().stream().collect(Collectors.toMap(e -> e.getBaseSpuInfo().getId(), mapper));
    }

}
