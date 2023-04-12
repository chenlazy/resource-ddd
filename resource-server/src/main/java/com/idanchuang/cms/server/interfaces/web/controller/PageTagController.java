package com.idanchuang.cms.server.interfaces.web.controller;

import com.idanchuang.cms.api.facade.PageTagFacade;
import com.idanchuang.cms.api.request.PageTagAddRequest;
import com.idanchuang.cms.api.request.PageTagConditionReq;
import com.idanchuang.cms.api.request.PageTagQueryRequest;
import com.idanchuang.cms.api.request.PageTagUpdateRequest;
import com.idanchuang.cms.api.response.PageTagDTO;
import com.idanchuang.cms.api.response.PageTagListDTO;
import com.idanchuang.cms.server.application.remote.AdminInfoService;
import com.idanchuang.cms.server.interfaces.web.config.RequestContext;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.component.base.exception.core.ExDefinition;
import com.idanchuang.component.base.exception.core.ExFactory;
import com.idanchuang.component.base.exception.core.ExType;
import com.idanchuang.component.base.page.PageData;
import com.idanchuang.sso.model.dto.system.UserInfoDTO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-05 18:01
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@RestController
@RequestMapping("/cms/subject/tag")
@Slf4j
public class PageTagController {

    @Resource
    private PageTagFacade pageTagFacade;

    @Resource
    private AdminInfoService adminInfoService;

    @PostMapping("/add")
    @ApiOperation(value = "新增页面标签")
    public JsonResult<Boolean> insert(@RequestBody PageTagAddRequest req) {
        PageTagDTO dto = new PageTagDTO();
        dto.setName(req.getName());
        dto.setPlatform(toPlatformCode(req.getPlatform()));
        dto.setOperatorId(RequestContext.getUserId());
        dto.setCreateTime(LocalDateTime.now());
        dto.setUpdateTime(LocalDateTime.now());
        dto.setPageCode(req.getPageCode());
        return pageTagFacade.insert(dto);
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改页面标签")
    public JsonResult<Boolean> updateById(@RequestBody PageTagUpdateRequest req) {
        PageTagDTO dto = new PageTagDTO();
        dto.setId(req.getId());
        dto.setName(req.getName());
        dto.setOperatorId(RequestContext.getUserId());
        dto.setUpdateTime(LocalDateTime.now());
        return pageTagFacade.updateById(dto);
    }

    @PostMapping("/pageList")
    @ApiOperation(value = "分页查询页面标签")
    public JsonResult<PageData<PageTagListDTO>> pageByCondition(@RequestBody PageTagQueryRequest req) {
        PageTagConditionReq condition = new PageTagConditionReq();
        condition.setPageNum(req.getCurrent());
        condition.setPageSize(req.getSize());
        condition.setPlatform(toPlatformCode(req.getPlatform()));
        JsonResult<PageData<PageTagDTO>> result = pageTagFacade.pageByCondition(condition);
        if (!result.isSuccess()) {
            log.error("调用专题标签后台服务 pageTagFacade#pageByCondition RPC接口出现错误,code:{},message:{}", result.getCode(), result.getMsg());
            throw ExFactory.throwWith(new ExDefinition(ExType.BUSINESS_ERROR, result.getCode(), result.getMsg()));
        }

        List<PageTagListDTO> pageTagList = null;
        PageData<PageTagDTO> pageInfo = result.getData();
        if (!CollectionUtils.isEmpty(result.getData().getRecords())) {
            pageTagList = result.getData().getRecords().stream().map(this::convertOf).collect(Collectors.toList());
            List<Integer> operatorIdList = pageTagList.stream().map(PageTagListDTO::getOperatorId).collect(Collectors.toList());
            Map<Long, UserInfoDTO> userInfoMap = adminInfoService.getUserInfoMap(operatorIdList);
            //Map<Long, String> operatorNameMap = adminInfoService.getOperatorName(operatorIdList);
            for (PageTagListDTO dto : pageTagList) {
                UserInfoDTO userInfo = userInfoMap.get(dto.getOperatorId().longValue());
                String nickName = userInfo != null ? userInfo.getNickname() : "未知";
                dto.setOperator(nickName);
            }
        }
        return JsonResult.success(PageData.of(pageTagList, pageInfo.getSize(), pageInfo.getTotal()));
    }

    private int toPlatformCode(String platformName) {
        if (StringUtils.hasText(platformName)) {
            platformName = platformName.toUpperCase();

            if ("VTN".equals(platformName)) {
                return 0;
            } else if ("ABM".equals(platformName)) {
                return 1;
            }
        }
        return 0;
    }

    private PageTagListDTO convertOf(PageTagDTO source) {
        PageTagListDTO target = new PageTagListDTO();
        target.setId(source.getId());
        target.setName(source.getName());
        target.setOperatorId(source.getOperatorId());
        target.setUpdatedAt(source.getUpdateTime());
        target.setPageCode(source.getPageCode());
        return target;
    }
}
