package com.idanchuang.cms.api.request;

import lombok.Data;

import java.util.List;

/**
 * @author lei.liu
 * @date 2021/9/6
 */
@Data
public class ContainerComponentConditionReq {

    private int pageNum = 1;
    private int pageSize = 10;

    private List<Long> containerIdList;
}
