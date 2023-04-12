package com.idanchuang.cms.server.interfaces.vo;

import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateShareForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-31 16:14
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRenderVO {

    /**
     * 页面定义id
     */
    private Integer id;

    /**
     * 页面实例ID
     */
    private Integer pageId;

    /**
     * 标签id
     */
    private Integer tagId;

    /**
     * 页面标题
     */
    private String pageTitle;

    /**
     * 后台标题
     */
    private String backEndTitle;

    /**
     * 别名
     */
    private String aliasTitle;

    /**
     * 平台类型
     */
    private String platform;

    /**
     * 是否分享 1-是 0-否
     */
    private Integer shareFlag;

    /**
     * 当前版本
     */
    private String version;

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
     * 容器列表
     */
    private List<ContainerRenderVO> containers;

    /**
     * 分享信息
     */
    private List<MasterplateShareForm> shareList;

    /**
     * 版本结束时间
     */
    private LocalDateTime versionEndTime;

    /**
     * 下个版本号
     */
    private String nextVersion;

    /**
     * 是否为页面在当前时间内投放的样式
     * @return  true:是；false:否
     */
    public boolean isCurrent() {

        if (startTime != null && endTime == null) {
            return LocalDateTime.now().isAfter(startTime);
        }

        return LocalDateTime.now().compareTo(startTime) >= 0 && LocalDateTime.now().compareTo(endTime) < 0;
    }

    /**
     * 版本是否失效
     * @return   false：未失效，true：已失效
     */
    public boolean isOver() {
        if (versionEndTime == null || versionEndTime.compareTo(LocalDateTime.now()) > 0) {
            return false;
        }
        return true;
    }
}
