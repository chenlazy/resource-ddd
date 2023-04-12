package com.idanchuang.resource.server.application.service;

import com.idanchuang.resource.server.domain.model.resource.ResourcePage;
import com.idanchuang.resource.server.domain.repository.ResourcePageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wengbinbin
 * @date 2021/3/18
 */

@Service
@Slf4j
public class ResourcePageService {
    @Resource
    private ResourcePageRepository resourcePageRepository;

    public List<ResourcePage> getResourcePageByBusinessId(Integer businessId) {
        return resourcePageRepository.getResourcePageByBusinessId(businessId);
    }
}
