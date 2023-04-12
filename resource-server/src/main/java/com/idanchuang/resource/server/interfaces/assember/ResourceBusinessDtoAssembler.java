package com.idanchuang.resource.server.interfaces.assember;

import com.idanchuang.resource.api.response.ResourceBusinessRespDTO;
import com.idanchuang.resource.api.response.ResourcePageDTO;
import com.idanchuang.resource.server.domain.model.resource.ResourceBusiness;
import com.idanchuang.resource.server.domain.model.resource.ResourcePage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wengbinbin
 * @date 2021/3/24
 */
public class ResourceBusinessDtoAssembler {
    public static ResourceBusinessRespDTO entityToDTO(ResourceBusiness resourceBusiness, List<ResourcePage> pages) {
        if ( resourceBusiness == null && pages == null ) {
            return null;
        }

        ResourceBusinessRespDTO resourceBusinessRespDTO = new ResourceBusinessRespDTO();

        if ( resourceBusiness != null ) {
            resourceBusinessRespDTO.setId( resourceBusiness.getId() );
            resourceBusinessRespDTO.setBusinessName( resourceBusiness.getBusinessName() );
        }
        if ( pages != null ) {
            resourceBusinessRespDTO.setPages( resourcePageListToResourcePageDTOList( pages ) );
        }

        return resourceBusinessRespDTO;
    }

    public static ResourcePageDTO resourcePageToResourcePageDTO(ResourcePage resourcePage) {
        if ( resourcePage == null ) {
            return null;
        }

        ResourcePageDTO resourcePageDTO = new ResourcePageDTO();

        resourcePageDTO.setId( resourcePage.getId() );
        resourcePageDTO.setPageName( resourcePage.getPageName() );
        resourcePageDTO.setPageCode( resourcePage.getPageCode() );

        return resourcePageDTO;
    }

    public static List<ResourcePageDTO> resourcePageListToResourcePageDTOList(List<ResourcePage> list) {
        if ( list == null ) {
            return null;
        }

        List<ResourcePageDTO> list1 = new ArrayList<ResourcePageDTO>( list.size() );
        for ( ResourcePage resourcePage : list ) {
            list1.add( resourcePageToResourcePageDTO( resourcePage ) );
        }

        return list1;
    }

}
