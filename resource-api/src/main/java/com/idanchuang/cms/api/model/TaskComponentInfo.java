package com.idanchuang.cms.api.model;

import lombok.Data;

import java.util.Map;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-27 13:56
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class TaskComponentInfo {

    /**
     * 活动id
     */
    private String activityId;

    /**
     * 活动任务配置
     */
    private Map<Integer, ActivityConfig> activityLevel;

    /**
     * 活动奖品配置
     */
    private ActivityPrize activityPrize;


    @Data
    public static class ActivityConfig {

        /**
         * 任务名称
         */
        private String name;

        /**
         * 任务描述
         */
        private String describe;
    }

    @Data
    public static class ActivityPrize {

        /**
         * 奖品名
         */
        private String name;

        /**
         * 奖品数量
         */
        private String num;
    }
}
