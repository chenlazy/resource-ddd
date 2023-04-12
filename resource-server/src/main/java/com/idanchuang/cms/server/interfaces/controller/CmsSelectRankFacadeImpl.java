package com.idanchuang.cms.server.interfaces.controller;

import com.idanchuang.cms.api.facade.CmsSelectRankFacade;
import com.idanchuang.cms.api.response.CmsSelectRankDTO;
import com.idanchuang.cms.server.domainNew.model.cms.external.selectInfo.CmsSelectRank;
import com.idanchuang.cms.server.domainNew.model.cms.external.selectInfo.CmsSelectRankService;
import com.idanchuang.cms.server.interfaces.assember.CmsSelectRankAssembler;
import com.idanchuang.component.base.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fym
 * @description :
 * @date 2022/2/14 下午1:57
 */
@RestController
@RequestMapping("/cms/select/rank")
@Slf4j
public class CmsSelectRankFacadeImpl implements CmsSelectRankFacade {

    @Resource
    private CmsSelectRankService cmsSelectRankService;

    @Override
    public JsonResult<List<CmsSelectRankDTO>> queryCmsSelectRankBySelectId(Integer selectType, Integer selectId) {
        List<CmsSelectRank> selectRankList = cmsSelectRankService.queryCmsSelectRankBySelectId(selectType, selectId);
        return JsonResult.success(selectRankList.stream().map(CmsSelectRankAssembler::modelToDTO).collect(Collectors.toList()));
    }
}
