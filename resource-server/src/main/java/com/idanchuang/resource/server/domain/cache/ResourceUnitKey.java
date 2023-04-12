package com.idanchuang.resource.server.domain.cache;

import com.idanchuang.resource.server.infrastructure.common.constant.RedisBusinessKeyConstant;
import lombok.Builder;
import lombok.Data;

/**
 * @author wengbinbin
 * @date 2021/3/23
 */

@Data
@Builder
public
class ResourceUnitKey {

    private Long resourceId;

    /**
     * 投放平台，默认1 1.客户端、2.h5、3.小程序
     */
    private Integer platformFrom;

    private Integer role;

    public String generatorUnitKey() {
        return String.format(RedisBusinessKeyConstant.RESOURCE_UNIT_KEY,
                this.resourceId, this.platformFrom.toString(),this.role);
    }

    public String generatorUnitListKey() {
        return String.format(RedisBusinessKeyConstant.RESOURCE_UNIT_LIST_KEY, this.resourceId);
    }
}
