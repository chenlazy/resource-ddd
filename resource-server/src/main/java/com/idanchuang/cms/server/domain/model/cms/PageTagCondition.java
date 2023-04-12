package com.idanchuang.cms.server.domain.model.cms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lei.liu
 * @date 2021/9/6
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageTagCondition {

    private int pageNum = 1;
    private int pageSize = 10;

    private Integer platform;

    private List<Integer> idList;
}
