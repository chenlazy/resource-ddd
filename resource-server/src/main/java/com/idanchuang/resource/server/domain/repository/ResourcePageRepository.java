package com.idanchuang.resource.server.domain.repository;

import com.idanchuang.resource.server.domain.model.resource.ResourcePage;

import java.util.List;

/**
 * Created by develop at 2021/2/5.
 */
public interface ResourcePageRepository {

    List<ResourcePage> getResourcePageByBusinessId(Integer businessId);

    ResourcePage getResourcePageByPageCode(String pageCode);
}
