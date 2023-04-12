package com.idanchuang.resource.server.domain.model.strategy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wengbinbin
 * @date 2021/3/28
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UnitStrategy {
    private int type;
    /**
     * 投放角色
     */
    private List<Integer> visibleRoleS;
}
