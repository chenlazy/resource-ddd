package com.idanchuang.cms.server.infrastructureNew.repository.convertor;

import com.idanchuang.cms.server.domainNew.model.cms.external.remind.RemindStatus;
import com.idanchuang.cms.server.domainNew.model.cms.external.remind.RemindType;
import com.idanchuang.cms.server.domainNew.model.cms.external.remind.UserId;
import com.idanchuang.cms.server.domainNew.model.cms.external.remind.UserRemindId;
import com.idanchuang.cms.server.domainNew.model.cms.external.remind.UserRemindRecord;
import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.abmau.UserRemindRecordDO;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-04-01 14:56
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public class RepositoryUserRemindConvert {

    private RepositoryUserRemindConvert() {

    }

    public static UserRemindRecord doToDomain(UserRemindRecordDO remindRecordDO) {

        if (null == remindRecordDO) {
            return null;
        }

        return new UserRemindRecord(new UserRemindId(remindRecordDO.getId()),
                remindRecordDO.getUnionId(), remindRecordDO.getRemindId(),
                remindRecordDO.getComponentId(), new UserId(remindRecordDO.getUserId()),
                remindRecordDO.getMsgTitle(), remindRecordDO.getMsgContent(),
                remindRecordDO.getTarget(), remindRecordDO.getRemindSendTime(),
                RemindStatus.fromVal(remindRecordDO.getRemindStatus()),
                RemindType.fromVal(remindRecordDO.getRemindType()));
    }
}
