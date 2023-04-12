package com.idanchuang.cms.server.application.service.log;

import com.idanchuang.cms.server.domain.model.cms.CmsPage;
import com.idanchuang.cms.server.domain.repository.CmsPageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-12 15:28
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Slf4j
@Component
public class PageDeleteOperateLogService extends AbstractOperateLogService {

    @Resource
    private CmsPageRepository cmsPageRepository;

    @Override
    public void writeLog(String field, StringBuilder bizNo, StringBuilder desc, String logType, Object objects) {
        Map paramMap = (Map) objects;
        String s = "";
        if (paramMap.get(field).getClass() == String[].class) {
            String[] s1 = (String[]) paramMap.get(field);
            s += s1[0];
        } else {
            s += paramMap.get(field);
        }
        CmsPage cmsPage = cmsPageRepository.selectByIdAndDelete(Integer.valueOf(s));
        if (cmsPage != null) {
            desc.append(bizNo);
            bizNo.setLength(0);
            bizNo.append(cmsPage.getPageSchemaId());
        } else {
            log.warn("PageDeleteOperateLogService warn field:{} bizNo:{} desc:{} logType:{} objects:{}", field, bizNo, desc, logType, objects);
        }
    }
}
