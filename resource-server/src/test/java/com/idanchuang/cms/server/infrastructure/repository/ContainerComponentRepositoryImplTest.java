package com.idanchuang.cms.server.infrastructure.repository;

import com.idanchuang.cms.server.domain.model.cms.ContainerComponent;
import com.idanchuang.cms.server.domain.model.cms.ContainerComponentCondition;
import com.idanchuang.cms.server.domain.repository.ContainerComponentRepository;
import com.idanchuang.resource.server.SpringTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author lei.liu
 * @date 2021/9/6
 */
@Slf4j
public class ContainerComponentRepositoryImplTest extends SpringTest {

    @Resource
    private ContainerComponentRepository containerComponentRepository;

    @Test
    public void insert() {
        boolean result = containerComponentRepository.insert(null);
        log.info("" + result);
    }

    @Test
    public void updateById() {
    }

    @Test
    public void selectById() {
    }

    @Test
    public void selectByCondition() {
        containerComponentRepository.selectByCondition(new ContainerComponentCondition());
    }
}