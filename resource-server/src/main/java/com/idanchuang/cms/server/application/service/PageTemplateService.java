package com.idanchuang.cms.server.application.service;

import com.idanchuang.cms.server.domain.repository.PageTemplateRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 页面渲染
 * @author lei.liu
 * @date 2021/9/10
 */
@Service
public class PageTemplateService {

    @Resource
    private PageTemplateRepository pageTemplateRepository;

    public void buildPageVersion(Integer id) {
        pageTemplateRepository.buildPageVersion(id);
    }

    public void deletePageVersion(Integer pageId) {
        pageTemplateRepository.deletePageVersion(pageId);
    }

    public void deletePageVersion(Integer pageId, String version) {
        pageTemplateRepository.deletePageVersion(pageId, version);
    }

}
