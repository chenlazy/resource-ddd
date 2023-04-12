package com.idanchuang.cms.server.application.remote;

import com.google.common.collect.Lists;
import com.idanchuang.member.point.api.entity.query.PrivilegeSyncTagIdRequest;
import com.idanchuang.resource.server.SpringTest;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author fym
 * @description :
 * @date 2022/4/15 上午11:38
 */
public class RemotePrivilegeServiceTest extends SpringTest {

    @Resource
    private RemotePrivilegeService remotePrivilegeService;

    @Test
    public void syncTagIds() {
        List<PrivilegeSyncTagIdRequest> list = Lists.newArrayList();
        PrivilegeSyncTagIdRequest request = new PrivilegeSyncTagIdRequest();
        request.setTagId(1111L);
        request.setId(619L);
        request.setType(1);
        list.add(request);
        remotePrivilegeService.syncTagIds(list);

    }
}