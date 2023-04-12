package com.idanchuang.cms.api.request;

import com.idanchuang.component.base.page.PageDTO;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-12 15:56
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class OperateLogRequest extends PageDTO {

    /**
     * 扩展属性（订单id、业务单独分类的数据等）
     */
    @NotBlank
    private String customParameters;

    /**
     * 模块名（sysName下的子模块）
     */
    @NotBlank
    private String module;

    /**
     * 日志类型
     */
    @NotBlank
    private String logType;
}
