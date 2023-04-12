package com.idanchuang.cms.server.infrastructure.adcontentservice.bulletin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 公告(WBulletin)实体类
 *
 * @author xf
 * @since 2021-02-22 11:07:43
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("w_bulletin")
public class WBulletin implements Serializable {

    private static final long serialVersionUID = -98577525158751371L;

    @ApiModelProperty("$column.comment")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    @ApiModelProperty("1,单创 2,abm ")
    @TableField(value = "platform_type")
    private Integer platformType;


    @ApiModelProperty("app公告显示位置 单创:1,首页公告栏 2,购物车公告栏   ABM: 3,工作台公告栏")
    @TableField(value = "position")
    private Integer position;


    @ApiModelProperty("定向等级")
    @TableField(value = "target_levels")
    private String targetLevels;


    @ApiModelProperty("公告标题")
    @TableField(value = "title")
    private String title;


    @ApiModelProperty("投放开始时间")
    @TableField(value = "start_time")
    private LocalDateTime startTime;


    @ApiModelProperty("投放结束时间")
    @TableField(value = "end_time")
    private LocalDateTime endTime;


    @ApiModelProperty("是否可跳转: 0不可跳转 1可跳转")
    @TableField(value = "is_jump")
    private Integer isJump;


    @ApiModelProperty("跳转类型：1，页面链接 2文本")
    @TableField(value = "jump_type")
    private Integer jumpType;


    @ApiModelProperty("文本标题")
    @TableField(value = "text_title")
    private String textTitle;


    @ApiModelProperty("文本内容")
    @TableField(value = "text_content")
    private String textContent;


    @ApiModelProperty("跳转链接")
    @TableField(value = "jump_url")
    private String jumpUrl;


    @ApiModelProperty("$column.comment")
    @TableField(value = "created_at")
    private LocalDateTime createdAt;


    @ApiModelProperty("$column.comment")
    @TableField(value = "updated_at")
    private LocalDateTime updatedAt;


    @ApiModelProperty("$column.comment")
    @TableField(value = "deleted_at")
    private LocalDateTime deletedAt;


}