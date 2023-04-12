package com.idanchuang.cms.server.interfaces.assember;

import com.idanchuang.cms.api.request.PageTagConditionReq;
import com.idanchuang.cms.api.response.PageTagDTO;
import com.idanchuang.cms.server.domain.shard.IdObject;
import com.idanchuang.cms.server.domain.shard.StringIdObject;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.*;
import com.idanchuang.cms.server.domainNew.shard.OperatorId;
import com.idanchuang.cms.server.domainNew.shard.PlatformCode;
import org.springframework.util.StringUtils;

import java.util.Optional;

public class InterfaceClientPageAssembler {
    private InterfaceClientPageAssembler() {
    }

    public static ClientPageForm toClientPageForm(PageTagDTO pageTag, Integer userId) {
        if (pageTag == null) {
            return null;
        }
        return ClientPageForm.builder()
                .id(pageTag.getId() != null ? new ClientPageId(pageTag.getId()) : null)
                .pageCode(StringUtils.isEmpty(pageTag.getPageCode()) ? null : new PageCode(pageTag.getPageCode()))
                .name(pageTag.getName())
                .platform(pageTag.getPlatform() != null ? PlatformCode.fromVal(pageTag.getPlatform()) : null)
                .operatorId(userId != null ? new OperatorId(userId) : null)
                .build();
    }

    public static ClientPageQueryForm toClientPageQueryForm(PageTagConditionReq condition) {
        return ClientPageQueryForm.builder()
                .pageNum(condition.getPageNum())
                .pageSize(condition.getPageSize())
                .platformCode(PlatformCode.fromVal(condition.getPlatform()))
                .build();
    }

    public static PageTagDTO toClientPageDTO(ClientPage source) {
        return PageTagDTO.builder()
                .id(Math.toIntExact(Optional.ofNullable(source.getId()).map(IdObject::getValue).orElse(0L)))
                .name(source.getName())
                .createTime(source.getCreateTime())
                .updateTime(source.getUpdateTime())
                .operatorId(Math.toIntExact(Optional.ofNullable(source.getOperatorId()).map(IdObject::getValue).orElse(0L)))
                .pageCode(Optional.ofNullable(source.getPageCode()).map(StringIdObject::getValue).orElse(""))
                .platform(Optional.ofNullable(source.getPlatform()).map(PlatformCode::getVal).orElse(0)).build();
    }
}
