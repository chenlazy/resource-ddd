package com.idanchuang.cms.server.domainNew.model.cms.clientPage;

import com.idanchuang.cms.server.domainNew.shard.OperatorId;
import com.idanchuang.component.base.page.PageData;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-13 18:05
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
public interface ClientPageRepository {

    /**
     * 存储页面
     * @param clientPage
     * @return
     */
    boolean store(ClientPage clientPage);

    /**
     * 删除页面
     * @param id
     * @return
     */
    boolean remove(ClientPageId id, OperatorId operatorId);

    /**
     * 更新页面
     * @param clientPage
     * @return
     */
    boolean update(ClientPage clientPage);

    /**
     * 查询页面
     * @param id
     * @return
     */
    ClientPage query(ClientPageId id);

    /**
     * 分页查询页面列表
     * @param queryForm
     * @return
     */
    PageData<ClientPage> page(ClientPageQueryForm queryForm);

    /**
     * 查询页面列表
     * @param queryForm
     * @return
     */
    List<ClientPage> list(ClientPageQueryForm queryForm);
}
