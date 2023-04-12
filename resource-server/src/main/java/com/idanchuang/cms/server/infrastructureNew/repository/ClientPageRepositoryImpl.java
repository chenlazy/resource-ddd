package com.idanchuang.cms.server.infrastructureNew.repository;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.idanchuang.cms.server.domain.shard.IdObject;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPage;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPageId;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPageQueryForm;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPageRepository;
import com.idanchuang.cms.server.domainNew.shard.OperatorId;
import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.ClientPageDO;
import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.mapper.ClientPageMapper;
import com.idanchuang.cms.server.infrastructureNew.repository.convertor.RepositoryClientPageConvert;
import com.idanchuang.component.base.page.PageData;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-24 11:25
 * @Desc: 客户端页面领域实现类
 * @Copyright VTN Limited. All rights reserved.
 */
@Repository
public class ClientPageRepositoryImpl implements ClientPageRepository {
    @Resource
    ClientPageMapper clientPageMapper;

    @Override
    public boolean store(ClientPage clientPage) {
        ClientPageDO clientPageDO = RepositoryClientPageConvert.toClientPageDO(clientPage);
        return clientPageMapper.insert(clientPageDO) > 0;
    }

    @Override
    public boolean remove(ClientPageId id, OperatorId operatorId) {
        if (id == null) {
            return false;
        }
        return clientPageMapper.remove(id.getValue(), Optional.ofNullable(operatorId).map(IdObject::getValue).orElse(0L));
    }

    @Override
    public boolean update(ClientPage clientPage) {
        ClientPageDO clientPageDO = RepositoryClientPageConvert.toClientPageDO(clientPage);
        return clientPageMapper.update(clientPageDO) > 0;
    }

    @Override
    public ClientPage query(ClientPageId id) {
        if (id == null) {
            return null;
        }
        ClientPageDO clientPageDO = clientPageMapper.get(id.getValue());
        return RepositoryClientPageConvert.toClientPage(clientPageDO);
    }

    @Override
    public PageData<ClientPage> page(ClientPageQueryForm queryForm) {
        Page<Object> page = PageHelper.startPage(queryForm.getPageNum(), queryForm.getPageSize());

        List<ClientPageId> pageIds = queryForm.getIds();
        List<Long> ids = null;
        if (!CollectionUtils.isEmpty(pageIds)) {
            ids = pageIds.stream().map(IdObject::getValue).collect(Collectors.toList());
        }
        Integer platform = null != queryForm.getPlatformCode() ? queryForm.getPlatformCode().getVal() : null;
        List<ClientPageDO> list = clientPageMapper.list(ids, platform, null);
        List<ClientPage> collect = list.stream().map(RepositoryClientPageConvert::toClientPage).collect(Collectors.toList());
        return new PageData<>(collect, queryForm.getPageNum(), queryForm.getPageSize(), page.getTotal());
    }

    @Override
    public List<ClientPage> list(ClientPageQueryForm queryForm) {

        Integer platform = null != queryForm.getPlatformCode() ? queryForm.getPlatformCode().getVal() : null;
        List<ClientPageId> pageIds = queryForm.getIds();
        List<Long> ids = null;
        if (!CollectionUtils.isEmpty(pageIds)) {
            ids = pageIds.stream().map(IdObject::getValue).collect(Collectors.toList());
        }
        String pageCode = null;
        if (queryForm.getPageCode() != null) {
            pageCode = queryForm.getPageCode().getValue();
        }

        List<ClientPageDO> list = clientPageMapper.list(ids, platform, pageCode);
        return list.stream()
                .map(RepositoryClientPageConvert::toClientPage)
                .collect(Collectors.toList());
    }
}
