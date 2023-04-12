package com.idanchuang.cms.server.infrastructureNew.repository.convertor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.idanchuang.cms.server.domain.model.cms.ActivityPage;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueId;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueType;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPageId;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.*;
import com.idanchuang.cms.server.domainNew.shard.CreateId;
import com.idanchuang.cms.server.domainNew.shard.OperatorId;
import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.MasterplateDO;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import org.assertj.core.util.Lists;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

public class RepositoryMasterplateConvert {

    private RepositoryMasterplateConvert() {


    }


    public static Masterplate doToDomain(MasterplateDO masterplateDO) {

        if (masterplateDO == null) {
            return null;
        }


        LocalDateTime startTime = masterplateDO.getStartTime();
        LocalDateTime endTime = masterplateDO.getEndTime();


        MasterplateStatus status = MasterplateStatus.INVALID;
        //转化模版状态  //TODO  历史原因  后期调整为状态机 先不改逻辑
        if (null != startTime && null != endTime) {

            if (LocalDateTime.now().isBefore(startTime)) {
                status = MasterplateStatus.INVALID;
            }

            if (LocalDateTime.now().isAfter(startTime) && LocalDateTime.now().isBefore(endTime)) {
                status = MasterplateStatus.VALID;
            }

            if (LocalDateTime.now().isAfter(endTime)) {
                status = MasterplateStatus.OVERDUE;
            }
        }

        if (null == endTime && null == startTime) {
            status = MasterplateStatus.VALID;
        }

        if (null != startTime && null == endTime) {
            status = startTime.isAfter(LocalDateTime.now()) ? MasterplateStatus.INVALID :
                    MasterplateStatus.VALID;
        }

        if (null == startTime && null != endTime) {
            status = endTime.isBefore(LocalDateTime.now()) ? MasterplateStatus.OVERDUE :
                    MasterplateStatus.VALID;
        }


        //分享信息
        List<MasterplateShareForm> cmsCoreShares = Lists.newArrayList();
        if (!StringUtils.isEmpty(masterplateDO.getShareJson())) {
            cmsCoreShares = JsonUtil.toList(masterplateDO.getShareJson(), new TypeReference<List<MasterplateShareForm>>() {
            });

        }

        //扩展信息
        MasterplateExtra masterplateExtra = new MasterplateExtra();
        masterplateExtra.setGoodsEnable(GoodsEnable.fromVal(masterplateDO.getGoodsEnable()));


        return new Masterplate(
                new MasterplateId(masterplateDO.getId()),
                masterplateDO.getPageSchemaId() != 0 ? new CatalogueId(masterplateDO.getPageSchemaId()) : null,
                masterplateDO.getPageName(),
                null, null,
                masterplateDO.getBackEndTitle(),
                CatalogueType.fromVal(masterplateDO.getPageType()),
                masterplateDO.getTagId() != 0 ? new ClientPageId(masterplateDO.getTagId()) : null,
                status,
                ShareFlag.fromVal(masterplateDO.getShareFlag()),
                masterplateDO.getVersion(),
                cmsCoreShares,
                masterplateDO.getStartTime(),
                masterplateDO.getEndTime(),
                masterplateDO.getPageStyle(),
                masterplateExtra,
                new OperatorId(masterplateDO.getOperatorId()),
                masterplateDO.getUpdateTime(),
                0L,
                new CreateId(masterplateDO.getCreateId())
        );
    }


    public static MasterplateDO domainToDo(Masterplate masterplate) {
        MasterplateDO masterplateDO = new MasterplateDO();
        masterplateDO.setId(masterplate.getId() != null ? masterplate.getId().getValue() : 0);
        masterplateDO.setPageSchemaId(masterplate.getCatalogueId() != null ? masterplate.getCatalogueId().getValue() : 0);
        masterplateDO.setPageName(masterplate.getAppTitle());
        masterplateDO.setPageType(masterplate.getCatalogueType().getVal());
        masterplateDO.setBackEndTitle(masterplate.getMasterplateName());
        masterplateDO.setTagId(masterplate.getPageId() != null ? masterplate.getPageId().getValue() : 0);
        masterplateDO.setVersion(masterplate.getVersion());
        masterplateDO.setStatus(masterplate.getStatus().getVal());
        masterplateDO.setShareFlag(masterplate.getShareFlag().getVal());
        masterplateDO.setShareJson(!CollectionUtils.isEmpty(masterplate.getShareForms()) ? JsonUtil.toJsonString(masterplate.getShareForms()) : "");
        masterplateDO.setStartTime(masterplate.getStartTime());
        masterplateDO.setPageStyle(masterplate.getPageStyle());
        masterplateDO.setEndTime(masterplate.getEndTime());
        masterplateDO.setGoodsEnable(masterplate.getExtra() != null ? masterplate.getExtra().getGoodsEnable().getVal() : 0);
        masterplateDO.setOperatorId(masterplate.getOperatorId().getValue());
        masterplateDO.setSnapRoot(masterplate.getSnapRoot());
        masterplateDO.setCreateId(masterplate.getCreateId().getValue());
        masterplateDO.setExtra(JsonUtil.toJsonString(masterplate.getExtra()));
        return masterplateDO;
    }

    public static ActivityPage doToActivityPage(MasterplateDO masterplateDO) {
        if (masterplateDO == null) {
            return null;
        }
        ActivityPage activityPage = new ActivityPage();
        activityPage.setPageId(masterplateDO.getPageSchemaId());
        activityPage.setStartTime(masterplateDO.getStartTime());
        activityPage.setEndTime(masterplateDO.getEndTime());
        return activityPage;
    }


}
