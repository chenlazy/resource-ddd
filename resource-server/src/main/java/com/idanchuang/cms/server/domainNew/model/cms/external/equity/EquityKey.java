package com.idanchuang.cms.server.domainNew.model.cms.external.equity;

import lombok.Data;

import java.util.Objects;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-05-20 10:31
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class EquityKey {

    /**
     * 权益id
     */
    private Long equityId;

    /**
     * 权益类型
     */
    private Integer equityType;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EquityKey equityKey = (EquityKey) o;
        return Objects.equals(equityId, equityKey.equityId) &&
                Objects.equals(equityType, equityKey.equityType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(equityId, equityType);
    }
}
