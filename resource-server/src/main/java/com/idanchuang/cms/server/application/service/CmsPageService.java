package com.idanchuang.cms.server.application.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.idanchuang.cms.server.application.config.SystemConfig;
import com.idanchuang.cms.server.application.remote.RemoteSsoService;
import com.idanchuang.cms.server.domain.model.cms.ActivityPage;
import com.idanchuang.cms.server.domain.model.cms.CmsPage;
import com.idanchuang.cms.server.domain.model.cms.CmsPageQuery;
import com.idanchuang.cms.server.domain.model.cms.CmsPageTemplate;
import com.idanchuang.cms.server.domain.model.cms.CmsPageTemplateList;
import com.idanchuang.cms.server.domain.repository.CmsPageRepository;
import com.idanchuang.cms.server.domain.repository.PageTemplateRepository;
import com.idanchuang.component.base.page.PageData;
import com.idanchuang.resource.server.infrastructure.common.constant.RedisBusinessKeyConstant;
import com.idanchuang.resource.server.infrastructure.utils.CacheUtil;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import com.idanchuang.sso.model.dto.system.UserDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-02 14:14
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Service
@Slf4j
public class CmsPageService {

    @Resource
    private RemoteSsoService remoteSsoService;
    @Resource
    private CmsPageRepository cmsPageRepository;
    @Resource
    private PageTemplateRepository pageTemplateRepository;
    @Resource
    private CacheUtil cacheUtil;

    public Boolean createCmsPage(CmsPage cmsPage) {
        return cmsPageRepository.saveCmsPage(cmsPage) > 0;
    }

    public Boolean updateCmsPage(CmsPage cmsPage) {
        return cmsPageRepository.updateCmsPage(cmsPage) > 0;
    }

    public Boolean batchCreateCmsPage(List<CmsPage> cmsPages) {
        return cmsPageRepository.batchSaveCmsPage(cmsPages) > 0;
    }

    public Boolean removeCmsPage(Integer id, Long operatorId) {
        return cmsPageRepository.removeCmsPage(id, operatorId);
    }

    public CmsPage selectCmsPage(CmsPageQuery cmsPageQuery) {

        if (null != cmsPageQuery.getId()) {
            return cmsPageRepository.selectCmsPageById(cmsPageQuery.getId());
        }
        return null;
    }

    public int getCmsPageCount(Integer pageSchemaId) {

        if (null != pageSchemaId) {
            return cmsPageRepository.getCmsPageCount(pageSchemaId);
        }

        return 0;
    }

    public List<CmsPage> getCmsPageList(Integer pageSchemaId) {

        if (null != pageSchemaId) {
            return cmsPageRepository.getCmsPageList(pageSchemaId);
        }
        return Lists.newArrayList();
    }

    public List<CmsPage> getCmsPageList(List<Integer> pageSchemaIdList) {
        if (CollectionUtils.isEmpty(pageSchemaIdList)) {
            return null;
        }

        return cmsPageRepository.getCmsPageList(pageSchemaIdList);
    }

    public PageData<CmsPageTemplate> getPageTemplateList(CmsPageTemplateList cmsPageTemplateList) {
        PageData<CmsPageTemplate> pageTemplateList = cmsPageRepository.getPageTemplateList(cmsPageTemplateList);

        List<CmsPageTemplate> records = pageTemplateList.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return new PageData<>(Lists.newArrayList(), pageTemplateList.getCurrent(), pageTemplateList.getSize(), 0);
        }

        List<Long> operatorIds = records.stream().map(p -> p.getOperatorId().longValue()).collect(Collectors.toList());
        List<UserDetailDTO> detailDTOS = remoteSsoService.getUsers(operatorIds);
        Map<Long, UserDetailDTO> usersMap = !CollectionUtils.isEmpty(detailDTOS) ?
                detailDTOS.stream().collect(Collectors.toMap(UserDetailDTO::getId, p -> p, (e1, e2) -> e1)) : null;

        records.forEach(p -> {
            UserDetailDTO detailDTO = null != usersMap ? usersMap.get(p.getOperatorId().longValue()) : null;
            p.setOperator(null != detailDTO ? detailDTO.getRealName() : "");
        });

        return new PageData<>(records, pageTemplateList.getCurrent(), pageTemplateList.getSize(), pageTemplateList.getTotal());
    }

    public Map<Long, List<ActivityPage>> queryActivityOnSubjectListBySpuId(Set<Long> spuId) {
        Map<Long, List<ActivityPage>> maps = Maps.newHashMap();
        for (Long m : spuId) {
            String infoKey = format(RedisBusinessKeyConstant.CMS_SUBJECT_BY_SPU_KEY, m);
            String info = cacheUtil.getForString(infoKey);

            List<CmsPage> cmsPages = JsonUtil.toListErrorNull(info, new TypeReference<List<CmsPage>>() {
            });
            List<ActivityPage> activityPages = Lists.newArrayList();
            if (null == info || cmsPages == null) {
                //无缓存走db
                cmsPages = cmsPageRepository.queryActivityPageBySpuId(m);
                cacheUtil.set(infoKey, JsonUtil.toJsonString(cmsPages), SystemConfig.getInstance().getQueryActivityOnSubjectListBySpuIdExpireTime(), TimeUnit.SECONDS);
                Map<Integer, CmsPage> pageMap = cmsPages.stream().collect(Collectors.toMap(CmsPage::getPageSchemaId, e -> e, (e1, e2) -> e1));
                List<CmsPage> collect = pageMap.values().stream().collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(collect)) {
                    collect.stream().forEach(u -> activityPages.add(this.cmsPage2ActivityPage(u)));
                }
            } else {
                //有缓存
                Map<Integer, CmsPage> pageMap = cmsPages.stream().collect(Collectors.toMap(CmsPage::getPageSchemaId, e -> e, (e1, e2) -> e1));
                List<CmsPage> collect = pageMap.values().stream().collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(collect)) {
                    collect.stream().forEach(u -> {
                        if (LocalDateTime.now().isAfter(u.getStartTime()) && LocalDateTime.now().isBefore(u.getEndTime())) {
                            activityPages.add(this.cmsPage2ActivityPage(u));
                        }
                    });
                }

            }
            if (CollectionUtils.isNotEmpty(activityPages)) {
                maps.put(m, activityPages);
            }
        }
        return maps;
    }

    public void deleteCache(LocalDateTime startTime, LocalDateTime endTime, List<Long> allGoodsIdList) {
        if (startTime != null && endTime != null && LocalDateTime.now().isBefore(endTime) && CollectionUtils.isNotEmpty(allGoodsIdList)) {
            List<String> keys = Lists.newArrayList();
            allGoodsIdList.stream().forEach(m -> {
                String infoKey = format(RedisBusinessKeyConstant.CMS_SUBJECT_BY_SPU_KEY, m);
                keys.add(infoKey);
            });
            try {
                cacheUtil.deleteBatch(keys);
            } catch (Exception e) {
                log.error("deleteCache error keys:{}", keys.toString());
            }
        }
    }

    /**
     * 修改页面的生效时间
     * @param id            页面实例ID
     * @param startTime     生效时间
     * @param operateId     操作人ID
     * @return  true：成功；false：失败
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStartTimeById(Integer id, LocalDateTime startTime, Long operateId) {
        boolean result = cmsPageRepository.updateStartTimeById(id, startTime, operateId);
        if (result) {
            CmsPage cmsPage = cmsPageRepository.selectCmsPageById(id);
            if (cmsPage != null) {
                pageTemplateRepository.buildPageVersion(cmsPage.getPageSchemaId());
            }
        }
        return result;
    }

    private ActivityPage cmsPage2ActivityPage(CmsPage cmsPage) {
        ActivityPage activityPage = new ActivityPage();
        activityPage.setPageId(cmsPage.getPageSchemaId().longValue());
        activityPage.setStartTime(cmsPage.getStartTime());
        activityPage.setEndTime(cmsPage.getEndTime());
        return activityPage;
    }

}
