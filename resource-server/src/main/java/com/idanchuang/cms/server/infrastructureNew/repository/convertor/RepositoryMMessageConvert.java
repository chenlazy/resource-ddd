package com.idanchuang.cms.server.infrastructureNew.repository.convertor;

import com.google.common.collect.Lists;
import com.idanchuang.cms.server.domainNew.model.cms.external.message.MMessage;
import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.abmau.MMessageDO;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-04-22 13:55
 * @Desc: 消息转化类
 * @Copyright VTN Limited. All rights reserved.
 */
public class RepositoryMMessageConvert {

    public static List<MMessage> convertDOToEntity(List<MMessageDO> mMessageDOS) {

        if (CollectionUtils.isEmpty(mMessageDOS)) {
            return Lists.newArrayList();
        }

        List<MMessage> messages = mMessageDOS.stream().map(p -> {
            MMessage mMessage = new MMessage();
            BeanUtils.copyProperties(p, mMessage);
            return mMessage;
        }).collect(Collectors.toList());

        return messages;
    }
}
