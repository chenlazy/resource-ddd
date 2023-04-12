package com.idanchuang.cms.server.infrastructure.repository;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.idanchuang.cms.server.domain.model.cms.CmsPage;
import com.idanchuang.cms.server.domain.model.cms.CmsPageTemplate;
import com.idanchuang.cms.server.domain.model.cms.CmsPageTemplateList;
import com.idanchuang.cms.server.domain.repository.CmsPageRepository;
import com.idanchuang.cms.server.infrastructure.persistence.mapper.CmsPageMapper;
import com.idanchuang.cms.server.infrastructure.persistence.model.CmsPageDO;
import com.idanchuang.cms.server.infrastructure.transfer.CmsPageTransfer;
import com.idanchuang.component.base.page.PageData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-01 17:32
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Repository
@Slf4j
public class CmsPageRepositoryImpl implements CmsPageRepository {

    @Resource
    private CmsPageMapper cmsPageMapper;

    @Override
    public int saveCmsPage(CmsPage cmsPage) {
        CmsPageDO cmsPageDO = CmsPageTransfer.entityToDO(cmsPage);
        int insert = cmsPageMapper.insert(cmsPageDO);
        cmsPage.setId(cmsPageDO.getId());
        return insert;
    }

    @Override
    public int updateCmsPage(CmsPage cmsPage) {
        CmsPageDO cmsPageDO = CmsPageTransfer.entityToDO(cmsPage);
        return cmsPageMapper.updateById(cmsPageDO);
    }

    @Override
    public int batchSaveCmsPage(List<CmsPage> cmsPageList) {

        if (CollectionUtils.isEmpty(cmsPageList)) {
            return 0;
        }
        List<CmsPageDO> cmsPageDOS = cmsPageList.stream().map(CmsPageTransfer::entityToDO).collect(Collectors.toList());
        int insertBatch = cmsPageMapper.insertBatch(cmsPageDOS);
        int size = cmsPageDOS.size();
        for (int i = 0; i < size; i++) {
            CmsPageDO entity = cmsPageDOS.get(i);
            if (entity != null) {
                cmsPageList.get(i).setId(entity.getId());
            }
        }
        return insertBatch;
    }

    @Override
    public Boolean removeCmsPage(Integer id, Long operatorId) {
        return cmsPageMapper.removeCmsPage(id, operatorId);
    }

    @Override
    public CmsPage selectByIdAndDelete(Integer id) {
        CmsPageDO cmsPageDO = cmsPageMapper.selectByIdAndDelete(id);
        return CmsPageTransfer.dOToEntity(cmsPageDO);
    }

    @Override
    public CmsPage selectCmsPageById(Integer id) {
        CmsPageDO cmsPageDO = cmsPageMapper.selectById(id);
        return CmsPageTransfer.dOToEntity(cmsPageDO);
    }

    @Override
    public CmsPage selectCmsPageByIdIncludeDeleted(Integer id) {
        CmsPageDO cmsPageDO = cmsPageMapper.selectByIdIncludeDeleted(id);
        return CmsPageTransfer.dOToEntity(cmsPageDO);
    }

    @Override
    public List<CmsPage> getCmsPageList(Integer pageSchemaId) {

        List<CmsPageDO> cmsPageList = cmsPageMapper.getCmsPageList(pageSchemaId);

        if (CollectionUtils.isEmpty(cmsPageList)) {
            return Lists.newArrayList();
        }

        return cmsPageList.stream().map(CmsPageTransfer::dOToEntity).collect(Collectors.toList());
    }

    @Override
    public int getCmsPageCount(Integer pageSchemaId) {
        return cmsPageMapper.getCmsPageCount(pageSchemaId);
    }

    @Override
    public List<CmsPage> getCmsPageList(List<Integer> pageSchemaIdList) {
        List<CmsPageDO> cmsPageList = cmsPageMapper.getCmsPageListBatch(pageSchemaIdList);

        if (CollectionUtils.isEmpty(cmsPageList)) {
            return Lists.newArrayList();
        }

        return cmsPageList.stream().map(CmsPageTransfer::dOToEntity).collect(Collectors.toList());
    }

    /**
     * 查询生效中页面实例
     *
     * @param pageSchemaIdList 页面定义ID
     * @return 页面实例列表
     */
    @Override
    public List<CmsPage> getCmsPageListForValid(List<Integer> pageSchemaIdList) {
        if (CollectionUtils.isEmpty(pageSchemaIdList)) {
            return null;
        }
        List<CmsPageDO> pageList = cmsPageMapper.getCmsPageListForValid(pageSchemaIdList);
        return CollectionUtils.isEmpty(pageList) ? null : pageList.stream().map(CmsPageTransfer::dOToEntity).collect(Collectors.toList());
    }

    @Override
    public List<CmsPage> getCmsPageListForActive(List<Integer> pageSchemaIdList) {
        if (CollectionUtils.isEmpty(pageSchemaIdList)) {
            return null;
        }
        List<CmsPageDO> pageList = cmsPageMapper.getCmsPageListForActive(pageSchemaIdList);
        return CollectionUtils.isEmpty(pageList) ? null : pageList.stream().map(CmsPageTransfer::dOToEntity).collect(Collectors.toList());
    }

    @Override
    public PageData<CmsPageTemplate> getPageTemplateList(CmsPageTemplateList cmsPageTemplateList) {
        //分页查询
        Integer id = cmsPageTemplateList.getId();
        Integer pageId = cmsPageTemplateList.getPageId();
        String pageTitle = cmsPageTemplateList.getPageTitle();
        Integer pageStatus = cmsPageTemplateList.getPageStatus();
        Page<Object> page = PageHelper.startPage(cmsPageTemplateList.getCurrent().intValue(), cmsPageTemplateList.getSize().intValue());
        List<CmsPageDO> templateList = cmsPageMapper.getPageTemplateList(id, pageId, pageTitle, pageStatus);

        if (CollectionUtils.isEmpty(templateList)) {
            return new PageData<>(Lists.newArrayList(), cmsPageTemplateList.getCurrent(), cmsPageTemplateList.getSize(), 0);
        }

        List<CmsPageTemplate> templates = templateList.stream().map(CmsPageTransfer::dOToTemplate).collect(Collectors.toList());

        return new PageData<>(templates, cmsPageTemplateList.getCurrent(), cmsPageTemplateList.getSize(), page.getTotal());
    }

    /**
     * 查询自动下架专题
     *
     * @param enableTime
     * @param tagId
     * @return
     */
//    @Override
//    public List<Long> getSubjectByGoodsEnable(LocalDateTime enableTime, Long tagId) {
//        return cmsPageMapper.getSubjectByGoodsEnable(enableTime, tagId);
//    }

    @Override
    public List<CmsPage> getPageListForEnable(LocalDateTime enableTime, Long tagId) {
        List<CmsPageDO> pageList = cmsPageMapper.getPageListForEnable(enableTime, tagId);
        return CollectionUtils.isEmpty(pageList) ? null : pageList.stream().map(CmsPageTransfer::dOToEntity).collect(Collectors.toList());
    }

    /**
     * 获取截止时间内容的页面实例
     * @param endTime      结束时间
     * @param tagIdList    标签ID
     * @return  页面实例列表
     */
    @Override
    public List<CmsPage> getPageListForValid(LocalDateTime endTime, List<Integer> tagIdList) {
        List<CmsPageDO> pageList = cmsPageMapper.getPageListForValid(endTime, tagIdList);
        return CollectionUtils.isEmpty(pageList) ? null : pageList.stream().map(CmsPageTransfer::dOToEntity).collect(Collectors.toList());
    }

    /**
     * 修改专题自动下架状态
     *
     * @param id
     * @param enable
     */
    @Override
    public void updateGoodsEnable(Long id, Integer enable) {
        cmsPageMapper.updateGoodsEnable(id, enable);
    }

    @Override
    public List<CmsPage> queryActivityPageIds() {
        List<CmsPageDO> cmsPageDOS = cmsPageMapper.queryActivityPageIds();
        if (CollectionUtils.isEmpty(cmsPageDOS)) {
            return Lists.newArrayList();
        }
        return cmsPageDOS.stream().map(CmsPageTransfer::dOToEntity).collect(Collectors.toList());
    }

    /**
     * 修改页面的生效时间
     *
     * @param id        页面实例ID
     * @param startTime 生效时间
     * @param operateId 操作人ID
     * @return true：成功；false：失败
     */
    @Override
    public boolean updateStartTimeById(Integer id, LocalDateTime startTime, Long operateId) {
        return cmsPageMapper.updateStartTimeById(id, startTime, operateId) > 0;
    }

    @Override
    public List<CmsPage> queryActivityPageBySpuId(Long spuId) {
        List<CmsPageDO> cmsPageDOS = cmsPageMapper.queryActivityPageBySpuId(spuId);
        if (CollectionUtils.isEmpty(cmsPageDOS)) {
            return Lists.newArrayList();
        }
        return cmsPageDOS.stream().map(CmsPageTransfer::dOToEntity).collect(Collectors.toList());
    }

    @Override
    public List<CmsPage> queryActivityPageByPageIds(List<Integer> pageIds) {
        if (CollectionUtils.isEmpty(pageIds)) {
            return Lists.newArrayList();
        }
        List<CmsPageDO> cmsPageDOS = cmsPageMapper.queryActivityPageByPageIds(pageIds);
        if (CollectionUtils.isEmpty(cmsPageDOS)) {
            return Lists.newArrayList();
        }
        return cmsPageDOS.stream().map(CmsPageTransfer::dOToEntity).collect(Collectors.toList());
    }

    @Override
    public Integer queryRecentlyCreationPageIdBy(Integer pageSchemaId) {
        return cmsPageMapper.queryRecentlyCreationPageIdBy(pageSchemaId);
    }
}
