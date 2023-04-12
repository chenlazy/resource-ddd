package com.idanchuang.cms.server.infrastructureNew.repository;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.idanchuang.cms.server.domain.model.cms.ActivityPage;
import com.idanchuang.cms.server.domain.shard.IdObject;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueId;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.*;
import com.idanchuang.cms.server.domainNew.shard.OperatorId;
import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.MasterplateDO;
import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.mapper.MasterplateMapper;
import com.idanchuang.cms.server.infrastructureNew.repository.convertor.RepositoryMasterplateConvert;
import com.idanchuang.component.base.page.PageData;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MasterplateRepositoryImpl implements MasterplateRepository {

    @Resource
    private MasterplateMapper masterplateMapper;


    @Override
    public PageData<Masterplate> queryMasterplateByPage(MasterplatePageQueryForm pageQueryForm) {


        Page<Object> page = PageHelper.startPage(pageQueryForm.getCurrent().intValue(), pageQueryForm.getSize().intValue());

        Long masterplateId = null != pageQueryForm.getMasterplateId() ? pageQueryForm.getMasterplateId().getValue() : null;
        Long catalogueId = null != pageQueryForm.getCatalogueId() ? pageQueryForm.getCatalogueId().getValue() : null;

        List<MasterplateDO> masterplateDOS = masterplateMapper.getMasterplateList(masterplateId, catalogueId, pageQueryForm.getMasterplateName(), pageQueryForm.getStatus());

        if (CollectionUtils.isEmpty(masterplateDOS)) {
            return new PageData<>(Lists.newArrayList(), pageQueryForm.getCurrent(), pageQueryForm.getSize(), 0);
        }

        List<Masterplate> templates = masterplateDOS.stream().map(RepositoryMasterplateConvert::doToDomain).collect(Collectors.toList());

        return new PageData<>(templates, pageQueryForm.getCurrent(), pageQueryForm.getSize(), page.getTotal());

    }

    @Override
    public Masterplate getMasterplateById(MasterplateId masterplateId) {

        MasterplateDO masterplateDO = masterplateMapper.getMasterplateById(masterplateId.getValue());

        return RepositoryMasterplateConvert.doToDomain(masterplateDO);
    }

    @Override
    public List<Masterplate> selectMasterplateByIds(List<MasterplateId> masterplateIds) {
        List<Long> ids = masterplateIds.stream().map(IdObject::getValue).collect(Collectors.toList());
        List<MasterplateDO> masterplateDOS = masterplateMapper.selectMasterplateByIds(ids);
        if (CollectionUtils.isEmpty(masterplateDOS)) {
            return Lists.newArrayList();
        }
        return masterplateDOS.stream().map(RepositoryMasterplateConvert::doToDomain).collect(Collectors.toList());
    }

    @Override
    public List<Masterplate> getBatchMasterplate(List<CatalogueId> catalogueIds) {

        if (CollectionUtils.isEmpty(catalogueIds)) {
            return Lists.newArrayList();
        }

        List<Long> catalogueIdList = catalogueIds.stream().map(IdObject::getValue).collect(Collectors.toList());
        List<MasterplateDO> masterplateDOS = masterplateMapper.getBatchMasterplateList(catalogueIdList);
        if (CollectionUtils.isEmpty(masterplateDOS)) {
            return Lists.newArrayList();
        }

        return masterplateDOS.stream().map(RepositoryMasterplateConvert::doToDomain).collect(Collectors.toList());
    }

    @Override
    public int countByCatalogueId(CatalogueId catalogueId) {
        return masterplateMapper.countByCatalogueId(catalogueId.getValue());
    }

    @Override
    public void remove(MasterplateId masterplateId, OperatorId operatorId) {

        masterplateMapper.remove(masterplateId.getValue(), operatorId.getValue());
    }

    @Override
    public Boolean updateStartTimeById(MasterplateId masterplateId, LocalDateTime startTime, OperatorId operatorId) {

        return masterplateMapper.updateStartTimeById(masterplateId.getValue(), startTime, operatorId.getValue()) > 0;
    }

    @Override
    public Boolean updateMasterplate(Masterplate masterplate) {

        MasterplateDO masterplateDO = RepositoryMasterplateConvert.domainToDo(masterplate);

        return masterplateMapper.updateById(masterplateDO);
    }

    @Override
    public void storeMasterplate(Masterplate masterplate) {
        MasterplateDO masterplateDO = RepositoryMasterplateConvert.domainToDo(masterplate);
        masterplateMapper.insert(masterplateDO);
        masterplate.setId(new MasterplateId(masterplateDO.getId()));

    }

    @Override
    public List<Masterplate> getMasterplateList(CatalogueId catalogueId) {

        if (null == catalogueId) {
            return Lists.newArrayList();
        }
        List<MasterplateDO> masterplates = masterplateMapper.getMasterplatesByCatalogueId(catalogueId.getValue());
        if (CollectionUtils.isEmpty(masterplates)) {
            return Lists.newArrayList();
        }
        return masterplates.stream().map(RepositoryMasterplateConvert::doToDomain).collect(Collectors.toList());
    }

    @Override
    public List<Masterplate> getMasterplateListForActive(CatalogueId catalogueId) {

        if (null == catalogueId) {
            return Lists.newArrayList();
        }
        List<MasterplateDO> masterplates = masterplateMapper.getMasterplatesForActive(catalogueId.getValue());

        if (CollectionUtils.isEmpty(masterplates)) {
            return Lists.newArrayList();
        }

        return masterplates.stream().map(RepositoryMasterplateConvert::doToDomain).collect(Collectors.toList());
    }

    @Override
    public List<Masterplate> getMasterplateListForValid() {

        List<MasterplateDO> masterplateDOS = masterplateMapper.getMasterplateListForValid();
        return masterplateDOS.stream().map(RepositoryMasterplateConvert::doToDomain).collect(Collectors.toList());
    }

    @Override
    public void updateGoodsEnable(MasterplateId masterplateId, GoodsEnable enable) {
        masterplateMapper.updateGoodsEnable(masterplateId.getValue(),enable.getVal());
    }

    @Override
    public List<Masterplate> getCmsPageListForValid(List<CatalogueId> catalogueIds) {
        if (CollectionUtils.isEmpty(catalogueIds)) {
            return Lists.newArrayList();
        }
        List<Long> catalogueIdList = catalogueIds.stream().map(IdObject::getValue).collect(Collectors.toList());

        List<MasterplateDO> masterplateDOS = masterplateMapper.getCmsPageListForValid(catalogueIdList);
        if (CollectionUtils.isEmpty(masterplateDOS)) {
            return Lists.newArrayList();
        }
        return masterplateDOS.stream().map(RepositoryMasterplateConvert::doToDomain).collect(Collectors.toList());
    }

    @Override
    public List<Masterplate> getCmsPageListForActive(List<CatalogueId> catalogueIds) {

        if (CollectionUtils.isEmpty(catalogueIds)) {
            return Lists.newArrayList();
        }
        List<Long> catalogueIdList = catalogueIds.stream().map(IdObject::getValue).collect(Collectors.toList());

        List<MasterplateDO> masterplateDOS = masterplateMapper.getCmsPageListForActive(catalogueIdList);
        if (CollectionUtils.isEmpty(masterplateDOS)) {
            return Lists.newArrayList();
        }
        return masterplateDOS.stream().map(RepositoryMasterplateConvert::doToDomain).collect(Collectors.toList());
    }

    @Override
    public List<Masterplate> getMasterplateSnapList(MasterplateId masterplateId) {

        if (null == masterplateId) {
            return Lists.newArrayList();
        }

        List<MasterplateDO> masterplateDOS = masterplateMapper.getMasterplateSnapList(masterplateId.getValue());

        if (CollectionUtils.isEmpty(masterplateDOS)) {
            return Lists.newArrayList();
        }

        return masterplateDOS.stream().map(RepositoryMasterplateConvert::doToDomain).collect(Collectors.toList());
    }

    @Override
    public List<ActivityPage> queryActivityPageBySpuId(Long spuId) {
        List<MasterplateDO> masterplateDOS = masterplateMapper.queryActivityPageBySpuId(spuId);
        if (CollectionUtils.isEmpty(masterplateDOS)) {
            return Lists.newArrayList();
        }
        return masterplateDOS.stream().map(RepositoryMasterplateConvert::doToActivityPage).collect(Collectors.toList());
    }



}
