package com.idanchuang.cms.server.application.remote;

import com.idanchuang.component.base.JsonResult;
import com.idanchuang.message.dingding.api.client.MessageSendApi;
import com.idanchuang.message.dingding.api.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-03-18 16:47
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Service
@Slf4j
public class RemoteMessageService {

    @Resource
    private MessageSendApi messageSendApi;

    public UserVo getSingleUser(String jobNumber) {

        try {
            JsonResult<UserVo> singleUser = messageSendApi.getSingleUser(jobNumber);

            if (singleUser.isSuccess()) {
                return singleUser.getData();
            }

        } catch (Exception e) {
            log.error("RemoteMessageService messageSendApi error, jobNumber:{}, e:{}", jobNumber, e.getMessage(), e);
        }

        return null;
    }
}
