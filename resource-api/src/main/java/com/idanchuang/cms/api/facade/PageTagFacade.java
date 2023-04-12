package com.idanchuang.cms.api.facade;

import com.idanchuang.cms.api.request.PageTagConditionReq;
import com.idanchuang.cms.api.response.PageTagDTO;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.component.base.page.PageData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-02 17:05
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */

//@Api(value = "CMS页面标签服务", tags = {"CMS页面标签服务"})
public interface PageTagFacade {

    @PostMapping("/insert")
    @ApiOperation(value = "新增页面标签")
    JsonResult<Boolean> insert(@RequestBody PageTagDTO pageTag);

    @PostMapping("/deleteById")
    @ApiOperation(value = "删除页面标签")
    JsonResult<Boolean> deleteById(@RequestParam("id") Integer id);

    @PostMapping("/updateById")
    @ApiOperation(value = "修改页面标签")
    JsonResult<Boolean> updateById(@RequestBody PageTagDTO pageTag);

    @PostMapping("/pageByCondition")
    @ApiOperation(value = "分页查询页面标签")
    JsonResult<PageData<PageTagDTO>> pageByCondition(@RequestBody PageTagConditionReq condition);


    @GetMapping("/update")
    JsonResult<Boolean> update(@RequestParam("id") Integer id);

    @GetMapping("/selectById")
    JsonResult<PageTagDTO> selectById(@RequestParam("id") Integer id);
}
