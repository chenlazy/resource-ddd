package com.idanchuang.cms.server.application.service;

import com.idanchuang.resource.server.SpringTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author lei.liu
 * @date 2021/9/16
 */
@Slf4j
public class PageTemplateServiceTest extends SpringTest {

    @Resource
    private PageTemplateService pageTemplateService;

    @Test
    public void deletePageVersion() {
        pageTemplateService.deletePageVersion(1670);
    }

    @Test
    public void deletePageVersions() {
        pageTemplateService.deletePageVersion(1670, "1670");
    }

//    @Test
//    public void buildPageVersion() {
//        PageRenderContext context = new PageRenderContext();
//        context.setId(1);
//        context.setPageId(1);
//        context.setVersion("1");
//        context.setPageTitle("新抽奖组件1311");
//        context.setStartTime(LocalDateTime.of(2021, 10, 13, 11, 30,10));
//        context.setEndTime(LocalDateTime.of(2021, 10, 13, 11, 30,10));
//        pageRenderService.buildPageVersion(context);
//    }

    @Test
    public void buildPageVersionId() {
        pageTemplateService.buildPageVersion(2087);
    }
}
