package com.idanchuang.cms.server.infrastructureNew.external.feign;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.idanchuang.cms.server.domainNew.model.cms.external.notice.ChannelIdConst;
import com.idanchuang.cms.server.domainNew.model.cms.external.notice.PushMessageTemplate;
import com.idanchuang.cms.server.domainNew.model.cms.external.notice.PushPlatform;
import com.idanchuang.cms.server.domainNew.model.cms.external.notice.PushService;
import com.idanchuang.cms.server.domainNew.model.cms.external.notice.PushWayType;
import com.idanchuang.cms.server.infrastructure.adcontentservice.common.constant.BaseConstant;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.message.push.api.enums.TargetUsersType;
import com.idanchuang.message.push.api.facade.NotifyCationApi;
import com.idanchuang.message.push.api.request.SendNotificationRequest;
import com.idanchuang.message.push.api.request.TargetUsers;
import com.idanchuang.message.push.api.response.SendNotificationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-03-31 11:49
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Slf4j
@Service
public class PushServiceImpl implements PushService {

    @Resource
    private NotifyCationApi notifyCationApi;

    @Override
    public PushMessageTemplate getPushMessageTemplate(String msgTitle, String msgContent, String target) {

        PushMessageTemplate pushMessageTemplate = new PushMessageTemplate();
        pushMessageTemplate.setTitle(msgTitle);
        pushMessageTemplate.setContent(msgContent);
        pushMessageTemplate.setLink(target);
        List<Integer> pushWays = new ArrayList<>();
        pushWays.add(PushWayType.OUTSIDE.getVal());
        pushMessageTemplate.setPushWays(pushWays);
        return pushMessageTemplate;
    }

    @Override
    public void pushMessageToDCUsers(PushMessageTemplate msg, List<Integer> idCodeList) {

        if (CollectionUtils.isEmpty(idCodeList)) {
            return;
        }
        // uuid
        String uuid = UUID.randomUUID().toString();
        TargetUsers targetUsers = new TargetUsers();
        targetUsers.setType(TargetUsersType.USER_LIST);
        targetUsers.setUserList(idCodeList);
        SendNotificationRequest request = new SendNotificationRequest();
        request.setTargetUsers(targetUsers);
        Integer platformId = msg.getPlatformId() != null ? msg.getPlatformId() : PushPlatform.DC.getVal();
        request.setPlatformId(platformId);
        Integer channelId = msg.getChannelId() != null ? msg.getChannelId() : ChannelIdConst.DC_NOTICE;
        request.setChannelId(channelId);
        String batchId = StringUtils.isEmpty(msg.getBatchId()) ? uuid : msg.getBatchId();
        request.setBatchId(batchId);
        //跳转类型
        request.setRedirectType(BaseConstant.JUMP_TO_LINK);
        SendNotificationRequest.Template template = new SendNotificationRequest.Template();
        template.setContent(msg.getContent());
        template.setTitle(msg.getTitle());
        template.setIconUrl(msg.getImageUrl());
        template.setExtras(msg.getLink());
        request.setTemplate(template);

        Gson gson = new Gson();
        log.info("pushMessageToDCUsers 请求push参数：{},request:{}", gson.toJson(request), JSONObject.toJSONString(request));
        JsonResult<SendNotificationResponse> result = notifyCationApi.sendNotification(request);
        if (!result.isSuccess()) {
            log.error("发送失败,返回值 {}", result);
            return;
        }
        log.info("pushMessageToDCUsers 发送推送 {}", result);

    }
}
