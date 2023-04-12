package com.idanchuang.cms.api.facade;

import com.idanchuang.component.base.JsonResult;
import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author lei.liu
 * @date 2021/10/9
 */
@FeignClient(value = "DC-RESOURCE", path = "/cms/lucky/component")
@Api(value = "抽奖组件服务", tags = {"抽奖组件服务"})
public interface LuckyComponentFacade {

    /**
     * 根据抽奖活动ID，获取关联的专题页别名
     * 若关联多个，返回最新的一个
     * @param activityId    抽奖活动ID
     * @return  专题别名
     */
    @GetMapping("/getLuckyComponentPageId")
    JsonResult<String> getLuckyComponentPageId(@RequestParam("activityId") Long activityId);
}
