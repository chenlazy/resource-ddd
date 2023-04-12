package com.idanchuang.cms.server.infrastructureNew.schedule.model;

import lombok.Data;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-03-08 13:57
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class PageNotice {

    /**
     * 通知人列表
     */
    private Long operatorId;

    /**
     * 创建人id
     */
    private Long createId;

    /**
     * 页面id
     */
    private Long catalogueId;

    /**
     * 页面名称
     */
    private String pageName;

    /**
     * 活动id
     */
    private Integer activityId;

    /**
     * 模版id
     */
    private Long masterplateId;

    /**
     * 页面别名
     */
    private String aliasTitle;

    /**
     * 通知的信息
     */
    private String noticeInfo;

}
