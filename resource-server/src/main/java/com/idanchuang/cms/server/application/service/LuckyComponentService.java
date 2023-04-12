package com.idanchuang.cms.server.application.service;

import com.alibaba.fastjson.JSON;
import com.idanchuang.cms.api.common.enums.ModelTypeEnum;
import com.idanchuang.cms.api.model.LuckyComponentInfo;
import com.idanchuang.cms.server.domain.model.cms.CmsPage;
import com.idanchuang.cms.server.domain.model.cms.CmsPageContainer;
import com.idanchuang.cms.server.domain.model.cms.ContainerComponent;
import com.idanchuang.cms.server.domain.model.cms.ContainerComponentCondition;
import com.idanchuang.cms.server.domain.repository.CmsPageContainerRepository;
import com.idanchuang.cms.server.domain.repository.CmsPageRepository;
import com.idanchuang.cms.server.domain.repository.ContainerComponentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lei.liu
 * @date 2021/10/9
 */
@Service
@Slf4j
public class LuckyComponentService {

    @Resource
    private CmsPageRepository cmsPageRepository;

    @Resource
    private ContainerComponentRepository containerComponentRepository;

    @Resource
    private CmsPageContainerRepository cmsPageContainerRepository;

    /**
     * 根据抽奖活动ID，获取关联的专题页别名
     * 若关联多个，返回最新的一个
     * @param activityId    抽奖活动ID
     * @return  专题别名
     */
    public String getLuckyComponentPageId(Long activityId) {

        // 获取未失效的专题页面
        List<CmsPage> pageList = cmsPageRepository.getPageListForValid(LocalDateTime.now(), null);

        // 获取专题页面关联的所有容器
        List<CmsPageContainer> pageContainerList = cmsPageContainerRepository.queryPageContainersByPageIds(pageList.stream().map(CmsPage::getId).collect(Collectors.toList()));
        if (CollectionUtils.isEmpty(pageContainerList)) {
            return null;
        }

        // 获取专题页面关联的抽奖组件
        ContainerComponentCondition condition = new ContainerComponentCondition();
        condition.setModelType(ModelTypeEnum.MODEL_TYPE_LUCKY.getModelType());
        condition.setContainerIdList(pageContainerList.stream().map(CmsPageContainer::getId).collect(Collectors.toList()));
        List<ContainerComponent> containerComponentList = containerComponentRepository.selectByCondition(condition);
        if (CollectionUtils.isEmpty(containerComponentList)) {
            return null;
        }

        Long containerId = null;
        // 组件ID倒序排列，获取最新的
        containerComponentList.sort((o1, o2) -> o2.getId().compareTo(o1.getId()));
        for (ContainerComponent component : containerComponentList) {
            if (StringUtils.hasText(component.getBizJson())) {
                LuckyComponentInfo luckyComponent = JSON.parseObject(component.getBizJson(), LuckyComponentInfo.class);
                if (luckyComponent != null && activityId.equals(luckyComponent.getActivityId())) {
                    containerId = component.getContainerId();
                    break;
                }
            }
        }

        if (containerId != null) {
            pageContainerList.sort((o1, o2) -> o2.getPageId().compareTo(o1.getPageId()));
            for (CmsPageContainer container : pageContainerList) {
                if (containerId.equals(container.getId())) {
                    for (CmsPage page : pageList) {
                        if (container.getPageId().equals(page.getId())) {
                            return page.getAliasTitle();
                        }
                    }
                }
            }
        }
        return null;
    }
}
