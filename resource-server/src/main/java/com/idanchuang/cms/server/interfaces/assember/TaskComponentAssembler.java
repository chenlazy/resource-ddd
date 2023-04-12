package com.idanchuang.cms.server.interfaces.assember;

import com.google.common.collect.Lists;
import com.idanchuang.activity.platform.client.common.enums.RewardRuleTypeEnum;
import com.idanchuang.activity.platform.client.dto.memberactivity.MemberActivityBaseInfoDTO;
import com.idanchuang.cms.api.response.TaskActivityConfigDTO;
import com.idanchuang.resource.server.infrastructure.utils.DateTimeUtil;

import java.util.Set;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-12 16:54
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public class TaskComponentAssembler {

    public static TaskActivityConfigDTO convertActivityConfig(MemberActivityBaseInfoDTO activityBaseInfoDTO) {

        TaskActivityConfigDTO activityConfigDTO = new TaskActivityConfigDTO();
        activityConfigDTO.setActivityId(activityBaseInfoDTO.getId());
        activityConfigDTO.setActivityName(activityBaseInfoDTO.getName());

        //获取激励规则
        Set<RewardRuleTypeEnum> ruleTypes = activityBaseInfoDTO.getRewardRuleTypes();
        if (ruleTypes.contains(RewardRuleTypeEnum.STEP_COMPLETE_REWARD)) {
            activityConfigDTO.setLevels(activityBaseInfoDTO.getUserScope());
        } else if (ruleTypes.contains(RewardRuleTypeEnum.COMPLETE_REWARD)) {
            //如果是全等级就返回0
            activityConfigDTO.setLevels(Lists.newArrayList(0));
        } else {
            return null;
        }
        activityConfigDTO.setStartTime(DateTimeUtil.asLocalDateTime(activityBaseInfoDTO.getStartTime()));
        activityConfigDTO.setEndTime(DateTimeUtil.asLocalDateTime(activityBaseInfoDTO.getEndTime()));
        return activityConfigDTO;
    }
}
