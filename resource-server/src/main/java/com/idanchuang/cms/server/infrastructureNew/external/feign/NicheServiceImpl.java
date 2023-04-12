package com.idanchuang.cms.server.infrastructureNew.external.feign;

import com.idanchuang.cms.server.domain.shard.IdObject;
import com.idanchuang.cms.server.domainNew.model.cms.external.niche.NicheId;
import com.idanchuang.cms.server.domainNew.model.cms.external.niche.NicheService;
import com.idanchuang.cms.server.infrastructure.shard.InfraException;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.niche.feign.client.NicheFeignClient;
import com.idanchuang.niche.feign.dto.NicheVisibleDTO;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NicheServiceImpl implements NicheService {

    @Resource
    private NicheFeignClient nicheFeignClient;


    @Override
    public void visible(List<NicheId> nicheIdList) {

        if(CollectionUtils.isEmpty(nicheIdList)){
            return;
        }
        JsonResult<Void> result = null;
        try {
            NicheVisibleDTO nicheVisibleDTO = new NicheVisibleDTO();
            nicheVisibleDTO.setNicheIds(nicheIdList.stream().map(IdObject::getValue).collect(Collectors.toList()));

            result =  nicheFeignClient.visible(nicheVisibleDTO);
            if(result != null && result.checkSuccess()){
                return;
            }

        } catch (Exception e) {
            log.error("资源位启用异常, e:{}", e.getMessage(), e);
        }

        log.error("资源位启用失败 nicheIdList{},jsonResult{}", nicheIdList, JsonUtil.toJsonString(result));
        throw new InfraException("资源位启用失败");
    }
}
