package com.idanchuang.resource.server.infrastructure.repository;

import com.idanchuang.resource.server.domain.model.resource.ResourcePage;
import com.idanchuang.resource.server.domain.repository.ResourcePageRepository;
import com.idanchuang.resource.server.infrastructure.persistence.mapper.ResourcePageMapper;
import com.idanchuang.resource.server.infrastructure.persistence.model.ResourcePageDO;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ResourcePageRepositoryImpl implements ResourcePageRepository {

    @Resource
    private ResourcePageMapper resourcePageMapper;

    @Override
    public List<ResourcePage> getResourcePageByBusinessId(Integer businessId) {
        List<ResourcePageDO> doList = resourcePageMapper.getResourcePageByBusinessId(businessId);
        return doList.stream().map(resourcePageDO -> {
            ResourcePage resourcePage = new ResourcePage();
            BeanUtils.copyProperties(resourcePageDO, resourcePage);
            return resourcePage;
        }).collect(toList());
    }

    @Override
    public ResourcePage getResourcePageByPageCode(String pageCode) {
        try {
            ResourcePageDO resourcePageDO = resourcePageMapper.getResourcePageByPageCode(pageCode);
            ResourcePage resourcePage = new ResourcePage();
            BeanUtils.copyProperties(resourcePageDO, resourcePage);
            return resourcePage;
        } catch (Exception e) {
            log.error("获取pageCode失败，pageCode = [{}]", pageCode);
            return new ResourcePage().setPageName("无");
        }
    }
}
