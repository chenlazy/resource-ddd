package com.idanchuang.cms.server.infrastructure.external.feign;

import com.idanchuang.cms.server.domain.model.cms.niche.NicheId;
import com.idanchuang.cms.server.domain.model.cms.niche.NicheService;
import com.idanchuang.cms.server.domain.shard.IdObject;
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
public class NicheExternalService implements NicheService {

    @Resource
    private NicheFeignClient nicheFeignClient;


    @Override
    public void visible(List<NicheId> nicheIdList) {



        JsonResult<Void> jsonResult = null;
        try {

            if(CollectionUtils.isEmpty(nicheIdList)){
                return;
            }

            NicheVisibleDTO nicheVisibleDTO = new NicheVisibleDTO();
            nicheVisibleDTO.setNicheIds(nicheIdList.stream().map(IdObject::getValue).collect(Collectors.toList()));

            jsonResult =  nicheFeignClient.visible(nicheVisibleDTO);

            if(jsonResult != null && jsonResult.checkSuccess()){
                return;
            }

        } catch (Exception e) {

            log.error("资源位启用异常",e);
        }

        log.error("资源位启用失败nicheIdList{},jsonResult{}",nicheIdList, JsonUtil.toJsonString(jsonResult));
        throw new InfraException("资源位启用失败");
    }
}
