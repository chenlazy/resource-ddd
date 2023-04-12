package com.idanchuang.cms.server.interfaces.adcontentservice.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: xf
 * @time: 2020/12/3
 */
@Data
public class BulletinPageDTO {

    private Integer id;

    /**
     * 1,单创 2,abm
     */
    private Integer type;

    /**
     * 平台名称
     */
    private String typeName;

    /**
     * app公告显示位置 单创:1,首页公告栏 2,购物车公告栏   ABM: 3,工作台公告栏
     */
    private Integer position;

    /**
     * 公告显示位置名称
     */
    private String positionName;

    /**
     * 定向等级
     */
    private String targetLevels;

    /**
     * 定向等级
     */
    private List<String> level;

    /**
     * 公告标题
     */
    private String text;

    /**
     * 投放开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime startAt;

    /**
     * 投放结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime endAt;

    /**
     * 0代表为未发布;1代表已发布
     */
    private Integer state;



}
