package com.idanchuang.cms.server.infrastructure.adcontentservice.hotArea.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.idanchuang.cms.server.infrastructure.adcontentservice.hotArea.entity.WHotArea;
import com.idanchuang.cms.server.infrastructure.adcontentservice.hotArea.mapper.WHotAreaMapper;
import com.idanchuang.cms.server.interfaces.adcontentservice.dto.ListHotAreaRequest;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author fym
 * @description :
 * @date 2021/2/23 2:27 下午
 */
@Repository
public class WHotAreaDao extends ServiceImpl<WHotAreaMapper, WHotArea> {

    @Resource
    private WHotAreaMapper wHotAreaMapper;

    public IPage<WHotArea> pageQueryHotArea(Page<WHotArea> page, ListHotAreaRequest request) {
        return wHotAreaMapper.pageQueryHotArea(page, request);
    }

    public List<WHotArea> getListByPositionPlatform(String position, Integer platform, LocalDateTime timePoint) {
        LambdaQueryWrapper<WHotArea> queryWrapper = new LambdaQueryWrapper<WHotArea>();
        queryWrapper.eq(WHotArea::getDisplayPosition, position);
        queryWrapper.le(WHotArea::getStartAt, timePoint);
        queryWrapper.ge(WHotArea::getEndAt, timePoint);
        queryWrapper.eq(WHotArea::getPlatform, platform);
        queryWrapper.orderByAsc(WHotArea::getDisplayLine);
        queryWrapper.orderByAsc(WHotArea::getLinePosition);
        return wHotAreaMapper.selectList(queryWrapper);
    }

    public Integer queryLineCount(Integer platform, String displayPosition) {
        return wHotAreaMapper.queryLineCount(platform,displayPosition);
    }
}
