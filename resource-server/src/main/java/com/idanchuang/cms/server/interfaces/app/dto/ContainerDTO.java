package com.idanchuang.cms.server.interfaces.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lei.liu
 * @date 2021/9/27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContainerDTO implements Serializable {
    private static final long serialVersionUID = 9006193492397235622L;

    @ApiModelProperty("容器ID")
    private Long containerId;

    @ApiModelProperty("容器code")
    private String containerCode;

    @ApiModelProperty("容器名称")
    private String containerName;

    @ApiModelProperty("样式内容")
    private String styleContent;
}
