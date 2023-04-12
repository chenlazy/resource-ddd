package com.idanchuang.cms.server.interfaces.controller;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.idanchuang.cms.api.facade.LuckyComponentFacade;
import com.idanchuang.cms.server.application.config.SystemConfig;
import com.idanchuang.cms.server.applicationNew.service.ComponentService;
import com.idanchuang.cms.server.domainNew.model.cms.container.Container;
import com.idanchuang.cms.server.domainNew.model.cms.container.ContainerId;
import com.idanchuang.cms.server.domainNew.model.cms.container.ContainerRepository;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.Masterplate;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateId;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateRepository;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.resource.server.infrastructure.common.constant.RedisBusinessKeyConstant;
import com.idanchuang.resource.server.infrastructure.utils.CacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * @author lei.liu
 * @date 2021/10/9
 */
@RestController
@RequestMapping("/cms/lucky/component")
@Slf4j
public class LuckyComponentFacadeImpl implements LuckyComponentFacade {

    @Resource
    private MasterplateRepository masterplateRepository;

    @Resource
    private ContainerRepository containerRepository;

    @Resource
    private ComponentService componentService;

    @Resource
    private CacheUtil cacheUtil;



    LoadingCache<Long, String> loadingCache = Caffeine.newBuilder()
            .maximumSize(10_000)
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .refreshAfterWrite(1, TimeUnit.SECONDS)
            .build(key -> getLuckyPageByActivity(key));

    /**
     * 根据抽奖活动ID，获取关联的专题页别名
     * 若关联多个，返回最新的一个
     * @param activityId    抽奖活动ID
     * @return  专题别名
     */
    @Override
    public JsonResult<String> getLuckyComponentPageId(Long activityId) {
        String aliasTitle = loadingCache.get(activityId);
        return JsonResult.success(aliasTitle);
    }

    private String getLuckyPageByActivity(Long activityId) {

        String infoKey = format(RedisBusinessKeyConstant.CMS_SUBJECT_BY_ACTIVITY_KEY, activityId);
        String result = cacheUtil.getForString(infoKey);

        if(!StringUtils.isEmpty(result)){
            return result;
        }

        List<Masterplate> masterplates = masterplateRepository.getMasterplateListForValid();

        List<MasterplateId> pageIds = masterplates.stream().map(Masterplate::getId).collect(Collectors.toList());

        List<Container> containers = containerRepository.queryContainerByMasterplateIds(pageIds);

        if (CollectionUtils.isEmpty(containers)) {
            return null;
        }

        List<ContainerId> containerIds = containers.stream().map(Container::getId).collect(Collectors.toList());

        result = componentService.getLuckyComponentPageId(activityId, containerIds);

        //不为空设置缓存
        if (null != result) {
            cacheUtil.set(infoKey, result, SystemConfig.getInstance().getQueryActivityOnSubjectListByActivityIdExpireTime(), TimeUnit.SECONDS);
        }

        return result;
    }
}
