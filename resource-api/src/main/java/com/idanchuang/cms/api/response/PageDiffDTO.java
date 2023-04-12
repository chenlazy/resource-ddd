package com.idanchuang.cms.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-03-11 16:22
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageDiffDTO implements Serializable {

    private static final long serialVersionUID = -852387288791978203L;

    /**
     * 差异信息
     */
    private String diffMessage;

    /**
     * 旧数据
     */
    private String oldValue;

    /**
     * 新数据
     */
    private String newValue;

    /**
     * 新增数据
     */
    private String addValue;

    /**
     * 删除数据
     */
    private String reduceValue;
}
