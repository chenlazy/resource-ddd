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
public class ResourceConfigKey {

    private Long resourceId;

    /**
     * 资源位所在页面code
     */
    private String pageCode;
    /**
     * 业务类型，1 abm，2vtn，3tj
     */
    private Integer businessType;

    public String generatorConfigKey() {
        return String.format(RedisBusinessKeyConstant.RESOURCE_CONFIG_KEY,
                this.resourceId, this.pageCode, this.businessType);
    }

    public String generatorConfigListKey() {
        return String.format(RedisBusinessKeyConstant.RESOURCE_CONFIG_LIST_KEY, this.resourceId);
    }
}
