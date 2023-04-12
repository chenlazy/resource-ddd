package com.idanchuang.cms.server.infrastructureNew.schedule;

import com.google.common.collect.Lists;
import com.idanchuang.cms.server.application.config.SystemConfig;
import com.idanchuang.cms.server.applicationNew.service.FeiShuService;
import com.idanchuang.cms.server.applicationNew.service.MasterplateService;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.Catalogue;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueId;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CataloguePageQueryForm;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueRepository;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.Masterplate;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateRepository;
import com.idanchuang.cms.server.infrastructureNew.schedule.model.PageNotice;
import com.idanchuang.component.base.page.PageData;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-03-07 19:18
 * @Desc: 核心页面空模版提醒脚本
 * @Copyright VTN Limited. All rights reserved.
 */
@Component
@Slf4j
@JobHandler(value = "corePageRemindJob")
public class CorePageRemindJob extends IJobHandler {

    private static final Integer LIMIT = 100;

    @Resource
    private CatalogueRepository catalogueRepository;

    @Resource
    private MasterplateRepository masterplateRepository;

    @Resource
    private MasterplateService masterplateService;

    @Resource
    private FeiShuService feiShuService;

    @Override
    public ReturnT<String> execute(String s) throws Exception {

        Integer totalCatalogue = catalogueRepository.queryTotalCatalogue();

        int pageTotal = (totalCatalogue % LIMIT == 0) ? totalCatalogue / LIMIT : totalCatalogue / LIMIT + 1;

        List<CatalogueId> corePageIdList = new ArrayList<>();
        try {
            String pageIdScope = SystemConfig.getInstance().getCorePageIdScope();
            corePageIdList = Arrays.stream(pageIdScope.split(",")).map(Long::valueOf).map(CatalogueId::new).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("corePageRemindJob parse pageId error, message:{}", e.getMessage(), e);
        }

        //查询核心页面模版信息
        List<Masterplate> masterplateList = masterplateRepository.getCmsPageListForValid(corePageIdList);
        Map<CatalogueId, List<Masterplate>> coreMasterplateListMap = masterplateList.stream().collect(Collectors.groupingBy(Masterplate::getCatalogueId));

        List<PageNotice> pageNotices = Lists.newArrayList();

        for (int pageNum = 1; pageNum <= pageTotal; pageNum++) {
            CataloguePageQueryForm queryForm = new CataloguePageQueryForm();
            queryForm.setCurrent((long)pageNum);
            queryForm.setSize((long)LIMIT);
            PageData<Catalogue> pageData = catalogueRepository.queryCatalogueByPage(queryForm);
            if (CollectionUtils.isEmpty(pageData.getRecords())) {
                break;
            }
            List<Catalogue> catalogues = pageData.getRecords();
            //批量查询目录列表信息
            List<CatalogueId> catalogueIds = catalogues.stream().map(Catalogue::getId).collect(Collectors.toList());
            List<Masterplate> pageListForActive = masterplateRepository.getCmsPageListForActive(catalogueIds);

            if (CollectionUtils.isEmpty(pageListForActive)) {
                continue;
            }

            Map<CatalogueId, List<Masterplate>> activePageMap = pageListForActive.stream().collect(Collectors.groupingBy(Masterplate::getCatalogueId));

            //遍历目录表，校验模版信息
            for (Catalogue catalogue : catalogues) {

                //如果是核心页面，校验是否存在生效中
                if (corePageIdList.contains(catalogue.getId())) {
                    List<Masterplate> masterplates = coreMasterplateListMap.get(catalogue.getId());
                    //不存在生效中模版，计入id进行通知
                    if (CollectionUtils.isEmpty(masterplates)) {
                        PageNotice pageNotice = new PageNotice();
                        pageNotice.setCatalogueId(catalogue.getId().getValue());
                        pageNotice.setAliasTitle(catalogue.getAliasTitle());
                        pageNotice.setOperatorId(catalogue.getOperatorId().getValue());
                        pageNotices.add(pageNotice);
                        continue;
                    }
                }

                //获取该目录下待生效和生效中的模版列表
                List<Masterplate> activeMasterplateList = activePageMap.get(catalogue.getId());

                //校验多模版直接是否存在空隙
                boolean exsitGap = masterplateService.checkMasterplateGap(activeMasterplateList);

                if (exsitGap) {
                    PageNotice pageNotice = new PageNotice();
                    pageNotice.setCatalogueId(catalogue.getId().getValue());
                    pageNotice.setAliasTitle(catalogue.getAliasTitle());
                    pageNotice.setOperatorId(catalogue.getOperatorId().getValue());
                    pageNotices.add(pageNotice);
                }
            }
        }

        //如果通知列表不为空，调用飞书服务进行通知
        feiShuService.noticeCard(pageNotices, "FSTemplate.json");
        return SUCCESS;
    }

}
