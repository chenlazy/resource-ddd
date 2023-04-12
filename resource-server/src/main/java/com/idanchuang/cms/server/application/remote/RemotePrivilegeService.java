package com.idanchuang.cms.server.application.remote;

import com.alibaba.fastjson.JSONObject;
import com.idanchuang.cms.server.infrastructure.shard.InfraException;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.member.point.api.entity.dto.PrivilegeDTO;
import com.idanchuang.member.point.api.entity.query.PrivilegeSyncTagIdRequest;
import com.idanchuang.member.point.api.enums.PrivilegeAcquireTypeEnum;
import com.idanchuang.member.point.api.enums.PrivilegeStatusEnum;
import com.idanchuang.member.point.api.service.PrivilegeFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author fym
 * @description :
 * @date 2022/4/13 下午3:56
 */
@Slf4j
@Service
public class RemotePrivilegeService {

    @Resource
    private PrivilegeFeign privilegeFeign;

    public void syncTagIds(List<PrivilegeSyncTagIdRequest> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        JsonResult<Boolean> booleanJsonResult;
        try {
            booleanJsonResult = privilegeFeign.syncTagIds(list);
        } catch (Exception e) {
            log.error("标签权益同步异常 list:{} e:{}", JSONObject.toJSONString(list), e);
            throw new InfraException("标签权益同步异常");
        }
        if (booleanJsonResult == null || !booleanJsonResult.isSuccess() || !booleanJsonResult.getData()) {
            log.error("标签权益同步失败 booleanJsonResult:{} list:{}", JSONObject.toJSONString(booleanJsonResult), JSONObject.toJSONString(list));
            throw new InfraException("标签权益同步失败");
        }
    }

    public void getEquityDetail(Long equityId) {

        JsonResult<PrivilegeDTO> result;
        try {
            result = privilegeFeign.getDetail(equityId, true);
        } catch (Exception e) {
            log.error("getEquityDetail error equityId:{} e:{}", equityId, e);
            throw new InfraException("权益服务查询异常,id:" + equityId);
        }

        if (null == result || !result.isSuccess()) {
            log.error("getEquityDetail error result:{} id:{}", result, equityId);
            throw new InfraException("权益组件配置权益id查询失败,id:" + equityId);
        }

        PrivilegeDTO data = result.getData();

        if (null == data) {
            log.error("getEquityDetail data error result:{} id:{}", result, equityId);
            throw new InfraException("权益组件配置权益id不存在，请检查权益id,id:" + equityId);
        }

        if (!PrivilegeStatusEnum.OPEN.getCode().equals(data.getStatus())) {
            log.error("getEquityDetail data close error result:{} id:{}", result, equityId);
            throw new InfraException("权益组件配置权益id未开启，请检查权益id,id:" + equityId);
        }

        if (!PrivilegeAcquireTypeEnum.AUTO_EXCHANGE.getCode().equals(data.getAcquireType())) {
            log.error("getEquityDetail data error result:{} id:{}", result, equityId);
            throw new InfraException("权益组件配置权益id不是自动获取，请检查权益id,id:" + equityId);
        }
    }
}
