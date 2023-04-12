package com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.mapper;

import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateStatus;
import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.MasterplateDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-01 17:40
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Mapper
public interface MasterplateMapper {


    /**
     * 分页查询
     * @param id
     * @param pageId
     * @param title
     * @param status
     * @return
     */
    List<MasterplateDO> getMasterplateList(@Param("id")Long id, @Param("pageId")Long pageId,
                                            @Param("title")String title, @Param("status") MasterplateStatus status);


    /**
     * 单个查询
     * @param id
     * @return
     */
    MasterplateDO getMasterplateById(@Param("id") long id);

    /**
     * 根据模版ids批量查询模版
     * @param masterplateIds
     * @return
     */
    List<MasterplateDO> selectMasterplateByIds(@Param("masterplateIds") List<Long> masterplateIds);

    /**
     * 获取目录下所有模版列表
     * @param catalogueId
     * @return
     */
    List<MasterplateDO> getMasterplatesByCatalogueId(@Param("catalogueId") long catalogueId);

    /**
     * 获取目录下待激活和已激活列表
     * @param catalogueId
     * @return
     */
    List<MasterplateDO> getMasterplatesForActive(@Param("catalogueId") long catalogueId);

    /**
     * 获取所有未生效的模版列表
     * @return
     */
    List<MasterplateDO> getMasterplateListForValid();

    /**
     * 批量查询模版列表
     * @param catalogueList
     * @return
     */
    List<MasterplateDO> getBatchMasterplateList(@Param("catalogueList") List<Long> catalogueList);


    /**
     * 目录下模板数量
     * @param catalogueId
     * @return
     */
    int countByCatalogueId(@Param("catalogueId")long catalogueId);

    /**
     * 删除模板
     * @param masterplateId
     * @param operatorId
     */
    void remove(@Param("masterplateId")long masterplateId,@Param("operatorId") long operatorId);

    /**
     * 更新模板开始时间
     * @param masterplateId
     * @param startTime
     * @param operateId
     * @return
     */
    int updateStartTimeById(@Param("masterplateId") long masterplateId,
                            @Param("startTime") LocalDateTime startTime,
                            @Param("operateId") long operateId);

    /**
     * 插入
     * @param masterplateDO
     */
    void insert(MasterplateDO masterplateDO);

    /**
     * 修改信息
     * @param masterplateDO
     * @return
     */
    Boolean updateById(MasterplateDO masterplateDO);

    /**
     * 根据模版更新自动下架状态
     * @param masterplateId
     * @param enable
     * @return
     */
    Boolean updateGoodsEnable(@Param("masterplateId") long masterplateId,@Param("enable") Integer enable);

    /**
     * 根据模版id+条件批量查询
     * @param pageSchemaIdList
     * @return
     */
    List<MasterplateDO> getCmsPageListForValid(@Param("list") List<Long> pageSchemaIdList);

    /**
     * 获取批量待生效和生效中的模版
     * @param pageSchemaIdList
     * @return
     */
    List<MasterplateDO> getCmsPageListForActive(@Param("list") List<Long> pageSchemaIdList);


    /**
     * 根据模版id获取所有模版快照数据
     * @param masterplateId
     * @return
     */
    List<MasterplateDO> getMasterplateSnapList(@Param("masterplateId") Long masterplateId);

    /**
     * 查询活动中的页面实例信息
     *
     * @param spuId
     * @return
     */
    List<MasterplateDO> queryActivityPageBySpuId(@Param("spuId") Long spuId);
}
