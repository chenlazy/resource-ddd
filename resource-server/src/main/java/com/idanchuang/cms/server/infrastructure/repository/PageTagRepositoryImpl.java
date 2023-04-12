package com.idanchuang.cms.server.infrastructure.repository;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.idanchuang.cms.server.domain.model.cms.PageTag;
import com.idanchuang.cms.server.domain.model.cms.PageTagCondition;
import com.idanchuang.cms.server.domain.repository.PageTagRepository;
import com.idanchuang.cms.server.infrastructure.persistence.mapper.PageTagMapper;
import com.idanchuang.cms.server.infrastructure.persistence.model.PageTagDO;
import com.idanchuang.component.base.page.PageData;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-01 17:33
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Repository
public class PageTagRepositoryImpl implements PageTagRepository {

    @Resource
    private PageTagMapper pageTagMapper;

    @Override
    public boolean insert(PageTag pageTag) {
        return pageTagMapper.insert(convertOf(pageTag)) > 0;
    }

    @Override
    public boolean deleteById(Integer id) {
        return pageTagMapper.deleteById(id) > 0;
    }

    @Override
    public boolean updateById(PageTag pageTag) {
        if (pageTag == null || pageTag.getId() == null) {
            return false;
        }
        return pageTagMapper.updateById(convertOf(pageTag)) > 0;
    }

    @Override
    public PageData<PageTag> pageByCondition(PageTagCondition condition) {
        Page<Object> page = PageHelper.startPage(condition.getPageNum(), condition.getPageSize());
        List<PageTag> pageTagList = selectList(condition);
        return PageData.of(pageTagList, page.getPageNum(), page.getPageSize(), page.getTotal());
    }

    @Override
    public List<PageTag> selectList(PageTagCondition condition) {
        List<PageTagDO> pageTagList = pageTagMapper.selectByCondition(condition);
        return CollectionUtils.isEmpty(pageTagList) ? null : pageTagList.stream().map(this::convertOf).collect(Collectors.toList());
    }

    @Override
    public boolean updatePlatform(Integer id) {
        return pageTagMapper.updatePlatform(id) > 0;
    }

    @Override
    public PageTag selectById(Integer id) {
        return convertOf(pageTagMapper.selectById(id));
    }


    private PageTag convertOf(PageTagDO source) {
        return PageTag.builder()
                .id(source.getId())
                .name(source.getName())
                .createTime(source.getCreateTime())
                .updateTime(source.getUpdateTime())
                .operatorId(source.getOperatorId())
                .platform(source.getPlatform()).build();
    }

    private PageTagDO convertOf(PageTag source) {
        return PageTagDO.builder()
                .id(source.getId())
                .name(source.getName())
                .createTime(source.getCreateTime())
                .updateTime(source.getUpdateTime())
                .operatorId(source.getOperatorId())
                .platform(source.getPlatform()).build();
    }
}
