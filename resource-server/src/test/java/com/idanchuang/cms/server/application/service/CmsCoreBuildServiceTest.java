package com.idanchuang.cms.server.application.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.idanchuang.cms.server.domain.model.cms.CmsCompData;
import com.idanchuang.cms.server.domain.model.cms.CmsCoreAdd;
import com.idanchuang.resource.server.SpringTest;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author lei.liu
 * @date 2021/10/10
 */
@Slf4j
public class CmsCoreBuildServiceTest extends SpringTest {

    @Resource
    private CmsCoreBuildService cmsCoreBuildService;

    @Test
    public void createOrUpdatePageContainer() {
        CmsCoreAdd cmsCoreAdd = new CmsCoreAdd();
        cmsCoreAdd.setTagId(1L);
        cmsCoreAdd.setOperatorId(1);
        cmsCoreAdd.setPageTitle("choujiang2");
        cmsCoreAdd.setDescribe("choujiang2");
        cmsCoreAdd.setPlatform("VTN");
        cmsCoreAdd.setCompData("{\"componentJsonData\":[{\"activityId\":\"578\",\"type\":\"access-luckydraw\",\"layoutType\":\"grid-view\"}]}");
        cmsCoreAdd.setStartTime("2021-10-10T06:27:49.000Z");
        cmsCoreAdd.setEndTime("2021-12-10T06:27:49.000Z");
        cmsCoreAdd.setShareFlag(0);
        //Boolean result = cmsCoreBuildService.createPageContainer(cmsCoreAdd);
        //log.info(result.toString());
    }

    public static void main(String[] args) {

        String pageStyle = "[{\"componentJsonData\":{},\"pageConfig\":{},\"nicheIds\":[1,2],\"containerCode\":\"111\"}]";

        //支持多容器方式
        List<CmsCompData> cmsCompDates = JsonUtil.toList(pageStyle, new TypeReference<List<CmsCompData>>() {
        });

        System.out.println(cmsCompDates);
    }

}