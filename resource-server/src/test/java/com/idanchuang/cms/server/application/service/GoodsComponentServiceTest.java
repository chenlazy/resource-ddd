package com.idanchuang.cms.server.application.service;

import com.alibaba.fastjson.JSON;
import com.idanchuang.cms.api.response.GoodsInfoDTO;
import com.idanchuang.cms.api.response.SubjectEnableGoodsInfoDTO;
import com.idanchuang.resource.server.SpringTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lei.liu
 * @date 2021/9/27
 */
@Slf4j
public class GoodsComponentServiceTest extends SpringTest {

    @Resource
    private GoodsComponentService goodsComponentService;

    @Test
    public void getSubjectGoodsList() {
        List<SubjectEnableGoodsInfoDTO> subjectGoodsList = goodsComponentService.getSubjectGoodsList(LocalDateTime.now());
        log.info(JSON.toJSONString(subjectGoodsList));
    }

    @Test
    public void selectListForGoods() {
        List<GoodsInfoDTO> goodsInfoList = goodsComponentService.selectListForGoods(1632281996101L);
        log.info(JSON.toJSONString(goodsInfoList));
    }
}