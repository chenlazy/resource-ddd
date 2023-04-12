package com.idanchuang.cms.server.infrastructure.schedule;

import com.idanchuang.cms.server.infrastructureNew.schedule.CorePageRemindJob;
import com.idanchuang.resource.server.SpringTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-03-10 15:51
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Slf4j
public class CardNoticeJobTest extends SpringTest {

    @Resource
    private CorePageRemindJob corePageRemindJob;

    @Test
    public void testCardNotice() {
        try {
            corePageRemindJob.execute("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
