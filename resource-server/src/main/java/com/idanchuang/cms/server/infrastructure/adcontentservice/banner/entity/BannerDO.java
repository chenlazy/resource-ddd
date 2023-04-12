package com.idanchuang.cms.server.infrastructure.adcontentservice.banner.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 广告图片表
 * </p>
 *
 * @author gaodong
 * @since 2020-03-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("w_banner")
public class BannerDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("image_id")
    private Integer imageId;

    @TableField("url")
    private String url;

    @TableField("name")
    private String name;

    /**
     * 0：首页banner, 1:首页活动广告
     */
    @TableField("type")
    private Integer type;

    /**
     * 1显示 或者隐藏
     */
    @TableField("is_visible")
    private Integer isVisible;

    @TableField("listorder")
    private Integer listorder;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    private LocalDateTime updatedAt;

    /**
     * 投放开始时间
     */
    @TableField("start_at")
    private LocalDateTime startAt;

    /**
     * 投放结束时间
     */
    @TableField("end_at")
    private LocalDateTime endAt;

    /**
     * 缩略图id
     */
    @TableField("image_thumb_id")
    private Integer imageThumbId;

    /**
     * 描述
     */
    @TableField("intro")
    private String intro;

    /**
     * 分享链接
     */
    @TableField("link")
    private String link;

    /**
     * 商品id type=12(品牌顶部图片)时存在商品id
     */
    @TableField("goods_id")
    private Integer goodsId;

    /**
     * iphoneX首页轮播图
     */
    @TableField("iphonex_id")
    private Integer iphonexId;

    /**
     * 会员等级 0-全部，1-会员，2-VIP，3-VIP（老），4-中级，5-高级
     */
    @TableField("level")
    private String level;

    /**
     * 倒计时类型：0-不需要倒计时 1-开始倒计时 2-结束倒计时
     */
    @TableField("time_type")
    private Integer timeType;

    /**
     * 是否分享:0 否  1:是
     */
    @TableField("is_share")
    private Integer isShare;

    /**
     * 订货中心分类id
     */
    @TableField("order_center_category_id")
    private Integer orderCenterCategoryId;

    /**
     * 分享标题
     */
    @TableField("share_title")
    private String shareTitle;

    /**
     * 品牌推荐ID
     */
    @TableField("brand_recommend_id")
    private Integer brandRecommendId;

    /**
     * 视频封面
     */
    @TableField("video_image_id")
    private Integer videoImageId;

    /**
     * 视频ID
     */
    @TableField("video_id")
    private Integer videoId;

    /**
     * 视频链接
     */
    @TableField("video_url")
    private String videoUrl;

    /**
     * url标题
     */
    @TableField("url_title")
    private String urlTitle;
    /**
     * 小程序跳转url
     */
    @TableField("wx_url")
    private String wxUrl;

}
