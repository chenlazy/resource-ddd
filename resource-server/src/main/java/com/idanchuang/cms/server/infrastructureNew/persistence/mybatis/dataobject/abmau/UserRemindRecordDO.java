package com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.abmau;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-04-01 14:24
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("w_user_remind_record")
public class UserRemindRecordDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("union_id")
    private String unionId;

    @TableField("remind_id")
    private String remindId;

    @TableField("component_id")
    private String componentId;

    @TableField("user_id")
    private Integer userId;

    @TableField("msg_title")
    private String msgTitle;

    @TableField("msg_content")
    private String msgContent;

    @TableField("target")
    private String target;

    @TableField("remind_send_time")
    private LocalDateTime remindSendTime;

    @TableField("remind_status")
    private Integer remindStatus;

    @TableField("remind_type")
    private Integer remindType;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
