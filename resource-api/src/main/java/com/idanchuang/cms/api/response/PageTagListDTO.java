package com.idanchuang.cms.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-12 16:20
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class PageTagListDTO implements Serializable {

    private static final long serialVersionUID = 8120146615029929724L;

    @ApiModelProperty(value = "标签ID")
    private Integer id;

    @ApiModelProperty(value = "标签名")
    private String name;

    @ApiModelProperty(value = "pageCode")
    private String pageCode;

    @ApiModelProperty(value = "操作人ID")
    private Integer operatorId;

    @ApiModelProperty(value = "操作人")
    private String operator;

    @ApiModelProperty("操作时间")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private LocalDateTime updatedAt;
}
