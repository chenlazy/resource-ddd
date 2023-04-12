package com.idanchuang.cms.server.interfaces.controller;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.idanchuang.cms.api.facade.ContainerComponentFacade;
import com.idanchuang.cms.api.request.ContainerComponentConditionReq;
import com.idanchuang.cms.api.response.ContainerComponentDTO;
import com.idanchuang.cms.server.application.service.ContainerComponentService;
import com.idanchuang.cms.server.domain.model.cms.ContainerComponent;
import com.idanchuang.cms.server.domain.model.cms.ContainerComponentCondition;
import com.idanchuang.component.base.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-02 16:48
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@RestController
@RequestMapping("/cms/container/component")
@Slf4j
public class ContainerComponentFacadeImpl implements ContainerComponentFacade {

    @Resource
    private ContainerComponentService containerComponentService;

    @Override
    public JsonResult<Boolean> insert(ContainerComponentDTO dto) {
        return JsonResult.success(containerComponentService.insert(convertOf(dto)));
    }

    @Override
    public JsonResult<Boolean> insertBatch(List<ContainerComponentDTO> dtoList) {
        List<Long> batch = containerComponentService.insertBatch(dtoList.stream().map(this::convertOf).collect(Collectors.toList()));
        return JsonResult.success(!CollectionUtils.isEmpty(batch));
    }

    LoadingCache<Long, ContainerComponent> loadingCache = Caffeine.newBuilder()
            .maximumSize(10_000)
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .refreshAfterWrite(1, TimeUnit.SECONDS)
            .build(key -> selectComponentById(key));

    @Override
    public JsonResult<ContainerComponentDTO> selectById(Long id) {
        ContainerComponent containerComponent = loadingCache.get(id);
        return JsonResult.success(containerComponent == null ? null : convertOf(containerComponent));
    }

    private ContainerComponent selectComponentById(Long id) {
        ContainerComponent containerComponent = containerComponentService.selectById(id);
        return containerComponent;
    }

    @Override
    public JsonResult<List<ContainerComponentDTO>> selectByCondition(ContainerComponentConditionReq condition) {
        List<ContainerComponent> dataList = containerComponentService.selectByCondition(convertOf(condition));
        return JsonResult.success(CollectionUtils.isEmpty(dataList) ? null : dataList.stream().map(this::convertOf).collect(Collectors.toList()));
    }

    private ContainerComponent convertOf(ContainerComponentDTO source) {

        return new ContainerComponent(source.getId(), source.getContainerId(), source.getComponentType(), source.getModelType(), source.getBizJson(), source.getModelJson(),
                source.getOperatorId(), null, null, source.getCreateTime(), source.getUpdateTime());
    }
    private ContainerComponentDTO convertOf(ContainerComponent source) {
        return ContainerComponentDTO.builder()
                .id(source.getId())
                .containerId(source.getContainerId())
                .componentType(source.getComponentType())
                .modelType(source.getModelType())
                .bizJson(source.getBizJson())
                .modelJson(source.getModelJson())
                .operatorId(source.getOperatorId())
                .createTime(source.getCreateTime())
                .updateTime(source.getUpdateTime())
                .build();
    }

    private ContainerComponentCondition convertOf(ContainerComponentConditionReq source) {
        return ContainerComponentCondition.builder()
                .pageNum(source.getPageNum())
                .pageSize(source.getPageSize())
                .containerIdList(source.getContainerIdList())
                .build();
    }
}
