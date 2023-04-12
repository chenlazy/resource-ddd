package com.idanchuang.cms.server.application.service;

import com.idanchuang.cms.server.domain.model.cms.ContainerComponent;
import com.idanchuang.cms.server.domain.model.cms.ContainerComponentCondition;
import com.idanchuang.cms.server.domain.repository.ContainerComponentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-02 14:12
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Service
@Slf4j
public class ContainerComponentService {

    @Resource
    private ContainerComponentRepository containerComponentRepository;

    public boolean insert(ContainerComponent containerComponent) {
        return containerComponentRepository.insert(containerComponent);
    }

    public List<Long> insertBatch(List<ContainerComponent> containerComponentList) {
        return containerComponentRepository.insertBatch(containerComponentList);
    }

    public boolean updateById(ContainerComponent containerComponent) {
        return containerComponentRepository.updateById(containerComponent);
    }

    /**
     * 根据组件ID更新容器ID
     * @param idList        组件ID
     * @param containerId   容器ID
     * @param operatorId    操作人ID
     * @return  是否成功
     */
    public boolean updateContainerId(List<Long> idList, Long containerId, Integer operatorId) {
        return containerComponentRepository.updateContainerId(idList, containerId, operatorId);
    }


    public ContainerComponent selectById(Long id) {
        return containerComponentRepository.selectById(id);
    }

    public List<ContainerComponent> selectByCondition(ContainerComponentCondition condition) {
        return containerComponentRepository.selectByCondition(condition);
    }

    /**
     * 根据容器ID查询
     * @param containerIdList   容器ID
     * @return   容器组件列表
     */
    public List<ContainerComponent> selectList(List<Long> containerIdList) {
        ContainerComponentCondition condition = new ContainerComponentCondition();
        condition.setContainerIdList(containerIdList);
        return selectByCondition(condition);
    }

}
