package com.idanchuang.cms.server.infrastructure.adcontentservice.bulletin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.idanchuang.cms.server.infrastructure.adcontentservice.bulletin.entity.WBulletin;
import com.idanchuang.cms.server.interfaces.adcontentservice.dto.BulletinQueryRequest;
import com.idanchuang.cms.server.interfaces.adcontentservice.dto.BulletinRequest;
import com.idanchuang.cms.server.interfaces.adcontentservice.vo.BulletinPageDTO;
import com.idanchuang.cms.api.adcontentservice.response.BulletinPortalDetailDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 公告(WBulletin)表数据库访问层
 *
 * @author xf
 * @since 2021-02-22 11:07:45
 */
public interface WBulletinMapper extends BaseMapper<WBulletin> {


    IPage<BulletinPageDTO> selectListBulletin(Page<Object> page, @Param("bulletinQueryRequest") BulletinQueryRequest bulletinQueryRequest);

    List<BulletinPortalDetailDTO> getListByPlatFormPosition(@Param("map") Map<String,Object> map);

    BulletinPortalDetailDTO hadConflictData(@Param("bulletinRequest") BulletinRequest bulletinRequest);
}