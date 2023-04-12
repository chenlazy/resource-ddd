package com.idanchuang.cms.server.domain.model.cms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-06 15:31
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CmsPageQuery {

    /**
     * 页面id
     */
    private Integer id;

}
