package com.idanchuang.cms.server.interfaces.controller;

import com.idanchuang.cms.api.facade.PageNoticeFacade;
import com.idanchuang.cms.api.request.ActivityNoticeReq;
import com.idanchuang.cms.server.applicationNew.service.FeiShuService;
import com.idanchuang.cms.server.domainNew.model.cms.external.selectInfo.CmsSelectRank;
import com.idanchuang.cms.server.domainNew.model.cms.external.selectInfo.CmsSelectRankService;
import com.idanchuang.cms.server.infrastructureNew.schedule.model.PageNotice;
import com.idanchuang.component.base.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-04-15 10:36
 * @Desc: 页面通知具体实现类
 * @Copyright VTN Limited. All rights reserved.
 */
@RestController
@RequestMapping("/cms/page/notice")
@Slf4j
public class PageNoticeFacadeImpl implements PageNoticeFacade {

    @Resource
    private FeiShuService feiShuService;

    @Resource
    private CmsSelectRankService cmsSelectRankService;

    @Override
    public JsonResult<Boolean> notifyActivityChange(ActivityNoticeReq activityNoticeReq) {

        if (StringUtils.isEmpty(activityNoticeReq.getNoticeInfo())) {
            return JsonResult.success();
        }

        List<CmsSelectRank> cmsSelectRanks = cmsSelectRankService.queryCmsSelectRankBySelectId(activityNoticeReq.getActivityType(),
                activityNoticeReq.getActivityId());

        //循环通知信息
        cmsSelectRanks.forEach(cmsSelectRank -> {
            PageNotice pageNotice = new PageNotice();
            pageNotice.setActivityId(activityNoticeReq.getActivityId());
            pageNotice.setCreateId(cmsSelectRank.getCreateId());
            pageNotice.setOperatorId(cmsSelectRank.getOperatorId());
            pageNotice.setCatalogueId(cmsSelectRank.getPageSchemaId());
            pageNotice.setAliasTitle(cmsSelectRank.getAliasTitle());
            pageNotice.setMasterplateId(cmsSelectRank.getPageId());
            pageNotice.setPageName(cmsSelectRank.getPageName());
            pageNotice.setNoticeInfo(activityNoticeReq.getNoticeInfo());
            feiShuService.noticeCard(pageNotice, "ActivityTemplate.json");
        });

        return JsonResult.success(true);
    }
}
