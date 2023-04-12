package com.idanchuang.cms.server.domainNew.model.cms.aggregation.render;

import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueId;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueType;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.PageCode;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-23 14:07
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public interface PageRenderRepository {

    /**
     * 保存页面缓存
     * @param pageRender
     */
    void storePageRender(PageRender pageRender);

    /**
     * 删除页面缓存
     * @param catalogueId
     */
    void removePageRender(CatalogueId catalogueId);

    /**
     * 获取待生效和生效中的页面模版
     * @param catalogueId
     * @return List<PageRender>
     */
    List<PageRender> getPageRenderListForActive(CatalogueId catalogueId);

    /**
     * 根据pageCode获取页面模版列表
     * @param pageCode
     * @return
     */
    List<PageRender> getPageRenderListByPageCode(PageCode pageCode,  CatalogueType catalogueType);

    /**
     * 根据目录id查询页面模版列表
     * @param catalogueId
     * @return
     */
    List<PageRender> getPageRenderListById(CatalogueId catalogueId);

    /**
     * 根据catalogueId获取优先的页面信息
     * @param catalogueId
     * @return
     */
    PageRender getPageRenderByCatalogueId(CatalogueId catalogueId);
}
