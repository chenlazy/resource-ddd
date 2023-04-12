package com.idanchuang.cms.server.infrastructure.repository;

import com.alibaba.fastjson.JSON;
import com.idanchuang.cms.server.domain.model.cms.CmsCorePageList;
import com.idanchuang.cms.server.domain.model.cms.CmsPageDetail;
import com.idanchuang.cms.server.domain.model.cms.CmsPageSchemaList;
import com.idanchuang.cms.server.domain.repository.CmsPageSchemaRepository;
import com.idanchuang.component.base.page.PageData;
import com.idanchuang.resource.server.SpringTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.annotation.ScopeMetadata;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @author lei.liu
 * @date 2021/9/22
 */
@Slf4j
public class CmsPageSchemaRepositoryImplTest extends SpringTest {

    @Resource
    private CmsPageSchemaRepository cmsPageSchemaRepository;

    @Test
    public void queryPageSchemaList() {
        CmsCorePageList cmsCorePageList = new CmsCorePageList();
        cmsCorePageList.setCurrent(1L);
        cmsCorePageList.setSize(20L);
        //cmsCorePageList.setAliasTitle("VTN_1");
        //cmsCorePageList.setPlatform("VTN");
        //cmsCorePageList.setTagId(1L);
        cmsCorePageList.setOld(1);
        PageData<CmsPageSchemaList> data = cmsPageSchemaRepository.queryPageSchemaList(cmsCorePageList);
        log.info(JSON.toJSONString(data));
    }

    @Test
    public void queryPageSchemaList2() {
        CmsCorePageList cmsCorePageList = new CmsCorePageList();
        cmsCorePageList.setOld(0);
        cmsCorePageList.setCurrent(1L);
        cmsCorePageList.setSize(20L);
        PageData<CmsPageSchemaList> data = cmsPageSchemaRepository.queryPageSchemaList(cmsCorePageList);
        log.info(JSON.toJSONString(data));
    }

    @Test
    public void getDetailById() {
        CmsPageDetail pageDetail = cmsPageSchemaRepository.getDetailById(1990, 3721);
        log.info(JSON.toJSONString(pageDetail));
    }

}