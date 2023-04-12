package com.idanchuang.cms.server.interfaces.controller;

import com.alibaba.fastjson.JSON;
import com.idanchuang.cms.api.request.GoodsComponentPageCondition;
import com.idanchuang.cms.api.request.GoodsComponentQueryReq;
import com.idanchuang.cms.api.response.GoodsComponentPageDTO;
import com.idanchuang.cms.api.response.GoodsInfoDTO;
import com.idanchuang.cms.api.response.SubjectEnableGoodsInfoDTO;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.component.base.page.PageData;
import com.idanchuang.resource.server.SpringTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * @author lei.liu
 * @date 2021/9/14
 */
@Slf4j
public class GoodsComponentFacadeImplTest extends SpringTest {

    @Resource
    private GoodsComponentFacadeImpl goodsComponentFacadeImpl;

    @Test
    public void selectListForGoods() {
        JsonResult<List<GoodsInfoDTO>> result = goodsComponentFacadeImpl.selectListForGoods(1632723109821L);
        log.info(JSON.toJSONString(result));
    }

    @Test
    public void selectPageForGoods() {
        GoodsComponentQueryReq query = new GoodsComponentQueryReq();
        query.setComponentId(1622008897702L);
        query.setLimit(40);
        JsonResult<PageData<GoodsInfoDTO>> result = goodsComponentFacadeImpl.selectPageForGoods(query);
        log.info(JSON.toJSONString(result));
    }

    @Test
    public void getSubjectEnableGoods() {
        JsonResult<List<SubjectEnableGoodsInfoDTO>> result = goodsComponentFacadeImpl.getSubjectEnableGoods(LocalDateTime.now());
        log.info(JSON.toJSONString(result));
    }

    @Test
    public void updateSubjectEnable() {
    }

    @Test
    public void getGoodsTagBySubjectId() {
    }

    @Test
    public void getGoodsComponentPage() {
        JsonResult<List<GoodsComponentPageDTO>> result = goodsComponentFacadeImpl.getGoodsComponentPage(Collections.singletonList(1000013));
        log.info(JSON.toJSONString(result));
    }
}