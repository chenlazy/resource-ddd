package com.idanchuang.cms.server.domain.model.cms.factory;

import com.google.common.collect.Lists;
import com.idanchuang.cms.server.application.config.SystemConfig;
import com.idanchuang.cms.server.application.enums.GoodsEnableEnum;
import com.idanchuang.cms.server.application.enums.PageStatusEnum;
import com.idanchuang.cms.server.application.enums.PageTypeEnum;
import com.idanchuang.cms.server.domain.model.cms.CmsCoreCreateOrUpdate;
import com.idanchuang.cms.server.domain.model.cms.CmsCoreShare;
import com.idanchuang.cms.server.domain.model.cms.CmsPage;
import com.idanchuang.cms.server.domain.model.cms.CmsPageContainer;
import com.idanchuang.resource.server.infrastructure.utils.DateTimeUtil;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-11 17:23
 * @Desc: 页面工厂类
 * @Copyright VTN Limited. All rights reserved.
 */
public class PageFactory {

    private PageFactory() {

    }

    public static CmsPage createPage(CmsCoreCreateOrUpdate coreCreateOrUpdate, Integer operatorId,
                                      Integer pageSchemaId, List<Long> cmsPageContainerIds) {

        String pageName = !StringUtils.isEmpty(coreCreateOrUpdate.getPageTitle()) ? coreCreateOrUpdate.getPageTitle() :  "";

        Long tagId = null != coreCreateOrUpdate.getTagId() ? coreCreateOrUpdate.getTagId() : 0L;
        String platform = null != coreCreateOrUpdate.getPlatform() ? coreCreateOrUpdate.getPlatform() : "";
        String upperCase = platform.toUpperCase();

        //设置别名
        String aliasTitle = upperCase + "_" + pageSchemaId;

        //封装分享的json数据
        List<CmsCoreShare> shareInfoList = coreCreateOrUpdate.getShareInfoList();
        String shareJson = "";
        if (!CollectionUtils.isEmpty(shareInfoList)) {
            shareJson = JsonUtil.toJsonString(shareInfoList);
        }

        //解析时间
        String startTimeStr = coreCreateOrUpdate.getStartTime();
        String endTimeStr = coreCreateOrUpdate.getEndTime();

        LocalDateTime startTime = null;
        LocalDateTime endTime = null;

        if (!StringUtils.isEmpty(startTimeStr)) {
            startTime = startTimeStr.contains("Z") ?  DateTimeUtil.formatLocalDateTime(startTimeStr) :
                    DateTimeUtil.string2LocalDateTime(startTimeStr, DateTimeUtil.YYYY_MM_DD_T_HH_MM_SS);
        }

        if (!StringUtils.isEmpty(endTimeStr)) {
            endTime = endTimeStr.contains("Z") ? DateTimeUtil.formatLocalDateTime(endTimeStr) :
                    DateTimeUtil.string2LocalDateTime(endTimeStr, DateTimeUtil.YYYY_MM_DD_T_HH_MM_SS);
        }

        //标签定义自动下架
        Integer goodsEnable = GoodsEnableEnum.GOODS_DISABLE.getEnable();
        if (tagId.equals(SystemConfig.getInstance().getSubjectTagId())) {
            if (null != startTime && null != endTime && endTime.isAfter(LocalDateTime.now())) {
                goodsEnable = GoodsEnableEnum.GOODS_ENABLE.getEnable();
            }
        }

        String templateName = !StringUtils.isEmpty(coreCreateOrUpdate.getTemplateName()) ? coreCreateOrUpdate.getTemplateName() : "";
        Integer shareFlag = null != coreCreateOrUpdate.getShareFlag() ? coreCreateOrUpdate.getShareFlag() : 0;

        List<String> sort = CollectionUtils.isEmpty(cmsPageContainerIds) ? Lists.newArrayList() : cmsPageContainerIds.stream().map(String::valueOf).collect(Collectors.toList());

        return new CmsPage(coreCreateOrUpdate.getTemplateId(), pageSchemaId, pageName,
                PageTypeEnum.PAGE_TYPE_SITUATION.getType(), templateName, tagId, upperCase,
                PageStatusEnum.PAGE_STATUS_PUBLISH.getStatus(), sort, aliasTitle, shareFlag,
                shareJson, startTime, endTime, goodsEnable, operatorId, null, null, 0, null);
    }
}
