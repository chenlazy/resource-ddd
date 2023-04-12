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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ContainerComponentCondition {

    private int pageNum = 1;
    private int pageSize = 10;

    /**
     * 容器ID
     */
    private List<Long> containerIdList;

    /**
     * 模型类型
     */
    private Integer modelType;

}
