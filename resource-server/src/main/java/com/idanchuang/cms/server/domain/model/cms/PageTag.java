package com.idanchuang.cms.server.domain.model.cms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lei.liu
 * @date 2021/9/6
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageTag implements Serializable {
    private static final long serialVersionUID = -5266446033252520322L;

    private Integer id;

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
