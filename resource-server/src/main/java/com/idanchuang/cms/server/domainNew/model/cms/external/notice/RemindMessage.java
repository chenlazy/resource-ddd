package com.idanchuang.cms.server.domainNew.model.cms.external.notice;

import com.idanchuang.cms.server.domainNew.model.cms.external.remind.UserRemindRecord;
import lombok.Value;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-03-31 10:26
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Value
public class RemindMessage {

    /**
     * 通知记录列表
     */
    private List<UserRemindRecord> remindRecords;

    /**
     * 渠道类型
     */
    private ChannelType channelType;
}
