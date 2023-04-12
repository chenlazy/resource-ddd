package com.idanchuang.cms.server.interfaces.adcontentservice.controller;

import com.idanchuang.cms.server.application.adcontentservice.AppHotAreaService;
import com.idanchuang.cms.server.infrastructure.adcontentservice.common.constant.BaseConstant;
import com.idanchuang.cms.server.infrastructure.adcontentservice.util.BusinessException;
import com.idanchuang.cms.server.infrastructure.adcontentservice.util.ErrorEnum;
import com.idanchuang.cms.server.interfaces.adcontentservice.dto.HotAreaDTO;
import com.idanchuang.component.base.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author fym
 * @description :
 * @date 2021/2/23 3:53 下午
 */
@Api(tags = "首页热区-hw")
@RestController
@Slf4j
@RequestMapping(value = "/api/hotArea/")
public class HotAreaController {

    @Resource
    private AppHotAreaService hotAreaService;

    @GetMapping(value = "getByPosition")
    @ApiOperation(value = "首页热区-hw-1", notes = "hotArea", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonResult getByPosition(String position, Integer platform, HttpServletRequest request) {
        if (StringUtils.isBlank(position)) {
            throw new BusinessException(ErrorEnum.BAD_REQUEST);
        }
        //不传值默认为1
        platform = Optional.ofNullable(platform).orElse(1);
        List<List<HotAreaDTO>> hotAreaLineList = hotAreaService.getByPosition(position, platform, resolveTimePoint(request));
        Map<String, List<List<HotAreaDTO>>> hotArea = new HashMap<>(1);
        hotArea.put("hotArea", hotAreaLineList);
        return JsonResult.success(hotArea);
    }

    @GetMapping(value = "getAbmByPosition")
    @ApiOperation(value = "ABM首页热区-hw-1", notes = "hotArea", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonResult<List<HotAreaDTO>> getAbmByPosition(@NotNull(message = "位置编码不能为空") @RequestParam("position") String position, @NotNull(message = "registerCode不能为空") @RequestParam("registerCode") String registerCode, @NotNull(message = "parentCode不能为空") @RequestParam("parentCode") String parentCode, @RequestParam(value = "platform", required = false) Integer platform) {
        if (StringUtils.isBlank(position)) {
            throw new BusinessException(ErrorEnum.BAD_REQUEST);
        }
        //abm 投放渠道为2
        platform = Optional.ofNullable(platform).orElse(2);
        List<HotAreaDTO> hotAreaLineList = hotAreaService.getByAbmPosition(position, registerCode, parentCode, platform);
        return JsonResult.success(hotAreaLineList);
    }

    private LocalDateTime resolveTimePoint(HttpServletRequest request) {
        String type = request.getHeader("Compile-Type");
        String timestamp = request.getHeader("Timestamp");
        if (BaseConstant.INNER_PACKAGE_TYPE.equals(type) && StringUtils.isNotBlank(timestamp)) {
            Instant instant = Instant.ofEpochSecond(Long.valueOf(timestamp));
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        }
        return null;
    }

}
