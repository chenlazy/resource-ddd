package com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.abmau.mapper;

import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.abmau.MMessageDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-04-22 13:44
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Mapper
public interface MMessageMapper {

    List<MMessageDO> selectPartNoSendMessage(@Param("statusList") List<Integer> statusList);

    boolean updateStatusById(@Param("id") Integer id, @Param("status") Integer status);
}
