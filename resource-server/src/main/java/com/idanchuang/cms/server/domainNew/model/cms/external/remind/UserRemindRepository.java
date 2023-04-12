package com.idanchuang.cms.server.domainNew.model.cms.external.remind;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-03-28 17:13
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public interface UserRemindRepository {

    Integer getRemindRecordCount(RemindStatus status, List<RemindType> remindTypes, LocalDateTime remindSendTime);

    List<UserRemindRecord> listRemindRecords(Integer page, Integer pageSize, RemindStatus status,
                                             List<RemindType> remindTypes, LocalDateTime remindSendTime);

    List<UserRemindRecord> listNearRemindRecords(UserId userId, String componentId, RemindType remindType,
                                                 RemindStatus status, LocalDateTime remindTime);

    boolean updateRemindRecordStatus(List<UserRemindId> remindIds, RemindStatus status);
}
