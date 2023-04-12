package com.idanchuang.cms.server.infrastructureNew.external.feign;

import com.alibaba.fastjson.JSONObject;
import com.idanchuang.cms.server.domainNew.model.cms.external.notice.ChannelIdConst;
import com.idanchuang.cms.server.domainNew.model.cms.external.notice.PushPlatform;
import com.idanchuang.cms.server.domainNew.model.cms.external.notice.SendMessageTemplate;
import com.idanchuang.cms.server.domainNew.model.cms.external.notice.SendService;
import com.idanchuang.cms.server.infrastructure.adcontentservice.common.constant.BaseConstant;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.message.inmail.api.enums.TargetUsersType;
import com.idanchuang.message.inmail.api.facade.InMailApi;
import com.idanchuang.message.inmail.api.request.SendInMailRequest;
import com.idanchuang.message.inmail.api.request.TargetUsers;
import com.idanchuang.message.inmail.api.response.SendInMailResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
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
public class SendServiceImpl implements SendService {

    @Resource
    private InMailApi inMailApi;

    @Override
    public SendMessageTemplate getSendMessageTemplate(String msgTitle, String msgContent, String target) {

        SendMessageTemplate sendMessageTemplate = new SendMessageTemplate();
        sendMessageTemplate.setTitle(msgTitle);
        sendMessageTemplate.setContent(msgContent);
        sendMessageTemplate.setLink(target);
        sendMessageTemplate.setTemplateId(BaseConstant.REMIND_SHOOT_MESSAGE);
        return sendMessageTemplate;
    }

    @Override
    public void sendMessageToDCUsers(SendMessageTemplate msg, List<Integer> idCodeList) {

        if (CollectionUtils.isEmpty(idCodeList)) {
            return;
        }
        TargetUsers targetUsers = new TargetUsers();
        targetUsers.setType(TargetUsersType.USER_LIST);
        targetUsers.setUserList(idCodeList);
        SendInMailRequest request = new SendInMailRequest();
        request.setTargetUsers(targetUsers);

        String uuid = UUID.randomUUID().toString();
        String batchId = StringUtils.isEmpty(msg.getBatchId()) ? uuid : msg.getBatchId();
        request.setBatchId(batchId);
        Integer platformId = msg.getPlatformId() != null ? msg.getPlatformId() : PushPlatform.DC.getVal();
        request.setPlatformId(platformId);
        //跳转类型
        request.setRedirectType(msg.getRedirectType() != null ? msg.getRedirectType() : BaseConstant.JUMP_TO_LINK);

        SendInMailRequest.Template template = new SendInMailRequest.Template();
        template.setContent(msg.getContent());
        template.setTitle(msg.getTitle());
        template.setIconUrl(msg.getImageUrl());
        template.setTemplateId(msg.getTemplateId());
        template.setExtras(msg.getLink());
        request.setTemplate(template);
        Integer channelId = msg.getChannelId() != null ? msg.getChannelId() : ChannelIdConst.DC_NOTICE;
        request.setChannelId(channelId);
        request.setSort(2);
        log.info("请求站内信参数 {}", JSONObject.toJSONString(request));
        JsonResult<SendInMailResponse> result = inMailApi.sendInMail(request);
        if (!result.isSuccess()) {
            log.error("发送失败,返回值 {}", result);
            return;
        }
        log.info("发送站内信 {}", result);

    }
}
