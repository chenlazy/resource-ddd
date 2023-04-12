package com.idanchuang.cms.api.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-12 15:43
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LuckyComponentPrizeDTO implements Serializable {

    private static final long serialVersionUID = 5003469063442051514L;

    @ApiModelProperty(value = "抽奖活动ID")
    private Long activityId;

//    @ApiModelProperty(value = "奖品ID")
//    private Long prizeId;

    @ApiModelProperty(value = "奖品标识ID")
    private String prizeMarkId;

    @ApiModelProperty(value = "奖品名称")
    private String prizeName;

    @ApiModelProperty(value = "奖品类型。1鼓励奖；2满减券；3组合券；4零元券；6积分；7生单实物奖；8满折券；9体验卡；10N元券")
    private Integer prizeType;
}
