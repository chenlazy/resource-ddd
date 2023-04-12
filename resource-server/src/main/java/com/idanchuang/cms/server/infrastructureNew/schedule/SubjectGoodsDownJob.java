package com.idanchuang.cms.server.infrastructureNew.schedule;

import com.google.common.collect.Lists;
import com.idanchuang.cms.api.facade.GoodsComponentFacade;
import com.idanchuang.cms.api.response.SubjectEnableGoodsInfoDTO;
import com.idanchuang.cms.server.application.remote.RemoteGoodsService;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.trade.goods.hulk.api.constant.SpuStatusEnum;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-03-31 15:35
 * @Desc: 专题页面商品下架脚本
 * @Copyright VTN Limited. All rights reserved.
 */
@Component
@Slf4j
@JobHandler(value = "SubjectGoodsDownJob")
public class SubjectGoodsDownJob extends IJobHandler {

    @Resource
    private GoodsComponentFacade goodsComponentFacade;

    @Resource
    private RemoteGoodsService remoteGoodsService;

    private static final String SOURCE_NAME = "ABM_MANAER_BIZ";

    @Override
    public ReturnT<String> execute(String s) throws Exception {

        JsonResult<List<SubjectEnableGoodsInfoDTO>> subjectEnableGoods = goodsComponentFacade.getSubjectEnableGoods(LocalDateTime.now());

        if (!subjectEnableGoods.isSuccess()) {
            log.warn("调用 GoodsComponentFacade#getSubjectEnableGoods 异常，msg: {}", subjectEnableGoods.getMsg());
            return new ReturnT<>(ReturnT.SUCCESS_CODE, "专题商品查询异常");
        }

        if (CollectionUtils.isEmpty(subjectEnableGoods.getData())) {
            return new ReturnT<>(ReturnT.SUCCESS_CODE, "无自动下架专题");
        }
        List<Long> ids = Lists.newArrayList();
        List<Long> goodsList = Lists.newArrayList();
        for (SubjectEnableGoodsInfoDTO goodsInfoDTO : subjectEnableGoods.getData()) {
            ids.add(goodsInfoDTO.getSubjectId());
            goodsList.addAll(goodsInfoDTO.getGoodsIds());
        }
        if (CollectionUtils.isEmpty(goodsList)) {
            return new ReturnT<>(ReturnT.SUCCESS_CODE, "无自动下架商品");
        }

        //商品id进行分批
        List<List<Long>> spuIdList = Lists.partition(goodsList, 20);
        //商品下架
        spuIdList.parallelStream().forEach(p -> remoteGoodsService.upOrDownGoods(p, SOURCE_NAME, SpuStatusEnum.PUT_OFF.getCode()));

        //变更自动下架状态
        ids.parallelStream().forEach(u -> goodsComponentFacade.updateSubjectEnable(u, 0));

        return new ReturnT<>(ReturnT.SUCCESS_CODE, ids.toString() + "专题商品自动下架成功");
    }
}
