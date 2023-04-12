package com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.mapper;


import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.SequenceNumberDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author fym
 * @description :
 * @date 2021/12/23 上午10:03
 */
@Mapper
public interface SequenceNumberMapper {

    /**
     * 单个发号
     * @param sequenceNumberDO
     * @return
     */
    int insert (SequenceNumberDO sequenceNumberDO);

    /**
     * 批量发号
     * @param lists
     * @return
     */
    int insertBatch(@Param("lists") List<SequenceNumberDO> lists);

}
