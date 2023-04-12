package com.idanchuang.cms.server.application.service.log;

import com.alibaba.fastjson.JSONObject;
import com.idanchuang.cms.server.domain.repository.CmsPageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-12 15:29
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Slf4j
@Component
public class PageUpdateOperateLogService extends AbstractOperateLogService {

    @Resource
    private CmsPageRepository cmsPageRepository;

    @Override
    public void writeLog(String field, StringBuilder bizNo, StringBuilder desc, String logType, Object objects) {
        JSONObject jsonObject = (JSONObject) objects;
        Integer templateId = (Integer) jsonObject.get("templateId");
        desc.setLength(0);
        if (templateId == null) {
            Integer id = (Integer) jsonObject.get(field);
            Integer pageId = cmsPageRepository.queryRecentlyCreationPageIdBy(id);
            desc.append("新增页面模版").append(pageId);
        } else {
            desc.append("编辑页面模版").append(templateId);
        }
        log.info("jsonObject:{}", jsonObject);
    }
}
