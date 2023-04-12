package com.idanchuang.cms.server.domainNew.model.cms.external.remind;

import lombok.Value;

import java.time.LocalDateTime;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-03-28 16:41
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Value
public class UserRemindRecord {

    /**
     * 主键id
     */
    private UserRemindId id;

    /**
     * 用户unionId
     */
    private String unionId;

    /**
     * 提醒id
     */
    private String remindId;

    /**
     * 专题id
     */
    private String componentId;

    /**
     * 用户id
     */
    private UserId userId;

    /**
     * 消息标题
     */
    private String msgTitle;

    /**
     * 消息内容
     */
    private String msgContent;

    /**
     * 跳转链接
     */
    private String target;

    /**
     * 提醒时间
     */
    private LocalDateTime remindSendTime;

    /**
     * 提醒状态
     */
    private RemindStatus remindStatus;

    /**
     * 提醒类型
     */
    private RemindType remindType;

}
