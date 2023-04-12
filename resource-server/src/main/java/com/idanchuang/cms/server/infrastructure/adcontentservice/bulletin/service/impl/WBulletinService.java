package com.idanchuang.cms.server.infrastructure.adcontentservice.bulletin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.idanchuang.cms.server.infrastructure.adcontentservice.bulletin.dao.WBulletinDao;
import com.idanchuang.cms.server.infrastructure.adcontentservice.bulletin.entity.WBulletin;
import com.idanchuang.cms.server.interfaces.adcontentservice.dto.BulletinQueryRequest;
import com.idanchuang.cms.server.interfaces.adcontentservice.dto.BulletinRequest;
import com.idanchuang.cms.server.interfaces.adcontentservice.vo.BulletinDetailDTO;
import com.idanchuang.cms.server.interfaces.adcontentservice.vo.BulletinPageDTO;
import com.idanchuang.cms.api.adcontentservice.response.BulletinPortalDetailDTO;
import com.idanchuang.component.base.JsonResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 公告(WBulletin)表服务实现类
 *
 * @author xf
 * @since 2021-02-22 11:07:44
 */
@Service
@Transactional(rollbackFor = Exception.class,transactionManager = "abmTransactionManager")
public class WBulletinService {

    @Resource
    private WBulletinDao wBulletinDao;


    public IPage<BulletinPageDTO> selectListBulletin(BulletinQueryRequest bulletinQueryRequest) {

        IPage<BulletinPageDTO> page = wBulletinDao.selectListBulletin(new Page<>(bulletinQueryRequest.getCurrent(), bulletinQueryRequest.getSize()), bulletinQueryRequest);
        if (page.getTotal() <= 0) {
            return new Page<>();
        }
        return page;
    }

    public BulletinDetailDTO getDetailBulletinById(Integer id) {
        WBulletin bulletin = wBulletinDao.getById(id);
        if (Objects.isNull(bulletin)) {
            return null;
        }
        BulletinDetailDTO bulletinDetailDTO = new BulletinDetailDTO();
        BeanUtils.copyProperties(bulletin, bulletinDetailDTO);
        bulletinDetailDTO.setTargetLevels(bulletin.getTargetLevels().split(","));
        return bulletinDetailDTO;
    }

    public JsonResult<Object> createBulletin(BulletinRequest bulletinRequest) {
        for (String targetLevel : bulletinRequest.getTargetLevels()) {
            bulletinRequest.setLevel(Integer.parseInt(targetLevel));
            BulletinPortalDetailDTO one = wBulletinDao.hadConflictData(bulletinRequest);
            if (Objects.nonNull(one)) {
                return JsonResult.failure("同一平台同一位置同一等级定向的公告的投放时间不可交叉");
            }
        }
        WBulletin bulletin = new WBulletin();
        BeanUtils.copyProperties(bulletinRequest, bulletin);
        bulletin.setTargetLevels(StringUtils.join(bulletinRequest.getTargetLevels(), ","));
        bulletin.setCreatedAt(LocalDateTime.now());
        bulletin.setUpdatedAt(LocalDateTime.now());
        bulletin.setStartTime(LocalDateTime.parse(bulletinRequest.getStartTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        bulletin.setEndTime(LocalDateTime.parse(bulletinRequest.getEndTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return wBulletinDao.save(bulletin)? JsonResult.success() : JsonResult.failure("新增失败");
    }


    public JsonResult<Object> updateBulletin(BulletinRequest bulletinRequest) {
        WBulletin bulletin = wBulletinDao.getById(bulletinRequest.getId());
        if (Objects.isNull(bulletin)) {
            return JsonResult.failure("公告为空");
        }
        for (String targetLevel : bulletinRequest.getTargetLevels()) {
            bulletinRequest.setLevel(Integer.parseInt(targetLevel));
            BulletinPortalDetailDTO one = wBulletinDao.hadConflictData(bulletinRequest);
            if (Objects.nonNull(one)) {
                return JsonResult.failure("是否同一平台同一位置同一等级定向的公告的投放时间不可交叉");
            }
        }
        BeanUtils.copyProperties(bulletinRequest, bulletin);
        bulletin.setTargetLevels(StringUtils.join(bulletinRequest.getTargetLevels(), ","));
        bulletin.setUpdatedAt(LocalDateTime.now());
        bulletin.setStartTime(LocalDateTime.parse(bulletinRequest.getStartTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        bulletin.setEndTime(LocalDateTime.parse(bulletinRequest.getEndTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return wBulletinDao.updateById(bulletin) ? JsonResult.success() : JsonResult.failure("更新失败");
    }

    public boolean deleteBulletinById(Integer id) {

        WBulletin bulletin = wBulletinDao.getById(id);
        if (Objects.isNull(bulletin)) {
            return Boolean.FALSE;
        }
        WBulletin one = new WBulletin();
        one.setId(bulletin.getId());
        one.setDeletedAt(LocalDateTime.now());
        return wBulletinDao.updateById(one);
    }

    public List<BulletinPortalDetailDTO> getListByPlatFormPosition(String platformType, String position) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("platformType", platformType);
        map.put("position", position);
        List<BulletinPortalDetailDTO> list = wBulletinDao.getListByPlatFormPosition(map);
        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }
        return list;
    }
}