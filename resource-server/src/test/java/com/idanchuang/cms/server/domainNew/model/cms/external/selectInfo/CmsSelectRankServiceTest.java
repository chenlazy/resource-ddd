package com.idanchuang.cms.server.domainNew.model.cms.external.selectInfo;

import com.idanchuang.resource.server.SpringTest;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author fym
 * @description :
 * @date 2022/2/14 下午3:12
 */
public class CmsSelectRankServiceTest extends SpringTest {

    @Resource
    private CmsSelectRankService cmsSelectRankService;
    @Test
    public void queryCmsSelectRankBySelectId() {
        List<CmsSelectRank> cmsSelectRanks = cmsSelectRankService.queryCmsSelectRankBySelectId(1, 2);
        System.out.println(cmsSelectRanks);
    }
}