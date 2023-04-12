package com.idanchuang.cms.server.application.adcontentservice;

import cn.hutool.json.JSONUtil;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.google.common.collect.Maps;
import com.idanchuang.cms.server.application.config.SystemConfig;
import com.idanchuang.cms.server.infrastructure.adcontentservice.common.constant.JetConstants;
import com.idanchuang.cms.server.infrastructure.adcontentservice.common.constant.NameConstants;
import com.idanchuang.cms.server.infrastructure.adcontentservice.util.BusinessException;
import com.idanchuang.cms.server.infrastructure.adcontentservice.util.URLUtils;
import com.idanchuang.cms.server.interfaces.adcontentservice.dto.BulletinDTO;
import com.idanchuang.cms.server.interfaces.adcontentservice.facade.AdminBulletinFacade;
import com.idanchuang.cms.api.adcontentservice.response.BulletinPortalDetailDTO;
import com.idanchuang.cms.server.interfaces.web.config.RequestContext;
import com.idanchuang.component.base.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 公告(WBulletin)表服务实现类
 *
 * @author xf
 * @since 2021-02-22 11:07:44
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class,transactionManager = "abmTransactionManager")
public class WBulletinAdminService {

    @Resource
    private AdminBulletinFacade adminBulletinFacade;

    @Resource
    private HttpServletRequest httpServletRequest;

    @Cached(name = NameConstants.GET_BULLETIN_BY_PLATFORM_POSITION, key = "#platformType +'_'+#position",
            cacheType = CacheType.REMOTE,
            expire = JetConstants.REMOTE_SHORT_EXPIRE, cacheNullValue = true)
    @SentinelResource(value = "getByPlatFormPosition", fallback = "getByPlatFormPositionFallback", entryType = EntryType.OUT)
    public BulletinDTO getByPlatFormPosition(String platformType, String position) {
        JsonResult<List<BulletinPortalDetailDTO>> baseResult;
        try {
            baseResult = adminBulletinFacade.getListByPlatFormPosition(platformType, position);
        } catch (Exception e) {
            log.error("查询公告详情异常,platformType,position:{},{}", platformType, position);
            throw new BusinessException(e.getMessage());
        }
        if (!baseResult.isSuccess()) {
            log.error("查询公告详情异常,response:{},", JSONUtil.toJsonStr(baseResult));
            throw new BusinessException(baseResult.getCode(), baseResult.getMsg());
        }

        Integer userLevel = RequestContext.get().getBrandProviderLevel();
        List<BulletinPortalDetailDTO> data = baseResult.getData();
        if (CollectionUtils.isEmpty(data)) {
            return null;
        }
        return data.stream().filter(t -> {
            List<String> targetLevels = Arrays.asList (t.getTargetLevels ().split (","));
            LocalDateTime startTime = t.getStartTime();
            LocalDateTime endTime = t.getEndTime();
            if (LocalDateTime.now().isAfter(startTime) && LocalDateTime.now().isBefore(endTime)
                && targetLevels.contains (userLevel.toString())) {
                return true;
            }
            return false;
        }).findFirst().map(t -> {
            BulletinDTO dto = new BulletinDTO();
            dto.setId(t.getId());
            dto.setPlatformType(t.getPlatformType());
            dto.setPosition(t.getPosition());
            dto.setTargetLevels(t.getTargetLevels());
            dto.setTitle(t.getTitle());
            dto.setStartTime(t.getStartTime());
            dto.setEndTime(t.getEndTime());
            dto.setIsJump(t.getIsJump());
            dto.setJumpType(t.getJumpType());
            Map<String, String> param = Maps.newHashMap();
            param.put("parent_code", RequestContext.get().getIdCode().toString ());
            param.put("register_code", httpServletRequest.getHeader("register_code"));
            param.put("token", httpServletRequest.getHeader("token"));
            try {
                dto.setJumpUrl (URLUtils.buildURL(SystemConfig.getInstance().getWapToolsDomain(), t.getJumpUrl (), param));
            } catch (URISyntaxException e) {
                e.printStackTrace ();
            }
            dto.setTextTitle(t.getTextTitle());
            dto.setTextContent(t.getTextContent());
            return dto;
        }).orElse(null);
    }

    public BulletinDTO getByPlatFormPositionFallback(String platformType, String position, Throwable throwable) {
        log.error("查询公告详情进入降级逻辑:{},{},", platformType, position, throwable);
        return null;
    }
}