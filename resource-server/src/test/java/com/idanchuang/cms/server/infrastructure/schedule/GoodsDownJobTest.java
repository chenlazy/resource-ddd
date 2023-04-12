package com.idanchuang.cms.server.infrastructure.schedule;

import com.idanchuang.cms.server.infrastructureNew.schedule.SubjectGoodsDownJob;
import com.idanchuang.resource.server.SpringTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-04-07 10:12
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public class GoodsDownJobTest extends SpringTest {

    @Resource
    private SubjectGoodsDownJob goodsDownJob;

    @Test
    public void testGoodsDown() {
        try {
            goodsDownJob.execute("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
