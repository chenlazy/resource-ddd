package com.idanchuang.cms.server.applicationNew.service;

import com.idanchuang.cms.server.domainNew.model.cms.component.Component;
import com.idanchuang.cms.server.domainNew.model.cms.component.ComponentFactory;
import com.idanchuang.cms.server.domainNew.model.cms.component.ComponentRepository;
import com.idanchuang.cms.server.domainNew.model.cms.container.*;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.Masterplate;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateRepository;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateUpsertForm;
import com.idanchuang.cms.server.domainNew.model.cms.number.ConstructionTypeEnum;
import com.idanchuang.cms.server.domainNew.model.cms.number.SequenceNumberId;
import com.idanchuang.cms.server.domainNew.model.cms.number.SequenceNumberRepository;
import com.idanchuang.cms.server.domainNew.shard.parse.ContainerData;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author fym
 * @description :
 * @date 2021/12/27 下午3:58
 */
@Service
@Slf4j
public class MasterplateSnapshotService {

    @Resource
    private ContainerRepository containerRepository;
    @Resource
    private ComponentRepository componentRepository;
    @Resource
    private MasterplateRepository masterplateRepository;
    @Resource
    private SequenceNumberRepository sequenceNumberRepository;

    public void createSnapshot(MasterplateUpsertForm upsertForm, Masterplate masterplate,
                               List<ContainerData> compDataList, List<Component> fillComponents) {

        if (null == masterplate) {
            return;
        }
        //将模版转化成快照
        masterplate.transferSnapshot();
        //保存快照模版
        masterplateRepository.storeMasterplate(masterplate);
        for (ContainerData containerData : compDataList) {
            //创建容器对象
            Container container = ContainerFactory.createContainer(masterplate.getId(),
                    StringUtils.isNotEmpty(containerData.getContainerCode()) ? new ContainerCode(containerData.getContainerCode()) : null,
                    containerData.getContainerName(),
                    ContainerStatus.VALID,
                    null, JsonUtil.toJsonString(upsertForm.parseContainerConfig(containerData)),
                    masterplate.getOperatorId());

            //转化成快照版本
            container.transferSnapshot();
            //保存或更新容器记录
            containerRepository.storeContainer(container);
            if (!CollectionUtils.isEmpty(fillComponents)) {
                //批量生成组件id
                List<SequenceNumberId> sequenceNumberIds = sequenceNumberRepository.batchSequenceNumber(ConstructionTypeEnum.COMPONENT.getVal(), fillComponents.size());
                if (!CollectionUtils.isEmpty(sequenceNumberIds) && sequenceNumberIds.size() == fillComponents.size()) {
                    List<Component> componentSnapshot = Lists.newArrayList();
                    for (int i = 0; i < fillComponents.size(); i++) {
                        Component domain = fillComponents.get(i);
                        Component component = ComponentFactory.createBasicsComponent(sequenceNumberIds.get(i), container, masterplate.getId(), domain);
                        //转成快照组件
                        component.transferSnapshot();
                        componentSnapshot.add(component);
                    }
                    if (!CollectionUtils.isEmpty(componentSnapshot)) {
                        //批量保存或更新组件信息
                        componentRepository.batchStoreComponent(componentSnapshot);
                    }
                }
            }
        }
    }

}
