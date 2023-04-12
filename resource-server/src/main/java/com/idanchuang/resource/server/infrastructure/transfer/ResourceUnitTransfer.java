package com.idanchuang.resource.server.infrastructure.transfer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idanchuang.resource.api.response.PutInfoDTO;
import com.idanchuang.resource.api.response.ResourceUnitRespDTO;
import com.idanchuang.resource.api.response.admin.ResourceUnitResp;
import com.idanchuang.resource.server.domain.model.resource.ResourceUnit;
import com.idanchuang.resource.server.domain.model.strategy.UnitStrategy;
import com.idanchuang.resource.server.infrastructure.persistence.model.ResourceUnitDO;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

/**
 * @author wengbinbin
 * @date 2021/3/18
 */
public class ResourceUnitTransfer {

    public static ObjectMapper objectMapper = new ObjectMapper();

    public static PutInfoDTO resourceUnit2PutInfoDTO(ResourceUnit resourceUnit) {
        if (resourceUnit == null) {
            return null;
        }
        PutInfoDTO putInfoDTO = new PutInfoDTO();
        putInfoDTO.setId(resourceUnit.getId());
        putInfoDTO.setCreateTime(resourceUnit.getCreateTime());
        putInfoDTO.setActiveStatus(resourceUnit.getActiveStatus());
        putInfoDTO.setResourceId(resourceUnit.getResourceId());
        putInfoDTO.setPlatformFrom(resourceUnit.getPlatformFrom());
        putInfoDTO.setWeight(resourceUnit.getWeight());
        putInfoDTO.setStartTime(resourceUnit.getStartTime());
        putInfoDTO.setEndTime(resourceUnit.getEndTime());
//        putInfoDTO.setVisibleRoleS(resourceUnit.getVisibleRoleS());
        putInfoDTO.setContentTitle(resourceUnit.getContentTitle());
        putInfoDTO.setComponentJsonData(resourceUnit.getComponentJsonData());
        return putInfoDTO;
    }

    public static ResourceUnit do2ResourceUnit(ResourceUnitDO resourceUnitDO) {
        if (resourceUnitDO == null) {
            return null;
        }
        ResourceUnit resourceUnit = new ResourceUnit();
        resourceUnit.setId(resourceUnitDO.getId());
        resourceUnit.setResourceId(resourceUnitDO.getResourceId());
        resourceUnit.setIsDeleted(resourceUnitDO.getIsDeleted());
        resourceUnit.setCreateTime(resourceUnitDO.getCreateTime());
        resourceUnit.setUpdateTime(resourceUnitDO.getUpdateTime());
        resourceUnit.setOperatorId(resourceUnitDO.getOperatorId());
        resourceUnit.setActiveStatus(resourceUnitDO.getActiveStatus());
        resourceUnit.setResourceId(resourceUnitDO.getResourceId());
        resourceUnit.setPlatformFrom(resourceUnitDO.getPlatformFrom());
        resourceUnit.setWeight(resourceUnitDO.getWeight());
        resourceUnit.setStartTime(resourceUnitDO.getStartTime());
        resourceUnit.setEndTime(resourceUnitDO.getEndTime());
//        resourceUnit.setVisibleRoleS(resourceUnitDO.getVisibleRoleS());
        try {
            resourceUnit.setUnitStrategy(objectMapper.readValue(resourceUnitDO.getUnitStrategy(), UnitStrategy.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        resourceUnit.setContentTitle(resourceUnitDO.getContentTitle());
        resourceUnit.setComponentJsonData(resourceUnitDO.getComponentJsonData());
        resourceUnit.setComponentName(resourceUnitDO.getComponentName());
        return resourceUnit;
    }

    public static ResourceUnitDO entityToDO(ResourceUnit resourceUnit) {
        if ( resourceUnit == null ) {
            return null;
        }

        ResourceUnitDO.ResourceUnitDOBuilder resourceUnitDO = ResourceUnitDO.builder();

        resourceUnitDO.id( resourceUnit.getId() );
        resourceUnitDO.isDeleted( resourceUnit.getIsDeleted() );
        resourceUnitDO.createTime(LocalDateTime.now());
        resourceUnitDO.updateTime( LocalDateTime.now() );
        resourceUnitDO.operatorId( resourceUnit.getOperatorId() );
        resourceUnitDO.operatorUser( resourceUnit.getOperatorUser() );
        resourceUnitDO.activeStatus( resourceUnit.getActiveStatus() );
        resourceUnitDO.resourceId( resourceUnit.getResourceId() );
        resourceUnitDO.platformFrom( resourceUnit.getPlatformFrom() );
        resourceUnitDO.weight( resourceUnit.getWeight() );
        resourceUnitDO.startTime( resourceUnit.getStartTime() );
        resourceUnitDO.endTime( resourceUnit.getEndTime() );

        try {
            if (resourceUnit.getUnitStrategy() != null) {
                resourceUnitDO.unitStrategy( objectMapper.writeValueAsString(resourceUnit.getUnitStrategy()) );
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        resourceUnitDO.contentTitle( resourceUnit.getContentTitle() );
        resourceUnitDO.componentJsonData( resourceUnit.getComponentJsonData() );
        resourceUnitDO.createUser(resourceUnit.getCreateUser());
        resourceUnitDO.createUserId(resourceUnit.getCreateUserId());
        resourceUnitDO.componentName(resourceUnit.getComponentName());

        return resourceUnitDO.build();
    }

    public static ResourceUnit doToEntity(ResourceUnitDO resourceUnitDO) {
        if ( resourceUnitDO == null ) {
            return null;
        }

        ResourceUnit.ResourceUnitBuilder resourceUnit = ResourceUnit.builder();

        resourceUnit.id( resourceUnitDO.getId() );
        resourceUnit.isDeleted( resourceUnitDO.getIsDeleted() );
        resourceUnit.createTime( resourceUnitDO.getCreateTime() );
        resourceUnit.updateTime( resourceUnitDO.getUpdateTime() );
        resourceUnit.operatorId( resourceUnitDO.getOperatorId() );
        resourceUnit.operatorUser( resourceUnitDO.getOperatorUser() );
        resourceUnit.activeStatus( resourceUnitDO.getActiveStatus() );
        resourceUnit.resourceId( resourceUnitDO.getResourceId() );
        resourceUnit.platformFrom( resourceUnitDO.getPlatformFrom() );
        resourceUnit.weight( resourceUnitDO.getWeight() );
        resourceUnit.startTime( resourceUnitDO.getStartTime() );
        resourceUnit.endTime( resourceUnitDO.getEndTime() );
//        resourceUnit.visibleRoleS( resourceUnitDO.getVisibleRoleS() );

        try {
            resourceUnit.unitStrategy(objectMapper.readValue(resourceUnitDO.getUnitStrategy(), UnitStrategy.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        resourceUnit.contentTitle( resourceUnitDO.getContentTitle() );
        resourceUnit.componentJsonData( resourceUnitDO.getComponentJsonData() );
        resourceUnit.createUser(resourceUnitDO.getCreateUser());
        resourceUnit.createUserId(resourceUnitDO.getCreateUserId());

        return resourceUnit.build();
    }

    public static List<ResourceUnit> doToEntity(List<ResourceUnitDO> resourceUnitDOList) {
        if ( resourceUnitDOList == null ) {
            return null;
        }

        List<ResourceUnit> list = new ArrayList<ResourceUnit>( resourceUnitDOList.size() );
        for ( ResourceUnitDO resourceUnitDO : resourceUnitDOList ) {
            list.add( doToEntity( resourceUnitDO ) );
        }

        return list;
    }

    public static ResourceUnitResp dtoToDto(ResourceUnitRespDTO dto) {
        ResourceUnitResp resp = new ResourceUnitResp();
        BeanUtils.copyProperties(dto, resp);
        resp.setResourceId(dto.getResourceId().longValue());
        try {
            resp.setPlatformFrom(Arrays.asList(objectMapper.readValue(dto.getPlatformFrom(), Integer[].class)));
            resp.setVisibleRoleS(dto.getVisibleRoleS());
        } catch (IOException e) {
            throw new RuntimeException(format("解析字段失败, PlatformFrom : [%s], VisibleRoleS : [%s]"
                    , dto.getPlatformFrom(), dto.getVisibleRoleS()));
        }
        return resp;
    }

    public static List<ResourceUnitResp> dtoToDto(List<ResourceUnitRespDTO> respDTOS) {
        return respDTOS.stream().map(ResourceUnitTransfer::dtoToDto).collect(toList());
    }
}
