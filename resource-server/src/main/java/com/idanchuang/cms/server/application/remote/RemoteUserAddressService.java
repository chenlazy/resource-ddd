package com.idanchuang.cms.server.application.remote;

import com.idanchuang.component.base.JsonResult;
import com.idanchuang.trade.member.api.entity.dto.UserAddressDTO;
import com.idanchuang.trade.member.api.entity.dto.UserAddressRegionDTO;
import com.idanchuang.trade.member.api.entity.query.UserAddressQuery;
import com.idanchuang.trade.member.api.service.UserAddressFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-12 17:24
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Service
@Slf4j
public class RemoteUserAddressService {

    @Resource
    private UserAddressFeignClient userAddressFeignClient;

    /**
     * 获取用户默认地址
     *
     * @param userId
     * @return
     */
    public UserAddressDTO getUserDefaultAddress(Long userId) {
        UserAddressDTO address = null;
        UserAddressQuery query = new UserAddressQuery();
        query.setType(1);
        query.setPage(1L);
        query.setSize(100L);
        query.setUserId(userId);
        JsonResult<List<UserAddressDTO>> result = userAddressFeignClient.getUserAddressList(query);
        if (result.isSuccess() && CollectionUtils.isNotEmpty(result.getData())) {
            int index = 0;
            int compareId = 0;
            for (int i = 0; i < result.getData().size(); i++) {
                UserAddressDTO dto = result.getData().get(i);
                // 用户默认地址
                if (dto.getDefaulted() != null && dto.getDefaulted().intValue() == 1) {
                    address = dto;
                    break;
                }

                if (dto.getId() != null && dto.getId().intValue() > compareId) {
                    compareId = dto.getId();
                    index = i;
                }
            }

            if (!validAddress(address)) {
                // 最新添加地址
                address = result.getData().get(index);
                if (validAddress(address)) {
                    return address;
                }
            } else {
                return address;
            }
        }
        // 小明地址
        return userAddressFeignClient.getDefaultAddress().getData();
    }

    /**
     * 直接获取小明地址
     *
     * @return
     */
    public UserAddressDTO getDefaultAddress() {
        return userAddressFeignClient.getDefaultAddress().getData();
    }

    private boolean validAddress(UserAddressDTO address) {
        if (address != null && address.getCountry() != null && address.getCountry().getId() != null
                && address.getProvince() != null && address.getProvince().getId() != null
                && address.getCity() != null && address.getCity().getId() != null) {

            if (address.getDistrict() == null || address.getDistrict().getId() == null) {
                UserAddressRegionDTO district = new UserAddressRegionDTO();
                district.setId(0);
                address.setDistrict(district);
            }
            return true;
        }
        return false;
    }
}
