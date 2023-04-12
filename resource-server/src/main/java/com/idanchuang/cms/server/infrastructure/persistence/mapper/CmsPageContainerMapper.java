package com.idanchuang.cms.server.infrastructure.persistence.mapper;

import com.idanchuang.cms.server.infrastructure.persistence.model.CmsPageContainerDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-01 17:40
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Mapper
public interface CmsPageContainerMapper {

    int insert(CmsPageContainerDO cmsPageContainerDO);

    int insertBatch(@Param("containers") List<CmsPageContainerDO> cmsPageContainerDOS);

    List<CmsPageContainerDO> queryPageContainer(@Param("pageId") Integer pageId);

    List<CmsPageContainerDO> queryPageContainerByPageIdList(@Param("list") List<Integer> pageIdList);

    boolean updateContainerPage(@Param("pageId") Integer pageId, @Param("operatorId") Integer operatorId,
                                @Param("containerIds") List<Long> containerIds);

    boolean updateContainerSnapRoot(@Param("ids") List<Long> ids, @Param("snapRoot") String snapRoot);

    /**
     * 根据页面实例ids查询关联容器信息
     *
     * @param pageIds
     * @return
     */
    List<CmsPageContainerDO> queryPageContainersByPageIds(@Param("pageIds") List<Integer> pageIds);

    /**
     * 获取容器信息
     *
     * @param id 容器ID
     * @return 容器持久化对象
     */
    CmsPageContainerDO getById(Long id);

    /**
     * 获取容器信息
     *
     * @param ids 容器ID
     * @return 容器持久化对象
     */
    List<CmsPageContainerDO> getByIds(@Param("ids") List<Long> ids);

}
