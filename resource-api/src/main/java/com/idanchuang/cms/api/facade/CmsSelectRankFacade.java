package com.idanchuang.cms.api.facade;

import com.idanchuang.cms.api.response.CmsSelectRankDTO;
import com.idanchuang.component.base.JsonResult;
import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author fym
 * @description :
 * @date 2022/2/14 下午1:55
 */
@FeignClient(value = "DC-RESOURCE", path = "/cms/select/rank")
@Api(value = "CMS圈选服务", tags = {"CMS圈选服务"})
public interface CmsSelectRankFacade {

    /**
     * 根据圈选类型和id查询关联页面和页面模版
     *
     *
     * @param selectType 圈选类型 1 营销活动 2 福利星期三 3 优惠券 4 abm活动
     * @param selectId   圈选id
     * @return
     */
    @GetMapping("/queryCmsSelectRankBySelectId")
    JsonResult<List<CmsSelectRankDTO>> queryCmsSelectRankBySelectId(@RequestParam("selectType") Integer selectType, @RequestParam("selectId") Integer selectId);
}
