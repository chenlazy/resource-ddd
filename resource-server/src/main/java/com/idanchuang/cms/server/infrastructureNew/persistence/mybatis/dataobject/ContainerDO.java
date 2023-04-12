package com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-28 15:56
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@TableName("w_cms_page_container")
public class ContainerDO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 页面实例id
     */
    private Integer pageId;

    /**
     * 容器code
     */
    private String containerCode;

    /**
     * 容器名称
     */
    private String containerName;

    /**
     * 容器状态 1 草稿 2 已发布
     */
    private Integer status;

    /**
     * 前端页面格式
     */
    private String pageStyle;

    /**
     * 操作人id
     */
    private Integer operatorId;

    /**
     * 是否是根
     */
    private Long snapRoot;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除时间
     */
    private Integer deleted;

    /**
     * 扩展
     */
    private String extra;
}
