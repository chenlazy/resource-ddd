package com.idanchuang.cms.server.infrastructureNew.repository;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.idanchuang.cms.server.domainNew.model.cms.external.remind.RemindStatus;
import com.idanchuang.cms.server.domainNew.model.cms.external.remind.RemindType;
import com.idanchuang.cms.server.domainNew.model.cms.external.remind.UserId;
import com.idanchuang.cms.server.domainNew.model.cms.external.remind.UserRemindId;
import com.idanchuang.cms.server.domainNew.model.cms.external.remind.UserRemindRecord;
import com.idanchuang.cms.server.domainNew.model.cms.external.remind.UserRemindRepository;
import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.abmau.UserRemindRecordDO;
import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.abmau.mapper.UserRemindMapper;
import com.idanchuang.cms.server.infrastructureNew.repository.convertor.RepositoryUserRemindConvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-03-31 15:44
 * @Desc: 用户提醒实现类
 * @Copyright VTN Limited. All rights reserved.
 */
@Repository
@Slf4j
public class UserRemindRepositoryImpl implements UserRemindRepository {

    @Resource
    private UserRemindMapper userRemindMapper;

    @Override
    public Integer getRemindRecordCount(RemindStatus status, List<RemindType> remindTypes, LocalDateTime remindSendTime) {

        List<Integer> remindTypeList = CollectionUtils.isEmpty(remindTypes) ? remindTypes.stream().map(RemindType::getVal)
                .collect(Collectors.toList()) : null;
        return userRemindMapper.getRemindRecordCount(status.getVal(), remindTypeList, remindSendTime);
    }

    @Override
    public List<UserRemindRecord> listRemindRecords(Integer page, Integer pageSize, RemindStatus status,
                                                    List<RemindType> remindTypes, LocalDateTime remindSendTime) {

        if (null == status || CollectionUtils.isEmpty(remindTypes)) {
            return Lists.newArrayList();
        }
        List<Integer> remindTypeList = remindTypes.stream().map(RemindType::getVal).collect(Collectors.toList());
        //分页查询
        PageHelper.startPage(page, pageSize);
        List<UserRemindRecordDO> recordDOS = userRemindMapper.listRemindRecords(status.getVal(), remindTypeList, remindSendTime);

        if (CollectionUtils.isEmpty(recordDOS)) {
            return Lists.newArrayList();
        }

        return recordDOS.stream().map(RepositoryUserRemindConvert::doToDomain).collect(Collectors.toList());
    }

    @Override
    public List<UserRemindRecord> listNearRemindRecords(UserId userId, String componentId, RemindType remindType,
                                                        RemindStatus status, LocalDateTime remindTime) {

        if (null == userId || StringUtils.isEmpty(componentId) || null == status|| null == remindType ||  null == remindTime) {
            return Lists.newArrayList();
        }

        List<UserRemindRecordDO> recordDOS = userRemindMapper.listNearRemindRecords(userId.getValue(), componentId,
                remindType.getVal(), status.getVal(), remindTime);

        if (CollectionUtils.isEmpty(recordDOS)) {
            return Lists.newArrayList();
        }

        return recordDOS.stream().map(RepositoryUserRemindConvert::doToDomain).collect(Collectors.toList());
    }

    @Override
    public boolean updateRemindRecordStatus(List<UserRemindId> remindIds, RemindStatus status) {

        if (CollectionUtils.isEmpty(remindIds) || null == status) {
            return false;
        }

        List<Long> remindIdList = remindIds.stream().map(UserRemindId::getValue).collect(Collectors.toList());

        return userRemindMapper.updateRemindRecordStatus(remindIdList, status.getVal());
    }
}
