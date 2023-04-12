package com.idanchuang.cms.api.request;

import lombok.Data;

/**
 * @author lei.liu
 * @date 2021/9/6
 */
@Data
public class PageTagConditionReq {

    private int pageNum = 1;
    private int pageSize = 10;

    private int platform;
}
