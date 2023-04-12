package com.idanchuang.resource.server.domain.model.resource;

import lombok.Data;

/**
 * @author fym
 * @description :
 * @date 2021/3/26 上午11:52
 */
@Data
public class PreviewPutContentQuery {

    /**
     * 资源位所在页面
     */
    private String pageCode;
    /**
     * 业务p平台 1 abm 2 vtn
     */
    private Integer business;
    /**
     * 用户角色
     */
    private Integer role;
    /**
     * 预览资源位id
     */
    private Long preResourceId;
    /**
     * 资源位id
     */
    private Long resourceId;
    /**
     * 内容投放id
     */
    private Long unitId;
    /**
     * 投放平台，默认1、1.客户端、2.H5、3.小程序
     */
    private String platform;
}
