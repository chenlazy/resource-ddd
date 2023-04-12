package com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.abmau.mapper;

import com.idanchuang.cms.server.domainNew.model.cms.external.remind.UserRemindId;
import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.abmau.UserRemindRecordDO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-04-01 14:09
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public interface UserRemindMapper {

    /**
     * 获取提醒记录数量
     * @return
     */
    Integer getRemindRecordCount(@Param("remindStatus") Integer remindStatus,
                                 @Param("remindTypes") List<Integer> remindTypes,
                                 @Param("remindSendTime") LocalDateTime remindSendTime);


    /**
     * 分页获取提醒信息
     * @param remindStatus
     * @param remindTypes
     * @param remindSendTime
     * @return
     */
    List<UserRemindRecordDO> listRemindRecords(@Param("remindStatus") Integer remindStatus,
                                               @Param("remindTypes") List<Integer> remindTypes,
                                               @Param("remindSendTime") LocalDateTime remindSendTime);

    /**
     * 查询附近的提醒信息
     * @param userId
     * @param componentId
     * @param remindStatus
     * @param remindType
     * @param remindTime
     * @return
     */
    List<UserRemindRecordDO> listNearRemindRecords(@Param("userId") Long userId, @Param("componentId") String componentId,
                                                   @Param("remindType") Integer remindType, @Param("remindStatus") Integer remindStatus,
                                                   @Param("remindTime") LocalDateTime remindTime);


    /**
     * 更新记录状态
     * @param remindIds
     * @param status
     * @return
     */
    boolean updateRemindRecordStatus(@Param("remindIds") List<Long> remindIds, @Param("status") Integer status);
}
