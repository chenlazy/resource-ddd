package com.idanchuang.cms.server.infrastructure.repository;

import com.google.common.collect.Lists;
import com.idanchuang.cms.server.domain.model.cms.CmsPageContainer;
import com.idanchuang.cms.server.domain.repository.CmsPageContainerRepository;
import com.idanchuang.cms.server.infrastructure.persistence.mapper.CmsPageContainerMapper;
import com.idanchuang.cms.server.infrastructure.persistence.model.CmsPageContainerDO;
import com.idanchuang.cms.server.infrastructure.transfer.CmsPageContainerTransfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-01 17:31
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Repository
@Slf4j
public class CmsPageContainerRepositoryImpl implements CmsPageContainerRepository {

    @Resource
    private CmsPageContainerMapper cmsPageContainerMapper;

    @Override
    public Boolean createPageContainer(CmsPageContainer cmsPageContainer) {
        CmsPageContainerDO cmsPageContainerDO = CmsPageContainerTransfer.entityToDO(cmsPageContainer);
        int insert = cmsPageContainerMapper.insert(cmsPageContainerDO);
        cmsPageContainer.setId(cmsPageContainerDO.getId());
        return insert > 0;
    }

    @Override
    public Boolean batchCreatePageContainer(List<CmsPageContainer> cmsPageContainers) {
        if (CollectionUtils.isEmpty(cmsPageContainers)) {
            return false;
        }
        List<CmsPageContainerDO> containerDOS = cmsPageContainers.stream().map(CmsPageContainerTransfer::entityToDO).collect(Collectors.toList());
        int insertBatch = cmsPageContainerMapper.insertBatch(containerDOS);
        int size = containerDOS.size();
        for (int i = 0; i < size; i++) {
            CmsPageContainerDO entity = containerDOS.get(i);
            if (entity != null) {
                cmsPageContainers.get(i).setId(entity.getId());
            }
        }
        return insertBatch > 0;
    }

    @Override
    public List<CmsPageContainer> queryPageContainer(Integer pageId) {
        if (null == pageId) {
            return Lists.newArrayList();
        }
        List<CmsPageContainerDO> containerDOS = cmsPageContainerMapper.queryPageContainer(pageId);
        if (CollectionUtils.isEmpty(containerDOS)) {
            return Lists.newArrayList();
        }
        return containerDOS.stream().map(CmsPageContainerTransfer::doToEntity).collect(Collectors.toList());
    }

    @Override
    public List<CmsPageContainer> queryPageContainer(List<Integer> pageIdList) {

        if (CollectionUtils.isEmpty(pageIdList)) {
            return Lists.newArrayList();
        }
        List<CmsPageContainerDO> pageContainerList = cmsPageContainerMapper.queryPageContainerByPageIdList(pageIdList);
        if (CollectionUtils.isEmpty(pageContainerList)) {
            return null;
        }
        return pageContainerList.stream().map(CmsPageContainerTransfer::doToEntity).collect(Collectors.toList());
    }

    @Override
    public Boolean updateContainerPage(Integer pageId, Integer operatorId, List<Long> containerIds) {
        return cmsPageContainerMapper.updateContainerPage(pageId, operatorId, containerIds);
    }

    @Override
    public List<CmsPageContainer> queryPageContainersByPageIds(List<Integer> pageIds) {
        if (org.apache.commons.collections.CollectionUtils.isEmpty(pageIds)) {
            return Lists.newArrayList();
        }
        List<CmsPageContainerDO> cmsPageContainerDOS = cmsPageContainerMapper.queryPageContainersByPageIds(pageIds);
        if (CollectionUtils.isEmpty(cmsPageContainerDOS)) {
            return Lists.newArrayList();
        }
        return cmsPageContainerDOS.stream().map(CmsPageContainerTransfer::doToEntity).collect(Collectors.toList());
    }

    /**
     * 获取容器信息
     *
     * @param id 容器ID
     * @return 容器模型对象
     */
    @Override
    public CmsPageContainer getById(Long id) {
        if (null == id) {
            return null;
        }
        CmsPageContainerDO pageContainer = cmsPageContainerMapper.getById(id);
        return pageContainer == null ? null : CmsPageContainerTransfer.doToEntity(pageContainer);
    }

    @Override
    public List<CmsPageContainer> getByIds(List<Long> ids) {

        if(CollectionUtils.isEmpty(ids)){
            return Lists.newArrayList();
        }
        List<CmsPageContainerDO> cmsPageContainerDOS = cmsPageContainerMapper.getByIds(ids);
        return CollectionUtils.isEmpty(cmsPageContainerDOS) ? Lists.newArrayList() :
                cmsPageContainerDOS.stream().map(CmsPageContainerTransfer::doToEntity).collect(Collectors.toList());
    }
}
