package com.idanchuang.cms.server.application.service;

import com.alibaba.fastjson.JSON;
import com.idanchuang.cms.server.domain.model.cms.PageTag;
import com.idanchuang.cms.server.domain.model.cms.PageTagCondition;
import com.idanchuang.component.base.page.PageData;
import com.idanchuang.resource.server.SpringTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author lei.liu
 * @date 2021/9/9
 */
@Slf4j
public class PageTagServiceTest extends SpringTest {

    @Resource
    private PageTagService pageTagService;

    @Test
    public void insert() {
        PageTag pageTag = new PageTag();
        pageTag.setName("标签X");
        pageTag.setOperatorId(10000);
        pageTag.setPlatform(0);
        pageTag.setCreateTime(LocalDateTime.now());
        pageTag.setUpdateTime(LocalDateTime.now());
        boolean result = pageTagService.insert(pageTag);
        log.info("" + result);
    }


    @Test
    public void deleteById() {
    }

    @Test
    public void updateById() {
    }

    @Test
    public void pageByCondition() {
        PageTagCondition condition = new PageTagCondition();
        condition.setPlatform(0);
        PageData<PageTag> pageData = pageTagService.pageByCondition(condition);
        log.info(JSON.toJSONString(pageData));
    }

    @Test
    public void selectList() {
        List<PageTag> pageTagList = pageTagService.selectList(Arrays.asList(1, 2));
        log.info(JSON.toJSONString(pageTagList));
    }

    @Test
    public void updatePlatform() {
        pageTagService.updatePlatform(1);
    }
}