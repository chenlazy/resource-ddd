package com.idanchuang.cms.server.application.adcontentservice;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.idanchuang.abmio.feign.dto.FileInfoDTO;
import com.idanchuang.cms.server.application.adcontentservice.transfer.AppHotAreaTransfer;
import com.idanchuang.cms.server.application.config.SystemConfig;
import com.idanchuang.cms.server.application.remote.RemoteFileService;
import com.idanchuang.cms.server.infrastructure.adcontentservice.util.DateTimeUtils;
import com.idanchuang.cms.server.infrastructure.adcontentservice.util.HotTypeEnum;
import com.idanchuang.cms.server.infrastructure.adcontentservice.util.URLUtils;
import com.idanchuang.cms.server.interfaces.adcontentservice.dto.HotAreaDTO;
import com.idanchuang.cms.server.interfaces.adcontentservice.dto.ImageDetailDTO;
import com.idanchuang.cms.api.adcontentservice.response.HotAreaAdminDTO;
import com.idanchuang.cms.server.interfaces.web.config.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author fym
 * @description :
 * @date 2021/2/23 3:31 下午
 */
@Service
@Slf4j
public class AppHotAreaService {

    @Resource
    private RemoteHotAreaAdContentService remoteHotAreaService;
    @Resource
    private RemoteFileService remoteFileService;

    public List<List<HotAreaDTO>> getByPosition(String position, Integer platform, LocalDateTime timePoint) {
        List<HotAreaAdminDTO> hotAreaDOList;
        if (timePoint == null) {
            hotAreaDOList = remoteHotAreaService.getListByPositionPlatform(position, platform, null);
        } else {
            hotAreaDOList = remoteHotAreaService.getListByPositionPlatform(position, platform, timePoint);
        }
        if (CollectionUtils.isEmpty(hotAreaDOList)) {
            return Lists.newArrayList();
        }
        Map<Long, FileInfoDTO> imageMap = this.queryImageUrl(hotAreaDOList);
        List<List<HotAreaDTO>> hotAreaLineList = new ArrayList<>();
        hotAreaDOList.stream().filter(this::hotAreaUserLevelCheck)
                .map(t -> {
                    HotAreaDTO hotAreaDTO = AppHotAreaTransfer.toDTO(t);
                    if (imageMap.get(hotAreaDTO.getImage().longValue()) != null) {
                        ImageDetailDTO imageDetailDTO = new ImageDetailDTO();
                        imageDetailDTO.setFileUrl(imageMap.get(hotAreaDTO.getImage().longValue()).getUrl());
                        imageDetailDTO.setWidth(imageMap.get(hotAreaDTO.getImage().longValue()).getWidth());
                        imageDetailDTO.setHeight(imageMap.get(hotAreaDTO.getImage().longValue()).getHeight());
                        hotAreaDTO.setImageDetail(imageDetailDTO);
                    }
                    if (imageMap.get(hotAreaDTO.getShareImage().longValue()) != null) {
                        ImageDetailDTO imageDetailDTO = new ImageDetailDTO();
                        imageDetailDTO.setFileUrl(imageMap.get(hotAreaDTO.getShareImage().longValue()).getUrl());
                        imageDetailDTO.setWidth(imageMap.get(hotAreaDTO.getShareImage().longValue()).getWidth());
                        imageDetailDTO.setHeight(imageMap.get(hotAreaDTO.getShareImage().longValue()).getHeight());
                        hotAreaDTO.setShareImageDetail(imageDetailDTO);
                    }
                    hotAreaDTO.setCurrentTime(DateTimeUtils.localDatTime2timeStamp(LocalDateTime.now()));
                    if (t.getHotType().equals(HotTypeEnum.TIME.getCode())) {
                        hotAreaDTO.setActivityStartAt(DateTimeUtils.localDatTime2timeStamp(t.getActivityStartAt()));
                        hotAreaDTO.setActivityEndAt(DateTimeUtils.localDatTime2timeStamp(t.getActivityEndAt()));
                    }
                    Map<String, String> param = Maps.newHashMap();
                    param.put("parentCode", RequestContext.get().getIdCode().toString());
                    param.put("register_code", RequestContext.get().getRegisterCode());
                    try {
                        String wapDomain = URLUtils.wapToolCheck(t.getShareUrl()) ? SystemConfig.getInstance().getWapToolsDomain() : SystemConfig.getInstance().getEnvDomain();
                        hotAreaDTO.setShareUrl(URLUtils.buildURL(wapDomain, t.getShareUrl(), param));
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    return hotAreaDTO;
                }).collect(Collectors.groupingBy(HotAreaDTO::getDisplayLine, LinkedHashMap::new, Collectors.toList()))
                .forEach((displayLine, hotAreaListByDisplayLine) -> hotAreaLineList.add(hotAreaListByDisplayLine));


        return hotAreaLineList;
    }

    private Map<Long, FileInfoDTO> queryImageUrl(List<HotAreaAdminDTO> hotAreaDOList) {
        Set<Long> imageSet = hotAreaDOList.stream().map(u -> u.getImage().longValue()).collect(Collectors.toSet());
        Set<Long> imageShareSet = hotAreaDOList.stream().map(u -> u.getShareImage().longValue()).collect(Collectors.toSet());
        imageSet.addAll(imageShareSet);
        List<Long> imgIds = new ArrayList<>(imageSet);
        List<List<Long>> lists = Lists.newArrayList();
        int size = imgIds.size();
        int toIndex = 50;
        for (int i = 0; i < size; i += 50) {
            if (i + 50 > size) {
                toIndex = size - i;
            }
            List<Long> newList = imgIds.subList(i, i + toIndex);
            lists.add(newList);
        }
        Map<Long, FileInfoDTO> imageMaps = Maps.newHashMap();
        for (List<Long> list : lists) {
            Map<Long, FileInfoDTO> imageMap = remoteFileService.batchQuery(list);
            imageMaps.putAll(imageMap);
        }
        return imageMaps;
    }

    /**
     * 用户等级校验
     *
     * @param hotArea
     * @return boolean
     */
    private boolean hotAreaUserLevelCheck(HotAreaAdminDTO hotArea) {
        Integer userLevel = RequestContext.get().getBrandProviderLevel();
        List<String> destLevels = Arrays.asList(hotArea.getDestLevel().split(","));
        return destLevels.contains(Integer.toString(userLevel));
    }

    public List<HotAreaDTO> getByAbmPosition(String position, String registerCode, String parentCode, Integer platform) {
        List<HotAreaAdminDTO> hotAreaDOList = remoteHotAreaService.getListByPositionPlatform(position, platform, null);
        if (CollectionUtils.isEmpty(hotAreaDOList)) {
            return Lists.newArrayList();
        }
        Map<Long, FileInfoDTO> imageMap = this.queryImageUrl(hotAreaDOList);
        return hotAreaDOList.stream().map(t -> {
                    HotAreaDTO hotAreaDTO = AppHotAreaTransfer.toDTO(t);
                    if (imageMap.get(hotAreaDTO.getImage().longValue()) != null) {
                        ImageDetailDTO imageDetailDTO = new ImageDetailDTO();
                        imageDetailDTO.setFileUrl(imageMap.get(hotAreaDTO.getImage().longValue()).getUrl());
                        imageDetailDTO.setWidth(imageMap.get(hotAreaDTO.getImage().longValue()).getWidth());
                        imageDetailDTO.setHeight(imageMap.get(hotAreaDTO.getImage().longValue()).getHeight());
                        hotAreaDTO.setImageDetail(imageDetailDTO);
                    }
                    if (imageMap.get(hotAreaDTO.getShareImage().longValue()) != null) {
                        ImageDetailDTO imageDetailDTO = new ImageDetailDTO();
                        imageDetailDTO.setFileUrl(imageMap.get(hotAreaDTO.getShareImage().longValue()).getUrl());
                        imageDetailDTO.setWidth(imageMap.get(hotAreaDTO.getShareImage().longValue()).getWidth());
                        imageDetailDTO.setHeight(imageMap.get(hotAreaDTO.getShareImage().longValue()).getHeight());
                        hotAreaDTO.setShareImageDetail(imageDetailDTO);
                    }
                    hotAreaDTO.setCurrentTime(DateTimeUtils.localDatTime2timeStamp(LocalDateTime.now()));
                    if (t.getHotType().equals(HotTypeEnum.TIME.getCode())) {
                        hotAreaDTO.setActivityStartAt(DateTimeUtils.localDatTime2timeStamp(t.getActivityStartAt()));
                        hotAreaDTO.setActivityEndAt(DateTimeUtils.localDatTime2timeStamp(t.getActivityEndAt()));
                    }
                    Map<String, String> param = Maps.newHashMap();
                    param.put("parentCode", parentCode);
                    param.put("register_code", registerCode);
                    try {
                        String wapDomain = URLUtils.wapToolCheck(t.getShareUrl()) ? SystemConfig.getInstance().getWapToolsDomain() : SystemConfig.getInstance().getEnvDomain();
                        hotAreaDTO.setShareUrl(URLUtils.buildURL(wapDomain, hotAreaDTO.getShareUrl(), param));
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    return hotAreaDTO;
                }
        ).collect(Collectors.toList());
    }
}
