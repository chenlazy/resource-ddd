package com.idanchuang.cms.server.application.adcontentservice.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wengbinbin
 * @date 2021/4/7
 */

@Data
@Accessors(chain = true)
public class WechatAccessTokenDTO implements Serializable {
    private String access_token;
    private Long expires_in;
    private String errcode;
    private String errmsg;

}
