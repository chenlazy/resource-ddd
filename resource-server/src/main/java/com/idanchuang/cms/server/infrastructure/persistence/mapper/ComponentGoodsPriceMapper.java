package com.idanchuang.cms.server.infrastructure.persistence.mapper;

import com.idanchuang.cms.server.domain.model.cms.ComponentGoodsPriceCondition;
import com.idanchuang.cms.server.infrastructure.persistence.model.ComponentGoodsPriceDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-01 17:41
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Mapper
public interface ComponentGoodsPriceMapper {

    int insert(ComponentGoodsPriceDO entity);

    int insertBatch(List<ComponentGoodsPriceDO> entityList);

    int deleteById(@Param("id") Serializable id, @Param("operatorId") Integer operatorId, @Param("updateTime") LocalDateTime updateTime);

    int updateById(ComponentGoodsPriceDO entity);

    List<ComponentGoodsPriceDO> selectByCondition(ComponentGoodsPriceCondition condition);

}
