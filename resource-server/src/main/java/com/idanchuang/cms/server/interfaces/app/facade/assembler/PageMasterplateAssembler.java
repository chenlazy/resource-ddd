package com.idanchuang.cms.server.interfaces.app.facade.assembler;

import com.idanchuang.abmio.feign.dto.FileInfoDTO;
import com.idanchuang.cms.server.application.config.SystemConfig;
import com.idanchuang.cms.server.application.constant.RedisKeyConstant;
import com.idanchuang.cms.server.domainNew.model.cms.aggregation.render.PageRender;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateShareForm;
import com.idanchuang.cms.server.domainNew.shard.PlatformCode;
import com.idanchuang.cms.server.infrastructure.adcontentservice.util.DateTimeUtils;
import com.idanchuang.cms.server.interfaces.app.dto.PageRenderDTO;
import com.idanchuang.cms.server.interfaces.web.config.GatewayUserDTO;
import com.idanchuang.component.redis.util.RedisUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;

import java.time.LocalDateTime;
import java.util.Map;

import static java.lang.String.format;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-01-04 10:50
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public class PageMasterplateAssembler {

    private PageMasterplateAssembler() {

    }

    public static PageRenderDTO domainToDto(PageRender pageRender, String currentVersion) {

        if (null == pageRender) {
            return null;
        }

        PageRenderDTO pageRenderDTO = new PageRenderDTO();
        pageRenderDTO.setPageId((int)pageRender.getCatalogueId().getValue());
        pageRenderDTO.setNewerVersion(pageRender.getVersion());
        pageRenderDTO.setPlatformVersion(PlatformCode.getVersionByPlatform(pageRender.getPlatform()));

        if (StringUtils.isBlank(currentVersion) || !currentVersion.equals(pageRender.getVersion())) {
            pageRenderDTO.setUpdateVersion(1);
            pageRenderDTO.addContainers(pageRender.getContainers());
        }
        return pageRenderDTO;
    }

    public static PageRenderDTO domainToDto(PageRender pageRender, GatewayUserDTO userDTO, Integer userRole,
                                            MasterplateShareForm shareForm, Map<Long, FileInfoDTO> imageMap,
                                            LocalDateTime previewTime, String currentVersion) {

        if (null == pageRender) {
            return null;
        }

        PageRenderDTO pageRenderDTO = new PageRenderDTO();
        pageRenderDTO.setId((int) pageRender.getCatalogueId().getValue());
        pageRenderDTO.setPageId((int) pageRender.getCatalogueId().getValue());
        pageRenderDTO.setAliasTitle(pageRender.getAliasTitle());
        pageRenderDTO.setPageTitle(pageRender.getPageTitle());
        pageRenderDTO.setShareFlag(pageRender.getShareFlag().getVal());
        pageRenderDTO.setUserIdCode(userDTO.getIdCode());
        pageRenderDTO.setUserLevel(userDTO.getBrandProviderLevel());
        pageRenderDTO.setPageStyle(pageRender.getPageStyle());
        pageRenderDTO.setNewerVersion(pageRender.getVersion());
        pageRenderDTO.setPlatformVersion(PlatformCode.getVersionByPlatform(pageRender.getPlatform()));
        pageRenderDTO.setRole(userRole);
        pageRenderDTO.setPlatform(PlatformCode.simplyPlatform(pageRender.getPlatform()));
        Integer status = pageRender.getStatusByTime(previewTime) != null ? pageRender.getStatusByTime(previewTime).getVal() : 0;
        pageRenderDTO.setStatus(status);
        //福三会场返回标签id
        Long subjectTagId = SystemConfig.getInstance().getSubjectTagId();

        if (null != subjectTagId && null != pageRender.getClientPageId() && null != pageRender.getMasterplateId()) {
            String tagKey = format(RedisKeyConstant.SUBJECT_GOODS_TAG_ID_KEY, pageRender.getMasterplateId().getValue());
            Integer tag = RedisUtil.getInstance().getObj(tagKey);
            pageRenderDTO.setGoodsTagId(pageRender.getClientPageId().getValue() == subjectTagId && null != tag ? tag.longValue() : null);
        }
        //设置专题会场标识
        Long taskType = SystemConfig.getInstance().getSubjectTagTaskType();
        if (null != pageRender.getClientPageId() && null != taskType) {
            pageRenderDTO.setTaskFlag(pageRender.getClientPageId().getValue() == taskType ? 9 : null);
        }
        pageRenderDTO.setStartDateTime(pageRender.getStartTime());
        pageRenderDTO.setEndDateTime(pageRender.getEndTime());
        pageRenderDTO.setStartTime(DateTimeUtils.localDateTime2String(pageRender.getStartTime(), "yyyy-MM-dd HH:mm:ss"));
        pageRenderDTO.setEndTime(DateTimeUtils.localDateTime2String(pageRender.getEndTime(), "yyyy-MM-dd HH:mm:ss"));
        pageRenderDTO.setServerTimestamp(System.currentTimeMillis());
        //设置分享信息
        if (null != shareForm) {
            pageRenderDTO.setShareTitle(shareForm.getShareTitle());
            pageRenderDTO.setShareDesc(shareForm.getShareDesc());
            FileInfoDTO shareImage = imageMap.get(shareForm.getShareImg());
            pageRenderDTO.setShareImage(null != shareImage ? shareImage.getUrl() : "");
            FileInfoDTO sharePoster = imageMap.get(shareForm.getSharePoster());
            pageRenderDTO.setSharePoster(null != sharePoster ? sharePoster.getUrl() : "");
            if (CollectionUtils.isEmpty(shareForm.getSharePosterList())) {
                pageRenderDTO.setSharePosterList(Lists.newArrayList(null != sharePoster ? sharePoster.getUrl() : ""));
            } else {
                pageRenderDTO.setSharePosterList(shareForm.getSharePosterList());
            }
        }

        if (StringUtils.isBlank(currentVersion) || !currentVersion.equals(pageRender.getVersion())) {
            pageRenderDTO.setUpdateVersion(1);
            pageRenderDTO.addContainers(pageRender.getContainers());
        }
        return pageRenderDTO;
    }
}
