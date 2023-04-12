package com.idanchuang.cms.server.infrastructure.transfer;

import com.google.common.collect.Lists;
import com.idanchuang.cms.api.common.constants.ModelNameConstant;
import com.idanchuang.cms.api.model.CmsGoods;
import com.idanchuang.cms.server.domain.model.cms.GoodsInfo;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-14 20:31
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public class CmsGoodsTransfer {

    public static List<CmsGoods> convertGoodsInfo(List<GoodsInfo> goodsInfos, String type) {

        if (CollectionUtils.isEmpty(goodsInfos)) {
            return Lists.newArrayList();
        }
        List<CmsGoods> cmsGoodsList = new ArrayList<>();

        for (GoodsInfo goodsInfo : goodsInfos) {
            CmsGoods cmsGoods = new CmsGoods();
            cmsGoods.setGoodsId(goodsInfo.getGoodsId());
            cmsGoods.setGiftImage(goodsInfo.getGiftImage());
            //默认自定义
            Integer goodsNameEnable = null != goodsInfo.getGoodsNameEnable() ? goodsInfo.getGoodsNameEnable() : 2;
            cmsGoods.setGoodsNameEnable(goodsNameEnable);
            Integer goodsImageEnable = null != goodsInfo.getGoodsImageEnable() ? goodsInfo.getGoodsImageEnable() : 2;
            cmsGoods.setGoodsImageEnable(goodsImageEnable);
            Integer goodsSubTitleEnable = null != goodsInfo.getGoodsSubTitleEnable() ? goodsInfo.getGoodsSubTitleEnable() : 2;
            cmsGoods.setGoodsSubTitleEnable(goodsSubTitleEnable);

            cmsGoods.setGoodsName(goodsNameEnable == 2 ? goodsInfo.getGoodsName() : null);
            cmsGoods.setGoodsImage(goodsImageEnable == 2 ? goodsInfo.getGoodsImage() : null);
            cmsGoods.setGoodsSubTitle(goodsSubTitleEnable == 2 ? goodsInfo.getGoodsSubTitle() : null);

            cmsGoods.setTextBgImage(!StringUtils.isEmpty(goodsInfo.getTextBackgroundImage()) ? goodsInfo.getTextBackgroundImage() : "");
            cmsGoods.setCreateTime(goodsInfo.getCreateTime());
            cmsGoods.setLimitNum(goodsInfo.getLimitNum());
            cmsGoods.setOperatorId(goodsInfo.getOperatorId());
            List<String> salesPoint = goodsInfo.getSalesPoint();
            cmsGoods.setSalesPoint(!CollectionUtils.isEmpty(salesPoint) ? JsonUtil.toJsonString(salesPoint) : "");

            if (null != goodsInfo.getSubjectGoodsType()) {
                cmsGoods.setSubjectGoodsType(goodsInfo.getSubjectGoodsType());
            } else {
                Integer subjectGoodsType = ModelNameConstant.COMP_INTEGRAL_GOOD.equals(type) ? 2 : 1;
                cmsGoods.setSubjectGoodsType(subjectGoodsType);
            }
            cmsGoods.setSwellPercentage(goodsInfo.getSwellPercentage());
            cmsGoods.setComponentId(goodsInfo.getComponentId());
            cmsGoods.setUpdateTime(LocalDateTime.now());
            cmsGoodsList.add(cmsGoods);
        }
        return cmsGoodsList;
    }
}
