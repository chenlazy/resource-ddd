package com.idanchuang.cms.server.application.service;

import com.idanchuang.cms.server.domain.model.cms.PageTag;
import com.idanchuang.cms.server.domain.model.cms.PageTagCondition;
import com.idanchuang.cms.server.domain.repository.PageTagRepository;
import com.idanchuang.component.base.page.PageData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-02 14:15
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Service
@Slf4j
public class PageTagService {

    @Resource
    private PageTagRepository pageTagRepository;

    public boolean insert(PageTag pageTag) {
        return pageTagRepository.insert(pageTag);
    }

    public boolean deleteById(Integer id) {
        return pageTagRepository.deleteById(id);
    }

    public boolean updateById(PageTag pageTag) {
        return pageTagRepository.updateById(pageTag);
    }


    public PageTag selectById(Integer id) {
        return pageTagRepository.selectById(id);
    }

    public PageData<PageTag> pageByCondition(PageTagCondition condition) {
        return pageTagRepository.pageByCondition(condition);
    }

    /**
     * 根据页面标签ID查询
     * @param idList    页面标签ID
     * @return  页面标签
     */
    public List<PageTag> selectList(List<Integer> idList) {
        PageTagCondition condition = new PageTagCondition();
        condition.setIdList(idList);
        return pageTagRepository.selectList(condition);
    }

    public boolean updatePlatform(Integer id) {
        return pageTagRepository.updatePlatform(id);
    }

}
