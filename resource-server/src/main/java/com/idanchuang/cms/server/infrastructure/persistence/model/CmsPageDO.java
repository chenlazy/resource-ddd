package com.idanchuang.cms.server.infrastructure.persistence.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-01 14:33
 * @Desc: CMS页面实例表
 * @Copyright VTN Limited. All rights reserved.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@TableName("w_cms_page")
public class CmsPageDO {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 页面定义id
     */
    private Integer pageSchemaId;

    /**
     * 页面名称
     */
    private String pageName;

    /**
     * 页面类型
     */
    private Integer pageType;

    /**
     * 后台标题
     */
    private String backEndTitle;

    /**
     * 标签id
     */
    private Long tagId;

    /**
     * 平台类型
     */
    private String platform;

    /**
     * 0:未发布 1:已发布 2:已失效
     */
    private Integer status;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 容器排序 数组结构
     */
    private String sort;

    /**
     * 页面别名
     */
    private String aliasTitle;

    /**
     * 是否分享 1-是 0-否
     */
    private Integer shareFlag;

    /**
     * 分享具体内容
     */
    private String shareJson;

    /**
     * 活动开始时间
     */
    private LocalDateTime startTime;

    /**
     * 活动结束时间
     */
    private LocalDateTime endTime;

    /**
     * 商品自动下架标记 0不下架 1下架'
     */
    private Integer goodsEnable;

    /**
     * 操作人id
     */
    private Integer operatorId;

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
}
