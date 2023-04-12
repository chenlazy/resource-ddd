package com.idanchuang.cms.server.interfaces.web.config;

import com.google.gson.Gson;
import com.idanchuang.component.just.web.dto.LoginUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Objects;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-12 15:02
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Slf4j
@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {

    private static final String FROM = "from";

    private static final String ENV = "env";

    private static final String DEVICE = "device";

    private static String env;

    @Value("${env}")
    public void setImgDomain(String env) {
        this.env = env;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return checkGateTransmitHeaderToken(request);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        RequestContext.clean();
    }

    private Boolean checkGateTransmitHeaderToken(HttpServletRequest request) throws UnsupportedEncodingException {

        String userinfo = request.getHeader("userinfo");
        log.info("userInfo{}", userinfo);
        if (!StringUtils.isEmpty(userinfo)) {
            String decode = URLDecoder.decode(userinfo, "UTF-8");
            log.info("userInfo decode:{}", decode);
            GatewayUserDTO dto = new Gson().fromJson(decode, GatewayUserDTO.class);
            dto.setRole("后台管理员");
            RequestContext.put(dto);
        } else {

            if ("test".equals(env) || "dev".equals(env)) {
                GatewayUserDTO vo = new GatewayUserDTO();
                vo.setId(6731323);
                vo.setIdCode(6931323);
                vo.setBrandProviderLevel(5);
                vo.setRealName("付瑜敏");
                vo.setLastLoginIp("127.0.0.1");
                vo.setRole("后台管理员");
                RequestContext.put(vo);
            }
            return Boolean.TRUE;
        }
        return Boolean.TRUE;
    }
}
