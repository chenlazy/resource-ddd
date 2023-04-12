package com.idanchuang.cms.server.domainNew.model.cms.external.notice;


import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-03-31 11:42
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public interface SendService {

    SendMessageTemplate getSendMessageTemplate(String msgTitle, String msgContent, String target);

    void sendMessageToDCUsers(SendMessageTemplate msg, List<Integer> idCodeList);
}
