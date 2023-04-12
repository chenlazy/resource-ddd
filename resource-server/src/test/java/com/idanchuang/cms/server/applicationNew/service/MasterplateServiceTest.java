package com.idanchuang.cms.server.applicationNew.service;

import com.alibaba.fastjson.JSON;
import com.idanchuang.component.just.web.dto.LoginUserDTO;
import com.idanchuang.resource.server.SpringTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;

/**
 * @author fym
 * @description :
 * @date 2022/2/14 下午3:22
 */
public class MasterplateServiceTest extends SpringTest {

    @Resource
    private MasterplateService masterplateService;

    @Test
    public void setPageAndSelectId() {
        Set<Integer> ids = new HashSet<>();
//        ids.add(2);
//        ids.add(5);
        ids.add(6);
        ids.add(7);
        ids.add(8);
        masterplateService.setPageAndSelectId(2145L, 1, ids);
    }

    @Test
    public void getUserInfo() {
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        loginUserDTO.setId(-1L);
        loginUserDTO.setIdCode(199999L);
        loginUserDTO.setBrandProviderLevel(1);
        String json = JSON.toJSONString(loginUserDTO);
        try {
            String s = URLEncoder.encode(json, "UTF-8");
            System.out.println("getUserInfo " + s);
        } catch (Exception e) {

        }

    }
}