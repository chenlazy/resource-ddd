package com.idanchuang.resource.server.infrastructure.persistence.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author wengbinbin
 * @date 2021/3/18
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@TableName("resource_business")
public class ResourceBusinessDO {

    /**
     * 业务id
     */
    private Integer id;

    /**
     * 业务名称
     */
    private String businessName;

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

}

