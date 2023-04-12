package com.idanchuang.cms.server.interfaces.controller;

import com.idanchuang.cms.api.facade.GoodsComponentFacade;
import com.idanchuang.cms.api.request.GoodsComponentQueryReq;
import com.idanchuang.cms.api.response.GoodsComponentPageDTO;
import com.idanchuang.cms.api.response.GoodsInfoDTO;
import com.idanchuang.cms.api.response.SubjectEnableGoodsInfoDTO;
import com.idanchuang.cms.server.application.service.GoodsComponentService;
import com.idanchuang.cms.server.applicationNew.service.ComponentGoodsService;
import com.idanchuang.cms.server.domain.model.cms.GoodsComponentQuery;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.component.base.page.PageData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lei.liu
 * @date 2021/9/13
 */
@RestController
@RequestMapping("/cms/goods/component")
@Slf4j
public class GoodsComponentFacadeImpl implements GoodsComponentFacade {

    @Resource
    private ComponentGoodsService componentGoodsService;
    @Resource
    private GoodsComponentService goodsComponentService;

    @Override
    public JsonResult<List<GoodsInfoDTO>> selectListForGoods(Long componentId) {
        return JsonResult.success(componentGoodsService.selectListForGoods(componentId));
    }

    @Override
    public JsonResult<PageData<GoodsInfoDTO>> selectPageForGoods(GoodsComponentQueryReq req) {
        GoodsComponentQuery query = new GoodsComponentQuery();
        query.setComponentId(req.getComponentId());
        query.setLimit(req.getLimit());
        return JsonResult.success(goodsComponentService.selectPageForGoods(query));
    }

    /**
     * 获取专题自动下架商品列表
     *
     * @param time
     * @return
     */
    @Override
    public JsonResult<List<SubjectEnableGoodsInfoDTO>> getSubjectEnableGoods(LocalDateTime time) {
        return JsonResult.success(componentGoodsService.getSubjectGoodsList(time));
    }

    /**
     * 更新专题自动下架状态
     *
     * @param subjectId
     * @param enable
     * @return
     */
    @Override
    public JsonResult updateSubjectEnable(Long subjectId, Integer enable) {
        componentGoodsService.updateSubjectEnable(subjectId, enable);
        return JsonResult.success();
    }

    @Override
    public JsonResult<Long> getGoodsTagBySubjectId(Long subjectId) {
        return JsonResult.success(componentGoodsService.getGoodsTagBySubjectId(subjectId));
    }

    @Override
    public JsonResult<List<GoodsComponentPageDTO>> getGoodsComponentPage(List<Integer> tagIdList) {
        return JsonResult.success(componentGoodsService.getGoodsComponentPage(tagIdList));
    }
}
