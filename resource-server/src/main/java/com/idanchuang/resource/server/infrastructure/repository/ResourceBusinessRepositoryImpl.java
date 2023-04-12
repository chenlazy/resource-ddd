package com.idanchuang.resource.server.infrastructure.repository;

import com.idanchuang.resource.server.domain.model.resource.ResourceBusiness;
import com.idanchuang.resource.server.domain.repository.ResourceBusinessRepository;
import com.idanchuang.resource.server.infrastructure.persistence.mapper.ResourceBusinessMapper;
import com.idanchuang.resource.server.infrastructure.persistence.model.ResourceBusinessDO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author fym
 * @description :
 * @date 2021/3/11 下午6:02
 */
@Repository
public class ResourceBusinessRepositoryImpl implements ResourceBusinessRepository {

    @Resource
    private ResourceBusinessMapper resourceBusinessMapper;

    @Override
    public List<ResourceBusiness> getResourceBusiness() {
        List<ResourceBusinessDO> doList = resourceBusinessMapper.getResourceBusiness();
        return doList.stream().map(resourceBusinessDO -> {
            ResourceBusiness resourceBusiness = new ResourceBusiness();
            BeanUtils.copyProperties(resourceBusinessDO, resourceBusiness);
            return resourceBusiness;
        }).collect(toList());
    }


}
