package com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author fym
 * @description :
 * @date 2021/12/23 上午10:00
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@TableName("cms_sequence_number")
public class SequenceNumberDO {

    /**
     * 生成号
     */
    private Long id;

    /**
     * 号码类型 1组件 2容器 3模版 4页面
     */
    private Integer numberType;

    /**
     * 申请时间
     */
    private LocalDateTime createTime;
}
