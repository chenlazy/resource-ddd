package com.idanchuang.cms.server.application.service.aggregation;

import com.idanchuang.cms.server.application.service.CmsCoreBuildService;
import com.idanchuang.cms.server.domain.model.cms.CmsCoreCreateOrUpdate;
import com.idanchuang.cms.server.domain.model.cms.niche.NicheService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;


//页面实例聚合服务
@Service
public class PageExampleAggregationService {


    @Resource
    private CmsCoreBuildService cmsCoreBuildService;

    @Resource
    private NicheService nicheService;

    //页面实例创建编辑流程
    @Transactional
    public boolean upsert (CmsCoreCreateOrUpdate cmsCoreCreateOrUpdate) {

        boolean upsertResult = cmsCoreBuildService.createOrUpdatePageContainer(cmsCoreCreateOrUpdate);


        if(!upsertResult){
            return false;
        }

        //区分判断是否资源位
        //包含资源位走启用流程
        if(!CollectionUtils.isEmpty(cmsCoreCreateOrUpdate.getNicheIds())){
            nicheService.visible(cmsCoreCreateOrUpdate.getNicheIds());
        }
        return true;
    }


}
