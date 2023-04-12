package com.idanchuang.cms.server.interfaces.adcontentservice.facade;

import com.idanchuang.cms.server.application.adcontentservice.WechatService;
import com.idanchuang.cms.server.interfaces.adcontentservice.dto.WechatTicketDTO;
import com.idanchuang.component.base.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;


@Component
@Slf4j
public class WechatFacade {


    @Resource
    private WechatService wechatService;

    @Value("#{${wechat.config.map}}")
    private Map<String, String> appInfoMap;

    public JsonResult<WechatTicketDTO> getSignature(@SpringQueryMap WechatTicketDTO dto) {

        String appSecret = appInfoMap.get(dto.getAppId());
        Optional.ofNullable(appSecret).orElseThrow(() -> new RuntimeException(MessageFormat.format("未配置对应的appSecret，appId=[{0}]", dto.getAppId())));
        dto.initialParameter();
        String token = wechatService.getAccessToken(dto.getAppId(), appSecret);
        String ticket = wechatService.getTicket(dto.getAppId(), token);
        String signature = wechatService.getSignature(ticket, dto.getNoncestr(), dto.getTimestamp(), dto.getUrl());
        dto.setSignature(signature);
        log.info("微信签名信息：token：{},ticket:{},noncestr:{},timestamp:{},url:{},signature:{}",
                token, ticket, dto.getNoncestr(), dto.getTimestamp(), dto.getUrl(), signature);
        return JsonResult.success(dto);

    }
}
