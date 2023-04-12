package com.idanchuang.resource.server.interfaces.assember;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idanchuang.resource.api.request.ResourceUnitCreateReqDTO;
import com.idanchuang.resource.api.request.ResourceUnitUpdateReqDTO;
import com.idanchuang.resource.api.response.ResourceUnitRespDTO;
import com.idanchuang.resource.server.domain.model.resource.ResourceUnit;
import com.idanchuang.resource.server.domain.model.strategy.UnitStrategy;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

/**
 * @author wengbinbin
 * @date 2021/3/24
 */

public class ResourceUnitDtoAssembler {
    public static ObjectMapper objectMapper = new ObjectMapper();

    public static ResourceUnit toEntity(ResourceUnitCreateReqDTO resourceUnitCreateReqDTO) {
        if ( resourceUnitCreateReqDTO == null ) {
            return null;
        }

        ResourceUnit.ResourceUnitBuilder resourceUnit = ResourceUnit.builder();

        resourceUnit.operatorId( resourceUnitCreateReqDTO.getOperatorId() );
        resourceUnit.operatorUser( resourceUnitCreateReqDTO.getOperatorUser() );
        resourceUnit.activeStatus( resourceUnitCreateReqDTO.getActiveStatus() );
        resourceUnit.resourceId( resourceUnitCreateReqDTO.getResourceId() );
        resourceUnit.platformFrom( resourceUnitCreateReqDTO.getPlatformFrom() );
        resourceUnit.weight( resourceUnitCreateReqDTO.getWeight() );
        resourceUnit.startTime( resourceUnitCreateReqDTO.getStartTime() );
        resourceUnit.endTime( resourceUnitCreateReqDTO.getEndTime() );
//        resourceUnit.visibleRoleS( resourceUnitCreateReqDTO.getVisibleRoleS() );
        resourceUnit.contentTitle( resourceUnitCreateReqDTO.getContentTitle() );
        resourceUnit.componentJsonData( resourceUnitCreateReqDTO.getComponentJsonData() );
        resourceUnit.createUser(resourceUnitCreateReqDTO.getCreateUser());
        resourceUnit.createUserId(resourceUnitCreateReqDTO.getCreateUserId());
        resourceUnit.componentName(resourceUnitCreateReqDTO.getComponentName());

        resourceUnit.unitStrategy(UnitStrategy.builder().type(1).visibleRoleS(resourceUnitCreateReqDTO.getVisibleRoleS()).build());
        return resourceUnit.build();
    }

    public static ResourceUnitRespDTO entityToDTO(ResourceUnit resourceUnit) {
        if ( resourceUnit == null ) {
            return null;
        }
        ResourceUnitRespDTO resourceUnitRespDTO = new ResourceUnitRespDTO();
        resourceUnitRespDTO.setId( resourceUnit.getId() );
        resourceUnitRespDTO.setResourceId( resourceUnit.getResourceId() );
        resourceUnitRespDTO.setResourceName( resourceUnit.getResourceName() );
        resourceUnitRespDTO.setPageName( resourceUnit.getPageName() );
        resourceUnitRespDTO.setPageCode(resourceUnit.getPageCode());
        resourceUnitRespDTO.setPlatformFrom( resourceUnit.getPlatformFrom() );
        resourceUnitRespDTO.setWeight( resourceUnit.getWeight() );
        resourceUnitRespDTO.setStartTime( resourceUnit.getStartTime() );
        resourceUnitRespDTO.setEndTime( resourceUnit.getEndTime() );
        resourceUnitRespDTO.setVisibleRoleS( resourceUnit.getUnitStrategy().getVisibleRoleS() );
        resourceUnitRespDTO.setContentTitle( resourceUnit.getContentTitle() );
        resourceUnitRespDTO.setComponentJsonData( resourceUnit.getComponentJsonData() );
        resourceUnitRespDTO.setActiveStatus( resourceUnit.getActiveStatus() );
        resourceUnitRespDTO.setWorkStatus( resourceUnit.getWorkStatus() );
        resourceUnitRespDTO.setOperatorUser( resourceUnit.getOperatorUser() );
        resourceUnitRespDTO.setComponentConfined( resourceUnit.getComponentConfined() );
        resourceUnitRespDTO.setComponentType( resourceUnit.getComponentType() );
        resourceUnitRespDTO.setCreateUser(resourceUnit.getCreateUser());
        resourceUnitRespDTO.setComponentName(resourceUnit.getComponentName());
        resourceUnitRespDTO.setResourceType(resourceUnit.getResourceType());
        resourceUnitRespDTO.setResourceScheme(resourceUnit.getResourceScheme());
        return resourceUnitRespDTO;
    }

    public static List<ResourceUnitRespDTO> entityToDTO(List<ResourceUnit> resourceUnitList) {
        if ( resourceUnitList == null ) {
            return null;
        }

        List<ResourceUnitRespDTO> list = new ArrayList<ResourceUnitRespDTO>( resourceUnitList.size() );
        for ( ResourceUnit resourceUnit : resourceUnitList ) {
            list.add( entityToDTO( resourceUnit ) );
        }

        return list;
    }

    public static ResourceUnit dtoToEntity(ResourceUnitUpdateReqDTO dto) {
        ResourceUnit resourceUnit = new ResourceUnit();
        BeanUtils.copyProperties(dto, resourceUnit);
        resourceUnit.setId(dto.getUnitId());
        checkBeginAndEndTime(resourceUnit, dto.getWeight());
        try {
            if (!StringUtils.isEmpty(dto.getPlatformFrom())) {
                resourceUnit.setPlatformFrom(objectMapper.writeValueAsString(dto.getPlatformFrom()));
            }
            if (!CollectionUtils.isEmpty(dto.getVisibleRoleS())) {
                resourceUnit.setUnitStrategy(UnitStrategy.builder().type(1).visibleRoleS(dto.getVisibleRoleS()).build());
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(format("解析字段失败, PlatformFrom : [%s], VisibleRoleS : [%s]"
                    , dto.getPlatformFrom(), dto.getVisibleRoleS()));
        }
        return resourceUnit;
    }

    public static ResourceUnit checkBeginAndEndTime(ResourceUnit resourceUnit ,Integer weight) {
        if (weight != null && weight.equals(-1)) {
            resourceUnit.setStartTime(LocalDateTime.now());
            resourceUnit.setEndTime( LocalDateTime.parse("2099-01-01T00:00:00"));
        }
        return resourceUnit;
    }
}
