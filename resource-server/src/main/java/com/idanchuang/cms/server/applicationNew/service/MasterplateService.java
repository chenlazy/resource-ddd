package com.idanchuang.cms.server.applicationNew.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Maps;
import com.idanchuang.cms.server.application.constant.RedisKeyConstant;
import com.idanchuang.cms.server.application.enums.ErrorEnum;
import com.idanchuang.cms.server.domainNew.model.cms.aggregation.render.PageRender;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.Catalogue;
import com.idanchuang.cms.server.domainNew.model.cms.component.Component;
import com.idanchuang.cms.server.domainNew.model.cms.container.Container;
import com.idanchuang.cms.server.domainNew.model.cms.container.ContainerCode;
import com.idanchuang.cms.server.domainNew.model.cms.container.ContainerFactory;
import com.idanchuang.cms.server.domainNew.model.cms.container.ContainerRepository;
import com.idanchuang.cms.server.domainNew.model.cms.container.ContainerStatus;
import com.idanchuang.cms.server.domainNew.model.cms.external.equity.EquityKey;
import com.idanchuang.cms.server.domainNew.model.cms.external.equity.EquityService;
import com.idanchuang.cms.server.domainNew.model.cms.external.goods.GoodsTagService;
import com.idanchuang.cms.server.domainNew.model.cms.external.goodsPrice.GoodsId;
import com.idanchuang.cms.server.domainNew.model.cms.external.goodsPrice.GoodsPrice;
import com.idanchuang.cms.server.domainNew.model.cms.external.niche.NicheService;
import com.idanchuang.cms.server.domainNew.model.cms.external.selectInfo.SelectInfo;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.Masterplate;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateFactory;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateId;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateRepository;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateUpsertForm;
import com.idanchuang.cms.server.domainNew.shard.OperatorId;
import com.idanchuang.cms.server.domainNew.shard.parse.ContainerData;
import com.idanchuang.cms.server.domainNew.shard.parse.PageContainerData;
import com.idanchuang.component.base.exception.core.ExDefinition;
import com.idanchuang.component.base.exception.core.ExType;
import com.idanchuang.component.base.exception.exception.BusinessException;
import com.idanchuang.resource.server.infrastructure.utils.CacheUtil;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-16 15:16
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Service
@Slf4j
public class MasterplateService {

    @Resource
    private MasterplateRepository masterplateRepository;

    @Resource
    private ContainerService containerService;

    @Resource
    private ContainerRepository containerRepository;

    @Resource
    private CatalogueService catalogueService;

    @Resource
    private ComponentService componentService;

    @Resource
    private GoodsTagService goodsTagService;

    @Resource
    private GoodsPriceService goodsPriceService;

    @Resource
    private PageRenderService pageRenderService;

    @Resource
    private NicheService nicheService;

    @Resource
    private MasterplateSnapshotService masterplateSnapshotService;

    @Resource
    private EquityService equityService;

    @Resource
    private CacheUtil cacheUtil;

    /**
     * 删除模版
     *
     * @param masterplateId
     * @param operatorId
     * @param needRemind
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public PageRender removeMasterplate(MasterplateId masterplateId, OperatorId operatorId, Boolean needRemind) {

        //查询模版信息
        Masterplate masterplate = masterplateRepository.getMasterplateById(masterplateId);

        if (null == masterplate) {
            throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.MASTERPLATE_EXIST_ERROR.getCode(),
                    ErrorEnum.MASTERPLATE_EXIST_ERROR.getMsg()));
        }

        //判断是否是最后一个模版
        if (needRemind) {

            int num = masterplateRepository.countByCatalogueId(masterplate.getCatalogueId());

            if (num <= 1) {
                throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.PAGE_VALID_ERROR.getCode(),
                        ErrorEnum.PAGE_VALID_ERROR.getMsg()));
            }
        }

        //失效模版标签，先失效后删除
        goodsTagService.expireOldTag(masterplateId);

        //删除模版
        masterplateRepository.remove(masterplateId, operatorId);

        //获取当前生效模板
        return needRemind ? pageRenderService.generateCurrentVersion(masterplate.getCatalogueId()) : null;
    }

    /**
     * 发布模版
     *
     * @param masterplateId
     * @param startTime
     * @param operatorId
     * @return
     */
    public PageRender publishMasterplate(MasterplateId masterplateId, LocalDateTime startTime, OperatorId operatorId) {

        //查询模版信息
        Masterplate masterplate = masterplateRepository.getMasterplateById(masterplateId);

        if (null == masterplate) {
            throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.MASTERPLATE_EXIST_ERROR.getCode(),
                    ErrorEnum.MASTERPLATE_EXIST_ERROR.getMsg()));
        }

        //更新生效时间
        masterplateRepository.updateStartTimeById(masterplateId, startTime, operatorId);

        //重新构建页面聚合信息
        return pageRenderService.generateCurrentVersion(masterplate.getCatalogueId());
    }

    /**
     * 更新或新增模版
     *
     * @param upsertForm
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public PageRender upsertMasterplate(MasterplateUpsertForm upsertForm) {

        //获取页面容器数据
        PageContainerData pageContainerData = upsertForm.parsePageStyle();

        //更新目录信息
        Catalogue catalogue = catalogueService.updateCatalogue(upsertForm);

        //创建模版对象
        Masterplate masterplate = MasterplateFactory.createMasterplate(catalogue, upsertForm, upsertForm.parsePageConfig());

        //创建或更新目录下的模版记录
        upsertMasterplate(masterplate);

        //圈选人群设置
        if (!CollectionUtils.isEmpty(upsertForm.getSelectInfoList())) {
            Map<Integer, List<SelectInfo>> selectMap = upsertForm.getSelectInfoList().stream().collect(Collectors.groupingBy(SelectInfo::getSelectType));
            for (Map.Entry<Integer, List<SelectInfo>> entry : selectMap.entrySet()) {
                if (!CollectionUtils.isEmpty(entry.getValue())) {
                    this.setPageAndSelectId(masterplate.getId().getValue(), entry.getKey(), entry.getValue().stream().map(SelectInfo::getSelectId).collect(Collectors.toSet()));
                }
            }
        }

        //查询模版下面原有的容器列表
        List<Container> oldContainers = Lists.newArrayList();
        if (masterplate.getId() != null) {
            oldContainers = containerRepository.queryContainerList(masterplate.getId());
        }

        //记录普通商品信息
        List<GoodsId> goodsIds = new ArrayList<>(512);
        //记录权益商品信息
        Map<EquityKey, List<GoodsId>> equityIdMap = Maps.newHashMap();

        //商品日常价格
        List<GoodsPrice> goodsPrices = new ArrayList<>();
        //模版组件信息
        List<Component> fillComponents = Lists.newArrayList();
        //遍历容器列表
        for (int m = 0; m < pageContainerData.getCompDataList().size(); m++) {

            ContainerData containerData = pageContainerData.getCompDataList().get(m);

            //创建容器对象
            Container container = ContainerFactory.createContainer(masterplate.getId(),
                    StringUtils.isNotEmpty(containerData.getContainerCode()) ? new ContainerCode(containerData.getContainerCode()) : null,
                    containerData.getContainerName(),
                    ContainerStatus.VALID,
                    null, JsonUtil.toJsonString(upsertForm.parseContainerConfig(containerData)),
                    masterplate.getOperatorId());

            //保存或更新容器记录
            containerService.upsertContainer(container, m, oldContainers);

            //填充完整的组件信息
            fillComponents = componentService.fillComponents(container.getComponents(), container.getId(), catalogue.getPageCode(),
                    masterplate.getId(), container.getOperatorId(), goodsIds, goodsPrices, equityIdMap);

            //批量保存或更新组件信息
            componentService.batchUpsertComponent(fillComponents, container.getId());
        }

        //批量保存商品日常价信息
        goodsPriceService.batchUpsertGoodsPrice(goodsPrices);

        //权益id校验前置
        equityService.checkEquityValid(equityIdMap.keySet());

        //逻辑移动 页面失效和创建标签
        Map<EquityKey, Long> equityTagMap = goodsTagService.pageExpireAndCreateTag(masterplate,goodsIds, catalogue.getPageId(),equityIdMap);

        //深拷贝 避开下方创建快照引用传递
        MasterplateId id = masterplate.getId();

        //生成当前聚合页面信息
        PageRender pageRender = pageRenderService.generateCurrentVersion(catalogue.getId());

        //生成快照模版
        masterplateSnapshotService.createSnapshot(upsertForm, masterplate, pageContainerData.getCompDataList(), fillComponents);

        //如果存在资源位，走资源位流程
        nicheService.visible(upsertForm.getNicheIds());

        //如果存在权益组件 同步权益id与标签id到会员服务
        equityService.syncEquityTag(equityTagMap, id);

        return pageRender;
    }

    /**
     * 设置模版和活动关联
     *
     * @param pageId     模版id
     * @param selectType 圈选类型
     * @param selectIds  圈选id集合
     */
    public void setPageAndSelectId(Long pageId, Integer selectType, Set<Integer> selectIds) {
        try {
            String pageKey = format(RedisKeyConstant.SUBJECT_PAGE_TO_SELECT, selectType, pageId);
            String pageOld = cacheUtil.getForString(pageKey);
            cacheUtil.set(pageKey, JsonUtil.toJsonString(selectIds));
            List<Integer> pageListOld = StringUtils.isEmpty(pageOld) ? Lists.newArrayList() : JsonUtil.toList(pageOld, new TypeReference<List<Integer>>() {
            });
            //模版历史关联活动id集合
            Map<Integer, Integer> mapOld = pageListOld.stream().collect(Collectors.toMap(x -> x, x -> x, (s1, s2) -> s1));
            //模版新增关联活动id集合
            Map<Integer, Integer> mapNew = Maps.newHashMap();
            for (Integer activityId : selectIds) {
                if (mapOld.containsKey(activityId)) {
                    mapOld.remove(activityId);
                } else {
                    mapNew.put(activityId, activityId);
                }
            }
            //新增关联活动id集合 活动id关联模版信息做增量缓存同步
            for (Map.Entry<Integer, Integer> entry : mapNew.entrySet()) {
                String key = format(RedisKeyConstant.SUBJECT_SELECT_TO_PAGE, selectType, entry.getKey());
                String forString = cacheUtil.getForString(key);
                List<Integer> list = StringUtils.isEmpty(forString) ? Lists.newArrayList() : JsonUtil.toList(forString, new TypeReference<List<Integer>>() {
                });
                list.add(pageId.intValue());
                cacheUtil.set(key, JsonUtil.toJsonString(list));
            }
            //删除关联活动id集合 活动id关联模版信息做减量缓存同步
            for (Map.Entry<Integer, Integer> entry : mapOld.entrySet()) {
                String key = format(RedisKeyConstant.SUBJECT_SELECT_TO_PAGE, selectType, entry.getKey());
                String forString = cacheUtil.getForString(key);
                List<Integer> list = StringUtils.isEmpty(forString) ? Lists.newArrayList() : JsonUtil.toList(forString, new TypeReference<List<Integer>>() {
                });
                List<Integer> collect = list.stream().filter(u -> !u.equals(pageId.intValue())).collect(Collectors.toList());
                cacheUtil.set(key, JsonUtil.toJsonString(collect));
            }
        } catch (Exception e) {
            log.error("setPageAndActivity cache error pageId:{} selectType:{} selectIds:{} e:{}", pageId, selectType, JsonUtil.toJsonString(selectIds), e);
        }
    }


    /**
     * 创建模版记录
     *
     * @param masterplate
     * @return
     */
    public void upsertMasterplate(Masterplate masterplate) {

        if (null != masterplate && masterplate.getId() != null) {
            masterplateRepository.updateMasterplate(masterplate);
        } else {
            masterplateRepository.storeMasterplate(masterplate);
        }
    }

    /**
     * 校验多模版之间是否存在时间间隙
     * @param masterplates
     * @return
     */
    public boolean checkMasterplateGap(List<Masterplate> masterplates) {

        if (CollectionUtils.isEmpty(masterplates) || masterplates.size() <= 1) {
            return false;
        }

        //对模版列表根据开始时间正序排序
        masterplates.sort(Comparator.comparing(Masterplate::getStartTime));

        //获取模版列表的最大值和最小值
        Masterplate firstMasterplate = masterplates.get(0);
        Masterplate lastMasterplate = masterplates.get(0);

        //初始化结束时间
        LocalDateTime maxEndTime = firstMasterplate.getEndTime();

        for (int m = 1; m < masterplates.size(); m ++) {
            Masterplate masterplate = masterplates.get(m);

            if (maxEndTime != null && masterplate.getEndTime() != null && masterplate.getEndTime().isAfter(maxEndTime)) {
                lastMasterplate = masterplate;
            } else if (maxEndTime != null && masterplate.getEndTime() == null) {
                lastMasterplate = masterplate;
            }
        }

        //如果第一个模版和最后一个模版是一个，直接返回
        if (firstMasterplate.getId().equals(lastMasterplate.getId()) || firstMasterplate.getEndTime() == null) {
            return false;
        }

        boolean checkGap = true;

        //从时间最小的第一个模版遍历，寻找下一个连续的模版
        for (Masterplate masterplate : masterplates) {

            if (masterplate.getId().equals(firstMasterplate.getId())) {
                continue;
            }
            if (masterplate.getStartTime().isBefore(firstMasterplate.getEndTime()) || masterplate.getStartTime().equals(firstMasterplate.getEndTime())) {
                //结束时间是长期有效直接跳出
                if (masterplate.getEndTime() == null) {
                    checkGap = false;
                    break;
                } else if (masterplate.getEndTime().isAfter(firstMasterplate.getEndTime())) {
                    firstMasterplate = masterplate;
                    //如果遍历到最后一个也直接跳出
                    if (firstMasterplate.getId().equals(lastMasterplate.getId())) {
                        checkGap = false;
                        break;
                    }
                }
            }

        }

        return checkGap;
    }

}
