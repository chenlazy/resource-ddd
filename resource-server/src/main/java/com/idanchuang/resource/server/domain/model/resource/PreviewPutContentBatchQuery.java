package com.idanchuang.resource.server.domain.model.resource;

import lombok.Data;

import java.util.List;

/**
 * @author fym
 * @description :
 * @date 2021/3/26 下午1:35
 */
@Data
public class PreviewPutContentBatchQuery {

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
     * 资源位id集合
     */
    private List<Long> resourceIds;
    /**
     * 内容投放id
     */
    private Long unitId;
    /**
     * 投放平台，默认1、1.客户端、2.H5、3.小程序
     */
    private String platform;
}
