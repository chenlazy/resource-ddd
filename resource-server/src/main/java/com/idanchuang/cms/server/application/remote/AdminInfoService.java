package com.idanchuang.cms.server.application.remote;

import com.alibaba.fastjson.JSON;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.sso.model.dto.system.UserInfoDTO;
import com.idanchuang.sso.service.facade.client.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-12 16:02
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Service
@Slf4j
public class AdminInfoService {

    @Resource
    private UserClient userClient;


    private List<UserInfoDTO> getUsersInfoList(List<String> userIds) {
        JsonResult<List<UserInfoDTO>> result = userClient.getUsersInfo(userIds);
        if (!result.isSuccess()) {
            log.error("调用 UserClient#getUsersInfo 异常，param: {}, code: {}, msg: {}", JSON.toJSONString(userIds), result.getCode(), result.getMsg());
            return null;
        }
        return result.getData();
    }

    public Map<Long, UserInfoDTO> getUserInfoMap(List<Integer> userIds) {

        Map<Long, UserInfoDTO> userInfoMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(userIds)) {
            List<UserInfoDTO> usersInfoList = getUsersInfoList(userIds.stream().map(e -> String.valueOf(e)).collect(Collectors.toList()));
            if (!CollectionUtils.isEmpty(usersInfoList)) {
                userInfoMap = usersInfoList.stream().collect(Collectors.toMap(UserInfoDTO::getId, t -> t, (a, b) -> a));
            }
        }
        return userInfoMap;
    }

//    public Map<Long, String> getOperatorName(List<Integer> userIds) {
//        JsonResult<List<AdminDTO>> result = adminFacade.getAdminByIds(userIds);
//        if (!result.isSuccess()) {
//            log.error("调用 WAdminFacade#getAdminByIds 异常，param: {}, code: {}, msg: {}", JSON.toJSONString(userIds), result.getCode(), result.getMsg());
//            return new HashMap<>();
//        }
//        if (result.getData() == null) {
//            return new HashMap<>();
//        }
//        return result.getData().stream().collect(Collectors.toMap(e -> e.getId().longValue(), AdminDTO::getName, (a, b) -> a));
//    }
}
