package com.idanchuang.cms.server.interfaces.web.vo;

import com.idanchuang.cms.api.request.GoodsMetaDataAddReq;
import com.idanchuang.cms.api.response.GoodsMetaDataDTO;
import com.idanchuang.cms.server.application.service.GoodsMetaDataService;
import com.idanchuang.component.base.exception.core.ExDefinition;
import com.idanchuang.component.base.exception.core.ExType;
import com.idanchuang.component.base.exception.exception.BusinessException;
import com.idanchuang.trade.goods.hulk.api.entity.dto.goods.SpuInfoDTO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-12 17:46
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
@Slf4j
public class GoodsInfoVO implements Serializable {

    private static final long serialVersionUID = -9003921221997137933L;

    private GoodsInfoVO() {
    }

    public GoodsInfoVO(GoodsMetaDataService goodsMetaDataService) {
        this.goodsMetaDataService = goodsMetaDataService;
    }

    private List<String> type = Arrays.asList("1", "2", "3", "4", "5");

    private Long componentId = System.currentTimeMillis();

    private Long operatorId;

    private GoodsMetaDataService goodsMetaDataService;

    private GoodsMetaDataAddReq req;

    private List<GoodsMetaDataDTO> metadataList;

    public void setReq(GoodsMetaDataAddReq req) {
        this.req = req;
        if (req.getComponentId() != null) {
            this.componentId = req.getComponentId();
        }
    }

    public void validGoodsExist() {
        int addSize = req.getGoodsIdList() != null ? req.getGoodsIdList().size() : 0;
        if (addSize <= 0) {
            return;
        }
        String goodsText = getGoodsText();
        List<String> tempGoodsIdList = new ArrayList<>(req.getGoodsIdList().size());
        for (String idStr : req.getGoodsIdList()) {
            if (tempGoodsIdList.contains(idStr)) {
                throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, 1023,
                        goodsText + "ID：" + idStr + "重复，请不要重复提交"));
            }
            tempGoodsIdList.add(idStr);
        }
        Integer sum = req.getPriceEnable() ? 40 : 200;
        if (req.getGoodsIdList().size() > sum) {
            throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, 10523, "一个组件，最多只能添加" + sum + "个" + goodsText));
        }
    }

    public void validGoodsUsable() {
        String goodsText = getGoodsText();
        Map<Long, SpuInfoDTO> maps = goodsMetaDataService.getGoodsByGoodsId(req.getGoodsIds(), req.getSubjectGoodsType(), req.getPriceEnable());
        for (Long goodsId : req.getGoodsIds()) {
            SpuInfoDTO goodsSpu = maps.get(goodsId);
            if (goodsSpu == null) {
                throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, 1023, goodsText + "ID：" + goodsId + "错误，请检查ID是否存在"));
            }
            if (req.getSubjectGoodsType() == 2 && goodsMetaDataService.getPointGoodsByGoodsId(goodsId) == null) {
                throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, 1024, goodsText + "ID：" + goodsId + "错误，请检查ID是否存在"));
            }
            if (goodsSpu.getBaseSpuInfo().getStatus() != null && goodsSpu.getBaseSpuInfo().getStatus() == 0) {
                throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, 1025, goodsText + "ID：" + goodsId + "为草稿状态，请将商品上架"));
            }
        }
        loadSubjectGoodsMetadata(maps);
    }

    private void loadSubjectGoodsMetadata(Map<Long, SpuInfoDTO> maps) {
        this.metadataList = goodsMetaDataService.getList(req.getGoodsIds(), this.type, req.getSubjectGoodsType(), req.getPriceEnable(), maps);
        if (CollectionUtils.isNotEmpty(this.metadataList)) {
            this.metadataList.forEach(e -> {
                e.setComponentId(this.componentId);
                e.setSubjectGoodsType(req.getSubjectGoodsType());
            });
        }
    }

    private String getGoodsText() {
        if (req.getSubjectGoodsType() == 2) {
            return "积分商品";
        }
        return "商品";
    }
}
