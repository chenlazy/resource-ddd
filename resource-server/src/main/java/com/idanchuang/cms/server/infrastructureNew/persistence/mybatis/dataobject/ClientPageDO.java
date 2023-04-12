package com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-24 11:12
 * @Desc: 客户端页面表
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("w_page_tag")
public class ClientPageDO {

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
