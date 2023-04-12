package com.idanchuang.cms.server.infrastructureNew.schedule;

import com.alibaba.fastjson.JSON;
import com.idanchuang.cms.server.domainNew.model.cms.external.message.MMessage;
import com.idanchuang.cms.server.domainNew.model.cms.external.message.MMessageRepository;
import com.idanchuang.cms.server.domainNew.model.cms.external.message.MMessageSendStatus;
import com.idanchuang.cms.server.infrastructureNew.schedule.model.SendRangeAtList;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-04-22 10:36
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Component
@Slf4j
@JobHandler(value = "PopMessageJob")
public class PopMessageJob extends IJobHandler {

    @Resource
    private MMessageRepository mMessageRepository;

    @Override
    public ReturnT<String> execute(String s) throws Exception {

        XxlJobLogger.log("PopMessageJob start execute");

        List<MMessage> popMessageList = mMessageRepository.getPopNoSendMessage();

        if (CollectionUtils.isEmpty(popMessageList)) {
            return SUCCESS;
        }

        for (MMessage mMessage : popMessageList) {
            List<SendRangeAtList> rangeAtLists = JSON.parseArray(mMessage.getSendRangeAt(), SendRangeAtList.class);

            if (CollectionUtils.isEmpty(rangeAtLists)) {
                continue;
            }
            try {
                boolean isDisplayedList = Boolean.TRUE;
                int notDisplayNum = 0;

                for (SendRangeAtList  sendAt : rangeAtLists) {
                    String rangeStart = ObjectUtils.isEmpty(sendAt.getValue().get(0)) ? "" : sendAt.getValue().get(0);
                    String rangeEnd = ObjectUtils.isEmpty(sendAt.getValue().get(1)) ? "" : sendAt.getValue().get(1);
                    if (StringUtils.isEmpty(rangeStart) || StringUtils.isEmpty(rangeEnd)) {
                        continue;
                    }

                    LocalDateTime start = LocalDateTime.parse(rangeStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    if (start.isAfter(LocalDateTime.now())) {
                        isDisplayedList = Boolean.FALSE;
                        notDisplayNum++;
                    }
                }

                //全部展示完毕更新状态
                if (isDisplayedList) {
                    mMessageRepository.updateStatusById(mMessage.getId(), MMessageSendStatus.SEND.getVal());
                } else {
                    if (MMessageSendStatus.NOT_SEND.getVal().equals(mMessage.getStatus())
                            && rangeAtLists.size() > 1 && rangeAtLists.size() > notDisplayNum) {
                        mMessageRepository.updateStatusById(mMessage.getId(), MMessageSendStatus.PART_SEND.getVal());
                    }
                }
            } catch (Exception e) {
                log.warn("PopMessageJob send pop exception, message:[{}]", mMessage.getId(), e);
            }
        }
        XxlJobLogger.log("PopMessageJob end execute");
        return SUCCESS;
    }
}
