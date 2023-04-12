package com.idanchuang.cms.server.infrastructure.adcontentservice.bulletin.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.idanchuang.cms.server.infrastructure.adcontentservice.bulletin.entity.WBulletin;
import com.idanchuang.cms.server.infrastructure.adcontentservice.bulletin.mapper.WBulletinMapper;
import com.idanchuang.cms.server.interfaces.adcontentservice.dto.BulletinQueryRequest;
import com.idanchuang.cms.server.interfaces.adcontentservice.dto.BulletinRequest;
import com.idanchuang.cms.server.interfaces.adcontentservice.vo.BulletinPageDTO;
import com.idanchuang.cms.api.adcontentservice.response.BulletinPortalDetailDTO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 公告(WBulletin)表数据库访问层
 *
 * @author xf
 * @since 2021-02-22 11:07:44
 */
@Repository
public class WBulletinDao extends ServiceImpl<WBulletinMapper, WBulletin> {

    @Resource
    private WBulletinMapper wBulletinMapper;

    public IPage<BulletinPageDTO> selectListBulletin(Page<Object> page, BulletinQueryRequest bulletinQueryRequest) {
        return wBulletinMapper.selectListBulletin(page, bulletinQueryRequest);
    }

    public List<BulletinPortalDetailDTO> getListByPlatFormPosition(Map<String, Object> map) {
        return wBulletinMapper.getListByPlatFormPosition(map);
    }

    public BulletinPortalDetailDTO hadConflictData(BulletinRequest bulletinRequest) {
        return wBulletinMapper.hadConflictData(bulletinRequest);
    }
}