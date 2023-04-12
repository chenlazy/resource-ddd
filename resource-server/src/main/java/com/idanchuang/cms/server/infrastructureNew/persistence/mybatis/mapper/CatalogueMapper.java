package com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.mapper;

import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.CatalogueDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-24 11:08
 * @Desc: 目录查询mapper
 * @Copyright VTN Limited. All rights reserved.
 */
@Mapper
public interface CatalogueMapper {

    int insertCatalogue(CatalogueDO catalogueDO);

    int updateCatalogue(CatalogueDO catalogueDO);

    boolean removeCatalogue(@Param("id") Long id, @Param("operatorId") Long operatorId);

    CatalogueDO getCatalogueById(@Param("catalogueId")Long catalogueId);

    List<CatalogueDO> getCatalogueByIds(@Param("catalogueIds") List<Long> catalogueIds);

    CatalogueDO getCatalogueByCode(@Param("pageCode")String pageCode, @Param("pageType")Integer pageType);

    List<CatalogueDO> queryCatalogueList(@Param("id") Long id, @Param("pageTitle") String pageTitle,
                                         @Param("clientPageId") Long clientPageId,
                                         @Param("clientPageIdList") List<Long> clientPageIdList,
                                         @Param("aliasTitle") String aliasTitle,  @Param("platforms") List<Integer> platforms,
                                         @Param("old") Integer old);

    List<CatalogueDO> queryAllCatalogueList();

    /**
     * 根据tagId查询模版目录
     * @param tagId
     * @return
     */
    List<CatalogueDO> queryAllCatalogueListByTagId(@Param("tagId")Long tagId);

    /**
     * 批量根据tagId查询模版目录信息
     * @param tagIds
     * @return
     */
    List<CatalogueDO> queryAllCatalogByTagIds(@Param("tagIds") List<Long> tagIds);

    /**
     * 查询所有有效的目录数量
     * @return
     */
    Integer queryTotalCatalogue();
}
