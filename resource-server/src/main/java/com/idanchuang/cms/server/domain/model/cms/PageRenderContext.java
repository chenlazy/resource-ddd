package com.idanchuang.cms.server.domain.model.cms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lei.liu
 * @date 2021/9/15
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PageRenderContext implements Serializable {
    private static final long serialVersionUID = -1050026715337967241L;

    /**
     * 页面定义id
     */
    private Integer id;
    /**
     * 页面实例id
     */
    private Integer pageId;

    /**
     * 页面版本
     */
    private String version;

    /**
     * 页面名称
     */
    private String pageTitle;;

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
    private Integer tagId;

    /**
     * 平台类型
     */
    private String platform;

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

    private String pageStyle;

}
