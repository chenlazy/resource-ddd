package com.idanchuang.cms.server.interfaces.controller;

import com.idanchuang.cms.server.applicationNew.service.FeiShuService;
import com.idanchuang.cms.server.domainNew.model.cms.external.selectInfo.CmsSelectRank;
import com.idanchuang.cms.server.domainNew.model.cms.external.selectInfo.CmsSelectRankService;
import com.idanchuang.cms.server.infrastructureNew.schedule.model.PageNotice;
import com.idanchuang.resource.server.SpringTest;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PageNoticeFacadeImplTest extends SpringTest {

    @MockBean
    private FeiShuService mockFeiShuService;
    @MockBean
    private CmsSelectRankService mockCmsSelectRankService;

    @Test
    public void testNotifyActivityChange() throws Exception {
        // Setup
        // Configure CmsSelectRankService.queryCmsSelectRankBySelectId(...).
        final CmsSelectRank cmsSelectRank = new CmsSelectRank();
        cmsSelectRank.setPageSchemaId(0L);
        cmsSelectRank.setPageName("pageName");
        cmsSelectRank.setAliasTitle("aliasTitle");
        cmsSelectRank.setPageId(0L);
        cmsSelectRank.setCreateId(0L);
        cmsSelectRank.setCreateName("createName");
        cmsSelectRank.setOperatorId(0L);
        cmsSelectRank.setOperatorName("operatorName");
        final List<CmsSelectRank> cmsSelectRanks = Arrays.asList(cmsSelectRank);
        when(mockCmsSelectRankService.queryCmsSelectRankBySelectId(0, 0)).thenReturn(cmsSelectRanks);
        verify(mockFeiShuService).noticeCard(new PageNotice(), "templateName");
    }

}
