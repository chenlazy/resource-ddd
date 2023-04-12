package com.idanchuang.cms.server.interfaces.assember;

import com.idanchuang.cms.api.response.PageDiffDTO;
import com.idanchuang.cms.server.infrastructureNew.util.BasicDiff;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-03-09 10:45
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public class EffectImproveAssembler {


    public static PageDiffDTO entityToDTO(BasicDiff.DiffResult diffResult) {

        if (null == diffResult) {
            return null;
        }

        if (diffResult.getOldValue() == null && diffResult.getNewValue() == null && diffResult.getAddValue() == null && diffResult.getReduceValue() == null) {
            diffResult.setDiffMessage("该页面没有做任何修改");
        }

        return PageDiffDTO.builder().diffMessage(diffResult.getDiffMessage()).oldValue(diffResult.getOldValue())
                .newValue(diffResult.getNewValue()).addValue(diffResult.getAddValue()).reduceValue(diffResult.getReduceValue()).build();
    }
}
