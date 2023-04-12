package com.idanchuang.cms.server.infrastructure.adcontentservice.banner.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.idanchuang.cms.server.infrastructure.adcontentservice.banner.entity.BannerDO;
import com.idanchuang.cms.server.infrastructure.adcontentservice.banner.mapper.BannerMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: lei.liu
 * @date 2021/1/27 15:04
 **/
@Service
public class IBannerService {

    @Resource
    private BannerMapper bannerMapper;

    public List<BannerDO> listById(List<Integer> idList, String level) {
        LambdaQueryWrapper<BannerDO> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.in(BannerDO::getId, idList);
        if (StringUtils.hasText(level)) {
            queryWrapper.and(wrapper -> wrapper.eq(BannerDO::getLevel, 0).or().like(BannerDO::getLevel, level));
        }
        return bannerMapper.selectList(queryWrapper);
    }
}
