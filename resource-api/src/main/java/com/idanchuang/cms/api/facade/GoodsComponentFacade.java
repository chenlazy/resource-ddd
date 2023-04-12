package com.idanchuang.cms.api.facade;

import com.idanchuang.cms.api.request.GoodsComponentQueryReq;
import com.idanchuang.cms.api.response.GoodsComponentPageDTO;
import com.idanchuang.cms.api.response.GoodsInfoDTO;
import com.idanchuang.cms.api.response.SubjectEnableGoodsInfoDTO;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.component.base.page.PageData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lei.liu
 * @date 2021/9/13
 */
@FeignClient(value = "DC-RESOURCE", path = "/cms/goods/component")
@Api(value = "商品组件服务", tags = {"商品组件服务"})
public interface GoodsComponentFacade {

    @GetMapping("/selectListForGoods")
    JsonResult<List<GoodsInfoDTO>> selectListForGoods(@RequestParam("componentId") Long componentId);

    @Deprecated
    @PostMapping("/selectPageForGoods")
    JsonResult<PageData<GoodsInfoDTO>> selectPageForGoods(@RequestBody GoodsComponentQueryReq goodsComponentQuery);

    /**
     * 获取专题自动下架商品列表
     * @param time
     * @return
     */
    @GetMapping("/getSubjectEnableGoods")
    @ApiOperation(value = "/getSubjectEnableGoods", notes = "获取专题自动下架商品列表", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<List<SubjectEnableGoodsInfoDTO>> getSubjectEnableGoods(@RequestParam("time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime time);

    /**
     * 更新专题自动下架状态
     * @param subjectId
     * @param enable
     * @return
     */
    @GetMapping("/updateSubjectEnable")
    @ApiOperation(value = "/updateSubjectEnable", notes = "更新专题自动下架状态", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult updateSubjectEnable(@RequestParam("subjectId") Long subjectId, @RequestParam("enable")Integer enable);

    @GetMapping("/getGoodsTagBySubjectId")
    @ApiOperation(value = "/getGoodsTagBySubjectId", notes = "根据专题id查询商品标签", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    JsonResult<Long> getGoodsTagBySubjectId(@RequestParam("subjectId") Long subjectId);

    @GetMapping("/getGoodsComponentPage")
    JsonResult<List<GoodsComponentPageDTO>> getGoodsComponentPage(@RequestParam("tagIdList") List<Integer> tagIdList);

}
