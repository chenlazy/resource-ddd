package com.idanchuang.cms.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @author fym
 * @description :
 * @date 2021/12/31 下午3:12
 */
@Data
public class ConstructionApmReq {
    /**
     * apm构造入参map k 组件id v坑位信息
     */
    @ApiModelProperty(value = "apm构造入参map k 组件id v坑位信息", required = true)
    private Map<String, List<ApmReq>> apmReqMap;

    @Data
    public static class ApmReq{
        /**
         * 内容投放Id，可以为空
         */
        @ApiModelProperty(value = "内容投放Id，可以为空", required = true)
        private String nicheLineId;
        /**
         * 返回后端列表所在项的位置，如果是分页接口的话就是 = pageSize * page + index
         * 为空默认1
         */
        @ApiModelProperty(value = "返回后端列表所在项的位置", required = true)
        private int location;
        /**
         * 内容类型，必须传
         */
        @NotNull
        @ApiModelProperty(value = "内容类型，必须传 无类型传0", required = true)
        private Integer contentTypeEnum;
        /**
         * 内容Id，必须传
         */
        @NotNull
        @ApiModelProperty(value = "内容Id，必须传 无内容 'NONE' ", required = true)
        private String contentId;
        /**
         * 自定义值，可为空
         */
        @ApiModelProperty(value = "自定义值，可为空", required = true)
        private Map<String, String> custom;
    }
}
