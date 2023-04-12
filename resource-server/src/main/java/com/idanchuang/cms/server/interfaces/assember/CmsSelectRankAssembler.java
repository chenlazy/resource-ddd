package com.idanchuang.cms.server.interfaces.assember;

import com.idanchuang.cms.api.response.CmsSelectRankDTO;
import com.idanchuang.cms.server.domainNew.model.cms.external.selectInfo.CmsSelectRank;

/**
 * @author fym
 * @description :
 * @date 2022/2/14 下午2:01
 */
public class CmsSelectRankAssembler {

    public static CmsSelectRankDTO modelToDTO(CmsSelectRank cmsSelectRank) {
        if (cmsSelectRank == null) {
            return null;
        }
        CmsSelectRankDTO cmsSelectRankDTO = new CmsSelectRankDTO();
        cmsSelectRankDTO.setPageSchemaId(cmsSelectRank.getPageSchemaId().intValue());
        cmsSelectRankDTO.setPageName(cmsSelectRank.getPageName());
        cmsSelectRankDTO.setAliasTitle(cmsSelectRank.getAliasTitle());
        cmsSelectRankDTO.setPageId(cmsSelectRank.getPageId().intValue());
        cmsSelectRankDTO.setCreateId(cmsSelectRank.getCreateId().intValue());
        cmsSelectRankDTO.setOperatorId(cmsSelectRank.getOperatorId().intValue());
        cmsSelectRankDTO.setCreateName(cmsSelectRank.getCreateName());
        cmsSelectRankDTO.setOperatorName(cmsSelectRank.getOperatorName());
        return cmsSelectRankDTO;
    }
}
