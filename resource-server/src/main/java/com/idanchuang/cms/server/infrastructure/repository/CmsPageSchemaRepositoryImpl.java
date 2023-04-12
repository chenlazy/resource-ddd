package com.idanchuang.cms.server.infrastructure.repository;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.idanchuang.cms.server.application.enums.ErrorEnum;
import com.idanchuang.cms.server.domain.model.cms.*;
import com.idanchuang.cms.server.domain.model.cms.schema.SchemaCode;
import com.idanchuang.cms.server.domain.model.cms.schema.SchemaId;
import com.idanchuang.cms.server.domain.repository.CmsPageRepository;
import com.idanchuang.cms.server.domain.repository.CmsPageSchemaRepository;
import com.idanchuang.cms.server.infrastructure.persistence.mapper.CmsPageMapper;
import com.idanchuang.cms.server.infrastructure.persistence.mapper.CmsPageSchemaMapper;
import com.idanchuang.cms.server.infrastructure.persistence.model.CmsPageDO;
import com.idanchuang.cms.server.infrastructure.persistence.model.CmsPageDetailDO;
import com.idanchuang.cms.server.infrastructure.persistence.model.CmsPageSchemaDO;
import com.idanchuang.cms.server.infrastructure.persistence.model.CmsPageSchemaListDO;
import com.idanchuang.cms.server.infrastructure.transfer.CmsPageSchemaTransfer;
import com.idanchuang.component.base.exception.core.ExDefinition;
import com.idanchuang.component.base.exception.core.ExType;
import com.idanchuang.component.base.exception.exception.BusinessException;
import com.idanchuang.component.base.page.PageData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-01 17:32
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Repository
@Slf4j
public class CmsPageSchemaRepositoryImpl implements CmsPageSchemaRepository {

    @Resource
    private CmsPageSchemaMapper pageSchemaMapper;

    @Resource
    private CmsPageMapper cmsPageMapper;

    @Resource
    private CmsPageRepository cmsPageRepository;

    @Override
    public int savePageSchema(CmsPageSchema cmsPageSchema) {
        CmsPageSchemaDO cmsPageSchemaDO = CmsPageSchemaTransfer.entityToDO(cmsPageSchema);
        int insert = pageSchemaMapper.insert(cmsPageSchemaDO);
        cmsPageSchema.setId(cmsPageSchemaDO.getId());
        return insert;
    }

    @Override
    public int batchSavePageSchema(List<CmsPageSchema> cmsPageSchemaList) {
        if (CollectionUtils.isEmpty(cmsPageSchemaList)) {
            return 0;
        }
        List<CmsPageSchemaDO> schemaDOS = cmsPageSchemaList.stream().map(CmsPageSchemaTransfer::entityToDO).collect(Collectors.toList());
        int insertBatch = pageSchemaMapper.insertBatch(schemaDOS);
        int size = schemaDOS.size();
        for (int i = 0; i < size; i++) {
            CmsPageSchemaDO entity = schemaDOS.get(i);
            if (entity != null) {
                cmsPageSchemaList.get(i).setId(entity.getId());
            }
        }
        return insertBatch;
    }

    @Override
    public CmsPageSchema getPageSchema(Integer pageSchemaId) {
        CmsPageSchemaDO cmsPageSchemaDO = pageSchemaMapper.selectById(pageSchemaId);
        return CmsPageSchemaTransfer.doToEntity(cmsPageSchemaDO);
    }

    @Override
    public SchemaId getPageSchemaIdByCode(SchemaCode schemaCode) {

        Long schemaIdValue = pageSchemaMapper.selectSchemaIdByCode(schemaCode.getValue());

        return schemaIdValue == null ? null : new SchemaId(schemaIdValue);
    }

    @Override
    public int updatePageSchema(CmsPageSchema cmsPageSchema) {
        return pageSchemaMapper.updateById(CmsPageSchemaTransfer.entityToDO(cmsPageSchema));
    }

    @Override
    public PageData<CmsPageSchemaList> queryPageSchemaList(CmsCorePageList cmsCorePageList) {
        Page<Object> page = PageHelper.startPage(cmsCorePageList.getCurrent().intValue(), cmsCorePageList.getSize().intValue());
        List<CmsPageSchemaListDO> listDOS = pageSchemaMapper.queryByCondition(cmsCorePageList);
        if (CollectionUtils.isEmpty(listDOS)) {
            return new PageData<>();
        }
        List<CmsPageSchemaList> lists = listDOS.stream().map(CmsPageSchemaTransfer::doToEntity).collect(Collectors.toList());
        return new PageData<>(lists, cmsCorePageList.getCurrent(), cmsCorePageList.getSize(), page.getTotal());
    }

    @Override
    public Boolean removePageSchema(Integer id, Long operator) {
        return pageSchemaMapper.removePageSchema(id, operator);
    }

    @Override
    public CmsPageDetail getDetailById(Integer id, Integer templateId) {
        CmsPageDetailDO pageDetailDO = pageSchemaMapper.getDetailById(id);
        if (pageDetailDO == null) {
            return null;
        }

        //优先获取模版中的id
        Integer pageId;
        if (templateId == null) {
            List<CmsPageDO> cmsPageList = cmsPageMapper.getCmsPageList(id);
            if (CollectionUtils.isEmpty(cmsPageList)) {
                return null;
            }

            //获取id值最大的模版id
            cmsPageList.sort((o1, o2) -> o2.getId().compareTo(o1.getId()));
            CmsPageDO cmsPageDO = cmsPageList.get(0);
            pageId = cmsPageDO.getId();

            //当前时间在生效时间段内的模板，如果当前时间点存在多个生效中模板，则取生效时间距离当前时间点最近的模板，生效时间相同情况下取模板ID值较大的模板
            cmsPageList = cmsPageList.stream().filter(e -> (e.getStartTime() == null || e.getStartTime().compareTo(LocalDateTime.now()) <= 0)
                    && (e.getEndTime() == null || e.getEndTime().compareTo(LocalDateTime.now()) > 0)).collect(Collectors.toList());

            if (!CollectionUtils.isEmpty(cmsPageList)) {
                cmsPageList.sort((o1, o2) -> {
                    if (null == o2.getStartTime()) {
                        return -1;
                    }else if (o2.getStartTime().compareTo(o1.getStartTime()) == 0) {
                        return o2.getId().compareTo(o1.getId());
                    } else {
                        return o2.getStartTime().compareTo(o1.getStartTime());
                    }
                });
                CmsPageDO pageDO = cmsPageList.get(0);
                pageId = pageDO.getId();
            }
        } else {
            pageId = templateId;
        }

        CmsPage cmsPage = cmsPageRepository.selectCmsPageById(pageId);

        if (null == cmsPage || !cmsPage.getPageSchemaId().equals(id)) {
            throw new BusinessException(new ExDefinition(ExType.BUSINESS_ERROR, ErrorEnum.PAGE_TEMPLATE_ERROR.getCode(),
                    ErrorEnum.PAGE_TEMPLATE_ERROR.getMsg()));
        }

        pageDetailDO.setSort(cmsPage.getSort());
        pageDetailDO.setPageId(cmsPage.getId());
        pageDetailDO.setPageTitle(cmsPage.getPageName());
        pageDetailDO.setBackEndTitle(cmsPage.getBackEndTitle());
        pageDetailDO.setShareFlag(cmsPage.getShareFlag());
        pageDetailDO.setShareJson(cmsPage.getShareJson());
        pageDetailDO.setStartTime(cmsPage.getStartTime());
        pageDetailDO.setEndTime(cmsPage.getEndTime());
        pageDetailDO.setPlatform(cmsPage.getPlatform());
        pageDetailDO.setAliasTitle(cmsPage.getAliasTitle());


        return CmsPageSchemaTransfer.doToEntity(pageDetailDO);
    }

    @Override
    public List<CmsPageSchemaListDO> queryByCondition(CmsCorePageList condition) {
        return pageSchemaMapper.queryByCondition(condition);
    }

}
