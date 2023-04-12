package com.idanchuang.cms.server.domainNew.model.cms.catalogue;

import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPageId;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.PageCode;
import com.idanchuang.cms.server.domainNew.shard.OperatorId;
import com.idanchuang.component.base.page.PageData;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-15 10:56
 * @Desc: 目录查询领域
 * @Copyright VTN Limited. All rights reserved.
 */
public interface CatalogueRepository {

    /**
     * 保存目录
     * @param catalogue
     * @return
     */
    int storeCatalogue(Catalogue catalogue);


    /**
     * 获取目录
     * @param catalogueId
     * @return
     */
    Catalogue getCatalogueById(CatalogueId catalogueId);

    /**
     * 批量获取目录
     * @param catalogueIds
     * @return
     */
    List<Catalogue> getCatalogueByIds(List<CatalogueId> catalogueIds);

    /**
     * 根据pageCode获取id
     * @param pageCode
     * @return
     */
    Catalogue getCatalogueByCode(PageCode pageCode, CatalogueType catalogueType);


    /**
     * 更新目录
     * @param catalogue
     * @return
     */
    int updateCatalogue(Catalogue catalogue);


    /**
     * 分页查询目录列表
     * @param pageQueryForm
     * @return
     */
    PageData<Catalogue> queryCatalogueByPage(CataloguePageQueryForm pageQueryForm);

    /**
     * 删除目录
     * @param catalogueId
     * @param operatorId
     * @return
     */
    Boolean removeCatalogue(CatalogueId catalogueId, OperatorId operatorId);


    /**
     * 查询所有目录列表
     * @return
     */
    List<Catalogue> queryAllCatalogueList();

    /**
     * 根据页面id查询所有目录信息
     * @param pageId
     * @return
     */
    List<Catalogue> queryAllCatalogByTagId(ClientPageId pageId);

    /**
     * 根据页面id集合查询所有目录信息
     * @param pageIds
     * @return
     */
    List<Catalogue> queryAllCatalogByTagIds(List<ClientPageId> pageIds);

    /**
     * 查询所有有效的目录数量
     * @return
     */
    Integer queryTotalCatalogue();
}
