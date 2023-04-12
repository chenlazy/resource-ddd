package com.idanchuang.cms.server.infrastructure.adcontentservice.hotArea.transfer;

import com.idanchuang.cms.server.infrastructure.adcontentservice.hotArea.entity.WHotArea;
import com.idanchuang.cms.server.infrastructure.adcontentservice.util.ReleasedEnum;
import com.idanchuang.cms.api.adcontentservice.response.HotAreaAdminDTO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * @author zhousun
 * @create 2020/11/26
 */
@Data
public class HotAreaTransfer {

    public static HotAreaAdminDTO transToDTO(WHotArea hotArea) {
        HotAreaAdminDTO hotAreaDTO = new HotAreaAdminDTO();
        BeanUtils.copyProperties(hotArea, hotAreaDTO);
        hotAreaDTO.setStatus(getStatus(hotArea));
        return hotAreaDTO;
    }

    private static Integer getStatus(WHotArea hotArea) {
        LocalDateTime startAt = hotArea.getStartAt();
        LocalDateTime endAt = hotArea.getEndAt();
        if(startAt.isBefore(LocalDateTime.now()) && endAt.isAfter(LocalDateTime.now())) {
            return ReleasedEnum.RELEASED.getStatus();
        }
        return ReleasedEnum.UN_RELEASE.getStatus();
    }

}
