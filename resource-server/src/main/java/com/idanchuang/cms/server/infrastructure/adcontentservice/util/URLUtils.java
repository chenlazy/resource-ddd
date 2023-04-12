package com.idanchuang.cms.server.infrastructure.adcontentservice.util;

import com.idanchuang.cms.server.application.config.SystemConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author wyh
 * @date 2020/4/3
 */
public class URLUtils {

    private static final String WEEX_PREFIX="weex:";

    public static String buildURL(String domain, String url, Map<String,String> paramMap) throws URISyntaxException {

        if(StringUtils.isBlank(url)) {
            return StringUtils.EMPTY;
        }

        if(StringUtils.startsWith(url,WEEX_PREFIX) ) {
            return url.trim();
        }

        URIBuilder urlBuilder = new URIBuilder(url);

        URIBuilder newUrIBuilder = new URIBuilder(domain);
        newUrIBuilder.setPath(urlBuilder.getPath());

        newUrIBuilder.setParameters(urlBuilder.getQueryParams());
        for (Map.Entry<String, String> entry: paramMap.entrySet()) {
            newUrIBuilder.setParameter(entry.getKey(), entry.getValue());
        }

        return newUrIBuilder.build().toString();
    }

    /**
     * 专题、文章、推荐链接检测
     * @param uri
     * @return
     */
    public static Boolean wapToolCheck(String uri){
        String pattern = SystemConfig.getInstance().getWapToolsCheckRegex();
        boolean isMatch = Pattern.matches(pattern, uri);
        return isMatch;

    }

    /**
     * 获取wap domain
     * @param url
     * @return
     */
//    public static String getWapDomain(String url){
//        return wapToolCheck (url) ? SystemConfig.getInstance ().getWapToolsDomain () : SystemConfig.getInstance().getEnvDomain();
//    }

}