package com.idanchuang.cms.server.interfaces.adcontentservice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.Instant;
import java.util.Optional;

/**
 * @author wengbinbin
 * @date 2021/4/7
 */

@Data
@Accessors(chain = true)
public class WechatTicketDTO implements Serializable {
    @ApiModelProperty(value = "微信公众号appId")
    @NotBlank(message = "微信公众号appId不能为空")
    private String appId;
    @ApiModelProperty(value = "随机字符串")
    private String noncestr;
    @ApiModelProperty(value = "时间戳")
    private Long timestamp;
    @ApiModelProperty(value = "当前网页的URL")
    @NotBlank(message = "当前网页的URL，不包含#及其后面部分")
    private String url;
    @ApiModelProperty(value = "签名")
    private String signature;

    public void initialParameter() {
        Optional.ofNullable(this.noncestr)
                .orElseGet(() -> this.noncestr = "abcdefg");
        Optional.ofNullable(this.timestamp)
                .orElseGet(() -> this.timestamp = Instant.now().getEpochSecond());
    }
}
