package com.idanchuang.cms.server.interfaces.assember;

import com.idanchuang.trade.member.api.entity.dto.UserAddressDTO;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-12 17:40
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public class UserAddressAssembler {

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
