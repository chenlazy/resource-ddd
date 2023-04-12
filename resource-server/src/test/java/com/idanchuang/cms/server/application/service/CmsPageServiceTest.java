package com.idanchuang.cms.server.application.service;

import com.idanchuang.cms.server.domain.model.cms.ActivityPage;
import com.idanchuang.resource.server.SpringTest;
import org.assertj.core.util.Sets;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author fym
 * @description :
 * @date 2021/9/22 上午11:22
 */
public class CmsPageServiceTest extends SpringTest {

    @Resource
    private CmsPageService cmsPageService;

    @Test
    public void queryActivityOnSubjectListBySpuId() {
        Set<Long> spuIds = Sets.newHashSet();
        spuIds.add(2000L);
        Map<Long, List<ActivityPage>> longListMap = cmsPageService.queryActivityOnSubjectListBySpuId(spuIds);
        System.out.println(longListMap);
    }
}