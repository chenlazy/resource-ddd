package com.idanchuang.cms.api.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lei.liu
 * @date 2021/10/9
 */
@Data
public class LuckyComponentInfo implements Serializable {
    private static final long serialVersionUID = 2819477929065071484L;

    /**
     * 抽奖活动ID
     */
    private Long activityId;
}
