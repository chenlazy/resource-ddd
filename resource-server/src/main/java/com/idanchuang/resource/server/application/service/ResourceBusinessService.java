package com.idanchuang.resource.server.application.service;

import com.idanchuang.resource.server.domain.model.resource.ResourceBusiness;
import com.idanchuang.resource.server.domain.repository.ResourceBusinessRepository;
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
public class ResourceBusinessService {
    @Resource
    private ResourceBusinessRepository resourceBusinessRepository;

    public List<ResourceBusiness> getResourceBusiness() {
        return resourceBusinessRepository.getResourceBusiness();
    }
}
