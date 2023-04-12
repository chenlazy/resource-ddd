package com.idanchuang.cms.server.domainNew.model.cms.external.message;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-04-22 11:33
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
public class MMessage {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 批次
     */
    private String batchId;

    /**
     * 消息类型 1,站内消息 2，促销消息 3,弹窗
     */
    private Integer messageType;

    private String platformIds;

    /**
     * dc分类id
     */
    private Integer dcCategoryId;

    /**
     * dt分类id
     */
    private Integer dtCategoryId;

    /**
     * 发送对象 1, 全部 2等级 3名单
     */
    private Integer targetUserType;

    /**
     * 等级
     */
    private String level;

    /**
     * 手机号码
     */
    private String mobiles;

    /**
     * 用户ids
     */
    private String userIds;

    /**
     * 订单号
     */
    private String orderSns;

    /**
     * 例如:{"title":"xxx","content":"xxxx","thumb_url":"xxx","icon_url":"xxxx","icon_color":"#0D0D0D"}
     */
    private String messageContent;

    /**
     * 例如:{"jump_type":1,"jump_url":"http://wwwww.baidu.com","jump_goods_id":1}
     */
    private String messageAction;

    /**
     * 对应push_message.id
     */
    private Integer pushId;

    /**
     * 1,支持 0不支持
     */
    private Integer isPush;


    /**
     * 是否支持预约: 1支持预约 0不支持预约
     */
    private Integer isReserve;

    /**
     * 是否支持分享:1，可分享 0不可分享
     */
    private Integer isShare;

    /**
     * 分享标题
     */
    private String shareTitle;

    /**
     * 分享描述
     */
    private String shareDesc;

    /**
     * 分享url
     */
    private String shareUrl;

    /**
     * 分享图片url
     */
    private String shareImageUrl;

    /**
     * 1,立即发送 2定时发送
     */
    private Integer sendType;

    /**
     * 发送时间
     */
    private LocalDateTime sendAt;

    /**
     * 发送时间段，针对弹窗
     */
    private String sendRangeAt;

    /**
     * 活动开始时间
     */
    private LocalDateTime activityStart;

    /**
     * 活动结束时间
     */
    private LocalDateTime activityEnd;

    /**
     * 0未发送1已发送
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

    /**
     * 删除时间
     */
    private LocalDateTime deletedAt;

    /**
     * 管理员id
     */
    private Integer adminUserId;

    /**
     * 切割文件列表的IDs
     */
    private String bigfileSplitTaskIds;

    /**
     * 排序(为0则为不推荐,大于0为推荐)用来区分DT的动态下的推荐类型
     */
    private Integer sort;
}
