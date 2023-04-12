package com.idanchuang.cms.server.applicationNew.service;

import com.idanchuang.cms.server.application.enums.ErrorEnum;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.Catalogue;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueFactory;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueId;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueRepository;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueStatus;
import com.idanchuang.cms.server.domainNew.model.cms.catalogue.CatalogueType;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPage;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPageId;
import com.idanchuang.cms.server.domainNew.model.cms.clientPage.ClientPageRepository;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.Masterplate;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateRepository;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateUpsertForm;
import com.idanchuang.cms.server.domainNew.shard.OperatorId;
import com.idanchuang.component.base.exception.core.ExDefinition;
import com.idanchuang.component.base.exception.core.ExType;
import com.idanchuang.component.base.exception.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-12-16 15:14
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Service
@Slf4j
public class CatalogueService {

    @Resource
    private CatalogueRepository catalogueRepository;

    @Resource
    private MasterplateRepository masterplateRepository;

    @Resource
    private MasterplateService masterplateService;

    @Resource
    private PageRenderService pageRenderService;

    @Resource
    private ClientPageRepository clientPageRepository;

    /**
     * 创建空目录
     *
     * @param operatorId
     * @return
     */
    public CatalogueId createCatalogue(OperatorId operatorId, ClientPageId clientPageId) {

        ClientPage clientPage = clientPageRepository.query(clientPageId);

        if (null == clientPage) {
            return null;
        }

        //默认创建是活动页，设置pageCode为空字符
        Catalogue catalogue = CatalogueFactory.createCatalogue(null, "", null, operatorId, clientPageId,
                CatalogueType.SUBJECT, CatalogueStatus.CATALOGUE_STATUS_DRAFT, "", null, null);

        catalogueRepository.storeCatalogue(catalogue);

        return catalogue.getId();
    }

    /**
     * 删除目录
     *
     * @param catalogueId
     * @param operatorId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeCatalogue(CatalogueId catalogueId, OperatorId operatorId) {

        //查询目录
        Catalogue catalogue = catalogueRepository.getCatalogueById(catalogueId);
        if (null == catalogue) {
            throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.CATALOGUE_EXIST_ERROR.getCode(),
                    ErrorEnum.CATALOGUE_EXIST_ERROR.getMsg()));
        }

        //查询目录下的所有模版列表
        List<Masterplate> masterplateList = masterplateRepository.getMasterplateList(catalogueId);

        //删除所有实例
        for (Masterplate masterplate : masterplateList) {
            masterplateService.removeMasterplate(masterplate.getId(), operatorId, false);
        }

        //删除目录
        catalogueRepository.removeCatalogue(catalogueId, operatorId);

        //删除缓存
        pageRenderService.deletePageRender(catalogueId);
    }


    /**
     * 更新目录信息
     *
     * @param upsertForm
     */
    public Catalogue updateCatalogue(MasterplateUpsertForm upsertForm) {


        //校验目录是否存在
        Catalogue catalogue = catalogueRepository.getCatalogueById(upsertForm.getCatalogueId());

        if (null == catalogue) {
            throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.CATALOGUE_EXIST_ERROR.getCode(),
                    ErrorEnum.CATALOGUE_EXIST_ERROR.getMsg()));
        }

        //创建目录数据 默认创建的都是专题页面
        Catalogue fillCatalogue = CatalogueFactory.createCatalogue(upsertForm.getCatalogueId(),
                upsertForm.getBackEndTitle(), catalogue.getPageCode(), upsertForm.getOperatorId(),
                catalogue.getPageId(), catalogue.getCatalogueType(), CatalogueStatus.CATALOGUE_STATUS_PUBLISH,
                catalogue.generateAliasTitle(upsertForm.getPlatform()), upsertForm.getPlatform(), null);

        //更新目录信息
        catalogueRepository.updateCatalogue(fillCatalogue);
        return fillCatalogue;
    }

    public boolean changeType(CatalogueId catalogueId) {
        Catalogue catalogue = catalogueRepository.getCatalogueById(catalogueId);
        ClientPage clientPage = null;
        // h5转原生页面时校验
        if (CatalogueType.SUBJECT.equals(catalogue.getCatalogueType())) {
            // pageCode校验
            clientPage = clientPageRepository.query(catalogue.getPageId());
            if (clientPage.getPageCode() == null) {
                throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.CLIENT_PAGE_NOT_EXIST_ERROR.getCode(),
                        ErrorEnum.CLIENT_PAGE_NOT_EXIST_ERROR.getMsg()));
            }
            // 唯一性校验，pageCode+type
            Catalogue clientPageCatalogue = catalogueRepository.getCatalogueByCode(clientPage.getPageCode(), CatalogueType.CLIENT_PAGE);
            if (clientPageCatalogue != null) {
                throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.CLIENT_PAGE_TYPE_EXIST_ERROR.getCode(),
                        ErrorEnum.CLIENT_PAGE_TYPE_EXIST_ERROR.getMsg()));
            }
        }
        // 修改页面类型
        catalogue.changeType(Optional.ofNullable(clientPage).map(ClientPage::getPageCode).orElse(null));
        // 落库
        return catalogueRepository.updateCatalogue(catalogue) > 0;
    }
}
