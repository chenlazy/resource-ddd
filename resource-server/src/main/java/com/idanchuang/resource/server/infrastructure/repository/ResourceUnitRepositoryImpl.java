package com.idanchuang.resource.server.infrastructure.repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.idanchuang.resource.server.domain.model.resource.ResourceUnit;
import com.idanchuang.resource.server.domain.model.resource.ResourceUnitQueryConditions;
import com.idanchuang.resource.server.domain.model.strategy.UnitStrategy;
import com.idanchuang.resource.server.domain.repository.ResourceUnitRepository;
import com.idanchuang.resource.server.infrastructure.persistence.mapper.ResourceUnitMapper;
import com.idanchuang.resource.server.infrastructure.persistence.model.ResourceUnitDO;
import com.idanchuang.resource.server.infrastructure.transfer.ResourceUnitTransfer;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static com.idanchuang.resource.server.infrastructure.transfer.ResourceUnitTransfer.doToEntity;
import static com.idanchuang.resource.server.infrastructure.transfer.ResourceUnitTransfer.entityToDO;

/**
 * @author fym
 * @description :
 * @date 2021/3/11 下午5:59
 */
@Repository
public class ResourceUnitRepositoryImpl implements ResourceUnitRepository {
    @Resource
    private ResourceUnitMapper resourceUnitMapper;

    @Override
    public ResourceUnit queryResourceUnitByResourceAndRole(Long resourceId, Integer role, String platform, LocalDateTime time) {
        List<ResourceUnitDO> resourceUnitDOS = resourceUnitMapper.queryResourceUnitDOListByResourceId(resourceId, "%" + platform + "%", time);
        for (ResourceUnitDO resourceUnitDO : resourceUnitDOS) {
            String roleStr = resourceUnitDO.getUnitStrategy();
            if (StringUtils.isBlank(roleStr)) {
                continue;
            }
            UnitStrategy uintStrategy = JsonUtil.toObject(roleStr, UnitStrategy.class);
            if (null == uintStrategy) {
                continue;
            }
            //todo 后期需要判断投放群体type后遍历
            List<Integer> userLevelList = uintStrategy.getVisibleRoleS();
            if (userLevelList.contains(role)) {
                return ResourceUnitTransfer.do2ResourceUnit(resourceUnitDO);
            }
        }
        return null;
    }

    @Override
    public Long getUnitIdByUnitNameLimitOne(String unitName) {
        return resourceUnitMapper.getUnitIdByUnitNameLimitOne("%" + unitName + "%");
    }

    @Override
    public ResourceUnit getUnitInfoById(Long unitId) {
        ResourceUnitDO unitInfoById = resourceUnitMapper.getUnitInfoById(unitId);
        if (unitInfoById == null) {
            throw new RuntimeException(String.format("查询的资源位单元不存在, unitId : [%s]", unitId));
        }
        return ResourceUnitTransfer.do2ResourceUnit(unitInfoById);
    }

    @Override
    public int saveResourceUnit(ResourceUnit resourceUnit) {
        ResourceUnitDO unitDO = entityToDO(resourceUnit);
        return resourceUnitMapper.insert(unitDO);
    }

    @Override
    public int updateResourceUnit(ResourceUnit resourceUnit) {
        ResourceUnitDO unitDO = entityToDO(resourceUnit);
        return resourceUnitMapper.updateById(unitDO);
    }

    @Override
    public PageInfo<ResourceUnit> searchResourceUnit(ResourceUnitQueryConditions queryConditions) {
        PageHelper.startPage(queryConditions.getPageNum(), queryConditions.getPageSize());
        PageInfo<ResourceUnitDO> pageInfoDO = new PageInfo<>(resourceUnitMapper.searchResourceUnit(queryConditions));
        PageInfo<ResourceUnit> pageInfo = new PageInfo<ResourceUnit>();
        pageInfo.setList(doToEntity(pageInfoDO.getList()));
        pageInfo.setPageNum(queryConditions.getPageNum());
        pageInfo.setPageSize(queryConditions.getPageSize());
        pageInfo.setTotal(pageInfoDO.getTotal());
        return pageInfo;

    }

}
