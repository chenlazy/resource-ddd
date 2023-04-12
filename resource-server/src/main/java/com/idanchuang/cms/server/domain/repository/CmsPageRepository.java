package com.idanchuang.cms.server.domain.repository;

import com.idanchuang.cms.server.domain.model.cms.CmsPage;
import com.idanchuang.cms.server.domain.model.cms.CmsPageTemplate;
import com.idanchuang.cms.server.domain.model.cms.CmsPageTemplateList;
import com.idanchuang.component.base.page.PageData;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-01 17:29
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public interface CmsPageRepository {

    int saveCmsPage(CmsPage cmsPage);

    int updateCmsPage(CmsPage cmsPage);

    int batchSaveCmsPage(List<CmsPage> cmsPageList);

    Boolean removeCmsPage(Integer id, Long operatorId);

    /**
     * 查询实例信息 不区分删除状态
     * @param id
     * @return
     */
    CmsPage selectByIdAndDelete(Integer id);

    CmsPage selectCmsPageById(Integer id);

    CmsPage selectCmsPageByIdIncludeDeleted(Integer id);

    List<CmsPage> getCmsPageList(Integer pageSchemaId);

    int getCmsPageCount(Integer pageSchemaId);

    List<CmsPage> getCmsPageList(List<Integer> pageSchemaIdList);

    /**
     * 查询生效中页面实例
     * @param pageSchemaIdList  页面定义ID
     * @return  页面实例列表
     */
    List<CmsPage> getCmsPageListForValid(List<Integer> pageSchemaIdList);

    List<CmsPage> getCmsPageListForActive(List<Integer> pageSchemaIdList);

    PageData<CmsPageTemplate> getPageTemplateList(CmsPageTemplateList cmsPageTemplateList);

    /**
     * 查询自动下架专题
     * @param enableTime
     * @param tagId
     * @return
     */
    //List<Long> getSubjectByGoodsEnable(LocalDateTime enableTime, Long tagId);

    List<CmsPage> getPageListForEnable(LocalDateTime enableTime, Long tagId);

    List<CmsPage> getPageListForValid(LocalDateTime endTime, List<Integer> tagIdList);

    /**
     * 修改专题自动下架状态
     * @param id
     * @param enable
     */
    void updateGoodsEnable(Long id, Integer enable);

    /**
     * vtn 活动中页面实例id
     * @return
     */
    List<CmsPage> queryActivityPageIds();

    /**
     * 修改页面的生效时间
     * @param id            页面实例ID
     * @param startTime     生效时间
     * @param operateId     操作人ID
     * @return  true：成功；false：失败
     */
    boolean updateStartTimeById(Integer id, LocalDateTime startTime, Long operateId);

    /**
     * vtn 活动中页面实例id
     * @param spuId
     * @return
     */
    List<CmsPage> queryActivityPageBySpuId(Long spuId);

    /**
     * 根据实例id查询详情数据
     * @param pageIds
     * @return
     */
    List<CmsPage> queryActivityPageByPageIds(List<Integer> pageIds);

    /**
     * 根据页面id查询最新创建的模版id
     * @param pageSchemaId
     * @return
     */
    Integer queryRecentlyCreationPageIdBy(Integer pageSchemaId);
}
