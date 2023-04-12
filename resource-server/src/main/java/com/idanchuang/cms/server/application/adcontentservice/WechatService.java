package com.idanchuang.cms.server.application.adcontentservice;


import com.idanchuang.cms.server.application.adcontentservice.dto.WechatAccessTokenDTO;
import com.idanchuang.cms.server.application.adcontentservice.dto.WechatTicket;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author wengbinbin
 * @date 2021/4/7
 */
@Slf4j
@Component
public class WechatService {
    @Resource
    private RestTemplate restTemplate;

    /**
     * 微信accessToken缓存key
     */
    public static final String WECHAT_ACCESS_TOKEN_KEY = "market:subject:wechat_access_token:appid_%s";
    /**
     * 微信ticket缓存key
     */
    public static final String WECHAT_TICKET_KEY = "market:subject:wechat_ticket:appid_%s";

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public String getAccessToken(String appId, String appSecrete) {
        return Optional.ofNullable(stringRedisTemplate.opsForValue().get(String.format(WECHAT_ACCESS_TOKEN_KEY, appId)))
                .orElseGet(() -> getAccessTokenFromWechat(appId, appSecrete));
    }

    public String getAccessTokenFromWechat(String appId, String appSecrete) {
        String apiUrl = MessageFormat.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}", appId, appSecrete);
        WechatAccessTokenDTO dto = restTemplate.getForObject(apiUrl, WechatAccessTokenDTO.class);
        Optional.ofNullable(dto.getAccess_token()).orElseThrow(() ->
                new RuntimeException(MessageFormat.format("获取微信token失败，appId=[{0}],{1}", appId, dto)));
        stringRedisTemplate.opsForValue().set(String.format(WECHAT_ACCESS_TOKEN_KEY, appId), dto.getAccess_token(), dto.getExpires_in(), TimeUnit.SECONDS);
        return dto.getAccess_token();
    }

    public String getTicket(String appId, String accessToken) {
        return Optional.ofNullable(stringRedisTemplate.opsForValue().get(String.format(WECHAT_TICKET_KEY, appId)))
                .orElseGet(() -> getTicketFromWechat(appId, accessToken));
    }

    public String getTicketFromWechat(String appId, String accessToken) {
        String apiUrl = MessageFormat.format("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={0}&type=jsapi", accessToken);
        WechatTicket dto = restTemplate.getForObject(apiUrl, WechatTicket.class);
        Optional.ofNullable(dto.getTicket()).orElseThrow(() ->
             new RuntimeException(MessageFormat.format("获取微信ticket失败，appId=[{0}]，token=[{1}]", appId, accessToken)));
        stringRedisTemplate.opsForValue().set(String.format(WECHAT_TICKET_KEY, appId), dto.getTicket(), dto.getExpires_in(), TimeUnit.SECONDS);
        return dto.getTicket();
    }

    public String getSignature(String jsapiTicket, String noncestr, Long timestamp, String url) {
        String a = MessageFormat.format("jsapi_ticket={0}&noncestr={1}&timestamp={2}&url={3}",
                jsapiTicket, noncestr, timestamp.toString(), url);
        return DigestUtils.sha1Hex(a);
    }
}
