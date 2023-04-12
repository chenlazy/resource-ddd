package com.idanchuang.cms.api.facade;

import com.idanchuang.cms.api.request.ContainerComponentConditionReq;
import com.idanchuang.cms.api.response.ContainerComponentDTO;
import com.idanchuang.component.base.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-02 17:04
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@FeignClient(value = "DC-RESOURCE", path = "/cms/container/component")
//@Api(value = "CMS组件实例服务", tags = {"CMS组件实例服务"})
public interface ContainerComponentFacade {

    @PostMapping("/insert")
    @ApiOperation(value = "新增组件实例")
    JsonResult<Boolean> insert(@RequestBody ContainerComponentDTO dto);

    @PostMapping("/insertBatch")
    @ApiOperation(value = "批量新增组件实例")
    JsonResult<Boolean> insertBatch(@RequestBody List<ContainerComponentDTO> dtoList);

    @GetMapping("/selectById")
    @ApiOperation(value = "获取组件实例")
    JsonResult<ContainerComponentDTO> selectById(@RequestParam("id") Long id);

    @PostMapping("/selectByCondition")
    @ApiOperation(value = "查询组件实例")
    JsonResult<List<ContainerComponentDTO>> selectByCondition(@RequestBody ContainerComponentConditionReq condition);
}
