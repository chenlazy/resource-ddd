package com.idanchuang.resource.server.domain.model.resource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-15 18:13
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Slf4j
@Data
public class OperatorUser {

    private String userInfo;
    private String nickName;
    private Integer id;

    public static ObjectMapper objectMapper = new ObjectMapper();

    public Boolean parseRequest(HttpServletRequest request) {
        try {
//            if (StringUtils.isEmpty(request.getHeader("userinfo"))) {
//                this.nickName = "admin";
//                this.id = 1;
//                return true;
//            }
            String userinfo = URLDecoder.decode(request.getHeader("userinfo"), "UTF-8");
            log.debug(userinfo);
            String userInfoJson = userinfo.substring(userinfo.indexOf('{'));
            JsonNode jsonNode = objectMapper.readTree(userInfoJson);
            this.userInfo = userinfo;
            this.nickName = jsonNode.get("nickname").asText();
            this.id = jsonNode.get("id").intValue();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
