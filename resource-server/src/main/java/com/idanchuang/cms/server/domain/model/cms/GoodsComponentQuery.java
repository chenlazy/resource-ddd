package com.idanchuang.cms.server.domain.model.cms;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lei.liu
 * @date 2021/9/14
 */
@Data
public class GoodsComponentQuery implements Serializable {
    private static final long serialVersionUID = -7709906233260314347L;

    private Long componentId;

    private int limit = 50;
}
