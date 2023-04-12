package com.idanchuang.resource.server.interfaces.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.idanchuang.cms.server.domain.model.cms.CmsCorePageList;
import com.idanchuang.cms.server.domain.model.cms.ContainerComponentCondition;
import com.idanchuang.cms.server.infrastructure.persistence.mapper.*;
import com.idanchuang.cms.server.infrastructure.persistence.model.*;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.assertj.core.util.Lists;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resource/test/syncData")
public class TestSyncDataController {
    @Resource
    PageTagMapper tagMapper;
    @Resource
    CmsPageSchemaMapper pageSchemaMapper;
    @Resource
    CmsPageMapper pageMapper;
    @Resource
    CmsPageContainerMapper cmsPageContainerMapper;
    @Resource
    ContainerComponentMapper containerComponentMapper;


    @GetMapping("/t1")
    public void t1() {
        // 从目录表同步pageCode字段到tag表(appPage表)
        for (int i = 1; i < 2; i++) {
            CmsCorePageList cmsCorePageList = new CmsCorePageList();
            cmsCorePageList.setCurrent((long) i);
            cmsCorePageList.setSize(5L);
            List<CmsPageSchemaListDO> cmsPageSchemaListDOS = pageSchemaMapper.queryByCondition(cmsCorePageList);
            if (CollectionUtils.isEmpty(cmsPageSchemaListDOS)) {
                break;
            }
            for (CmsPageSchemaListDO cmsPageSchemaDO : cmsPageSchemaListDOS) {
                PageTagDO pageTagDO = tagMapper.selectById(cmsPageSchemaDO.getTagId());
                pageTagDO.setPageCode(cmsPageSchemaDO.getPageCode());
                tagMapper.updateById(pageTagDO);
            }

        }
    }

    @GetMapping("/t2")
    public void t2() {
        // 从模板表同步platform和页面别名字段到目录表
        for (int i = 1; i < 2000; i++) {
            Page<Object> page = PageHelper.startPage(1, 50);
            List<CmsPageDO> templateList = pageMapper.getPageTemplateList(0, 0, null, null);

            if (CollectionUtils.isEmpty(templateList)) {
                break;
            }
            for (CmsPageDO cmsPageDO : templateList) {
                CmsPageSchemaDO cmsPageSchemaDO = pageSchemaMapper.selectById(cmsPageDO.getPageSchemaId());
                cmsPageSchemaDO.setPlatform(cmsPageDO.getPlatform());
                cmsPageSchemaDO.setAliasTitle(cmsPageDO.getAliasTitle());
                pageSchemaMapper.updateById(cmsPageSchemaDO);
            }
        }
    }

    @GetMapping("/t3")
    public void t3() {
        // 同步pageCode和模版id到组件表
        for (int i = 1; i < 2000; i++) {
            ContainerComponentCondition containerComponentCondition = new ContainerComponentCondition();
            containerComponentCondition.setPageNum(i);
            containerComponentCondition.setPageSize(50);
            List<ContainerComponentDO> containerComponentDOS = containerComponentMapper.selectByCondition(containerComponentCondition);
            if (CollectionUtils.isEmpty(containerComponentDOS)) {
                break;
            }
            List<Long> containerIds = containerComponentDOS.stream().map(ContainerComponentDO::getContainerId).collect(Collectors.toList());
            Map<Long, CmsPageContainerDO> containerDOMap = cmsPageContainerMapper.getByIds(containerIds).stream()
                    .collect(Collectors.toMap(CmsPageContainerDO::getId, a -> a, (a1, a2) -> a1));
            for (ContainerComponentDO containerComponentDO : containerComponentDOS) {
                CmsPageContainerDO cmsPageContainerDO = containerDOMap.get(containerComponentDO.getContainerId());
                CmsPageDO cmsPageDO = pageMapper.selectById(cmsPageContainerDO.getPageId());
                CmsPageSchemaDO cmsPageSchemaDO = pageSchemaMapper.selectById(cmsPageDO.getPageSchemaId());
                containerComponentDO.setPageCode(cmsPageSchemaDO.getPageCode());
                containerComponentDO.setPageId(cmsPageContainerDO.getPageId());
                containerComponentMapper.updateById(containerComponentDO);
            }
        }
    }

    @GetMapping("/t4")
    public void t4() {
        //  同时创建根容器快照？--> 不需要
        // 1、将当前所有模版置为根模版
        // 2、从模板表sort字段中取出与容器的关联关系，并将这些容器置为根容器
        // 3、将每个容器所关联的组件置为根组件
        for (int i = 1; i < 2000; i++) {
            Page<Object> page = PageHelper.startPage(1, 50);
            List<CmsPageDO> templateList = pageMapper.getPageTemplateList(0, 0, null, null);
            List<Integer> collect = templateList.stream().map(CmsPageDO::getId).collect(Collectors.toList());
            pageMapper.updatePageSnapRoot(collect, "0");

            if (CollectionUtils.isEmpty(templateList)) {
                break;
            }
            for (CmsPageDO cmsPageDO : templateList) {
                List<String> sort = Lists.newArrayList();
                if (!StringUtils.isEmpty(cmsPageDO.getSort())) {
                    sort = NumberUtils.isCreatable(cmsPageDO.getSort()) ? Collections.singletonList(cmsPageDO.getSort()) :
                            JsonUtil.toList(cmsPageDO.getSort(), new TypeReference<List<String>>() {
                            });
                }
                List<Long> containerIds = sort.stream().map(Long::parseLong).collect(Collectors.toList());
                // 将使用中的容器置为根容器
                cmsPageContainerMapper.updateContainerSnapRoot(containerIds, "0");
                // 将使用中的组件置为根组件
                List<ContainerComponentDO> componentDOS = containerComponentMapper.queryComponentInfoByContainerIds(containerIds);
                List<Long> componentIds = componentDOS.stream().map(ContainerComponentDO::getId).collect(Collectors.toList());
                containerComponentMapper.updateComponentSnapRoot(componentIds, "0");
            }
        }
    }

}
