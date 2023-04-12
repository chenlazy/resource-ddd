package com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.abmau;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-04-22 13:49
 * @Desc: 商城业务消息实体类
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("w_m_message")
public class MMessageDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 批次
     */
    @TableField("batch_id")
    private String batchId;

    /**
     * 消息类型 1,站内消息 2，促销消息 3,弹窗
     */
    @TableField("message_type")
    private Integer messageType;

    @TableField("platform_ids")
    private String platformIds;

    /**
     * dc分类id
     */
    @TableField("dc_category_id")
    private Integer dcCategoryId;

    /**
     * dt分类id
     */
    @TableField("dt_category_id")
    private Integer dtCategoryId;

    /**
     * 发送对象 1, 全部 2等级 3名单
     */
    @TableField("target_user_type")
    private Integer targetUserType;

    /**
     * 等级
     */
    @TableField("level")
    private String level;

    /**
     * 手机号码
     */
    @TableField("mobiles")
    private String mobiles;

    /**
     * 用户ids
     */
    @TableField("user_ids")
    private String userIds;

    /**
     * 订单号
     */
    @TableField("order_sns")
    private String orderSns;

    /**
     * 例如:{"title":"xxx","content":"xxxx","thumb_url":"xxx","icon_url":"xxxx","icon_color":"#0D0D0D"}
     */
    @TableField("message_content")
    private String messageContent;

    /**
     * 例如:{"jump_type":1,"jump_url":"http://wwwww.baidu.com","jump_goods_id":1}
     */
    @TableField("message_action")
    private String messageAction;

    /**
     * 对应push_message.id
     */
    @TableField("push_id")
    private Integer pushId;

    /**
     * 1,支持 0不支持
     */
    @TableField("is_push")
    private Integer isPush;


    /**
     * 是否支持预约: 1支持预约 0不支持预约
     */
    @TableField("is_reserve")
    private Integer isReserve;

    /**
     * 是否支持分享:1，可分享 0不可分享
     */
    @TableField("is_share")
    private Integer isShare;

    /**
     * 分享标题
     */
    @TableField("share_title")
    private String shareTitle;

    /**
     * 分享描述
     */
    @TableField("share_desc")
    private String shareDesc;

    /**
     * 分享url
     */
    @TableField("share_url")
    private String shareUrl;

    /**
     * 分享图片url
     */
    @TableField("share_image_url")
    private String shareImageUrl;

    /**
     * 1,立即发送 2定时发送
     */
    @TableField("send_type")
    private Integer sendType;

    /**
     * 发送时间
     */
    @TableField("send_at")
    private LocalDateTime sendAt;

    /**
     * 发送时间段，针对弹窗
     */
    @TableField("send_range_at")
    private String sendRangeAt;

    /**
     * 活动开始时间
     */
    @TableField("activity_start")
    private LocalDateTime activityStart;

    /**
     * 活动结束时间
     */
    @TableField("activity_end")
    private LocalDateTime activityEnd;

    /**
     * 0未发送1已发送
     */
    @TableField("status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;

    /**
     * 删除时间
     */
    @TableField("deleted_at")
    private LocalDateTime deletedAt;

    /**
     * 管理员id
     */
    @TableField("admin_user_id")
    private Integer adminUserId;

    /**
     * 切割文件列表的IDs
     */
    @TableField("bigfile_split_task_ids")
    private String bigfileSplitTaskIds;

    /**
     * 排序(为0则为不推荐,大于0为推荐)用来区分DT的动态下的推荐类型
     */
    @TableField("sort")
    private Integer sort;
}
