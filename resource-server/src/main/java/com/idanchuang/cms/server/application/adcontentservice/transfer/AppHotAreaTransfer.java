package com.idanchuang.cms.server.application.adcontentservice.transfer;

import com.idanchuang.cms.server.interfaces.adcontentservice.dto.HotAreaDTO;
import com.idanchuang.cms.api.adcontentservice.response.HotAreaAdminDTO;

/**
 * @author: hulk.Wang
 **/
public class AppHotAreaTransfer {

    public static HotAreaDTO toDTO(HotAreaAdminDTO hotAreaDO) {
        if (hotAreaDO == null) {
            return null;
        }
        HotAreaDTO hotAreaDTO = new HotAreaDTO ();
        hotAreaDTO.setId (hotAreaDO.getId ());
        hotAreaDTO.setTitle (hotAreaDO.getTitle ());
        hotAreaDTO.setImage (hotAreaDO.getImage ());
        hotAreaDTO.setJumpType (hotAreaDO.getJumpType ());
        hotAreaDTO.setJumpWx(hotAreaDO.getJumpWx());
        hotAreaDTO.setJumpUrl(hotAreaDO.getJumpUrl());
        hotAreaDTO.setGoodsId (hotAreaDO.getGoodsId ());
        hotAreaDTO.setDisplayPosition (hotAreaDO.getDisplayPosition ());
        hotAreaDTO.setDisplayLine (hotAreaDO.getDisplayLine ());
        hotAreaDTO.setLinePosition (hotAreaDO.getLinePosition ());
        hotAreaDTO.setHotType (hotAreaDO.getHotType ());
        hotAreaDTO.setDestLevel (hotAreaDO.getDestLevel ());
        hotAreaDTO.setStartAt (hotAreaDO.getStartAt ());
        hotAreaDTO.setEndAt (hotAreaDO.getEndAt ());
        hotAreaDTO.setDisplayOrder (hotAreaDO.getDisplayOrder ());
        hotAreaDTO.setFontSize (hotAreaDO.getFontSize ());
        hotAreaDTO.setFontColor (hotAreaDO.getFontColor ());
        hotAreaDTO.setShareTitle (hotAreaDO.getShareTitle ());
        hotAreaDTO.setShareImage (hotAreaDO.getShareImage ());
        hotAreaDTO.setShareUrl (hotAreaDO.getShareUrl ());
        hotAreaDTO.setShareDesc (hotAreaDO.getShareDesc ());
        return hotAreaDTO;
    }
}
