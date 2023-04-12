package com.idanchuang.cms.server.applicationNew.service;

import com.idanchuang.cms.server.domainNew.model.cms.external.notice.ChannelType;
import com.idanchuang.cms.server.domainNew.model.cms.external.notice.PushMessageTemplate;
import com.idanchuang.cms.server.domainNew.model.cms.external.notice.PushService;
import com.idanchuang.cms.server.domainNew.model.cms.external.notice.RemindMessage;
import com.idanchuang.cms.server.domainNew.model.cms.external.notice.SendMessageTemplate;
import com.idanchuang.cms.server.domainNew.model.cms.external.notice.SendService;
import com.idanchuang.cms.server.domainNew.model.cms.external.remind.UserRemindRecord;
import com.idanchuang.cms.server.infrastructure.adcontentservice.common.constant.BaseConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-03-31 10:07
 * @Desc: 消息通知服务
 * @Copyright VTN Limited. All rights reserved.
 */
@Slf4j
@Service
public class MessageNoticeService {

    @Resource
    private PushService pushService;

    @Resource
    private SendService sendService;

    public void pushRemindMsg(RemindMessage remindMessage) {

        if (null == remindMessage || CollectionUtils.isEmpty(remindMessage.getRemindRecords())) {
            return;
        }

        List<UserRemindRecord> remindRecords = remindMessage.getRemindRecords();

        Map<String, List<UserRemindRecord>> remindRecordMap = remindRecords.stream().collect(Collectors.groupingBy(UserRemindRecord::getRemindId));

        for (Map.Entry<String, List<UserRemindRecord>> entry : remindRecordMap.entrySet()) {

            List<UserRemindRecord> recordList = entry.getValue();

            if (CollectionUtils.isEmpty(recordList)) {
                continue;
            }

            UserRemindRecord remindRecord = recordList.get(0);
            String msgTitle = remindRecord.getMsgTitle();
            String msgContent = remindRecord.getMsgContent();
            String target = remindRecord.getTarget();
            List<Integer> idCodes = recordList.stream().map(p -> (int)p.getUserId().getValue() + BaseConstant.IDCODE_SEQ).distinct().collect(Collectors.toList());
            PushMessageTemplate pushMessageTemplate = pushService.getPushMessageTemplate(msgTitle, msgContent, target);
            SendMessageTemplate sendMessageTemplate = sendService.getSendMessageTemplate(msgTitle, msgContent, target);
            pushAndSendMessageToUser(idCodes, pushMessageTemplate, sendMessageTemplate, remindMessage.getChannelType());
        }

    }

    /**
     * 发送站内信和push消息
     * @param userIdCodes
     * @param pushMsg
     * @param sendMsg
     */
    private void pushAndSendMessageToUser(List<Integer> userIdCodes, PushMessageTemplate pushMsg,
                                          SendMessageTemplate sendMsg, ChannelType channelType) {

        if (ChannelType.CHANNEL_TYPE_ALL.equals(channelType)) {
            pushService.pushMessageToDCUsers(pushMsg, userIdCodes);
            sendService.sendMessageToDCUsers(sendMsg, userIdCodes);
        }

        if (ChannelType.CHANNEL_TYPE_PUSH.equals(channelType)) {
            pushService.pushMessageToDCUsers(pushMsg, userIdCodes);
        }

        if (ChannelType.CHANNEL_TYPE_SEND.equals(channelType)) {
            sendService.sendMessageToDCUsers(sendMsg, userIdCodes);
        }
    }
}
