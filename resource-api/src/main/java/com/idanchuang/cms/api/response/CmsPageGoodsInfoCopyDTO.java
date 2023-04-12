package com.idanchuang.cms.api.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-10 15:33
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class CmsPageGoodsInfoCopyDTO implements Serializable {

    private static final long serialVersionUID = 2918939860233334249L;

    private Map<Long, List<CmsPageGoodsCopyDTO>> listMap;
}
