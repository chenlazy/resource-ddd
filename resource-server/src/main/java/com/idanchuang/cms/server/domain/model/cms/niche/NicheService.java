package com.idanchuang.cms.server.domain.model.cms.niche;

import java.util.List;

/**
 * 资源位相关service
 */
public interface NicheService {


    //资源位开启可见
    void visible (List<NicheId> nicheIdList);


}
