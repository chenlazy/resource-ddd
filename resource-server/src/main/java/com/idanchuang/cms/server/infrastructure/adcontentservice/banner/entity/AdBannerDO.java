package com.idanchuang.cms.server.infrastructure.adcontentservice.banner.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 广告图管理
 * </p>
 *
 * @author lp
 * @since 2020-03-27
 */
@Data
@TableName("w_ad_banner")
public class AdBannerDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**bannerId*/
    @TableField("banner_id")
    private Integer bannerId;

    /**brand_id*/
    @TableField("brand_id")
    private Integer brandId;

    /**广告图分类key*/
    @TableField("ad_category_key")
    private String adCategoryKey;

    /**广告图位置key*/
    @TableField("ad_position_key")
    private String adPositionKey;

    /**定向类型 1:等级 2:品牌 3:商品*/
    @TableField("orientation_type")
    private Integer orientationType;

    /**定向的id 对应等级id 品牌id 商品id*/
    @TableField("orientation_value")
    private String orientationValue;

    /**操作人*/
    @TableField("operator")
    private String operator;

    /***/
    @TableField("created_at")
    private Date createdAt;

    /***/
    @TableField("updated_at")
    private Date updatedAt;

    /**跳转方式 1 url 2 商品*/
    @TableField("type")
    private Integer type;

    /**
     * 跳转方式 0=不跳转，1=商品，2=url
     */
    @TableField("skip_type")
    private Integer skipType;

    /**视频上传方式：0:无视频 1:直接上传; 2:视频URL*/
    @TableField("video_upload_type")
    private Integer videoUploadType;

    /**排序*/
    @TableField("sort")
    private Integer sort;

    /**投放开始时间*/
    @TableField("start_at")
    private LocalDateTime startAt;

    /**投放结束时间*/
    @TableField("end_at")
    private LocalDateTime endAt;
}
