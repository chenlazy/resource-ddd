package com.idanchuang.resource.server.infrastructure.repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.idanchuang.resource.server.domain.model.resource.ResourceConfig;
import com.idanchuang.resource.server.domain.model.resource.ResourceConfigQueryConditions;
import com.idanchuang.resource.server.domain.model.resource.ResourcePutInfo;
import com.idanchuang.resource.server.domain.repository.ResourceConfigRepository;
import com.idanchuang.resource.server.infrastructure.persistence.mapper.ResourceConfigMapper;
import com.idanchuang.resource.server.infrastructure.persistence.model.ResourceConfigDO;
import com.idanchuang.resource.server.infrastructure.transfer.ResourceConfigTransfer;
import com.idanchuang.resource.server.infrastructure.transfer.ResourcePutInfoTransfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.idanchuang.resource.server.infrastructure.transfer.ResourceConfigTransfer.doToEntity;
import static com.idanchuang.resource.server.infrastructure.transfer.ResourceConfigTransfer.entityToDO;

/**
 * Created by develop at 2021/2/5.
 *
 * @author wuai
 */
@Repository
@Slf4j
public class ResourceConfigRepositoryImpl implements ResourceConfigRepository {

    @Resource
    private ResourceConfigMapper resourceConfigMapper;

    @Override
    public ResourcePutInfo getResourceConfigByReq(Long resourceId, String pageCode, Integer business) {
        ResourceConfigDO resourceConfigByReq = resourceConfigMapper.getResourceConfigByReq(resourceId, pageCode, business);
        return ResourcePutInfoTransfer.do2Model(resourceConfigByReq);
    }

    @Override
    public List<ResourceConfig> getResourceListByPageId(String pageCode, Integer business) {
        List<ResourceConfigDO> resourceListByPageId = resourceConfigMapper.getResourceListByPageId(pageCode, business);
        List<ResourceConfig> resourceConfigs = resourceListByPageId.stream().map(ResourceConfigTransfer::do2Model).collect(Collectors.toList());
        return resourceConfigs;
    }

    @Override
    public Long getResourceIdByNameLimitOne(String resourceName) {
        return resourceConfigMapper.getResourceIdByNameLimitOne("%" + resourceName + "%");
    }

    @Override
    public int saveResourceConfig(ResourceConfig resourceConfig) {
        return resourceConfigMapper.insert(entityToDO(resourceConfig));
    }

    @Override
    public int updateResourceConfig(ResourceConfig resourceConfig) {
        ResourceConfigDO configDO = entityToDO(resourceConfig);
        return resourceConfigMapper.updateById(configDO);
    }

    @Override
    public ResourceConfig getResourceConfigById(Long resourceId) {
        ResourceConfigDO activityConfigDO = resourceConfigMapper.selectById(resourceId);
        if (activityConfigDO == null) {
            throw new RuntimeException(String.format("查询的资源位不存在, resourceId : [%s]", resourceId));
        }
        return doToEntity(activityConfigDO);
    }

    @Override
    public PageInfo<ResourceConfig> searchResourceConfig(ResourceConfigQueryConditions queryConditions) {
        PageHelper.startPage(queryConditions.getPageNum(), queryConditions.getPageSize());
        PageInfo<ResourceConfigDO> pageInfoDO = new PageInfo<>(resourceConfigMapper.searchResourceConfig(queryConditions));
        PageInfo<ResourceConfig> pageInfo = new PageInfo<ResourceConfig>();
        pageInfo.setList(doToEntity(pageInfoDO.getList()));
        pageInfo.setPageNum(queryConditions.getPageNum());
        pageInfo.setPageSize(queryConditions.getPageSize());
        pageInfo.setTotal(pageInfoDO.getTotal());
        return pageInfo;
    }

    @Override
    public Boolean checkResourceNumbUniq(String pageCode, Integer resourceNumb, Long resourceId) {
        List<ResourceConfigDO> resourceConfigDOList = resourceConfigMapper.checkResourceNumbUniq(pageCode, resourceNumb, resourceId);
        return resourceConfigDOList.size() == 0;
    }

    @Override
    public Boolean checkResourceNameUniq(String pageCode, String resourceName, Long resourceId) {
        List<ResourceConfigDO> resourceConfigDOList = resourceConfigMapper.checkResourceNameUniq(pageCode, resourceName, resourceId);
        return resourceConfigDOList.size() == 0;
    }

}
