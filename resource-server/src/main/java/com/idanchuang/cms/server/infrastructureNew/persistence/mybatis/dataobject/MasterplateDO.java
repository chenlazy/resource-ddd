package com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//模板表
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@TableName("w_cms_page")
public class MasterplateDO {

    /**
     * 主键id
     */
    private long id;

    /**
     * 页面定义id
     */
    private long pageSchemaId;

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
     * 标签id --client_page_id
     */
    private long tagId;

    /**
     * 版本号
     */
    private int version;


    /**
     * 0:未发布 1:已发布 2:已失效
     */
    private Integer status;


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
     * 分享具体内容
     */
    private String pageStyle;

    /**
     * 活动结束时间
     */
    private LocalDateTime endTime;

    /**
     * 商品自动下架标记 0不下架 1下架'  --映射到领域的扩展字段
     */
    private Integer goodsEnable;

    /**
     * 操作人id
     */
    private long operatorId;

    /**
     * 快照标识
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
     * 创建人id
     */
    private long createId;

    /**
     * 额外信息
     */
    private String extra;
}
