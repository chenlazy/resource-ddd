package com.idanchuang.resource.server.application.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.idanchuang.resource.server.SpringTest;
import com.idanchuang.resource.server.domain.model.resource.ResourceConfig;
import com.idanchuang.resource.server.domain.model.resource.ResourceConfigQueryConditions;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author wengbinbin
 * @date 2021/3/19
 */

@Slf4j
public class ResourceConfigServiceTest extends SpringTest {
    @Resource
    private ResourceConfigService resourceConfigService;

    @Test
    public void createResourceConfig() {
    }

    @Test
    public void getResourceConfigById() {
    }

    @Test
    public void searchResourceConfig() {
        ResourceConfigQueryConditions queryConditions = new ResourceConfigQueryConditions();
        queryConditions.setResourceId(1L);
        PageInfo<ResourceConfig> result = resourceConfigService.searchResourceConfig(queryConditions);

        log.info(JSON.toJSONString(result));
    }

    @Test
    public void updateResourceConfig() {
    }
}