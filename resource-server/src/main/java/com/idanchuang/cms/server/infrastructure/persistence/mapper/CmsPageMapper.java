package com.idanchuang.cms.server.infrastructure.persistence.mapper;

import com.idanchuang.cms.server.domain.model.cms.CmsPage;
import com.idanchuang.cms.server.infrastructure.persistence.model.CmsPageDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-01 17:40
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Mapper
public interface CmsPageMapper {

    int insert(CmsPageDO cmsPageDO);

    int insertBatch(@Param("pageDOS") List<CmsPageDO> cmsPageDOS);

    int updateById(CmsPageDO cmsPageDO);

    CmsPageDO selectByIdAndDelete(@Param("id") Integer id);

    CmsPageDO selectById(@Param("id") Integer id);

    CmsPageDO selectByIdIncludeDeleted(@Param("id") Integer id);

    List<CmsPageDO> selectByIds(@Param("list") List<Integer> id);

    boolean removeCmsPage(@Param("id") Integer id, @Param("operatorId") Long operatorId);

    List<CmsPageDO> getCmsPageList(@Param("pageSchemaId") Integer pageSchemaId);

    int getCmsPageCount(@Param("pageSchemaId") Integer pageSchemaId);

    List<CmsPageDO> getCmsPageListBatch(@Param("list") List<Integer> pageSchemaIdList);

    List<CmsPageDO> getCmsPageListForValid(@Param("list") List<Integer> pageSchemaIdList);

    List<CmsPageDO> getCmsPageListForActive(@Param("list") List<Integer> pageSchemaIdList);

    List<CmsPageDO> getPageTemplateList(@Param("id") Integer id, @Param("pageId") Integer pageId,
                                        @Param("title") String title, @Param("status") Integer status);

    /**
     * 查询自动下架专题
     *
     * @param enableTime
     * @param tagId
     * @return
     */
    //List<Long> getSubjectByGoodsEnable(@Param("enableTime") LocalDateTime enableTime, @Param("tagId") Long tagId);

    List<CmsPageDO> getPageListForEnable(@Param("enableTime") LocalDateTime enableTime, @Param("tagId") Long tagId);

    List<CmsPageDO> getPageListForValid(@Param("endTime") LocalDateTime endTime, @Param("tagIdList") List<Integer> tagIdList);

    /**
     * 修改专题自动下架状态
     *
     * @param id
     * @param enable
     */
    void updateGoodsEnable(@Param("id") Long id, @Param("enable") Integer enable);

    /**
     * 查询活动中的页面实例信息
     *
     * @return
     */
    List<CmsPageDO> queryActivityPageIds();

    /**
     * 修改页面的生效时间
     *
     * @param id        页面实例ID
     * @param startTime 生效时间
     * @param operateId 操作人ID
     * @return 影响行数
     */
    int updateStartTimeById(@Param("id") Integer id, @Param("startTime") LocalDateTime startTime, @Param("operateId") Long operateId);

    /**
     * 查询活动中的页面实例信息
     *
     * @param spuId
     * @return
     */
    List<CmsPageDO> queryActivityPageBySpuId(@Param("spuId") Long spuId);

    /**
     * 根据实例id查询详情数据
     *
     * @param pageIds
     * @return
     */
    List<CmsPageDO> queryActivityPageByPageIds(@Param("pageIds") List<Integer> pageIds);

    /**
     * 根据页面id查询最新创建的模版id
     *
     * @param pageSchemaId
     * @return
     */
    Integer queryRecentlyCreationPageIdBy(@Param("pageSchemaId") Integer pageSchemaId);

    void updatePageSnapRoot(@Param("ids") List<Integer> ids, @Param("snapRoot") String snapRoot);
}
