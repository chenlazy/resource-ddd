package com.idanchuang.cms.server.infrastructureNew.schedule.model;

import lombok.Data;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-03-09 18:29
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class FSMessageSend {

    /**
     * 配置页面信息
     */
    private String pageIdInfo;

    private String pageName;

    private String templateIdInfo;

    private String noticeInfo;

    private List<PublishInfo> publishInfos;

    private String templateName;

    private Integer activityId;

    @Data
    public static class PublishInfo {

        private String openId;

        private String publishName;
    }
}
