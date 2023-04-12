package com.idanchuang.cms.api.adcontentservice.facade;

import com.idanchuang.cms.api.adcontentservice.request.AdBannerUrlRequest;
import com.idanchuang.cms.api.adcontentservice.response.AdBannerUrlDTO;
import com.idanchuang.component.base.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * Ad Banner 接口
 * @author: lei.liu
 * @date 2021/1/27 10:01
 **/
@Api(value = "Ad Banner服务", tags = "Ad Banner服务")
@FeignClient(value = "DC-RESOURCE", path = "/api/ad/banner")
public interface AdBannerFacade {

    @ApiOperation(value = "获取Banner url")
    @PostMapping("/getUrl")
    JsonResult<List<AdBannerUrlDTO>> getUrl(@RequestBody @Valid AdBannerUrlRequest req);
}
