package com.idanchuang.cms.server.infrastructureNew.repository;

import com.google.common.collect.Lists;
import com.idanchuang.cms.server.domainNew.model.cms.external.message.MMessage;
import com.idanchuang.cms.server.domainNew.model.cms.external.message.MMessageRepository;
import com.idanchuang.cms.server.domainNew.model.cms.external.message.MMessageSendStatus;
import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.abmau.MMessageDO;
import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.abmau.mapper.MMessageMapper;
import com.idanchuang.cms.server.infrastructureNew.repository.convertor.RepositoryMMessageConvert;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-04-22 11:44
 * @Desc: 消息弹窗实现类
 * @Copyright VTN Limited. All rights reserved.
 */
@Repository
public class MMessageRepositoryImpl implements MMessageRepository {

    @Resource
    private MMessageMapper mMessageMapper;

    @Override
    public List<MMessage> getPopNoSendMessage() {

        List<MMessageDO> mMessageDOS = mMessageMapper.selectPartNoSendMessage(Lists.newArrayList(MMessageSendStatus.NOT_SEND.getVal(),
                        MMessageSendStatus.PART_SEND.getVal()));

        return RepositoryMMessageConvert.convertDOToEntity(mMessageDOS);
    }

    @Override
    public boolean updateStatusById(Integer id, Integer status) {
        return mMessageMapper.updateStatusById(id, status);
    }

}
