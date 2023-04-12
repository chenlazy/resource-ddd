package com.idanchuang.resource.server.application.remote;

import com.idanchuang.component.base.JsonResult;
import com.idanchuang.dealer.dataplat.api.feign.CommonSearchFeignClient;
import com.idanchuang.dealer.dataplat.api.model.commonquery.CommonPageData;
import com.idanchuang.dealer.dataplat.api.model.commonquery.DistinctSqlRequestDTO;
import com.idanchuang.dealer.dataplat.api.model.commonquery.KeyValueRequestDTO;
import com.idanchuang.dealer.dataplat.api.model.commonquery.PageRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-16 16:10
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Service
@Slf4j
public class RemoteCommonSearchService {

    @Resource
    private CommonSearchFeignClient commonSearchFeignClient;

    public CommonPageData<Map<String, Object>> pageSearch(PageRequestDTO pageRequestDTO) {
        JsonResult<CommonPageData<Map<String, Object>>> commonPageDataJsonResult = null;
        try {
            commonPageDataJsonResult = commonSearchFeignClient.pageSearch(pageRequestDTO);
        } catch (Exception e) {
            log.error("RemoteCommonSearchService pageSearch error pageRequestDTO:{} e:{}", pageRequestDTO.toString(), e);
        }
        if (commonPageDataJsonResult != null && commonPageDataJsonResult.checkSuccess()) {
            return commonPageDataJsonResult.getData();
        } else {
            return new CommonPageData(Lists.newArrayList(), 20, 0, "");
        }
    }

    public List<Map<String, Object>> listAllData(KeyValueRequestDTO keyValueRequestDTO) {
        JsonResult<List<Map<String, Object>>> listJsonResult = null;
        try {
            listJsonResult = commonSearchFeignClient.listAllData(keyValueRequestDTO);
        } catch (Exception e) {
            log.error("RemoteCommonSearchService listAllData error keyValueRequestDTO:{} e:{}", keyValueRequestDTO.toString(), e);
        }

        if (listJsonResult != null && listJsonResult.checkSuccess()) {
            return listJsonResult.getData();
        } else {
            return Lists.newArrayList();
        }
    }

    public List<String> distinct(DistinctSqlRequestDTO distinctSqlRequestDTO) {
        JsonResult<List<String>> distinct = null;
        try {
            distinct = commonSearchFeignClient.distinct(distinctSqlRequestDTO);
        } catch (Exception e) {
            log.error("RemoteCommonSearchService distinct error distinctSqlRequestDTO:{} e:{}", distinctSqlRequestDTO.toString(), e);
        }

        if (distinct != null && distinct.checkSuccess()) {
            return distinct.getData();
        } else {
            return Lists.newArrayList();
        }
    }
}
