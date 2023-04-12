package com.idanchuang.cms.server.interfaces.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 * Spring Boot 配置
 * </p>
 *
 * @author Caratacus
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public TokenInterceptor tokenInterceptor() {
        return new TokenInterceptor ();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/actuator/*", "/appVersionConfig/*", "/appVersion/*")
                .excludePathPatterns("/**/swagger-resources/configuration/ui", "/**/swagger-resources/configuration/security")
                .excludePathPatterns("/**/swagger-resources/", "/**/v2/api-docs/")
                .excludePathPatterns("/**/csrf/")
                .excludePathPatterns("/**/error/")
                .excludePathPatterns("/swagger-ui.html/**", "/**/doc.html", "/webjars/**", "/api-docs");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").
                allowedOrigins("*").
                allowedMethods("GET,POST,PUT,DELETE,OPTIONS").
                allowedHeaders("Token, App-Key, App-Secret, Sign-Time, Cache-Control, X-Requested-With, x_requested_with").
                allowCredentials(true);

    }

}
