package com.idanchuang.cms.server.infrastructure.schedule;

import com.idanchuang.cms.server.infrastructureNew.schedule.UserShootRemindJob;
import com.idanchuang.resource.server.SpringTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-04-07 10:03
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public class UserRemindJobTest extends SpringTest {

    @Resource
    private UserShootRemindJob shootRemindJob;

    @Test
    public void testUserRemind() {
        try {
            shootRemindJob.execute("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
