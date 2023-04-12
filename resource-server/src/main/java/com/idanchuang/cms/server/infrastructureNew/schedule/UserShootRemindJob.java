package com.idanchuang.cms.server.infrastructureNew.schedule;

import com.google.common.collect.Lists;
import com.idanchuang.cms.server.applicationNew.service.MessageNoticeService;
import com.idanchuang.cms.server.domainNew.model.cms.external.notice.ChannelType;
import com.idanchuang.cms.server.domainNew.model.cms.external.notice.RemindMessage;
import com.idanchuang.cms.server.domainNew.model.cms.external.remind.RemindStatus;
import com.idanchuang.cms.server.domainNew.model.cms.external.remind.RemindType;
import com.idanchuang.cms.server.domainNew.model.cms.external.remind.UserId;
import com.idanchuang.cms.server.domainNew.model.cms.external.remind.UserRemindId;
import com.idanchuang.cms.server.domainNew.model.cms.external.remind.UserRemindRecord;
import com.idanchuang.cms.server.domainNew.model.cms.external.remind.UserRemindRepository;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-03-28 16:18
 * @Desc: 用户提醒记录脚本
 * @Copyright VTN Limited. All rights reserved.
 */
@Component
@Slf4j
@JobHandler(value = "UserShootRemindJob")
public class UserShootRemindJob extends IJobHandler {

    private static final Integer LIMIT_COUNT = 100;

    @Resource
    private UserRemindRepository userRemindRepository;

    @Resource
    private MessageNoticeService messageNoticeService;


    @Override
    public ReturnT<String> execute(String s) throws Exception {
        XxlJobLogger.log("UserRemindJob start execute");

        Integer recordCount = userRemindRepository.getRemindRecordCount(RemindStatus.REMIND_STATUS_WAIT,
                RemindType.getRemindComponentList(), LocalDateTime.now());

        int totalPage = recordCount / LIMIT_COUNT + 1;
        int page = 1;

        //先进行站内信消息的发送，发送全部预约的站内信，不更改状态
        while (page <= totalPage) {
            List<UserRemindRecord> remindRecords = userRemindRepository.listRemindRecords(page, LIMIT_COUNT,
                    RemindStatus.REMIND_STATUS_WAIT, RemindType.getRemindComponentList(), LocalDateTime.now());

            if (CollectionUtils.isEmpty(remindRecords)) {
                break;
            }
            //发送待发送的站内信消息
            RemindMessage remindMessage = new RemindMessage(remindRecords, ChannelType.CHANNEL_TYPE_SEND);
            messageNoticeService.pushRemindMsg(remindMessage);

            log.info("UserRemindJob send remindRecords, page:{}, size:{}", page, remindRecords.size());
            page++;
        }

        //page重新置为1
        page = 1;
        //进行push消息的发送，相近5分钟内只提醒一条，更改状态
        while(page <= totalPage) {
            List<UserRemindRecord> remindRecords = userRemindRepository.listRemindRecords(1, LIMIT_COUNT,
                    RemindStatus.REMIND_STATUS_WAIT, RemindType.getRemindComponentList(), LocalDateTime.now());

            if (CollectionUtils.isEmpty(remindRecords)) {
                break;
            }

            Map<UserId, List<UserRemindRecord>> remindMap = remindRecords.stream().collect(Collectors.groupingBy(UserRemindRecord::getUserId));

            List<UserRemindRecord> needRemindRecords = new ArrayList<>();

            List<UserId> userIds = Lists.newArrayList();

            List<UserRemindRecord> allNearRecords = new ArrayList<>();

            for (Map.Entry<UserId, List<UserRemindRecord>> entry : remindMap.entrySet()) {
                userIds.add(entry.getKey());
                List<UserRemindRecord> recordList = entry.getValue();
                //获取最先的一条，专题页不同类型的提醒唯一
                List<String> remindKeys = new ArrayList<>();


                for (UserRemindRecord remindRecord : recordList) {
                    String componentId = remindRecord.getComponentId();
                    RemindType remindType = remindRecord.getRemindType();
                    String remindKey = componentId + remindType;

                    if (remindKeys.contains(remindKey)) {
                        continue;
                    }

                    remindKeys.add(remindKey);
                    needRemindRecords.add(remindRecord);
                    //查询开抢前五分钟的提醒记录
                    List<UserRemindRecord> nearRecords = userRemindRepository.listNearRemindRecords(entry.getKey(),
                            componentId, remindType, RemindStatus.REMIND_STATUS_WAIT, LocalDateTime.now().plusMinutes(5));

                    if (CollectionUtils.isNotEmpty(nearRecords)) {
                        allNearRecords.addAll(nearRecords);
                    }
                }
            }

            //推送第一条push消息
            RemindMessage remindMessage = new RemindMessage(needRemindRecords, ChannelType.CHANNEL_TYPE_PUSH);
            messageNoticeService.pushRemindMsg(remindMessage);

            //将五分钟内所有用户的预约提醒设置为已提醒，防止重复提醒
            List<UserRemindId> nearIds = CollectionUtils.isNotEmpty(allNearRecords) ? allNearRecords.stream()
                    .map(UserRemindRecord::getId).collect(Collectors.toList()) : Lists.newArrayList();


            //更新消息状态
            List<UserRemindId> remindIds = needRemindRecords.stream().map(UserRemindRecord::getId).collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(nearIds)) {
                remindIds.addAll(nearIds);
                log.info("UserRemindJob fetch nearIds, userIds:{}, nearIds:{}, page:{}, size:{}", userIds, nearIds,
                        page, remindRecords.size());
            }

            userRemindRepository.updateRemindRecordStatus(remindIds, RemindStatus.REMIND_STATUS_SEND);

            log.info("UserRemindJob fetch remindRecords, page:{}, size:{}", page, remindRecords.size());
            page++;
        }

        XxlJobLogger.log("UserRemindJob end execute");
        return SUCCESS;
    }

}
