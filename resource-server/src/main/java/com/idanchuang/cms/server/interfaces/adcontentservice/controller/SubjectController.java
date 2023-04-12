package com.idanchuang.cms.server.interfaces.adcontentservice.controller;

import com.idanchuang.cms.server.interfaces.adcontentservice.dto.WechatTicketDTO;
import com.idanchuang.cms.server.interfaces.adcontentservice.facade.WechatFacade;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.resource.server.infrastructure.utils.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.net.URLDecoder;

/**
 * @author fym
 * @description :
 * @date 2021/1/22 3:23 下午
 */

@Slf4j
@RestController
@RequestMapping("/subject")
@Api(tags = "专题")
public class SubjectController {



    @Resource
    private WechatFacade wechatFacade;

    @ApiOperation(value = "获取微信签名接口")
    @GetMapping("/signature")
    public JsonResult<WechatTicketDTO> getSignature(@Valid WechatTicketDTO req) {
        log.info("WechatTicketDTO{}", JsonUtil.toJsonString(req));

        if(req != null && !StringUtils.isEmpty(req.getUrl())){
            req.setUrl(URLDecoder.decode(req.getUrl()));
        }
        return wechatFacade.getSignature(req);
    }

}
