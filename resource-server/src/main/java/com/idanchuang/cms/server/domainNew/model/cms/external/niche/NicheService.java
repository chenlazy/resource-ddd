package com.idanchuang.cms.server.domainNew.model.cms.external.niche;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-24 17:41
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public interface NicheService {

    /**
     * 资源位开启可见
     */
    void visible (List<NicheId> nicheIdList);
}
