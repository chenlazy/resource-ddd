package com.idanchuang.cms.server.applicationNew.service;

import com.idanchuang.cms.server.domainNew.model.cms.container.Container;
import com.idanchuang.cms.server.domainNew.model.cms.container.ContainerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-16 15:15
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Service
@Slf4j
public class ContainerService {

    @Resource
    private ContainerRepository containerRepository;

    @Transactional(rollbackFor = Exception.class)
    public boolean upsertContainer(Container container, int index, List<Container> oldContainers) {

        Boolean upsert;
        if (index < oldContainers.size()) {
            Container oldContainer = oldContainers.get(index);
            container.setContainerId(oldContainer.getId());
            upsert = containerRepository.updateContainer(container);
        } else {
            upsert = containerRepository.storeContainer(container);
        }
        return upsert;
    }
}
