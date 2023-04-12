package com.idanchuang.cms.server.infrastructure.persistence.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-01 17:20
 * @Desc: 页面标签表
 * @Copyright VTN Limited. All rights reserved.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@TableName("w_page_tag")
public class PageTagDO {

    /**
     * 自增id
     */
    private Integer id;

    /**
     * 页面code
     */
    private String pageCode;

    /**
     * 标签名
     */
    private String name;

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
     * 平台类型，0：VTN；1：ABM
     */
    private Integer platform;
}
