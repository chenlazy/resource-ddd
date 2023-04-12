package com.idanchuang.cms.server.interfaces.feign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.idanchuang.cms.api.feign.PageFeignClient;
import com.idanchuang.cms.api.request.PageHistoryDiffReq;
import com.idanchuang.cms.api.response.PageDiffDTO;
import com.idanchuang.cms.server.application.config.SystemConfig;
import com.idanchuang.cms.server.applicationNew.service.MasterplateService;
import com.idanchuang.cms.server.applicationNew.service.PageRenderService;
import com.idanchuang.cms.server.domainNew.model.cms.aggregation.render.PageRender;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.Catalogue;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueId;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueRepository;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueType;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.PageCode;
import com.idanchuang.cms.server.domainNew.model.cms.container.Container;
import com.idanchuang.cms.server.domainNew.model.cms.container.ContainerFactory;
import com.idanchuang.cms.server.domainNew.model.cms.container.ContainerRepository;
import com.idanchuang.cms.server.domainNew.model.cms.container.ContainerStatus;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.Masterplate;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateId;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateRepository;
import com.idanchuang.cms.server.infrastructureNew.util.BasicDiff;
import com.idanchuang.cms.server.interfaces.assember.EffectImproveAssembler;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-03-18 17:07
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@RestController
@RequestMapping("/page/feign")
@Slf4j
public class PageFeignClientImpl implements PageFeignClient {

    @Resource
    private MasterplateRepository masterplateRepository;

    @Resource
    private CatalogueRepository catalogueRepository;

    @Resource
    private ContainerRepository containerRepository;

    @Resource
    private MasterplateService masterplateService;

    @Resource
    private PageRenderService pageRenderService;

    @Override
    public JsonResult<PageDiffDTO> comparePageJsonDiff(PageHistoryDiffReq diffReq) {


        PageDiffDTO pageDiffDTO = new PageDiffDTO();

        if (StringUtils.isBlank(diffReq.getPageCode()) && (null == diffReq.getPageId() || diffReq.getPageId() <= 0) && null == diffReq.getMasterplateId()) {
            pageDiffDTO.setDiffMessage("请检查入参的准确性");
            return JsonResult.success(pageDiffDTO);
        }

        Catalogue catalogue = null;
        if (StringUtils.isNotBlank(diffReq.getPageCode())) {
            catalogue = catalogueRepository.getCatalogueByCode(new PageCode(diffReq.getPageCode()), CatalogueType.CLIENT_PAGE);
        } else if (null != diffReq.getPageId() && diffReq.getPageId() > 0){
            catalogue = catalogueRepository.getCatalogueById(new CatalogueId(diffReq.getPageId()));
        }

        //定义新样式数组
        List<Container> newContainerList = Lists.newArrayList();

        //记录当前生效的模版id
        MasterplateId masterplateId = null != diffReq.getMasterplateId() && diffReq.getMasterplateId() > 0 ? new MasterplateId(diffReq.getMasterplateId()) : null;

        if (null != masterplateId) {
            //根据模版id获取模版数据
            Masterplate masterplate = masterplateRepository.getMasterplateById(masterplateId);

            if (null == masterplate) {
                pageDiffDTO.setDiffMessage("当前模版不存在");
                return JsonResult.success(pageDiffDTO);
            }

            newContainerList = containerRepository.queryContainerList(masterplate.getId());

        } else if (null != catalogue) {
            //获取当前页面的容器
            PageRender currentPageStyle = pageRenderService.getCurrentPageStyle(catalogue.getId());

            if (null == currentPageStyle) {
                pageDiffDTO.setDiffMessage("当前页面不存在生效中的模版");
                return JsonResult.success(pageDiffDTO);
            }
            //记录当前模版id
            masterplateId = currentPageStyle.getMasterplateId();

            //添加新容器列表
            if (!CollectionUtils.isEmpty(currentPageStyle.getContainers())) {
                List<Container> finalNewContainerList = newContainerList;
                currentPageStyle.getContainers().forEach(p -> {
                    Container container = ContainerFactory.createContainer(null, p.getContainerCode(), "", ContainerStatus.VALID,
                            null, p.getStyleContent(), null);
                    container.setContainerId(p.getContainerId());
                    finalNewContainerList.add(container);
                });
            }
        }

        if (CollectionUtils.isEmpty(newContainerList) || null == masterplateId) {
            pageDiffDTO.setDiffMessage("当前页面不存在容器信息");
            return JsonResult.success(pageDiffDTO);
        }

        //获取当前模版的上一次修改数据记录
        List<Masterplate> masterplateSnapList = masterplateRepository.getMasterplateSnapList(masterplateId);

        if (CollectionUtils.isEmpty(masterplateSnapList) || masterplateSnapList.size() <= 1) {
            pageDiffDTO.setDiffMessage("当前模版不存在历史数据");
            return JsonResult.success(pageDiffDTO);
        }

        //获取快照的第一个历史数据
        Masterplate masterplate = masterplateSnapList.get(1);

        List<Container> oldContainerList = containerRepository.querySnapContainerList(masterplate.getId());

        BasicDiff basicDiff = new BasicDiff();

        Map<String, Object> oldContainerMap = new HashMap<>();

        for (int i = 0; i < oldContainerList.size(); i++) {
            Container container = oldContainerList.get(i);
            JSONObject oldContainer = JSON.parseObject(container.getPageStyle());
            oldContainerMap.put("容器" + (i + 1), oldContainer);
        }

        Map<String, Object> newContainerMap = new HashMap<>();

        for (int i = 0; i < newContainerList.size(); i++) {
            Container p = newContainerList.get(i);
            newContainerMap.put("容器" + (i + 1), JSON.parseObject(p.getPageStyle()));
        }

        JSONObject diffOld = JSON.parseObject(JsonUtil.toJsonString(oldContainerMap));

        JSONObject diffNew = JSON.parseObject(JsonUtil.toJsonString(newContainerMap));

        BasicDiff.DiffResult diffResult = basicDiff.compareObject(diffOld, diffNew, "", 0);

        return JsonResult.success(EffectImproveAssembler.entityToDTO(diffResult));
    }

    @Override
    public JsonResult<String> checkEmptyPage(Integer pageId) {

        if (pageId <= 0) {
            return JsonResult.success("参数错误");
        }

        List<Masterplate> masterplateList = masterplateRepository.getMasterplateList(new CatalogueId(pageId));

        boolean checkGap = masterplateService.checkMasterplateGap(masterplateList);

        if (checkGap) {
            return JsonResult.success("页面id：" + pageId + "，多个模版之间存在时间间隙，请检查");
        }

        return JsonResult.success("页面模版配置正常");
    }

    @Override
    public JsonResult<String> queryCompAddress(String keyword) {

        Map<String, String> compAddressMap = new HashMap<>();
        try {
            String compAddressInfo = SystemConfig.getInstance().getCompAddressInfo();
            compAddressMap = JsonUtil.toMap(compAddressInfo, Map.class);
        } catch (Exception e) {
            log.info("queryCompAddress parse compAddress error, e:{}", e.getMessage(), e);
        }

        if (CollectionUtils.isEmpty(compAddressMap)) {
            return JsonResult.success("配置信息为空");
        }
        String addressInfo = "";
        for (Map.Entry<String, String> entry : compAddressMap.entrySet()) {
            if (entry.getKey().contains(keyword)) {
                addressInfo = entry.getKey() + "说明文档地址：" + entry.getValue();
            }
        }

        if (StringUtils.isEmpty(addressInfo)) {
            addressInfo = "未找到对应组件的说明地址，请检查关键字是否正确";
        }

        return JsonResult.success(addressInfo);
    }
}
