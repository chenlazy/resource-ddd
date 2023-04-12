package com.idanchuang.cms.server.infrastructure.schedule;

import com.idanchuang.cms.server.infrastructureNew.schedule.PopMessageJob;
import com.idanchuang.resource.server.SpringTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-04-22 17:33
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public class PopMessageJobTest extends SpringTest {

    @Resource
    private PopMessageJob popMessageJob;

    @Test
    public void testPopMessage() {
        try {
            popMessageJob.execute("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
