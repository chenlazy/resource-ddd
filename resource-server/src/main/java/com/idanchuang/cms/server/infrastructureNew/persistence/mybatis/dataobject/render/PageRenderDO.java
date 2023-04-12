package com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.render;

import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateShareForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-01-04 16:09
 * @Desc: 缓存对象类
 * @Copyright VTN Limited. All rights reserved.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PageRenderDO {

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
     * 下一个带生效版本开始时间
     */
    private LocalDateTime nextStartTime;

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
     * 页面相关信息
     */
    private String pageStyle;

    /**
     * 容器列表
     */
    private List<ContainerRenderDO> containers;

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
}
