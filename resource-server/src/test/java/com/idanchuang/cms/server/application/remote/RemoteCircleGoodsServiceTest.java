package com.idanchuang.cms.server.application.remote;

import com.idanchuang.resource.server.SpringTest;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author fym
 * @description :
 * @date 2022/5/11 下午2:47
 */
public class RemoteCircleGoodsServiceTest extends SpringTest {

    @Resource
    private RemoteCircleGoodsService remoteCircleGoodsService;

    @Test
    public void getSpuIdListByBizId() {
        List<Long> spuIdListByBizId = remoteCircleGoodsService.getSpuIdListByBizId(1L);
        System.out.println(spuIdListByBizId);
    }
}