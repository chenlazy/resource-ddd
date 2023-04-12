package com.idanchuang.cms.api.adcontentservice.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: xf
 * @time: 2020/12/3
 */
@Data
public class BulletinPortalDetailDTO {

    private Integer id;

    /**
     * 1,单创 2,abm
     */
    private Integer platformType;

    /**
     * app公告显示位置 单创:1,首页公告栏 2,购物车公告栏   ABM: 3,工作台公告栏
     */
    private Integer position;

    /**
     * 定向等级
     */
    private String targetLevels;

    /**
     * 公告标题
     */
    private String title;

    /**
     * 投放开始时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime startTime;

    /**
     * 投放结束时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime endTime;

    /**
     * 是否可跳转: 0不可跳转 1可跳转
     */
    private Integer isJump;

    /**
     * 跳转类型：1，页面链接 2文本
     */
    private Integer jumpType;

    /**
     * 文本标题
     */
    private String textTitle;

    /**
     * 文本内容
     */
    private String textContent;

    /**
     * 跳转链接
     */
    private String jumpUrl;




}
