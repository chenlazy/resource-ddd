package com.idanchuang.resource.server.infrastructure.persistence.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@TableName("resource_page")
public class ResourcePageDO {
    /**
     * id
     */
    private Integer id;

    /**
     * 页面名称
     */
    private String pageName;

    /**
     * 页面编号
     */
    private String pageCode;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除，1删除
     */
    private Integer isDeleted;

    private Integer businessId;
}
