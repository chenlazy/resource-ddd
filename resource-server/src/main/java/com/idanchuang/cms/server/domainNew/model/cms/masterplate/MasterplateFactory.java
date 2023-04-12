package com.idanchuang.cms.server.domainNew.model.cms.masterplate;

import com.alibaba.fastjson.JSONObject;
import com.idanchuang.cms.server.application.config.SystemConfig;
import com.idanchuang.cms.server.application.enums.ErrorEnum;
import com.idanchuang.cms.server.application.enums.GoodsEnableEnum;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.Catalogue;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueType;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPageId;
import com.idanchuang.cms.server.domainNew.shard.CreateId;
import com.idanchuang.component.base.exception.core.ExDefinition;
import com.idanchuang.component.base.exception.core.ExType;
import com.idanchuang.component.base.exception.exception.BusinessException;
import com.idanchuang.resource.server.infrastructure.utils.DateTimeUtil;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-17 17:35
 * @Desc: 模版工厂类
 * @Copyright VTN Limited. All rights reserved.
 */
public class MasterplateFactory {

    private MasterplateFactory() {

    }

    public static Masterplate createMasterplate(Catalogue catalogue, MasterplateUpsertForm upsertForm,  Map<String, Object> pageConfig) {

        if (StringUtils.isEmpty(upsertForm.getAppTitle())) {
            throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.PAGE_TITLE_EMPTY_ERROR.getCode(),
                    ErrorEnum.PAGE_TITLE_EMPTY_ERROR.getMsg()));
        }

        if (StringUtils.isEmpty(upsertForm.getMasterplateName())) {
            throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.MASTERPLATE_NAME_NOT_EMPTY.getCode(),
                    ErrorEnum.MASTERPLATE_NAME_NOT_EMPTY.getMsg()));
        }

        //分享信息
        List<MasterplateShareForm> shareInfoList = upsertForm.getShareInfoList();

        ShareFlag shareFlag = null == upsertForm.getShareFlag() ? ShareFlag.fromVal(0) : upsertForm.getShareFlag();

        //解析时间
        if (StringUtils.isEmpty(upsertForm.getStartTime())) {
            throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.START_TIME_EMPTY_ERROR.getCode(),
                    ErrorEnum.START_TIME_EMPTY_ERROR.getMsg()));
        }

        LocalDateTime startTime = upsertForm.getStartTime().contains("Z") ? DateTimeUtil.formatLocalDateTime(upsertForm.getStartTime()) :
                DateTimeUtil.string2LocalDateTime(upsertForm.getStartTime(), DateTimeUtil.YYYY_MM_DD_T_HH_MM_SS);

        LocalDateTime endTime = null;
        if (!StringUtils.isEmpty(upsertForm.getEndTime())) {
            endTime = upsertForm.getEndTime().contains("Z") ? DateTimeUtil.formatLocalDateTime(upsertForm.getEndTime()) :
                    DateTimeUtil.string2LocalDateTime(upsertForm.getEndTime(), DateTimeUtil.YYYY_MM_DD_T_HH_MM_SS);
        }

        //定义自动下架
        ClientPageId pageId = catalogue.getPageId();
        Integer goodsEnable = GoodsEnableEnum.GOODS_DISABLE.getEnable();
        if (SystemConfig.getInstance().getSubjectTagId().equals(pageId.getValue())) {
            if (null != startTime && null != endTime && endTime.isAfter(LocalDateTime.now())) {
                goodsEnable = GoodsEnableEnum.GOODS_ENABLE.getEnable();
            }
        }
        MasterplateExtra extra = new MasterplateExtra(GoodsEnable.fromVal(goodsEnable), upsertForm.getSelectInfoList());

        return new Masterplate(upsertForm.getMasterplateId(), catalogue.getId(), upsertForm.getAppTitle(), null, null,
                upsertForm.getMasterplateName(), CatalogueType.SUBJECT, catalogue.getPageId(),
                MasterplateStatus.VALID, shareFlag, 0, shareInfoList, startTime, endTime,
                JSONObject.toJSONString(pageConfig), extra, upsertForm.getOperatorId(), LocalDateTime.now(), 0L,new CreateId(upsertForm.getOperatorId().getValue()));
    }
}
