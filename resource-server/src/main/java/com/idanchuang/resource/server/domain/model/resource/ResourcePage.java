package com.idanchuang.resource.server.domain.model.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Accessors(chain = true)
public class ResourcePage {
    /**
     * id
     */
    private int id;

    /**
     * 页面名称
     */
    private String pageName;

    /**
     * 页面编号
     */
    private String pageCode;

    private Integer businessId;
}
