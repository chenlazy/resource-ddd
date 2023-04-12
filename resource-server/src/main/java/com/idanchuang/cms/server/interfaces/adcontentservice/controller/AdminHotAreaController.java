package com.idanchuang.cms.server.interfaces.adcontentservice.controller;

import com.idanchuang.cms.server.interfaces.adcontentservice.dto.HotAreaRequest;
import com.idanchuang.cms.server.interfaces.adcontentservice.dto.ListHotAreaRequest;
import com.idanchuang.cms.server.interfaces.adcontentservice.facade.AdminHotAreaFacade;
import com.idanchuang.cms.server.interfaces.adcontentservice.vo.HotAreaVO;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.component.base.page.PageData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zhousun
 * @create 2020/11/30
 */
@RestController
@RequestMapping("/hotArea")
@Api(value = "首页热区",tags = {"首页热区"})
public class AdminHotAreaController {

    @Resource
    private AdminHotAreaFacade hotAreaService;

    /**
     * 首页热区列表
     * @param request
     * @return
     */
    @ApiOperation("首页热区列表")
    @PostMapping("/pageList")
    public JsonResult<PageData<HotAreaVO>> pageListHotArea(@RequestBody ListHotAreaRequest request) {
        return hotAreaService.listHotArea(request);
    }

    /**
     * 添加首页热区
     * @param request
     * @return
     */
    @ApiOperation("添加首页热区")
    @PostMapping("/add")
    public JsonResult<Integer> addHotArea(@RequestBody HotAreaRequest request) {
        return hotAreaService.addHotArea(request);
    }

    /**
     * 插入首页热区
     * @param request
     * @return
     */
    @ApiOperation("插入首页热区")
    @PostMapping("/insert")
    public JsonResult<Integer> insertHotArea(@RequestBody HotAreaRequest request) {
        return hotAreaService.insertHotArea(request);
    }

    /**
     * 编辑首页热区
     * @param id
     * @param request
     * @return
     */
    @ApiOperation("编辑首页热区")
    @PutMapping("/edit/{id}")
    public JsonResult<Integer> editHotArea(@PathVariable("id") Integer id, @RequestBody HotAreaRequest request) {
        request.setShareTitle(request.getShareTitle() == null ? "" : request.getShareTitle());
        request.setShareDesc(request.getShareDesc() == null ? "" : request.getShareDesc());
        request.setShareImage(request.getShareImage() == null ? 0 : request.getShareImage());
        return hotAreaService.editHotArea(id, request);
    }

    /**
     * 删除首页热区
     * @param id
     * @return
     */
    @ApiOperation("删除首页热区")
    @DeleteMapping("/delete/{id}")
    public JsonResult<Integer> deleteHotArea(@PathVariable("id") Integer id) {
        return hotAreaService.deleteHotArea(id);
    }

}
