package com.idanchuang.cms.server.domain.model.cms;

import lombok.*;
import lombok.experimental.NonFinal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-06 14:23
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CmsPage {

    /**
     * 主键id
     */
    @NonFinal
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
     * 容器排序 数组结构
     */
    private List<String> sort;

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

    private Integer version;

    public void setId(Integer id) {
        this.id = id;
    }
}
