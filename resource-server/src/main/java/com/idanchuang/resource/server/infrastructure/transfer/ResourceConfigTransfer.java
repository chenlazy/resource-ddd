package com.idanchuang.resource.server.infrastructure.transfer;

import com.idanchuang.resource.api.response.admin.ResourceConfigResp;
import com.idanchuang.resource.api.response.PutResourcePageListDTO;
import com.idanchuang.resource.api.response.ResourceConfigRespDTO;
import com.idanchuang.resource.server.domain.model.resource.ResourceConfig;
import com.idanchuang.resource.server.infrastructure.persistence.model.ResourceConfigDO;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author fym
 * @description :
 * @date 2021/3/15 上午11:48
 */
public class ResourceConfigTransfer {

    public static ResourceConfig do2Model(ResourceConfigDO resourceConfigDO) {
        if (resourceConfigDO == null) {
            return null;
        }
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.setId(resourceConfigDO.getId());
        resourceConfig.setResourceName(resourceConfigDO.getResourceName());
        resourceConfig.setPageCode(resourceConfigDO.getPageCode());
        resourceConfig.setBusinessType(resourceConfigDO.getBusinessType());
        resourceConfig.setResourceType(resourceConfigDO.getResourceType());
        resourceConfig.setResourceScheme(resourceConfigDO.getResourceScheme());
        resourceConfig.setComponentConfined(resourceConfigDO.getComponentConfined());
        resourceConfig.setComponentType(resourceConfigDO.getComponentType());
        resourceConfig.setResourceStatus(resourceConfigDO.getResourceStatus());
        resourceConfig.setOperatorUser(resourceConfigDO.getOperatorUser());
        resourceConfig.setOperatorId(resourceConfigDO.getOperatorId());
        resourceConfig.setCreateTime(resourceConfigDO.getCreateTime());
        resourceConfig.setUpdateTime(resourceConfigDO.getUpdateTime());
        resourceConfig.setIsDeleted(resourceConfigDO.getIsDeleted());
        resourceConfig.setResourceNumb(resourceConfigDO.getResourceNumb());
        return resourceConfig;
    }


    public static PutResourcePageListDTO resourceConfig2PageListDTO(ResourceConfig resourceConfig) {
        if (resourceConfig == null) {
            return null;
        }
        PutResourcePageListDTO putResourcePageListDTO = new PutResourcePageListDTO();
        putResourcePageListDTO.setResourceId(resourceConfig.getId());
        putResourcePageListDTO.setResourceStatus(resourceConfig.getResourceStatus());
        putResourcePageListDTO.setResourceNumb(resourceConfig.getResourceNumb());
        return putResourcePageListDTO;
    }

    public static ResourceConfigDO entityToDO(ResourceConfig resourceConfig) {
        if (resourceConfig == null) {
            return null;
        }
        ResourceConfigDO.ResourceConfigDOBuilder resourceConfigDO = ResourceConfigDO.builder();
        resourceConfigDO.id(resourceConfig.getId());
        resourceConfigDO.resourceName(resourceConfig.getResourceName());
        resourceConfigDO.pageCode(resourceConfig.getPageCode());
        resourceConfigDO.businessType(resourceConfig.getBusinessType());
        resourceConfigDO.resourceType(resourceConfig.getResourceType());
        resourceConfigDO.resourceScheme(resourceConfig.getResourceScheme());
        resourceConfigDO.componentConfined(resourceConfig.getComponentConfined());
        resourceConfigDO.componentType(resourceConfig.getComponentType());
        resourceConfigDO.resourceStatus(resourceConfig.getResourceStatus());
        resourceConfigDO.operatorUser(resourceConfig.getOperatorUser());
        resourceConfigDO.operatorId(resourceConfig.getOperatorId());
        resourceConfigDO.createTime(LocalDateTime.now());
        resourceConfigDO.updateTime(LocalDateTime.now());
        resourceConfigDO.isDeleted(resourceConfig.getIsDeleted());
        resourceConfigDO.resourceNumb(resourceConfig.getResourceNumb());
        resourceConfigDO.createUser(resourceConfig.getCreateUser());
        resourceConfigDO.createUserId(resourceConfig.getCreateUserId());
        return resourceConfigDO.build();
    }

    public static ResourceConfig doToEntity(ResourceConfigDO resourceConfigDO) {
        if (resourceConfigDO == null) {
            return null;
        }
        ResourceConfig.ResourceConfigBuilder resourceConfig = ResourceConfig.builder();
        resourceConfig.id(resourceConfigDO.getId());
        resourceConfig.resourceName(resourceConfigDO.getResourceName());
        resourceConfig.pageCode(resourceConfigDO.getPageCode());
        resourceConfig.businessType(resourceConfigDO.getBusinessType());
        resourceConfig.resourceType(resourceConfigDO.getResourceType());
        resourceConfig.resourceScheme(resourceConfigDO.getResourceScheme());
        resourceConfig.componentConfined(resourceConfigDO.getComponentConfined());
        resourceConfig.componentType(resourceConfigDO.getComponentType());
        resourceConfig.resourceStatus(resourceConfigDO.getResourceStatus());
        resourceConfig.operatorUser(resourceConfigDO.getOperatorUser());
        resourceConfig.operatorId(resourceConfigDO.getOperatorId());
        resourceConfig.createTime(resourceConfigDO.getCreateTime());
        resourceConfig.updateTime(resourceConfigDO.getUpdateTime());
        resourceConfig.isDeleted(resourceConfigDO.getIsDeleted());
        resourceConfig.resourceNumb(resourceConfigDO.getResourceNumb());
        resourceConfig.createUser(resourceConfigDO.getCreateUser());
        resourceConfig.createUserId(resourceConfigDO.getCreateUserId());
        return resourceConfig.build();
    }

    public static ResourceConfigResp dtoToDto(ResourceConfigRespDTO dto) {
        ResourceConfigResp resp = new ResourceConfigResp();
        BeanUtils.copyProperties(dto, resp);
        return resp;
    }

    public static List<ResourceConfigResp> dtoToDto(List<ResourceConfigRespDTO> respDTOS) {
        return respDTOS.stream().map(ResourceConfigTransfer::dtoToDto).collect(toList());
    }

    public static ResourceConfigResp dto2facadeResp(ResourceConfigRespDTO dto) {
        if (dto == null) {
            return null;
        }
        ResourceConfigResp resourceConfigResp = new ResourceConfigResp();
        resourceConfigResp.setId(dto.getId());
        resourceConfigResp.setBusinessType(dto.getBusinessType());
        resourceConfigResp.setPageCode(dto.getPageCode());
        resourceConfigResp.setPageName(dto.getPageName());
        resourceConfigResp.setResourceName(dto.getResourceName());
        resourceConfigResp.setResourceStatus(dto.getResourceStatus());
        resourceConfigResp.setOperatorUser(dto.getOperatorUser());
        resourceConfigResp.setCreateUser(dto.getCreateUser());
        resourceConfigResp.setResourceNumb(dto.getResourceNumb());
        resourceConfigResp.setComponentConfined(dto.getComponentConfined());
        resourceConfigResp.setComponentType(dto.getComponentType());
        resourceConfigResp.setResourceType(dto.getResourceType());
        resourceConfigResp.setResourceScheme(dto.getResourceScheme());
        return resourceConfigResp;
    }

    public static List<ResourceConfig> doToEntity(List<ResourceConfigDO> resourceConfigDOList) {
        if (resourceConfigDOList == null) {
            return null;
        }
        List<ResourceConfig> list = new ArrayList<ResourceConfig>(resourceConfigDOList.size());
        for (ResourceConfigDO resourceConfigDO : resourceConfigDOList) {
            list.add(doToEntity(resourceConfigDO));
        }
        return list;
    }
}
