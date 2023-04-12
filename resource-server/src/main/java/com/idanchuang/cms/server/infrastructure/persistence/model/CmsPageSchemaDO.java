package com.idanchuang.cms.server.infrastructure.persistence.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-01 14:22
 * @Desc: CMS页面定义表
 * @Copyright VTN Limited. All rights reserved.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@TableName("w_cms_page_schema")
public class CmsPageSchemaDO {

    /**
     * 自增id
     */
    private Integer id;

    /**
     * 平台类型
     */
    private String platform;

    /**
     * 页面code
     */
    private String pageCode;

    /**
     * 页面名称
     */
    private String pageName;

    /**
     * 页面别名
     */
    private String aliasTitle;

    /**
     * 页面类型
     */
    private Integer pageType;

    /**
     * 页面发布版本
     */
    private String putVersions;

    /**
     * 操作人id
     */
    private Integer operatorId;

    /**
     * 发布状态 0：草稿，1：发布
     */
    private Integer status;

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
     * 标签id
     */
    private Long tagId;

    /**
     * 扩展信息
     */
    private String extra;
}
