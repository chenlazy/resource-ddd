package com.idanchuang.cms.server.infrastructure.adcontentservice.hotArea.service.impl;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.idanchuang.cms.server.application.config.SystemConfig;
import com.idanchuang.cms.server.infrastructure.adcontentservice.common.constant.BaseConstant;
import com.idanchuang.cms.server.infrastructure.adcontentservice.common.constant.JetCacheExpireConstant;
import com.idanchuang.cms.server.infrastructure.adcontentservice.common.constant.JetCacheNameConstant;
import com.idanchuang.cms.server.infrastructure.adcontentservice.hotArea.dao.WHotAreaDao;
import com.idanchuang.cms.server.infrastructure.adcontentservice.hotArea.entity.WHotArea;
import com.idanchuang.cms.server.infrastructure.adcontentservice.util.Asserts;
import com.idanchuang.cms.server.infrastructure.adcontentservice.util.ErrorEnum;
import com.idanchuang.cms.server.infrastructure.adcontentservice.util.JumpTypeEnum;
import com.idanchuang.cms.server.interfaces.adcontentservice.dto.HotAreaRequest;
import com.idanchuang.cms.server.interfaces.adcontentservice.dto.ListHotAreaRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;


/**
 * @author fym
 * @description :
 * @date 2021/2/23 2:27 下午
 */
@Service
public class IWHotAreaService {

    @Resource
    private WHotAreaDao wHotAreaDao;

    public IPage<WHotArea> pageQueryHotArea(Page<WHotArea> page, ListHotAreaRequest request) {
        return wHotAreaDao.pageQueryHotArea(page, request);
    }

    public Integer save(HotAreaRequest request) {
        WHotArea hotArea = prepareHotArea(request);
        checkHotAreaTime(hotArea);
        hotArea.setCreatedAt(LocalDateTime.now());
        wHotAreaDao.save(hotArea);
        return hotArea.getId();
    }

    @Transactional(rollbackFor = Exception.class,transactionManager = "abmTransactionManager")
    public Integer insertHotArea(HotAreaRequest request) {
        // 插入一行
        WHotArea hotArea = prepareHotArea(request);
        // 校验最大数
        Integer count = wHotAreaDao.queryLineCount(request.getPlatform(),request.getDisplayPosition());
        int maxDisplayLine = SystemConfig.getInstance().getHotAreaMaxDisplayLine();
        Asserts.isTrue(count < maxDisplayLine, ErrorEnum.DISPLAY_LINE_EXCEED);
        // 更新大于或等于插入行的首页热区行数
        Integer insertLine = request.getDisplayLine();
        List<WHotArea> oldList = wHotAreaDao.lambdaQuery()
                .eq(WHotArea::getPlatform, request.getPlatform()).ge(WHotArea::getDisplayLine, insertLine).list();
        if (CollectionUtils.isNotEmpty(oldList)) {
            for (WHotArea oldArea : oldList) {
                oldArea.setDisplayLine(oldArea.getDisplayLine() + 1);
                wHotAreaDao.updateById(oldArea);
            }
        }

        hotArea.setCreatedAt(LocalDateTime.now());
        wHotAreaDao.save(hotArea);
        return hotArea.getId();
    }

    public Integer editHotArea(Integer id, HotAreaRequest request) {
        Asserts.isTrue(id != null, ErrorEnum.ID_NOT_EXIST);
        WHotArea hotArea = prepareHotArea(request);
        hotArea.setId(id);
        checkHotAreaTime(hotArea);
        wHotAreaDao.updateById(hotArea);
        return id;
    }

    public Integer deleteHotArea(Integer id) {
        Asserts.isTrue(id != null, ErrorEnum.ID_NOT_EXIST);
        wHotAreaDao.removeById(id);
        return id;
    }

    @Cached(name = JetCacheNameConstant.MARKET_AD_HOT_AREA, key = "#position+'_'+#platform", cacheType = CacheType.REMOTE,
            expire = JetCacheExpireConstant.local_expire_M_1, cacheNullValue = true)
    public List<WHotArea> getListByPositionPlatform(String position, Integer platform) {
        return this.getListByPositionPlatform(position, platform, LocalDateTime.now());
    }


    public List<WHotArea> getListByPositionPlatform(String position, Integer platform, LocalDateTime timePoint) {
        return wHotAreaDao.getListByPositionPlatform(position, platform, timePoint);
    }

    private WHotArea prepareHotArea(HotAreaRequest request) {
        //TODO 下线
        //ValidateUtils.validate(request, ErrorEnum.COMMON_REQUEST_ERROR);
        checkOnCondition(request);
        WHotArea hotArea = new WHotArea();
        hotArea.setImage(request.getImage());
        hotArea.setTitle(request.getTitle());
        hotArea.setDisplayPosition(request.getDisplayPosition());
        hotArea.setPlatform(request.getPlatform());
        hotArea.setDisplayLine(request.getDisplayLine());
        hotArea.setJumpType(request.getJumpType());
        perfectJumpInfo(hotArea, request);
        hotArea.setStartAt(request.getSendAt()[0]);
        hotArea.setEndAt(request.getSendAt()[1]);
        hotArea.setLinePosition(request.getLinePosition());
        hotArea.setDestLevel(StringUtils.join(request.getDestLevel(), ","));
        hotArea.setShareImage(request.getShareImage());
        hotArea.setShareUrl(request.getShareUrl());
        hotArea.setShareTitle(request.getShareTitle());
        hotArea.setShareDesc(request.getShareDesc());
        hotArea.setGoodsId(request.getGoodsId());
        hotArea.setHotType(request.getHotType());
        hotArea.setActivityStartAt(request.getActivityStartAt());
        hotArea.setActivityEndAt(request.getActivityEndAt());
        hotArea.setFontColor(request.getFontColor());
        hotArea.setFontSize(request.getFontSize());
        hotArea.setUpdatedAt(LocalDateTime.now());
        return hotArea;
    }

    private void checkOnCondition(HotAreaRequest request) {
        Integer hotType = request.getHotType();
        // banner
        if (hotType == 1) {
            Integer jumpType = request.getJumpType();
            if (jumpType.equals(JumpTypeEnum.JUMP_GOODS.getStatus())) {
                Asserts.isTrue(request.getGoodsId() != null, ErrorEnum.COMMON_REQUEST_ERROR, "请填写商品SPUID");
            }

            Integer platform = request.getPlatform();
            if (jumpType.equals(JumpTypeEnum.JUMP_URL.getStatus())) {
                Asserts.isTrue(StringUtils.isNotBlank(request.getJumpUrl()), ErrorEnum.COMMON_REQUEST_ERROR, "请填写App跳转URL");
                if (platform == 1) {
                    Asserts.isTrue(StringUtils.isNotBlank(request.getJumpWx()), ErrorEnum.COMMON_REQUEST_ERROR, "请填写小程序跳转URL");
                }
            }
        }
        // 倒计时
        else {
            Asserts.isTrue(ObjectUtils.allNotNull(request.getActivityStartAt(), request.getActivityEndAt()),
                    ErrorEnum.COMMON_REQUEST_ERROR, "请设置活动时间");
            Asserts.isTrue(StringUtils.isNotBlank(request.getFontSize()), ErrorEnum.COMMON_REQUEST_ERROR, "请设置倒计时字体");
            Asserts.isTrue(StringUtils.isNotBlank(request.getFontColor()), ErrorEnum.COMMON_REQUEST_ERROR, "请设置倒计时色值");
        }

    }

    private void perfectJumpInfo(WHotArea hotArea, HotAreaRequest request) {
        if (request.getJumpType().equals(JumpTypeEnum.JUMP_GOODS.getStatus())) {
            String weexUrl = BaseConstant.GOODS_DETAIL_LINK;
            if (request.getPlatform() == 2) {
                weexUrl = BaseConstant.GOODS_DETAIL_LINK_ABM;
            }
            hotArea.setJumpUrl(weexUrl + "?goods_id=" + request.getGoodsId());
            hotArea.setJumpWx(BaseConstant.WX_GOODS_DETAIL_LINK + request.getGoodsId());
        } else {
            hotArea.setJumpUrl(request.getJumpUrl());
            hotArea.setJumpWx(request.getJumpWx());
        }
    }

    private void checkHotAreaTime(WHotArea hotArea) {
        LocalDateTime startAt = hotArea.getStartAt();
        LocalDateTime endAt = hotArea.getEndAt();
        Integer id = hotArea.getId();
        Integer displayLine = hotArea.getDisplayLine();
        Integer linePosition = hotArea.getLinePosition();
        Integer platform = hotArea.getPlatform();
        String displayPosition = hotArea.getDisplayPosition();
        // startAt <= dbStartAt  && dbStartAt =< endAt
        Integer count1 = wHotAreaDao.lambdaQuery()
                .ne(id != null, WHotArea::getId, id)
                .eq(WHotArea::getDisplayLine, displayLine)
                .eq(WHotArea::getLinePosition, linePosition)
                .eq(WHotArea::getPlatform, platform)
                .eq(WHotArea::getDisplayPosition, displayPosition)
                .between(WHotArea::getStartAt, startAt, endAt).count();
        Asserts.isTrue(count1 == 0, ErrorEnum.HOT_AREA_TIME_CROSS);
        // startAt <= dbEndAt && dbEndAt =< endAt
        Integer count2 = wHotAreaDao.lambdaQuery()
                .ne(id != null, WHotArea::getId, id)
                .eq(WHotArea::getDisplayLine, displayLine)
                .eq(WHotArea::getLinePosition, linePosition)
                .eq(WHotArea::getPlatform, platform)
                .eq(WHotArea::getDisplayPosition, displayPosition)
                .between(WHotArea::getEndAt, startAt, endAt).count();
        Asserts.isTrue(count2 == 0, ErrorEnum.HOT_AREA_TIME_CROSS);
        // dbStartAt <= startAt && endAt <= dbEndAt
        Integer count3 = wHotAreaDao.lambdaQuery()
                .ne(id != null, WHotArea::getId, id)
                .eq(WHotArea::getDisplayLine, displayLine)
                .eq(WHotArea::getLinePosition, linePosition)
                .eq(WHotArea::getPlatform, platform)
                .eq(WHotArea::getDisplayPosition, displayPosition)
                .le(WHotArea::getStartAt, startAt).ge(WHotArea::getEndAt, endAt).count();
        Asserts.isTrue(count3 == 0, ErrorEnum.HOT_AREA_TIME_CROSS);
    }
}
