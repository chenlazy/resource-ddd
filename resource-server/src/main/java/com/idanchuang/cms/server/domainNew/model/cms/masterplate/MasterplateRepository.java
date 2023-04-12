package com.idanchuang.cms.server.domainNew.model.cms.masterplate;

import com.idanchuang.cms.server.domain.model.cms.ActivityPage;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueId;
import com.idanchuang.cms.server.domainNew.shard.OperatorId;
import com.idanchuang.component.base.page.PageData;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-16 11:02
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public interface MasterplateRepository {

    /**
     * 分页查询模版列表
     * @param pageQueryForm
     * @return
     */
    PageData<Masterplate> queryMasterplateByPage(MasterplatePageQueryForm pageQueryForm);


    //查询单个模板
    Masterplate getMasterplateById(MasterplateId masterplateId);

    /**
     * 根据模版ids批量查询模版
     * @param masterplateIds
     * @return
     */
    List<Masterplate> selectMasterplateByIds(List<MasterplateId> masterplateIds);


    /**
     * 批量查询模版列表
     * @param catalogueIds
     * @return
     */
    List<Masterplate> getBatchMasterplate(List<CatalogueId> catalogueIds);

    //指定目录下的模板数量
    int countByCatalogueId(CatalogueId catalogueId);

    //删除模板
    void remove(MasterplateId masterplateId, OperatorId operatorId);


    /**
     * 更新开始时间
     * @param masterplateId
     * @param startTime
     * @param operatorId
     * @return
     */
    Boolean updateStartTimeById(MasterplateId masterplateId, LocalDateTime startTime, OperatorId operatorId);

    /**
     * 更新模版
     * @param masterplate
     * @return
     */
    Boolean updateMasterplate(Masterplate masterplate);

    /**
     * 保存模版
     * @param masterplate
     * @return
     */
    void storeMasterplate(Masterplate masterplate);

    /**
     * 获取目录下所有的模版
     * @param catalogueId
     * @return
     */
    List<Masterplate> getMasterplateList(CatalogueId catalogueId);

    /**
     * 查询目录下已生效和待生效模版
     * @param catalogueId
     * @return
     */
    List<Masterplate> getMasterplateListForActive(CatalogueId catalogueId);

    /**
     * 获取所有未失效的模版
     * @return
     */
    List<Masterplate> getMasterplateListForValid();

    /**
     * 修改模版自动下架状态
     * @param masterplateId
     * @param enable
     */
    void updateGoodsEnable(MasterplateId masterplateId, GoodsEnable enable);

    /**
     * 根据模版id+条件批量查询
     * @param catalogueIds
     * @return
     */
    List<Masterplate> getCmsPageListForValid(List<CatalogueId> catalogueIds);

    /**
     * 根据批量目录id查询待生效和生效中的模版
     * @param catalogueIds
     * @return
     */
    List<Masterplate> getCmsPageListForActive(List<CatalogueId> catalogueIds);

    /**
     * 获取当前模版的所有历史快照数据
     * @param masterplateId
     * @return
     */
    List<Masterplate> getMasterplateSnapList(MasterplateId masterplateId);

    /**
     * vtn 活动中页面实例id
     * @param spuId
     * @return
     */
    List<ActivityPage> queryActivityPageBySpuId(Long spuId);

}
