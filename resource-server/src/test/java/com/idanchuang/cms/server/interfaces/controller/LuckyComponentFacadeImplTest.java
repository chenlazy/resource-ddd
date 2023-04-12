package com.idanchuang.cms.server.interfaces.controller;

import com.alibaba.fastjson.JSON;
import com.idanchuang.cms.api.response.LuckyComponentActivityDTO;
import com.idanchuang.cms.api.response.LuckyComponentPrizeDTO;
import com.idanchuang.cms.server.application.remote.RemoteLuckyService;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.resource.server.SpringTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lei.liu
 * @date 2021/10/10
 */
@Slf4j
public class LuckyComponentFacadeImplTest extends SpringTest {

    @Resource
    private LuckyComponentFacadeImpl luckyComponentFacadeImpl;

    @Resource
    private RemoteLuckyService remoteLuckyService;

    @Test
    public void getLuckyComponentPageId() {
        JsonResult<String> result = luckyComponentFacadeImpl.getLuckyComponentPageId(578L);
        log.info(JSON.toJSONString(result));
    }

    @Test
    public void getActivityDetail() {
        LuckyComponentActivityDTO detail = remoteLuckyService.getActivityDetail(321L);
        Assert.assertNotNull(detail);
    }

    @Test
    public void getActivityPrize() {
        List<LuckyComponentPrizeDTO> activityPrize = remoteLuckyService.getActivityPrize(324L);
        Assert.assertNotNull(activityPrize);
    }
}