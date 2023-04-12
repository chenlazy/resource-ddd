package com.idanchuang.cms.server.domainNew.model.cms.masterplate;

import com.idanchuang.cms.server.application.constant.PageStyleConstant;
import com.idanchuang.cms.server.application.enums.ErrorEnum;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueId;
import com.idanchuang.cms.server.domainNew.model.cms.external.niche.NicheId;
import com.idanchuang.cms.server.domainNew.model.cms.external.selectInfo.SelectInfo;
import com.idanchuang.cms.server.domainNew.shard.OperatorId;
import com.idanchuang.cms.server.domainNew.shard.PlatformCode;
import com.idanchuang.cms.server.domainNew.shard.parse.ContainerData;
import com.idanchuang.cms.server.domainNew.shard.parse.PageContainerData;
import com.idanchuang.component.base.exception.core.ExDefinition;
import com.idanchuang.component.base.exception.core.ExType;
import com.idanchuang.component.base.exception.exception.BusinessException;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import springfox.documentation.spring.web.json.Json;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-17 15:00
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MasterplateUpsertForm {

    /**
     * 目录id
     */
    private CatalogueId catalogueId;

    /**
     * 模版id
     */
    private MasterplateId masterplateId;

    /**
     * 操作人id
     */
    private OperatorId operatorId;

    /**
     * 页面标题(app端标题 pageTitle)
     */
    private String appTitle;

    /**
     * 后台标题（目录名称 describe）
     */
    private String backEndTitle;

    /**
     * 页面别名
     */
    private String aliasTitle;

    /**
     * 模版名称（templateName）
     */
    private String masterplateName;

    /**
     * 平台类型
     */
    private PlatformCode platform;

    /**
     * 前端页面格式
     */
    private String pageStyle;

    /**
     * 活动开始时间
     */
    private String startTime;

    /**
     * 活动结束时间
     */
    private String endTime;

    /**
     * 是否分享 1-是 0-否
     */
    private ShareFlag shareFlag;

    /**
     * 分享信息
     */
    private List<MasterplateShareForm> shareInfoList;

    /**
     * 资源位id合集
     */
    private List<NicheId> nicheIds;

    /**
     * 圈选信息
     */
    private List<SelectInfo> selectInfoList;

    /**
     * 解析页面详情信息
     * @return
     */
    public PageContainerData parsePageStyle() {

        //获取多容器参数
        PageContainerData pageContainerData = JsonUtil.toObject(this.pageStyle, PageContainerData.class);

        if (null == pageContainerData || CollectionUtils.isEmpty(pageContainerData.getCompDataList())) {
            throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.PAGE_STYLE_PARSE_ERROR.getCode(),
                    ErrorEnum.PAGE_STYLE_PARSE_ERROR.getMsg()));
        }

        //获取容器列表
        List<ContainerData> compDataList = pageContainerData.getCompDataList();

        Map<String, List<ContainerData>> containerMap = compDataList.stream().filter(p -> !StringUtils.isEmpty(p.getContainerCode())).collect(Collectors.groupingBy(ContainerData::getContainerCode));

        //如果存在一个containerCode对应多个容器，提示code重复
        for (Map.Entry<String, List<ContainerData>> entry : containerMap.entrySet()) {
            List<ContainerData> containerDataList = entry.getValue();
            if (containerDataList.size() > 1) {
                throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.CONTAINER_CODE_REPEAT.getCode(),
                        ErrorEnum.CONTAINER_CODE_REPEAT.getMsg()));
            }
        }

        return pageContainerData;

    }

    /**
     * 解析获取页面相关信息
     * @return
     */
    public Map<String, Object> parsePageConfig() {

        Map<String, Object> metaDataMap = JsonUtil.toMap(this.pageStyle, Map.class);
        //过滤容器字段
        metaDataMap.remove(PageStyleConstant.COMP_DATA_LIST);

        return metaDataMap;
    }

    /**
     * 解析获取容器相关信息
     * @return
     */
    public Map<String, Object> parseContainerConfig(ContainerData containerData) {

        //获取公用的页面信息数据
        Map<String, Object> metaDataMap = parsePageConfig();

        //插入容器相关数据
        metaDataMap.put(PageStyleConstant.COMPONENT_JSON_DATA, containerData.getComponentJsonData());

        return metaDataMap;
    }
}
