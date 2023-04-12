package com.idanchuang.resource.server.interfaces.assember;

import com.idanchuang.resource.api.request.ResourceConfigCreateReqDTO;
import com.idanchuang.resource.api.request.ResourceConfigUpdateReqDTO;
import com.idanchuang.resource.api.response.ResourceConfigRespDTO;
import com.idanchuang.resource.server.domain.model.resource.ResourceConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wengbinbin
 * @date 2021/3/24
 */

public class ResourceConfigDtoAssembler {
    public static ResourceConfig toEntity(ResourceConfigCreateReqDTO resourceConfigCreateReqDTO) {
        if (resourceConfigCreateReqDTO == null) {
            return null;
        }
        ResourceConfig.ResourceConfigBuilder resourceConfig = ResourceConfig.builder();
        resourceConfig.resourceName(resourceConfigCreateReqDTO.getResourceName());
        resourceConfig.pageCode(resourceConfigCreateReqDTO.getPageCode());
        resourceConfig.businessType(resourceConfigCreateReqDTO.getBusinessType());
        resourceConfig.resourceType(resourceConfigCreateReqDTO.getResourceType());
        resourceConfig.resourceScheme(resourceConfigCreateReqDTO.getResourceScheme());
        resourceConfig.componentConfined(resourceConfigCreateReqDTO.getComponentConfined());
        resourceConfig.componentType(resourceConfigCreateReqDTO.getComponentType());
        resourceConfig.operatorUser(resourceConfigCreateReqDTO.getOperatorUser());
        resourceConfig.operatorId(resourceConfigCreateReqDTO.getOperatorId());
        resourceConfig.resourceNumb(resourceConfigCreateReqDTO.getResourceNumb());
        resourceConfig.createUser(resourceConfigCreateReqDTO.getCreateUser());
        resourceConfig.createUserId(resourceConfigCreateReqDTO.getCreateUserId());
        return resourceConfig.build();
    }

    public static ResourceConfig toEntity(ResourceConfigUpdateReqDTO resourceConfigUpdateReqDTO) {
        if (resourceConfigUpdateReqDTO == null) {
            return null;
        }
        ResourceConfig.ResourceConfigBuilder resourceConfig = ResourceConfig.builder();
        resourceConfig.id(resourceConfigUpdateReqDTO.getResourceId());
        resourceConfig.resourceName(resourceConfigUpdateReqDTO.getResourceName());
        resourceConfig.pageCode(resourceConfigUpdateReqDTO.getPageCode());
        resourceConfig.businessType(resourceConfigUpdateReqDTO.getBusinessType());
        resourceConfig.resourceType(resourceConfigUpdateReqDTO.getResourceType());
        resourceConfig.resourceScheme(resourceConfigUpdateReqDTO.getResourceScheme());
        resourceConfig.componentConfined(resourceConfigUpdateReqDTO.getComponentConfined());
        resourceConfig.componentType(resourceConfigUpdateReqDTO.getComponentType());
        resourceConfig.resourceStatus(resourceConfigUpdateReqDTO.getResourceStatus());
        resourceConfig.operatorUser(resourceConfigUpdateReqDTO.getOperatorUser());
        resourceConfig.operatorId(resourceConfigUpdateReqDTO.getOperatorId());
        resourceConfig.resourceNumb(resourceConfigUpdateReqDTO.getResourceNumb());
        return resourceConfig.build();
    }

    public static ResourceConfigRespDTO entityToDTO(ResourceConfig resourceConfig) {
        if (resourceConfig == null) {
            return null;
        }
        ResourceConfigRespDTO resourceConfigRespDTO = new ResourceConfigRespDTO();
        resourceConfigRespDTO.setId(resourceConfig.getId());
        resourceConfigRespDTO.setBusinessType(resourceConfig.getBusinessType());
        resourceConfigRespDTO.setPageCode(resourceConfig.getPageCode());
        resourceConfigRespDTO.setPageName(resourceConfig.getPageName());
        resourceConfigRespDTO.setResourceName(resourceConfig.getResourceName());
        resourceConfigRespDTO.setResourceStatus(resourceConfig.getResourceStatus());
        resourceConfigRespDTO.setOperatorUser(resourceConfig.getOperatorUser());
        resourceConfigRespDTO.setResourceNumb(resourceConfig.getResourceNumb());
        resourceConfigRespDTO.setComponentConfined(resourceConfig.getComponentConfined());
        resourceConfigRespDTO.setComponentType(resourceConfig.getComponentType());
        resourceConfigRespDTO.setCreateUser(resourceConfig.getCreateUser());
        resourceConfigRespDTO.setResourceType(resourceConfig.getResourceType());
        resourceConfigRespDTO.setResourceScheme(resourceConfig.getResourceScheme());
        return resourceConfigRespDTO;
    }

    public static List<ResourceConfigRespDTO> entityToDTO(List<ResourceConfig> resourceConfigList) {
        if (resourceConfigList == null) {
            return null;
        }
        List<ResourceConfigRespDTO> list = new ArrayList<ResourceConfigRespDTO>(resourceConfigList.size());
        for (ResourceConfig resourceConfig : resourceConfigList) {
            list.add(entityToDTO(resourceConfig));
        }
        return list;
    }
}
