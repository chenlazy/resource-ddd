package com.idanchuang.cms.server.application.remote;

import com.alibaba.fastjson.JSONObject;
import com.idanchuang.activity.platform.client.api.ActivityClient;
import com.idanchuang.activity.platform.client.common.enums.ActivityRuleTypeEnum;
import com.idanchuang.activity.platform.client.dto.MemberActivityConfigDTO;
import com.idanchuang.activity.platform.client.dto.memberactivity.MemberActivityBaseInfoDTO;
import com.idanchuang.component.base.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-12 16:45
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Service
@Slf4j
public class RemoteActivityService {

    @Resource
    private ActivityClient activityClient;

    public MemberActivityBaseInfoDTO getConsumeActivityConfig(Long activityId) {

        try {

            MemberActivityConfigDTO activityConfigDTO = new MemberActivityConfigDTO();
            activityConfigDTO.setActivityId(activityId);
            activityConfigDTO.setActivityRule(ActivityRuleTypeEnum.CONSUMPTION_REWARD);
            JsonResult<MemberActivityBaseInfoDTO> activityConfig = activityClient.getMemberActivityConfig(activityConfigDTO);
            log.info("getConsumeActivityConfig fetch info, activityId:{}, activityConfig:{}", activityId, JSONObject.toJSONString(activityConfig));

            if (activityConfig.isSuccess()) {
                return activityConfig.getData();
            }
        } catch (Exception e) {
            log.error("getConsumeActivityConfig fetch error, activityId:{}, e:{}", activityId, e);
        }

        return null;
    }
}
