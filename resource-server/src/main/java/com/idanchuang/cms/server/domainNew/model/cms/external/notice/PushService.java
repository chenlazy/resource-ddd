package com.idanchuang.cms.server.domainNew.model.cms.external.notice;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-03-31 11:41
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public interface PushService {

    PushMessageTemplate getPushMessageTemplate(String msgTitle, String msgContent, String target);

    void pushMessageToDCUsers(PushMessageTemplate msg, List<Integer> idCodeList);

}
