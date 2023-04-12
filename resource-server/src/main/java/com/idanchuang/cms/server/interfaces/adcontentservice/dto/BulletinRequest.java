package com.idanchuang.cms.server.interfaces.adcontentservice.dto;


import com.idanchuang.member.point.api.entity.dto.assist.Create;
import com.idanchuang.member.point.api.entity.dto.assist.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

/**
 * @author: xf
 * @time: 2020/12/3
 */
@Data
public class BulletinRequest {

    @Null(groups = {Create.class}, message = "不能传入公告ID")
    @NotNull(groups = {Update.class}, message = "公告ID不能为空")
    private Integer id;


    /**
     * 1,单创 2,abm
     */
    @NotNull(message = "公告平台类型为空")
    private Integer platformType;

    /**
     * app公告显示位置 单创:1,首页公告栏 2,购物车公告栏   ABM: 3,工作台公告栏
     */
    @NotNull(message = "公告显示位置为空")
    private Integer position;

    /**
     * 定向等级
     */
    @NotNull(message = "定向等级为空")
    @Size(min = 1,max = 5,message = "定向等级最多选择5种")
    private String[] targetLevels;

    /**
     * 定向等级
     */
    private Integer level;

    /**
     * 公告标题
     */
    @NotBlank(message = "文本标题为空")
    private String title;

    /**
     * 投放开始时间
     */
    @NotNull(message = "投放开始时间为空")
    private String startTime;

    /**
     * 投放结束时间
     */
    @NotNull(message = "投放结束时间为空")
    private String endTime;

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
