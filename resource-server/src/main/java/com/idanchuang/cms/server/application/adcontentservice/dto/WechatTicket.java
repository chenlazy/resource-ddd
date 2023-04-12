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
public class WechatTicket implements Serializable {
    private String errcode;
    private String ticket;
    private String errmsg;
    private Long expires_in;
}
