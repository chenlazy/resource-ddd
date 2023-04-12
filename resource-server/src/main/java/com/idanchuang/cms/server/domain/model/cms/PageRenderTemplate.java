package com.idanchuang.cms.server.domain.model.cms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lei.liu
 * @date 2021/10/13
 */
@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRenderTemplate implements Serializable {
    private static final long serialVersionUID = 8482805252390993851L;

    /**
     * 页面定义ID
     */
    private Integer id;

    /**
     * 页面实例ID
     */
    private Integer pageId;

    /**
     * 当前版本
     */
    private String version;

    /**
     * 活动开始时间
     */
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    /**
     * 是否为页面在当前时间内投放的样式
     * @return  true:是；false:否
     */
    public boolean isCurrent() {
        if (startTime == null && endTime == null) {
            return true;
        }

        if (startTime != null && endTime == null) {
            return LocalDateTime.now().isAfter(startTime);
        }

        if (startTime == null) {
            return LocalDateTime.now().isBefore(endTime);
        }

        return LocalDateTime.now().isAfter(startTime) && LocalDateTime.now().isBefore(endTime);
    }
}
