package com.idanchuang.cms.api.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lei.liu
 * @date 2021/10/20
 */
@Data
public class GoodsComponentPageCondition implements Serializable {
    private static final long serialVersionUID = -163856317502023257L;

    private List<Integer> tagIdList;
}
