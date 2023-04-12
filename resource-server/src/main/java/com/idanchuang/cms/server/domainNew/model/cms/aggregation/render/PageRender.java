package com.idanchuang.cms.server.domainNew.model.cms.aggregation.render;

import com.google.common.collect.Lists;
import com.idanchuang.cms.server.application.enums.ErrorEnum;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueId;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPageId;
import com.idanchuang.cms.server.domainNew.model.cms.container.Container;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.GoodsEnable;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateId;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateShareForm;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.ShareFlag;
import com.idanchuang.cms.server.domainNew.shard.PlatformCode;
import com.idanchuang.component.base.exception.core.ExDefinition;
import com.idanchuang.component.base.exception.core.ExType;
import com.idanchuang.component.base.exception.exception.BusinessException;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-23 11:00
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Value
public class PageRender {

    /**
     * 目录id
     */
    private CatalogueId catalogueId;

    /**
     * 模版id
     */
    private MasterplateId masterplateId;

    /**
     * 页面id
     */
    private ClientPageId clientPageId;

    /**
     * 页面标题
     */
    private String pageTitle;

    /**
     * 后台标题
     */
    private String backEndTitle;

    /**
     * 别名
     */
    private String aliasTitle;

    /**
     * 平台类型
     */
    private PlatformCode platform;

    /**
     * 是否分享 1-是 0-否
     */
    private ShareFlag shareFlag;

    /**
     * 当前版本
     */
    private String version;

    /**
     * 下个版本开始时间
     */
    @NonFinal
    private LocalDateTime nextStartTime;

    /**
     * 活动开始时间
     */
    private LocalDateTime startTime;

    /**
     * 活动结束时间
     */
    private LocalDateTime endTime;

    /**
     * 商品自动下架标记 0不下架 1下架'
     */
    private GoodsEnable goodsEnable;

    /**
     * 页面样式信息
     */
    private String pageStyle;

    /**
     * 容器列表
     */
    @NonFinal
    private List<ContainerRender> containers;

    /**
     * 分享信息
     */
    private List<MasterplateShareForm> shareList;

    /**
     * 版本结束时间
     */
    private LocalDateTime versionEndTime;


    /**
     * 版本是否失效
     * @return   false：未失效，true：已失效
     */
    public boolean isOver() {
        return versionEndTime != null && versionEndTime.compareTo(LocalDateTime.now()) <= 0;
    }

    /**
     * 是否为页面在当前时间内投放的样式
     * @return  true:是；false:否
     */
    public boolean isCurrent() {

        if (endTime == null) {
            return LocalDateTime.now().isAfter(startTime);
        }

        return LocalDateTime.now().compareTo(startTime) >= 0 && LocalDateTime.now().compareTo(endTime) < 0;
    }

    public void setNextStartTime(LocalDateTime nextStartTime) {
        this.nextStartTime = nextStartTime;
    }

    /**
     * 添加容器列表
     * @param containers
     */
    public void addContainers(List<Container> containers) {

        if (CollectionUtils.isEmpty(containers)) {
            return;
        }

        for (Container container : containers) {
            if (!this.getMasterplateId().equals(container.getMasterplateId())) {
                throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.CONTAINER_RELATION_ERROR.getCode(),
                        ErrorEnum.CONTAINER_RELATION_ERROR.getMsg()));
            }
        }

        this.containers = containers.stream().map(p -> new ContainerRender(p.getId(), p.getContainerCode(),
                p.getPageStyle(), Lists.newArrayList())).collect(Collectors.toList());
    }


    public MasterplateShareForm getShareInfoByLevel(Integer level) {

        if (null == level) {
            return null;
        }

        if (CollectionUtils.isEmpty(this.shareList)) {
            return null;
        }

        MasterplateShareForm shareForm = this.getShareList().get(0);

        if (shareForm.getShareScope() == null || shareForm.getShareScope() == 0) {
            return shareForm;
        }

        for (MasterplateShareForm p : this.getShareList()) {
            if (p.getLevel().equals(level)) {
               return p;
            }
        }
        return null;
    }

    /**
     * 根据时间返回当前页面状态
     * @param dateTime
     * @return
     */
    public PageRenderStatus getStatusByTime(LocalDateTime dateTime) {

        if (null == dateTime) {
            return null;
        }

        if (endTime == null) {
            return dateTime.isAfter(startTime) ? PageRenderStatus.PAGE_STATUS_PROCESS :
                    PageRenderStatus.PAGE_STATUS_NO_START;
        }

        if (dateTime.isAfter(startTime)) {
            return dateTime.isBefore(endTime) ? PageRenderStatus.PAGE_STATUS_PROCESS : PageRenderStatus.PAGE_STATUS_END;
        } else {
            return PageRenderStatus.PAGE_STATUS_NO_START;
        }
    }
}
