package com.idanchuang.cms.server.application.adcontentservice.transfer;

import com.idanchuang.trade.member.api.entity.dto.UserAddressDTO;

/**
 * @author fym
 * @description :
 * @date 2021/4/29 上午11:07
 */
public class UserAddressTransfer {

    public static com.idanchuang.trade.goods.hulk.api.entity.dto.UserAddressDTO memberDTOToDTO(UserAddressDTO userAddressDTO) {
        com.idanchuang.trade.goods.hulk.api.entity.dto.UserAddressDTO a = new com.idanchuang.trade.goods.hulk.api.entity.dto.UserAddressDTO();
        a.setId(userAddressDTO.getId());
        a.setCity(userAddressDTO.getCity().getId());
        a.setCountry(userAddressDTO.getCountry().getId());
        a.setDistrict(userAddressDTO.getDistrict().getId());
        a.setProvince(userAddressDTO.getProvince().getId());
        return a;
    }
}
