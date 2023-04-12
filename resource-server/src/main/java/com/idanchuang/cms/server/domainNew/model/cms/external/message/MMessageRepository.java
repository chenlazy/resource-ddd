package com.idanchuang.cms.server.domainNew.model.cms.external.message;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-04-22 11:38
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public interface MMessageRepository {

    List<MMessage> getPopNoSendMessage();

    boolean updateStatusById(Integer id, Integer status);
}
