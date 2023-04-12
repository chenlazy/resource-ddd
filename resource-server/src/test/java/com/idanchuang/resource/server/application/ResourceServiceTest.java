package com.idanchuang.resource.server.application;

import com.google.common.collect.Lists;
import com.idanchuang.resource.server.SpringTest;
import com.idanchuang.resource.server.application.service.ResourceService;
import com.idanchuang.resource.server.domain.model.resource.ResourceConfig;
import com.idanchuang.resource.server.domain.model.resource.ResourcePutInfo;
import com.idanchuang.resource.server.infrastructure.utils.CacheUtil;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author fym
 * @description :
 * @date 2021/3/18 下午3:31
 */
public class ResourceServiceTest extends SpringTest {

    @Resource
    private ResourceService resourceService;
    @Resource
    private CacheUtil cacheUtil;

    @Test
    public void getContentPutInfoTest() {
        List<ResourcePutInfo> contentPutInfo = resourceService.getContentPutInfo(Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L), "abm_screen", 1, "1", 53);
        System.out.println(contentPutInfo.toString());
    }

    @Test
    public void getContentPutInfoByResourceIdTest() {
        ResourcePutInfo contentPutInfoByResourceId = resourceService.getContentPutInfoByResourceId(1L, "abm_screen", 1, "1", 53);
        System.out.println(contentPutInfoByResourceId.toString());
    }

    @Test
    public void getResourceListByPageIdTest() {
        List<ResourceConfig> resourceListByPageId = resourceService.getResourceListByPageId("abm_screen", 1);
        System.out.println(resourceListByPageId.toString());
    }

//    @Test
//    public void getPreviewPutContentTest() {
//        ResourcePutInfo previewPutContent = resourceService.getPreviewPutContent("abm_screen", 1, Long.valueOf(6), Long.valueOf(1), Long.valueOf(6), 1, "1");
//        System.out.println(previewPutContent.toString());
//    }
//
//    @Test
//    public void getPreviewPutContentListTest() {
//        List<ResourcePutInfo> previewPutContentList = resourceService.getPreviewPutContentList("abm_screen", 1, Lists.newArrayList(Long.valueOf(1), Long.valueOf(2), Long.valueOf(3)), Long.valueOf(1), Long.valueOf(1), 53, "1");
//        System.out.println(previewPutContentList.toString());
//    }

    @Test
    public void setRedisTest(){
//        String listKey = "content:ResourceUnit:keyList:resourceId_1";
//        List<String> strings = resourceService.setRedisTest(listKey,"1");
//        System.out.println(strings);
    }
}
