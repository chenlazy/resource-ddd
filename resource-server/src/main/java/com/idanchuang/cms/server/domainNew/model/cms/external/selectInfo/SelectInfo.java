package com.idanchuang.cms.server.domainNew.model.cms.external.selectInfo;

import lombok.Data;

/**
 * @author fym
 * @description :
 * @date 2022/2/14 下午1:36
 */
@Data
public class SelectInfo {

    /**
     * 圈选类型 1 活动 2 优惠券
     */
    private Integer selectType;
    /**
     * 圈选id
     */
    private Integer selectId;

}
