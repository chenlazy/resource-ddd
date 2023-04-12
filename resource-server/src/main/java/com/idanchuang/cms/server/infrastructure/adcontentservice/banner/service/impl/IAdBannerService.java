package com.idanchuang.cms.server.infrastructure.adcontentservice.banner.service.impl;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.idanchuang.abmio.feign.client.FileServiceFeignClient;
import com.idanchuang.abmio.feign.dto.FileInfoDTO;
import com.idanchuang.cms.api.adcontentservice.request.AdBannerUrlRequest;
import com.idanchuang.cms.api.adcontentservice.response.AdBannerUrlDTO;
import com.idanchuang.cms.server.infrastructure.adcontentservice.banner.entity.AdBannerDO;
import com.idanchuang.cms.server.infrastructure.adcontentservice.banner.entity.BannerDO;
import com.idanchuang.cms.server.infrastructure.adcontentservice.banner.mapper.AdBannerMapper;
import com.idanchuang.cms.server.infrastructure.adcontentservice.common.constant.JetCacheExpireConstant;
import com.idanchuang.cms.server.infrastructure.adcontentservice.common.constant.JetCacheNameConstant;
import com.idanchuang.component.base.JsonResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: lei.liu
 * @date 2021/1/27 15:05
 **/
@Service
public class IAdBannerService  {

    @Resource
    private AdBannerMapper adBannerMapper;

    @Resource
    private IBannerService bannerService;

    @Resource
    private FileServiceFeignClient fileServiceFeignClient;

    public List<AdBannerDO> list(String adCategoryKey, String adPositionKey) {
        LambdaQueryWrapper<AdBannerDO> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(AdBannerDO::getAdCategoryKey, adCategoryKey)
                .eq(AdBannerDO::getAdPositionKey, adPositionKey);
        return adBannerMapper.selectList(queryWrapper);
    }

    @Cached(name = JetCacheNameConstant.MARKET_AD_BANNER_URL,key = "#cacheKey",cacheType = CacheType.REMOTE,
            expire = JetCacheExpireConstant.expire_M_1, localLimit = 1000, cacheNullValue = true)
    public List<AdBannerUrlDTO> getUrl(AdBannerUrlRequest req, String cacheKey) {
        List<AdBannerDO> adBannerList = list(req.getAdCategoryKey(), req.getAdPositionKey());
        if (CollectionUtils.isEmpty(adBannerList)) {
            return null;
        }

        List<Integer> bannerIdList = adBannerList.stream().map(AdBannerDO::getBannerId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(bannerIdList)) {
            return null;
        }

        List<BannerDO> bannerList = bannerService.listById(bannerIdList, req.getLevel());
        if (CollectionUtils.isEmpty(bannerList)) {
            return null;
        }

        Map<Integer, BannerDO> bannerMap = bannerList.stream().collect(Collectors.toMap(BannerDO::getId, e -> e, (a, b) -> a));
        List<AdBannerUrlDTO> adBannerUrlList = new ArrayList<>();
        AdBannerUrlDTO adBannerUrl = null;
        for (AdBannerDO adBanner : adBannerList) {
            BannerDO bannerDO = bannerMap.get(adBanner.getBannerId());
            if (bannerDO == null) {
                continue;
            }

            adBannerUrl = convertFor(bannerDO);
            adBannerUrl.setSkipType(adBanner.getSkipType());
            adBannerUrlList.add(adBannerUrl);
        }
        return adBannerUrlList;
    }

    private AdBannerUrlDTO convertFor(BannerDO banner) {
        AdBannerUrlDTO adBannerUrl = new AdBannerUrlDTO();
        BeanUtils.copyProperties(banner, adBannerUrl);
        adBannerUrl.setBannerId(banner.getId());

        FileInfoDTO fileInfo = getFileInfo(banner.getImageId().longValue());
        adBannerUrl.setImageUrl(fileInfo != null ? fileInfo.getUrl() : null);
        return  adBannerUrl;
    }

    private FileInfoDTO getFileInfo(Long fileId) {
        JsonResult<FileInfoDTO> result = fileServiceFeignClient.singleQuery(fileId);
        if (!result.isSuccess()) {
            return null;
        }
        return result.getData();
    }

}
