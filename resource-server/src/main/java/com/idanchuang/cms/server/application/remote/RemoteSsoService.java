package com.idanchuang.cms.server.application.remote;

import com.google.common.collect.Lists;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.component.base.exception.common.ErrorCode;
import com.idanchuang.component.base.exception.core.ExFactory;
import com.idanchuang.sso.model.dto.system.UserDTO;
import com.idanchuang.sso.model.dto.system.UserDetailDTO;
import com.idanchuang.sso.service.facade.system.UserServiceFacade;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-14 14:36
 * @Desc: 原创sso服务
 * @Copyright VTN Limited. All rights reserved.
 */
@Service
@Slf4j
public class RemoteSsoService {

    @Resource
    private UserServiceFacade userServiceFacade;

    /**
     * 查询sso用户信息
     * @param userIds
     */
    public List<UserDetailDTO> getUsers(List<Long> userIds) {
        JsonResult<List<UserDetailDTO>> result;
        try {
            if (CollectionUtils.isEmpty(userIds)) {
                return Lists.newArrayList();
            }

            //list转arr
            userIds = userIds.stream().distinct().collect(Collectors.toList());
            Long[] userIdArr = new Long[userIds.size()];
            userIdArr = userIds.toArray(userIdArr);

            result = userServiceFacade.getUserDTOByIds(userIdArr);
        } catch (Exception e) {
            log.error("查询sso信息异常", e);
            throw ExFactory.throwWith(ErrorCode.SERVICE_UNAVAILABLE);
        }
        if (result == null || !result.isSuccess()) {
            log.error("查询sso信息异常, query:{},errMsg: {}", userIds, result.getMsg());
            return null;
        }
        return result.getData();
    }

    /**
     * 查询单个sso用户信息
     * @param userId
     * @return
     */
    public UserDTO getUser(Long userId) {
        JsonResult<UserDTO> result;
        try {
            result = userServiceFacade.getUserDTO(userId);
        } catch (Exception e) {
            log.error("查询sso信息异常", e);
            throw ExFactory.throwWith(ErrorCode.SERVICE_UNAVAILABLE);
        }
        if (result == null || !result.isSuccess()) {
            log.error("查询sso信息异常, query:{},errMsg: {}", userId, result.getMsg());
            return null;
        }
        return result.getData();
    }
}
