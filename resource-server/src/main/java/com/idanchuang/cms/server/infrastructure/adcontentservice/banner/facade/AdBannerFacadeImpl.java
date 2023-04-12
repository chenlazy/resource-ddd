package com.idanchuang.cms.server.infrastructure.adcontentservice.banner.facade;

import com.idanchuang.cms.api.adcontentservice.facade.AdBannerFacade;
import com.idanchuang.cms.api.adcontentservice.request.AdBannerUrlRequest;
import com.idanchuang.cms.api.adcontentservice.response.AdBannerUrlDTO;
import com.idanchuang.cms.server.infrastructure.adcontentservice.banner.service.impl.IAdBannerService;
import com.idanchuang.component.base.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author: lei.liu
 * @date 2021/1/27 14:47
 **/
@RestController
@RequestMapping("/api/ad/banner")
@Slf4j
public class AdBannerFacadeImpl implements AdBannerFacade {

    @Resource
    private IAdBannerService adBannerService;

    @Override
    public JsonResult<List<AdBannerUrlDTO>> getUrl(@Valid AdBannerUrlRequest req) {
        StringBuffer sb = new StringBuffer("");
        sb.append(req.getAdCategoryKey()).append("_").append(req.getAdPositionKey());
        if (StringUtils.hasText(req.getLevel())) {
            sb.append("_").append(req.getLevel());
        }
        return JsonResult.success(adBannerService.getUrl(req, sb.toString()));
    }
}
