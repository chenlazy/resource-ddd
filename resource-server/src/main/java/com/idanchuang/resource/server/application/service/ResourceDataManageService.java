package com.idanchuang.resource.server.application.service;

import com.google.common.collect.Lists;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.component.base.page.PageData;
import com.idanchuang.dealer.dataplat.api.common.constant.TableColumnConstant;
import com.idanchuang.dealer.dataplat.api.model.commonquery.CommonPageData;
import com.idanchuang.dealer.dataplat.api.model.commonquery.DistinctSqlRequestDTO;
import com.idanchuang.dealer.dataplat.api.model.commonquery.KeyValueRequestDTO;
import com.idanchuang.dealer.dataplat.api.model.commonquery.PageRequestDTO;
import com.idanchuang.dealer.dataplat.api.model.commonquery.RequestBuilders;
import com.idanchuang.dealer.dataplat.api.model.commonquery.WhereClause;
import com.idanchuang.dealer.dataplat.api.model.commonquery.filter.QueryConditionBuilders;
import com.idanchuang.resource.api.request.admin.ResourceDataPageReqDTO;
import com.idanchuang.resource.api.request.admin.ResourceDataReqDTO;
import com.idanchuang.resource.api.request.admin.ResourceDataUnitPageReqDTO;
import com.idanchuang.resource.api.request.admin.ResourceDataUnitReqDTO;
import com.idanchuang.resource.api.response.admin.ResourceClickIncidentRespDTO;
import com.idanchuang.resource.api.response.admin.ResourceDataManagePageRespDTO;
import com.idanchuang.resource.api.response.admin.ResourceDataManageRespDTO;
import com.idanchuang.resource.api.response.admin.ResourceDataRespDTO;
import com.idanchuang.resource.server.application.remote.RemoteCommonSearchService;
import com.idanchuang.resource.server.domain.model.resource.ResourceUnit;
import com.idanchuang.resource.server.infrastructure.common.constant.BrokenLineShowEnum;
import com.idanchuang.resource.server.infrastructure.common.constant.DataShowEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-16 15:14
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Service
@Slf4j
public class ResourceDataManageService {

    @Resource
    private RemoteCommonSearchService remoteCommonSearchService;
    @Resource
    private ResourceConfigService resourceConfigService;
    @Resource
    private ResourceUnitService resourceUnitService;

    public JsonResult<ResourceDataManageRespDTO> getResourceDataManageInfo(ResourceDataUnitReqDTO reqDTO) {
        if (StringUtils.isBlank(reqDTO.getContentTitle()) && null == reqDTO.getUnitId()) {
            return JsonResult.failure("投放id与投放内容名不能都为空");
        }
        List<String> dataShowKeys = DataShowEnum.getKeyList();
        Long aLong = this.countDays(this.UDateToLocalDate(reqDTO.getStartTime()), this.UDateToLocalDate(reqDTO.getEndTime()));
        List<String> timeList = this.timeList(reqDTO.getStartTime(), reqDTO.getEndTime());
        List<String> downLabelList = this.showList(timeList, BrokenLineShowEnum.getShowNum(aLong.intValue()));
        KeyValueRequestDTO keyValueRequestDTO = this.resourceDataUnitReqDTO2Data(reqDTO);
        List<Map<String, Object>> listAllData = remoteCommonSearchService.listAllData(keyValueRequestDTO);
        List<ResourceDataRespDTO> pilColumnSeriesData = Lists.newArrayList();
        for (String key : dataShowKeys) {
            ResourceDataRespDTO dataRespDTO = new ResourceDataRespDTO();
            dataRespDTO.setName(key);
            List<Integer> showListData = Lists.newArrayList();
            for (String dayName : downLabelList) {
                Integer num = 0;
                for (Map<String, Object> map : listAllData) {
                    if ("all".equals(map.get(TableColumnConstant.R_UNIT_DAY_STAT.COLUMNS.POSITION)) && dayName.equals(map.get(TableColumnConstant.R_UNIT_DAY_STAT.COLUMNS.STAT_DAY)) && StringUtils.isNotBlank(DataShowEnum.getValue(key))) {
                        num = (Integer) map.get(DataShowEnum.getValue(key));
                    }
                }
                showListData.add(num);
            }
            dataRespDTO.setData(showListData);
            pilColumnSeriesData.add(dataRespDTO);
        }
        ResourceDataManageRespDTO respDTO = new ResourceDataManageRespDTO();
        respDTO.setPilColumnLegendData(dataShowKeys);
        respDTO.setPilColumnXAxisData(downLabelList);
        respDTO.setPilColumnSeriesData(pilColumnSeriesData);
        return JsonResult.success(respDTO);
    }

    public JsonResult<PageData<ResourceDataManagePageRespDTO>> getResourceDataManagePageRespList(ResourceDataUnitPageReqDTO reqDTO) {
        if (StringUtils.isBlank(reqDTO.getContentTitle()) && null == reqDTO.getUnitId()) {
            return JsonResult.failure("投放id与投放内容名不能都为空");
        }
        PageRequestDTO pageRequestDTO = this.resourceDataUnitPageReqDTO2Data(reqDTO);
        CommonPageData<Map<String, Object>> objectCommonPageData = remoteCommonSearchService.pageSearch(pageRequestDTO);
        Long unitId;
        if (StringUtils.isNotBlank(reqDTO.getContentTitle()) && reqDTO.getUnitId() == null) {
            unitId = resourceUnitService.getUnitIdByUnitNameLimitOne(reqDTO.getContentTitle());
        } else {
            unitId = reqDTO.getUnitId();
        }
        ResourceUnit resourceUnit = resourceUnitService.getResourceUnitById(unitId);
        String componentName = null != resourceUnit ? resourceUnit.getComponentName() : "";
        List<ResourceDataManagePageRespDTO> list = objectCommonPageData.getRecords().stream().map(u -> {
            ResourceDataManagePageRespDTO respDTO = new ResourceDataManagePageRespDTO();
            respDTO.setDateTime((String) u.get(TableColumnConstant.R_UNIT_DAY_STAT.COLUMNS.STAT_DAY));
            respDTO.setClickPv((Integer) u.get(TableColumnConstant.R_UNIT_DAY_STAT.COLUMNS.C_PV));
            respDTO.setClickUv((Integer) u.get(TableColumnConstant.R_UNIT_DAY_STAT.COLUMNS.C_UV));
            respDTO.setExpPv((Integer) u.get(TableColumnConstant.R_UNIT_DAY_STAT.COLUMNS.E_PV));
            respDTO.setExpUv((Integer) u.get(TableColumnConstant.R_UNIT_DAY_STAT.COLUMNS.E_UV));
            respDTO.setLevel((String) u.get(TableColumnConstant.R_UNIT_DAY_STAT.COLUMNS.LEVEL_DESC));
            if ("all".equals(u.get(TableColumnConstant.R_UNIT_DAY_STAT.COLUMNS.POSITION))) {
                respDTO.setClickIncident("所有");
            } else {
                respDTO.setClickIncident(componentName + u.get(TableColumnConstant.R_UNIT_DAY_STAT.COLUMNS.POSITION));
            }
            return respDTO;
        }).collect(Collectors.toList());
        PageData pageData = new PageData(list, objectCommonPageData.getCurrent(), objectCommonPageData.getSize(), objectCommonPageData.getTotal());
        return JsonResult.success(pageData);
    }

    public JsonResult<ResourceDataManageRespDTO> getResourceData(ResourceDataReqDTO reqDTO) {
        if (StringUtils.isBlank(reqDTO.getResourceName()) && reqDTO.getResourceId() == null) {
            return JsonResult.failure("资源位id与资源位名不能都不为空");
        }
        List<String> dataShowKeys = DataShowEnum.getKeyList();
        Long aLong = this.countDays(this.UDateToLocalDate(reqDTO.getStartTime()), this.UDateToLocalDate(reqDTO.getEndTime()));
        List<String> timeList = this.timeList(reqDTO.getStartTime(), reqDTO.getEndTime());
        List<String> downLabelList = this.showList(timeList, BrokenLineShowEnum.getShowNum(aLong.intValue()));
        KeyValueRequestDTO keyValueRequestDTO = this.resourceDataReqDTO2Data(reqDTO);
        List<Map<String, Object>> listAllData = remoteCommonSearchService.listAllData(keyValueRequestDTO);
        List<ResourceDataRespDTO> pilColumnSeriesData = Lists.newArrayList();
        for (String key : dataShowKeys) {
            ResourceDataRespDTO dataRespDTO = new ResourceDataRespDTO();
            dataRespDTO.setName(key);
            List<Integer> showListData = Lists.newArrayList();
            for (String dayName : downLabelList) {
                Integer num = 0;
                for (Map<String, Object> map : listAllData) {
                    if (dayName.equals(map.get(TableColumnConstant.RESOURCE_DAY_STAT.COLUMNS.STAT_DAY)) && StringUtils.isNotBlank(DataShowEnum.getValue(key))) {
                        num = (Integer) map.get(DataShowEnum.getValue(key));
                    }
                }
                showListData.add(num);
            }
            dataRespDTO.setData(showListData);
            pilColumnSeriesData.add(dataRespDTO);
        }
        ResourceDataManageRespDTO respDTO = new ResourceDataManageRespDTO();
        respDTO.setPilColumnLegendData(dataShowKeys);
        respDTO.setPilColumnXAxisData(downLabelList);
        respDTO.setPilColumnSeriesData(pilColumnSeriesData);
        return JsonResult.success(respDTO);
    }

    public JsonResult<PageData<ResourceDataManagePageRespDTO>> getResourceDataPageRespList(ResourceDataPageReqDTO reqDTO) {
        if (StringUtils.isBlank(reqDTO.getResourceName()) && reqDTO.getResourceId() == null) {
            return JsonResult.failure("资源位id与资源位名不能都不为空");
        }
        PageRequestDTO pageRequestDTO = this.resourceDataPageReqDTO2Data(reqDTO);
        CommonPageData<Map<String, Object>> objectCommonPageData = remoteCommonSearchService.pageSearch(pageRequestDTO);
        List<ResourceDataManagePageRespDTO> list = objectCommonPageData.getRecords().stream().map(u -> {
            ResourceDataManagePageRespDTO respDTO = new ResourceDataManagePageRespDTO();
            respDTO.setDateTime((String) u.get(TableColumnConstant.RESOURCE_DAY_STAT.COLUMNS.STAT_DAY));
            respDTO.setClickPv((Integer) u.get(TableColumnConstant.RESOURCE_DAY_STAT.COLUMNS.C_PV));
            respDTO.setClickUv((Integer) u.get(TableColumnConstant.RESOURCE_DAY_STAT.COLUMNS.C_UV));
            respDTO.setExpPv((Integer) u.get(TableColumnConstant.RESOURCE_DAY_STAT.COLUMNS.E_PV));
            respDTO.setExpUv((Integer) u.get(TableColumnConstant.RESOURCE_DAY_STAT.COLUMNS.E_UV));
            respDTO.setLevel((String) u.get(TableColumnConstant.RESOURCE_DAY_STAT.COLUMNS.LEVEL_DESC));
            return respDTO;
        }).collect(Collectors.toList());
        PageData pageData = new PageData(list, objectCommonPageData.getCurrent(), objectCommonPageData.getSize(), objectCommonPageData.getTotal());
        return JsonResult.success(pageData);
    }

    public JsonResult<ResourceClickIncidentRespDTO> getClickIncidentList(Long unitId) {
        DistinctSqlRequestDTO distinctList = this.getDistinctList(unitId);
        List<String> distinct = remoteCommonSearchService.distinct(distinctList);
        ResourceClickIncidentRespDTO respDTO = new ResourceClickIncidentRespDTO();
        respDTO.setUnitId(unitId);
        ResourceUnit resourceUnit = resourceUnitService.getResourceUnitById(unitId);
        String componentName = null != resourceUnit ? resourceUnit.getComponentName() : "";
        respDTO.setComponentName(componentName);
        respDTO.setClickIncidentList(distinct);
        return JsonResult.success(respDTO);
    }

    private KeyValueRequestDTO resourceDataUnitReqDTO2Data(ResourceDataUnitReqDTO reqDTO) {
        WhereClause whereClause = new WhereClause();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isNotBlank(reqDTO.getContentTitle()) && reqDTO.getUnitId() == null) {
            Long unitId = resourceUnitService.getUnitIdByUnitNameLimitOne(reqDTO.getContentTitle());
            whereClause.and(QueryConditionBuilders.equals(TableColumnConstant.R_UNIT_DAY_STAT.COLUMNS.R_UNIT_ID, unitId));
        } else {
            whereClause.and(QueryConditionBuilders.equals(TableColumnConstant.R_UNIT_DAY_STAT.COLUMNS.R_UNIT_ID, reqDTO.getUnitId()));
        }
        whereClause.and(QueryConditionBuilders.equals(TableColumnConstant.R_UNIT_DAY_STAT.COLUMNS.POSITION, "all"));
        whereClause.and(QueryConditionBuilders.equals(TableColumnConstant.R_UNIT_DAY_STAT.COLUMNS.LEVEL, reqDTO.getLevel()));
        whereClause.and(QueryConditionBuilders.lte(TableColumnConstant.R_UNIT_DAY_STAT.COLUMNS.STAT_DAY, sdf.format(reqDTO.getEndTime())));
        whereClause.and(QueryConditionBuilders.gte(TableColumnConstant.R_UNIT_DAY_STAT.COLUMNS.STAT_DAY, sdf.format(reqDTO.getStartTime())));
        return RequestBuilders.kv(TableColumnConstant.R_UNIT_DAY_STAT.TB_NAME, whereClause, null, null);
    }

    private PageRequestDTO resourceDataUnitPageReqDTO2Data(ResourceDataUnitPageReqDTO reqDTO) {
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        pageRequestDTO.setPageSize(reqDTO.getSize().intValue());
        pageRequestDTO.setPageIndex(reqDTO.getCurrent().intValue());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        WhereClause whereClause = new WhereClause();
        if (StringUtils.isNotBlank(reqDTO.getContentTitle()) && reqDTO.getUnitId() == null) {
            Long unitId = resourceUnitService.getUnitIdByUnitNameLimitOne(reqDTO.getContentTitle());
            whereClause.and(QueryConditionBuilders.equals(TableColumnConstant.R_UNIT_DAY_STAT.COLUMNS.R_UNIT_ID, unitId));
        } else {
            whereClause.and(QueryConditionBuilders.equals(TableColumnConstant.R_UNIT_DAY_STAT.COLUMNS.R_UNIT_ID, reqDTO.getUnitId()));
        }
        if (StringUtils.isNotBlank(reqDTO.getClickIncident())) {
            whereClause.and(QueryConditionBuilders.equals(TableColumnConstant.R_UNIT_DAY_STAT.COLUMNS.POSITION, reqDTO.getClickIncident()));
        }
        if (null != reqDTO.getLevel()) {
            whereClause.and(QueryConditionBuilders.equals(TableColumnConstant.R_UNIT_DAY_STAT.COLUMNS.LEVEL, reqDTO.getLevel()));
        }
        whereClause.and(QueryConditionBuilders.lte(TableColumnConstant.R_UNIT_DAY_STAT.COLUMNS.STAT_DAY, sdf.format(reqDTO.getEndTime())));
        whereClause.and(QueryConditionBuilders.gte(TableColumnConstant.R_UNIT_DAY_STAT.COLUMNS.STAT_DAY, sdf.format(reqDTO.getStartTime())));
        pageRequestDTO.setRequestParamDTO(RequestBuilders.kv(TableColumnConstant.R_UNIT_DAY_STAT.TB_NAME, whereClause, null, null));
        return pageRequestDTO;
    }

    private KeyValueRequestDTO resourceDataReqDTO2Data(ResourceDataReqDTO reqDTO) {
        WhereClause whereClause = new WhereClause();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isNotBlank(reqDTO.getResourceName()) && reqDTO.getResourceId() == null) {
            Long resourceId = resourceConfigService.getResourceIdByNameLimitOne(reqDTO.getResourceName());
            whereClause.and(QueryConditionBuilders.equals(TableColumnConstant.RESOURCE_DAY_STAT.COLUMNS.RESOURCE_ID, resourceId));
        } else {
            whereClause.and(QueryConditionBuilders.equals(TableColumnConstant.RESOURCE_DAY_STAT.COLUMNS.RESOURCE_ID, reqDTO.getResourceId()));
        }
        whereClause.and(QueryConditionBuilders.equals(TableColumnConstant.RESOURCE_DAY_STAT.COLUMNS.LEVEL, reqDTO.getLevel()));
        whereClause.and(QueryConditionBuilders.lte(TableColumnConstant.RESOURCE_DAY_STAT.COLUMNS.STAT_DAY, sdf.format(reqDTO.getEndTime())));
        whereClause.and(QueryConditionBuilders.gte(TableColumnConstant.RESOURCE_DAY_STAT.COLUMNS.STAT_DAY, sdf.format(reqDTO.getStartTime())));
        return RequestBuilders.kv(TableColumnConstant.RESOURCE_DAY_STAT.TB_NAME, whereClause, null, null);
    }

    private PageRequestDTO resourceDataPageReqDTO2Data(ResourceDataPageReqDTO reqDTO) {
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        pageRequestDTO.setPageSize(reqDTO.getSize().intValue());
        pageRequestDTO.setPageIndex(reqDTO.getCurrent().intValue());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        WhereClause whereClause = new WhereClause();
        if (StringUtils.isNotBlank(reqDTO.getResourceName()) && reqDTO.getResourceId() == null) {
            Long resourceId = resourceConfigService.getResourceIdByNameLimitOne(reqDTO.getResourceName());
            whereClause.and(QueryConditionBuilders.equals(TableColumnConstant.RESOURCE_DAY_STAT.COLUMNS.RESOURCE_ID, resourceId));
        } else {
            whereClause.and(QueryConditionBuilders.equals(TableColumnConstant.RESOURCE_DAY_STAT.COLUMNS.RESOURCE_ID, reqDTO.getResourceId()));
        }
        if (null != reqDTO.getLevel()) {
            whereClause.and(QueryConditionBuilders.equals(TableColumnConstant.RESOURCE_DAY_STAT.COLUMNS.LEVEL, reqDTO.getLevel()));
        }
        whereClause.and(QueryConditionBuilders.lte(TableColumnConstant.RESOURCE_DAY_STAT.COLUMNS.STAT_DAY, sdf.format(reqDTO.getEndTime())));
        whereClause.and(QueryConditionBuilders.gte(TableColumnConstant.RESOURCE_DAY_STAT.COLUMNS.STAT_DAY, sdf.format(reqDTO.getStartTime())));
        pageRequestDTO.setRequestParamDTO(RequestBuilders.kv(TableColumnConstant.RESOURCE_DAY_STAT.TB_NAME, whereClause, null, null));
        return pageRequestDTO;
    }

    private DistinctSqlRequestDTO getDistinctList(Long unitId) {
        WhereClause whereClause = new WhereClause();
        whereClause.and(QueryConditionBuilders.equals(TableColumnConstant.R_UNIT_DAY_STAT.COLUMNS.R_UNIT_ID, unitId));
        return RequestBuilders.distinct(TableColumnConstant.R_UNIT_DAY_STAT.TB_NAME, whereClause, TableColumnConstant.R_UNIT_DAY_STAT.COLUMNS.POSITION);
    }

    public List<String> timeList(Date startDate, Date endDate) {
        List<String> result = Lists.newArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(startDate);
        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(endDate);
        while (tempStart.before(tempEnd) || tempStart.compareTo(tempEnd) == 0) {
            result.add(sdf.format(tempStart.getTime()));
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }

    public List<String> showList(List<String> timeList, Integer showNum) {
        if (null == showNum) {
            showNum = 1;
        }
        List<String> showList = Lists.newArrayList();
        Integer size = timeList.size();
        Integer num = 0;
        while (size > 0) {
            showList.add(timeList.get(num));
            size = size - showNum;
            num = num + showNum;
        }
        return showList;
    }


    private Long countDays(LocalDate startDate, LocalDate endDate) {
        long daysDiff = ChronoUnit.DAYS.between(startDate, endDate);
        return daysDiff + 1L;
    }

    public LocalDate UDateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDate localDate = localDateTime.toLocalDate();
        return localDate;
    }
}
