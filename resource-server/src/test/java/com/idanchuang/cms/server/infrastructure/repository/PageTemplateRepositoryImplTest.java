package com.idanchuang.cms.server.infrastructure.repository;

import com.alibaba.fastjson.JSON;
import com.idanchuang.cms.server.domain.model.cms.PageRender;
import com.idanchuang.cms.server.domain.repository.PageTemplateRepository;
import com.idanchuang.resource.server.SpringTest;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.List;

/**
 * @author lei.liu
 * @date 2021/9/27
 */
@Slf4j
public class PageTemplateRepositoryImplTest extends SpringTest {

    @Resource
    private PageTemplateRepository pageTemplateRepository;

    @Test
    public void getInfoById() {
        log.info("pageRender0");
        PageRender pageRender1 = pageTemplateRepository.getInfoById(1);
        log.info("pageRender1{}", JsonUtil.toJsonString(pageRender1));

        PageRender pageRender2 = pageTemplateRepository.getInfoById(1);
        log.info("pageRender2{}", JsonUtil.toJsonString(pageRender2));
    }

    @Test
    public void getInfoList() {
        List<PageRender> infoList = pageTemplateRepository.getInfoList(1801);
        log.info(JSON.toJSONString(infoList));
    }

    @Test
    public void buildPageVersion() {
        pageTemplateRepository.buildPageVersion(1942);
    }

    @Test
    public void deletePageVersion() {
    }

    @Test
    public void testDeletePageVersion() {
    }
}