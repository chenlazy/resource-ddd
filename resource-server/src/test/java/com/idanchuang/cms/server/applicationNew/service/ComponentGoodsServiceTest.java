package com.idanchuang.cms.server.applicationNew.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.idanchuang.cms.api.response.GoodsComponentPageDTO;
import com.idanchuang.cms.api.response.GoodsInfoDTO;
import com.idanchuang.cms.api.response.SubjectEnableGoodsInfoDTO;
import com.idanchuang.cms.server.domain.model.cms.ActivityPage;
import com.idanchuang.resource.server.SpringTest;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Sets;
import org.junit.Test;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author fym
 * @description :
 * @date 2022/1/11 上午9:41
 */
@Slf4j
public class ComponentGoodsServiceTest extends SpringTest {

    @Resource
    private ComponentGoodsService componentGoodsService;

    @Test
    public void selectListForGoods() {
        List<GoodsInfoDTO> goodsInfoDTOS = componentGoodsService.selectListForGoods(1632723109821L);
        log.info(JSON.toJSONString(goodsInfoDTOS));
    }

    @Test
    public void getSubjectGoodsList() {
        List<SubjectEnableGoodsInfoDTO> subjectGoodsList = componentGoodsService.getSubjectGoodsList(LocalDateTime.now());
        log.info(JSON.toJSONString(subjectGoodsList));

    }

    @Test
    public void updateSubjectEnable() {
        componentGoodsService.updateSubjectEnable(2171L, 1);
    }

    @Test
    public void getGoodsComponentPage() {
        List<GoodsComponentPageDTO> goodsComponentPage = componentGoodsService.getGoodsComponentPage(Lists.newArrayList(1000013));
        log.info(JSON.toJSONString(goodsComponentPage));
    }

    @Test
    public void getGoodsTagBySubjectId() {
        Long goodsTagBySubjectId = componentGoodsService.getGoodsTagBySubjectId(1000013L);
        log.info(JSON.toJSONString(goodsTagBySubjectId));
    }

    @Test
    public void queryActivityOnSubjectListBySpuId() {
        Set<Long> spuIds = Sets.newHashSet();
        spuIds.add(2000L);
        Map<Long, List<ActivityPage>> longListMap = componentGoodsService.queryActivityOnSubjectListBySpuId(spuIds);
        System.out.println(longListMap);
    }
}