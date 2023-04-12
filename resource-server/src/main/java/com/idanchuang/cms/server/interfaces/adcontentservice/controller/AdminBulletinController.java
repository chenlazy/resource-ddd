package com.idanchuang.cms.server.interfaces.adcontentservice.controller;


import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import com.idanchuang.cms.server.infrastructure.adcontentservice.util.BusinessException;
import com.idanchuang.cms.server.interfaces.adcontentservice.dto.BulletinQueryRequest;
import com.idanchuang.cms.server.interfaces.adcontentservice.dto.BulletinRequest;
import com.idanchuang.cms.server.interfaces.adcontentservice.facade.AdminBulletinFacade;
import com.idanchuang.cms.server.interfaces.adcontentservice.vo.BulletinDetailDTO;
import com.idanchuang.cms.server.interfaces.adcontentservice.vo.BulletinPageDTO;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.component.base.page.PageData;
import com.idanchuang.member.point.api.entity.dto.assist.Create;
import com.idanchuang.member.point.api.entity.dto.assist.Update;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.groups.Default;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 公告 前端控制器
 * </p>
 *
 * @author xf
 * @since 2020-12-02
 */
@Api(tags = "站内公告管理服务")
@Slf4j
@RestController
@RequestMapping("/bulletin")
public class AdminBulletinController {


    @Resource
    private AdminBulletinFacade adminBulletinFacade;

    /**
     * 公告列表
     *
     * @return
     */
    @GetMapping("/selectListBulletin")
    public JsonResult<Object> selectListBulletin(
            String platformType,
            String position,
            String level,
            String state,
            Long current,
            Long size
    ) {
        BulletinQueryRequest req = new BulletinQueryRequest();
        req.setPlatformType(NumberUtils.isNumber(platformType) ? Integer.parseInt(platformType) : null);
        req.setPosition(NumberUtils.isNumber(position) ? Integer.parseInt(position) : null);
        req.setLevel(NumberUtils.isNumber(level) ? Integer.parseInt(level) : null);
        req.setState(NumberUtils.isNumber(state) ? Integer.parseInt(state) : null);
        req.setCurrent(current);
        req.setSize(size);

        JsonResult<PageData<BulletinPageDTO>> result = null;
        try {
            result = adminBulletinFacade.selectListBulletin(req);
        } catch (Exception e) {
            log.error("公告创建接口调用失败,request:{}", JSONUtil.toJsonStr(req));
            throw new BusinessException(e.getMessage());
        }
        if (!result.isSuccess()) {
            log.error("公告列表列表调用失败,response:{}", new Gson().toJson(result));
            throw new BusinessException(result.getCode(), result.getMsg());
        }
        List<BulletinPageDTO> records = result.getData().getRecords().stream().map(vo -> {
            BulletinPageDTO dto = new BulletinPageDTO();
            BeanUtils.copyProperties(vo, dto);
            dto.setTargetLevels(null);
            dto.setLevel(Arrays.asList(vo.getTargetLevels().split(",")));
            return dto;
        }).collect(Collectors.toList());
        return JsonResult.success(MapUtil.builder().put
                ("bulletin", MapUtil.builder().put(
                        "bulletinList", MapUtil.builder().put
                                ("count", result.getData().getTotal()).put
                                ("list", records).build()).build()).build());
    }


    /**
     * 新增公告
     *
     * @param bulletinRequest
     * @return
     */
    @PostMapping("/createBulletin")
    public JsonResult<Object> createBulletin(@Validated({Create.class, Default.class}) @RequestBody BulletinRequest bulletinRequest) {
        JsonResult<Object> result = null;
        try {
            result = adminBulletinFacade.createBulletin(bulletinRequest);
        } catch (Exception e) {
            log.error("公告创建接口调用失败,request:{}", JSONUtil.toJsonStr(bulletinRequest), e);
            throw new BusinessException(e.getMessage());
        }
        if (!result.isSuccess()) {
            log.error("公告创建接口调用失败,response:{}", new Gson().toJson(result));
            return result;
        }
        return JsonResult.success(MapUtil.builder().put("bulletin", MapUtil.builder().put("addBulletin", MapUtil.builder().put("code", 0).build()).build()).build());
    }

    /**
     * 公告详情
     *
     * @param id
     * @return
     */
    @GetMapping("/getDetailBulletinById")
    public JsonResult<Object> getDetailBulletinById(@RequestParam("id") Integer id) {
        JsonResult<BulletinDetailDTO> result = null;
        try {
            result = adminBulletinFacade.getDetailBulletinById(id);
        } catch (Exception e) {
            log.error("公告详情接口调用失败,request:{}", id);
            throw new BusinessException(e.getMessage());
        }
        if (!result.isSuccess()) {
            log.error("公告详情接口调用失败,response:{}", JSONUtil.toJsonStr(result));
            throw new BusinessException(result.getCode(), result.getMsg());
        }
        return JsonResult.success(MapUtil.builder().put("bulletin", MapUtil.builder().put("bulletinDetail", result.getData()).build()).build());
    }

    /**
     * 更新公告
     *
     * @param bulletinRequest
     * @return
     */
    @PutMapping("/updateBulletin")
    public JsonResult<Object> updateBulletin(@Validated({Update.class, Default.class}) @RequestBody BulletinRequest bulletinRequest) {
        JsonResult<Object> result;
        try {
            result = adminBulletinFacade.updateBulletin(bulletinRequest);
        } catch (Exception e) {
            log.error("公告详情接口调用失败,param:{}", JSONUtil.toJsonStr(bulletinRequest), e);
            throw new BusinessException(e.getMessage());
        }
        if (!result.isSuccess()) {
            log.error("公告详情接口调用失败,param:{},code:{},message:{}", new Gson().toJson(result), result.getCode(), result.getMsg());
            throw new BusinessException(result.getCode(), result.getMsg());
        }
        return JsonResult.success(MapUtil.builder().put("bulletin", MapUtil.builder().put("editBulletin", MapUtil.builder().put("code", 0).build()).build()).build());
    }

    /**
     * 删除公告
     *
     * @param id
     * @return
     */
    @DeleteMapping("/deleteBulletinById")
    public JsonResult<Object> deleteBulletinById(@RequestParam("id") Integer id) {
        JsonResult<Boolean> result = null;
        try {
            result = adminBulletinFacade.deleteBulletinById(id);
        } catch (Exception e) {
            log.error("删除公告失败,request:{}", id);
            throw new BusinessException(e.getMessage());
        }
        if (!result.isSuccess()) {
            log.error("删除公告失败,response:{},", JSONUtil.toJsonStr(result));
            throw new BusinessException(result.getCode(), result.getMsg());
        }
        return JsonResult.success(MapUtil.builder().put("bulletin", MapUtil.builder().put("deleteBulletin", MapUtil.builder().put("code", 0).build()).build()).build());
    }

}
