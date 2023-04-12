package com.idanchuang.cms.server.application.service;

import com.idanchuang.activity.platform.client.dto.memberactivity.MemberActivityBaseInfoDTO;
import com.idanchuang.cms.api.response.TaskActivityConfigDTO;
import com.idanchuang.cms.server.application.remote.RemoteActivityService;
import com.idanchuang.cms.server.interfaces.assember.TaskComponentAssembler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-12 16:34
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Service
@Slf4j
public class TaskComponentService {

    @Resource
    private RemoteActivityService remoteActivityService;

    public TaskActivityConfigDTO getTaskActivityConfig(Long activityId) {

        MemberActivityBaseInfoDTO activityConfig = remoteActivityService.getConsumeActivityConfig(activityId);

        if (null != activityConfig) {
            return TaskComponentAssembler.convertActivityConfig(activityConfig);
        }
        return null;
    }
}
