package com.idanchuang.cms.server.infrastructureNew.util;

import com.idanchuang.link.apmsdk.ApmInfo;
import com.idanchuang.link.apmsdk.ApmSdk;
import com.idanchuang.link.apmsdk.enums.ApmContentTypeEnum;
import com.idanchuang.link.apmsdk.enums.ApmDomainEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author fym
 * @description :
 * @date 2021/12/23 下午4:31
 */
@Slf4j
public class ApmUtils {

    /**
     * 创建apm
     *
     * @param resourceId      资源位，可以为空
     * @param nicheLineId           投放Id，可以为空
     * @param location        返回后端列表所在项的位置，如果是分页接口的话就是 = pageSize * page + index 为空默认1
     * @param domainEnum      业务域，必须传
     * @param contentTypeEnum 内容类型，必须传
     * @param contentId       内容Id，必须传
     * @param custom          自定义值，可为空
     * @return
     */
    public static String createApm(String resourceId, String nicheLineId, int location,
                                   ApmDomainEnum domainEnum, ApmContentTypeEnum contentTypeEnum,
                                   String contentId, Map<String, String> custom) {
        ApmInfo.ApmInfoBuilder builder = ApmInfo.builder();
        ApmInfo apmInfo = builder
                .resourceId(resourceId)
                .nicheLineId(nicheLineId)
                .contentId(contentId)
                .contentTypeEnum(contentTypeEnum)
                .custom(custom)
                .location(location)
                .domainEnum(domainEnum)
                .build();
        String apm = "";
        try {
            apm += ApmSdk.buildApmString(apmInfo);
        } catch (Exception e) {
            log.warn("构造APM信息异常 e:{}", e);
        }
        return apm;
    }
}
