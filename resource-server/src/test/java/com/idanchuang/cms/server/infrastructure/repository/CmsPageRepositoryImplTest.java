package com.idanchuang.cms.server.infrastructure.repository;

import com.alibaba.fastjson.JSON;
import com.idanchuang.cms.server.domain.repository.CmsPageRepository;
import com.idanchuang.resource.server.SpringTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author lei.liu
 * @date 2021/9/27
 */
@Slf4j
public class CmsPageRepositoryImplTest extends SpringTest {

    @Resource
    private CmsPageRepository cmsPageRepository;

    @Test
    public void getSubjectByGoodsEnable() {
        //List<Long> subjectByGoodsEnable = cmsPageRepository.getSubjectByGoodsEnable(LocalDateTime.now(), 1L);
        //log.info(JSON.toJSONString(subjectByGoodsEnable));
    }
}