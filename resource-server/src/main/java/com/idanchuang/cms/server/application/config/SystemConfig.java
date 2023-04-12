package com.idanchuang.cms.server.application.config;

import com.alibaba.fastjson.JSON;
import com.idanchuang.cms.server.domain.model.cms.PageDegrade;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
@Data
public class SystemConfig {

    private static SystemConfig instance;

    public static SystemConfig getInstance() {
        return instance;
    }

    @PostConstruct
    public void init() {
        SystemConfig.instance = this;
    }

    @Value("${subject_tag_id:16}")
    private Long subjectTagId;

    @Value("${subject_preference_tag_id:14}")
    private Long subjectPreferenceTagId;

    @Value("${subject_new_year_tag_id:21}")
    private Long subjectNewYearTagId;

    @Value("${celebration_new_year_tag_id:25}")
    private Long celebrationNewYearTagId;

    @Value("${goddess_tag_id:33}")
    private Long goddessTagId;

    @Value("${activity_subject_spuId_expire_time:60}")
    private Integer queryActivityOnSubjectListBySpuIdExpireTime;

    @Value("${activity_subject_activityId_expire_time:300}")
    private Integer queryActivityOnSubjectListByActivityIdExpireTime;

    /**
     *  页面版本降级是否开启
     *  0 不开启；1 开启
     */
    @Value("${page.version.degrade.enabled:0}")
    private int pageVersionDegradeEnabled;

    /**
     * 页面版本降级配置
     */
    @Value("${page.version.degrade.config:}")
    private String pageVersionDegradeConfig;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${switch.check.goods.status:false}")
    private Boolean checkGoodsStatus;

    @Value("${hotarea_max_display_line:50}")
    private Integer hotAreaMaxDisplayLine;

    /**
     * wap-tools域名更换uri检查规则
     */
    @Value("${wap-tools.check.regex:}")
    private String wapToolsCheckRegex;

    @Value("${env.wap.tools.domain:https://wap-tools.danchuanggolbal.com/}")
    private String wapToolsDomain;

    @Value("${env.wap.domain:https://wap.idanchuang.com}")
    private String envDomain;

    @Value("${bulletin.downgrade.enable:false}")
    public Boolean bulletinDowngradeEnable;

    @Value("${subject.tag.taskType:13}")
    private Long subjectTagTaskType;

    @Value("${core.page.scope:}")
    private String corePageIdScope;

    @Value("${web.hook.address:''}")
    private String webHookAddress;

    @Value("${comp.address.info:''}")
    private String compAddressInfo;

    //页面降级配置
    public boolean checkDegrade(String device,String appVersion) {
        int enabled = SystemConfig.getInstance().getPageVersionDegradeEnabled();
        if (enabled == 0) {
            return false;
        }

        String config = SystemConfig.getInstance().getPageVersionDegradeConfig();
        if (StringUtils.isBlank(config)) {
            return false;
        }

        List<PageDegrade> degradeList = JSON.parseArray(config, PageDegrade.class);
        if (CollectionUtils.isEmpty(degradeList)) {
            return false;
        }

        if (StringUtils.isBlank(appVersion) || StringUtils.isBlank(device)) {
            return false;
        }

        for (PageDegrade degrade : degradeList) {
            if (degrade.getEnabled() == 1
                    && device.equals(degrade.getPlatform())
                    && appVersion.equals(degrade.getAppVersion())) {
                return true;
            }
        }
        return false;
    }



}
