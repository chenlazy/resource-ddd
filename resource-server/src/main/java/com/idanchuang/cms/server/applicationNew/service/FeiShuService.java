package com.idanchuang.cms.server.applicationNew.service;

import com.google.common.collect.Lists;
import com.idanchuang.cms.server.application.config.SystemConfig;
import com.idanchuang.cms.server.application.remote.RemoteMessageService;
import com.idanchuang.cms.server.application.remote.RemoteSsoService;
import com.idanchuang.cms.server.infrastructureNew.schedule.model.PageNotice;
import com.idanchuang.cms.server.infrastructureNew.schedule.model.FSMessageSend;
import com.idanchuang.cms.server.infrastructureNew.util.GetTemplateUtils;
import com.idanchuang.cms.server.infrastructureNew.util.HttpClientUtils;
import com.idanchuang.message.dingding.api.vo.UserVo;
import com.idanchuang.resource.server.infrastructure.utils.DateTimeUtil;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import com.idanchuang.sso.model.dto.system.UserDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-03-18 16:40
 * @Desc: 飞书通知服务
 * @Copyright VTN Limited. All rights reserved.
 */
@Slf4j
@Service
public class FeiShuService {


    @Resource
    private RemoteSsoService remoteSsoService;

    @Resource
    private RemoteMessageService remoteMessageService;


    /**
     * 飞书单条通知信息
     * @param pageNotice
     */
    public void noticeCard(PageNotice pageNotice, String templateName) {

        if (null == pageNotice) {
            return;
        }

        FSMessageSend fsMessageSend = new FSMessageSend();
        Long createId = pageNotice.getCreateId();
        Long operatorId = pageNotice.getOperatorId();

        List<FSMessageSend.PublishInfo> publishInfoList = getPublishInfoList(Lists.newArrayList(createId, operatorId));
        fsMessageSend.setPublishInfos(publishInfoList);
        fsMessageSend.setActivityId(pageNotice.getActivityId());
        fsMessageSend.setTemplateName(templateName);
        fsMessageSend.setPageIdInfo(!StringUtils.isEmpty(pageNotice.getAliasTitle()) ?
                    pageNotice.getAliasTitle() : pageNotice.getCatalogueId().toString());
        fsMessageSend.setPageName(pageNotice.getPageName());
        fsMessageSend.setTemplateIdInfo(pageNotice.getMasterplateId().toString());
        fsMessageSend.setNoticeInfo(pageNotice.getNoticeInfo());

        //获取通知的模版
        String messageTemplate = getFSMessageTemplate(fsMessageSend);
        //发送通知
        sendNotice(messageTemplate);
        log.info("corePageRemindJob, notice page, fsMessageSend:{}", JsonUtil.toJsonString(fsMessageSend));
    }

    /**
     * 飞书卡片通知
     * @param pageNotices
     */
    public void noticeCard(List<PageNotice> pageNotices, String templateName) {

        //如果通知列表不为空，调用飞书服务进行通知
        if (!CollectionUtils.isEmpty(pageNotices)) {
            FSMessageSend fsMessageSend = new FSMessageSend();
            List<String> aliasTitles = pageNotices.stream().map(PageNotice::getAliasTitle).collect(Collectors.toList());
            fsMessageSend.setPageIdInfo(String.join("、", aliasTitles));

            //获取具体操作人
            List<Long> operators = pageNotices.stream().map(PageNotice::getOperatorId).collect(Collectors.toList());

            //设置具体发布者信息
            List<FSMessageSend.PublishInfo> publishInfoList = getPublishInfoList(operators);
            fsMessageSend.setPublishInfos(publishInfoList);
            fsMessageSend.setTemplateName(templateName);

            //获取通知的模版
            String messageTemplate = getFSMessageTemplate(fsMessageSend);
            sendNotice(messageTemplate);
            log.info("corePageRemindJob, batch notice page, fsMessageSend:{}", JsonUtil.toJsonString(fsMessageSend));
        }
    }

    /**
     * 获取发布者信息
     * @param operators
     * @return
     */
    private List<FSMessageSend.PublishInfo> getPublishInfoList(List<Long> operators) {

        if (CollectionUtils.isEmpty(operators)) {
            return Lists.newArrayList();
        }
        List<FSMessageSend.PublishInfo> publishInfos = new ArrayList<>();

        List<UserDetailDTO> detailDTOS = remoteSsoService.getUsers(operators);
        if (!CollectionUtils.isEmpty(detailDTOS)) {
            Map<String, UserDetailDTO> userInfoMap = detailDTOS.stream().collect(Collectors.toMap(UserDetailDTO::getJobNumber, p -> p, (e1, e2) -> e1));

            List<String> jobNumbers = detailDTOS.stream().map(UserDetailDTO::getJobNumber).collect(Collectors.toList());
            jobNumbers.forEach(p -> {
                //根据工单号查询用户的openId
                UserVo userVo = remoteMessageService.getSingleUser(p);
                if (null != userVo) {
                    FSMessageSend.PublishInfo publishInfo = new FSMessageSend.PublishInfo();
                    publishInfo.setOpenId(userVo.getOpenId());
                    publishInfo.setPublishName(null != userInfoMap.get(p) ? userInfoMap.get(p).getRealName() : "");
                    publishInfos.add(publishInfo);
                }
            });
        }
        return publishInfos;
    }

    /**
     * 创建模版
     * @param fsMessageSend
     * @return
     */
    private String getFSMessageTemplate(FSMessageSend fsMessageSend) {

        String msgTemplate = null;
        Map<String, Object> map = new HashMap<>();

        map.put("noticeTime", DateTimeUtil.localDateTime2String(LocalDateTime.now(), DateTimeUtil.YYYY_MM_DD_HH_MM_SS));

        if (!StringUtils.isEmpty(fsMessageSend.getPageIdInfo())) {
            map.put("pageIdInfo", fsMessageSend.getPageIdInfo());
        }

        if (!StringUtils.isEmpty(fsMessageSend.getPageName())) {
            map.put("pageName", fsMessageSend.getPageName());
        }

        if (!StringUtils.isEmpty(fsMessageSend.getNoticeInfo())) {
            map.put("noticeInfo", fsMessageSend.getNoticeInfo());
        }

        if (!StringUtils.isEmpty(fsMessageSend.getTemplateIdInfo())) {
            map.put("templateId", fsMessageSend.getTemplateIdInfo());
        }

        if (null != fsMessageSend.getActivityId()) {
            map.put("activityId", fsMessageSend.getActivityId());
        }

        List<FSMessageSend.PublishInfo> publishInfos = fsMessageSend.getPublishInfos();

        //拼接对应页面负责人
        StringBuilder userInfo = new StringBuilder();
        for (FSMessageSend.PublishInfo publishInfo : publishInfos) {
            userInfo.append("<at id=").append(publishInfo.getOpenId()).append(">").append(publishInfo.getPublishName()).append("</at>");
        }
        if (!StringUtils.isEmpty(userInfo.toString())) {
            map.put("userInfo", userInfo.toString());
        }

        try {
            msgTemplate = GetTemplateUtils.mergeTemplateContent(map, fsMessageSend.getTemplateName());
        } catch (Exception e) {
            log.error("getFSMessageTemplate parse json error, e:{}", e.getMessage());
        }

        return msgTemplate;
    }

    private void sendNotice(String messageTemplate) {

        //向自定义机器人发送post请求
        Map<String, Object> paramMap = new HashMap<>(2);
        paramMap.put("msg_type", "interactive");
        paramMap.put("card", messageTemplate);
        HttpClientUtils.doPost(SystemConfig.getInstance().getWebHookAddress(), paramMap);
    }
}
