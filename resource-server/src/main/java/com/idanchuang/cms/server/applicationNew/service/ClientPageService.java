package com.idanchuang.cms.server.applicationNew.service;

import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPage;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPageFactory;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPageForm;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPageId;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPageRepository;
import com.idanchuang.cms.server.application.enums.ErrorEnum;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.*;
import com.idanchuang.cms.server.domainNew.shard.OperatorId;
import com.idanchuang.component.base.exception.core.ExDefinition;
import com.idanchuang.component.base.exception.core.ExType;
import com.idanchuang.component.base.exception.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-16 15:14
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Service
@Slf4j
public class ClientPageService {

    @Resource
    private ClientPageRepository clientPageRepository;

    /**
     * 保存客户端页面
     *
     * @return
     */
    public boolean createClientPage(ClientPageForm clientPageForm) {
        // 唯一性校验
        check(clientPageForm.getPageCode());
        ClientPage clientPage = ClientPageFactory.createClientPage(clientPageForm);
        return clientPageRepository.store(clientPage);
    }

    public void check(PageCode pageCode) {
        if (pageCode != null) {
            ClientPageQueryForm clientPageQueryForm = ClientPageQueryForm.builder()
                    .pageCode(pageCode)
                    .build();
            List<ClientPage> list = clientPageRepository.list(clientPageQueryForm);
            if (!CollectionUtils.isEmpty(list)) {
                throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.CLIENT_PAGE_EXIST_ERROR.getCode(),
                        ErrorEnum.CLIENT_PAGE_EXIST_ERROR.getMsg()));
            }
        }
    }

    /**
     * 删除客户端页面
     *
     * @param pageId
     * @return
     */
    public boolean removeClientPage(ClientPageId pageId, OperatorId operatorId) {
        return clientPageRepository.remove(pageId, operatorId);
    }

    /**
     * 更新客户端页面
     *
     * @return
     */
    public boolean updateClientPage(ClientPageForm clientPageForm) {
        ClientPage clientPage = ClientPageFactory.createClientPage(clientPageForm);
        return clientPageRepository.update(clientPage);
    }


}
