package com.idanchuang.cms.server.domainNew.model.cms.catalogue;

import com.idanchuang.cms.server.application.enums.ErrorEnum;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPageId;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.PageCode;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.Masterplate;
import com.idanchuang.cms.server.domainNew.shard.OperatorId;
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
 * @Date: 2021-12-13 10:40
 * @Desc: 目录领域类
 * @Copyright VTN Limited. All rights reserved.
 */
@Value
public class Catalogue {

    /**
     * 目录编号
     */
    @NonFinal
    private CatalogueId id;

    /**
     * 目录名称 (page_name)
     */
    private String catalogueName;

    /**
     * 页面code
     */
    @NonFinal
    private PageCode pageCode;

    /**
     * 操作人id
     */
    private OperatorId operatorId;

    /**
     * 页面id （tag_id）
     */
    private ClientPageId pageId;

    /**
     * 目录类型 （page_type）
     */
    @NonFinal
    private CatalogueType catalogueType;

    /**
     * 目录状态
     */
    private CatalogueStatus catalogueStatus;

    /**
     * 目录别名
     */
    private String aliasTitle;

    /**
     * 平台类型
     */
    private PlatformCode platform;

    /**
     * 目录额外信息
     */
    private CatalogueExtra extra;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 设置目录id
     *
     * @param catalogueId
     */
    public void setCatalogueId(CatalogueId catalogueId) {
        this.id = catalogueId;
    }

    /**
     * 生产别名
     *
     * @param platformCode
     * @return
     */
    public String generateAliasTitle(PlatformCode platformCode) {

        if (null == platformCode) {
            return "";
        }
        String platform = platformCode.getDesc();
        if (platformCode.getDesc().contains("_")) {
            platform = platform.substring(0, platformCode.getDesc().indexOf("_"));
        }
        return platform + "_" + this.id.getValue();
    }

    /**
     * 获取优先展示的模版
     *
     * @return
     */
    public Masterplate fetchPriorityMasterplate(List<Masterplate> masterplateList) {

        if (CollectionUtils.isEmpty(masterplateList)) {
            return null;
        }
        //校验是否是该目录下的模版
        long count = masterplateList.stream().filter(p -> !p.getCatalogueId().sameValueAs(this.id)).count();
        if (count > 0) {
            throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.MASTERPLATE_CONTAINER_ERROR.getCode(),
                    ErrorEnum.MASTERPLATE_CONTAINER_ERROR.getMsg()));
        }

        //过滤出生效中的模版信息
        List<Masterplate> masterplates = masterplateList.stream().filter(e -> (e.getStartTime().compareTo(LocalDateTime.now()) <= 0)
                && (e.getEndTime() == null || e.getEndTime().compareTo(LocalDateTime.now()) > 0)).collect(Collectors.toList());

        //如果生效中为空就获取模版id最大的那个模版
        if (CollectionUtils.isEmpty(masterplates)) {
            masterplateList.sort((o1, o2) -> Long.compare(o2.getId().getValue(), o1.getId().getValue()));
            return masterplateList.get(0);
        } else {
            masterplates.sort((o1, o2) -> {
                if (o2.getStartTime().equals(o1.getStartTime())) {
                    return Long.compare(o2.getId().getValue(), o1.getId().getValue());
                }
                return o2.getStartTime().compareTo(o1.getStartTime());
            });
            return masterplates.get(0);
        }
    }

    /**
     * 改变页面类型 常态频道页面<->活动页面
     *
     * @param pageCode
     */
    public void changeType(PageCode pageCode) {
        if (CatalogueType.CLIENT_PAGE.equals(catalogueType)) {
            catalogueType = CatalogueType.SUBJECT;
            this.pageCode = null;
        } else if (CatalogueType.SUBJECT.equals(catalogueType)) {
            catalogueType = CatalogueType.CLIENT_PAGE;
            this.pageCode = pageCode;
        } else {
            throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.CATALOGUE_TYPE_EXIST_ERROR.getCode(),
                    ErrorEnum.CATALOGUE_TYPE_EXIST_ERROR.getMsg()));
        }
    }
}
