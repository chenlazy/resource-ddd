package com.idanchuang.cms.server.application.remote;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.idanchuang.cms.api.common.enums.ErrorEnum;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.dealer.grade.feign.client.AbmGradeQueryFeignClient;
import com.idanchuang.dealer.grade.feign.dto.IdCodeQueryDTO;
import com.idanchuang.resource.server.infrastructure.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-01-14 10:40
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Service
@Slf4j
public class RemoteUserRoleService {

    @Resource
    private AbmGradeQueryFeignClient abmGradeQueryFeignClient;

    @SentinelResource(value = "getAbmLevelByIdCode", entryType = EntryType.IN, fallback = "getAbmLevelByIdCodeFallback")
    public Integer getAbmLevelByIdCode(Long idCode, Boolean useCache) {
        JsonResult<Integer> abmLevelByIdCode;
        try {
            IdCodeQueryDTO idCodeQueryDTO = new IdCodeQueryDTO();
            idCodeQueryDTO.setIdCode(idCode);
            idCodeQueryDTO.setUseCache(useCache);
            abmLevelByIdCode = abmGradeQueryFeignClient.getAbmLevelByIdCode(idCodeQueryDTO);
        } catch (Exception e) {
            log.error("RemoteGoodsCommissionService getAbmLevelByIdCode idCode:{} e:{}", idCode, e);
            throw new BusinessException(ErrorEnum.QUERY_USER_ROLE_ERROR);
        }
        if (abmLevelByIdCode != null && abmLevelByIdCode.checkSuccess()) {
            return abmLevelByIdCode.getData();
        } else {
            return null;
        }
    }

    public Integer getAbmLevelByIdCodeFallback(Long idCode, Boolean useCache, Throwable throwable) {
        log.error("获取用户角色服务出现熔断进行降级处理 idCode:{} useCache:{} throwable:{}", idCode, useCache, throwable);
        return null;
    }

}
