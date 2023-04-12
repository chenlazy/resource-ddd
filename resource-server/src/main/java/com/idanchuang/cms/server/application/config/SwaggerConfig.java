package com.idanchuang.cms.server.application.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-13 17:19
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Component("SwaggerConfig")
public class SwaggerConfig implements InitializingBean {

    @Resource
    private Docket docket;

    @Override
    public void afterPropertiesSet() {

        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("token").description("用户token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        docket.globalOperationParameters(pars);

    }
}
