package com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.mapper;

import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.ClientPageDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClientPageMapper {
    int insert(ClientPageDO clientPageDO);

    int update(ClientPageDO clientPageDO);

    boolean remove(@Param("id") Long id, @Param("operatorId") Long operatorId);

    ClientPageDO get(@Param("id") Long id);

    List<ClientPageDO> list(@Param("ids") List<Long> ids, @Param("platform") Integer platform, @Param("pageCode") String pageCode);
}
