package com.idanchuang.cms.server.domain.repository;

import com.idanchuang.cms.server.domain.model.cms.PageRender;
import com.idanchuang.cms.server.domain.model.cms.schema.SchemaCode;

import java.util.List;

/**
 * @author lei.liu
 * @date 2021/9/13
 */
public interface PageTemplateRepository {

    /**
     * 获取页面的当前的样式
     * @param id    页面定义ID
     * @return  渲染的页面
     */
    PageRender getInfoById(Integer id);

    /**
     * 获取页面的当前的样式
     */
    PageRender getInfoBySchemaCode(SchemaCode schemaCode);


    /**
     * 根据页面定义ID，获取所有页面实例列表
     * @param id    页面定义ID
     * @return  页面实例列表
     */
    List<PageRender> getInfoList(Integer id);


    List<PageRender> getInfoListBySchemaCode(SchemaCode schemaCode);

    /**
     * 创建页面样式的缓存
     * @param pageRender    渲染的页面
     */
    //void buildPageVersion(PageRender pageRender);

    /**
     * 构建页面生效样式
     * @param id    页面定义ID
     */
    void buildPageVersion(Integer id);

    void buildAllPageVersion();

    /**
     * 删除页面样式缓存
     * @param pageId    页面ID
     */
    void deletePageVersion(Integer pageId);

    /**
     * 删除页面某个版本的样式缓存
     * @param pageId    页面ID
     * @param version   版本号
     */
    void deletePageVersion(Integer pageId, String version);


    void deleteAllPageVersion();

    PageRender getCachePageVersion(Integer pageId);
}
