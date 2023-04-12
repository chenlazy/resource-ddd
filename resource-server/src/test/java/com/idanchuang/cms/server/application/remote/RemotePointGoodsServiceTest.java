package com.idanchuang.cms.server.application.remote;

import com.idanchuang.resource.server.SpringTest;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @author fym
 * @description :
 * @date 2022/5/13 上午10:42
 */
public class RemotePointGoodsServiceTest extends SpringTest {

    @Resource
    private RemotePointGoodsService remotePointGoodsService;

    @Test
    public void getActivityInfoByIdOpinionValidity() {
        remotePointGoodsService.getActivityInfoByIdOpinionValidity(749L);
    }
}