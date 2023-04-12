package com.idanchuang.cms.server.interfaces.web.config;

import lombok.Data;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-12 15:01
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class GatewayUserDTO {

    private Integer id;

    private Integer idCode;

    private String realName;

    private String lastLoginIp;

    private String role;

    private String registerCode;

    private Integer brandProviderLevel;


}
