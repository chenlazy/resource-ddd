package com.idanchuang.cms.server.domain.repository;

import com.idanchuang.cms.server.domain.model.cms.CmsPageContainer;
import com.idanchuang.cms.server.infrastructure.persistence.model.CmsPageContainerDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-01 17:29
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public interface CmsPageContainerRepository {

    Boolean createPageContainer(CmsPageContainer cmsPageContainer);

    Boolean batchCreatePageContainer(List<CmsPageContainer> cmsPageContainers);

    List<CmsPageContainer> queryPageContainer(Integer pageId);

    List<CmsPageContainer> queryPageContainer(List<Integer> pageIdList);

    Boolean updateContainerPage(Integer pageId, Integer operatorId, List<Long> containerIds);

    /**
     * 根据页面实例ids查询关联容器信息
     * @param pageIds
     * @return
     */
    List<CmsPageContainer> queryPageContainersByPageIds(List<Integer> pageIds);

    /**
     * 获取容器信息
     * @param id    容器ID
     * @return  容器模型对象
     */
    CmsPageContainer getById(Long id);


    /**
     * 批量获取容器信息
     */
    List<CmsPageContainer> getByIds(List<Long> ids);
}
