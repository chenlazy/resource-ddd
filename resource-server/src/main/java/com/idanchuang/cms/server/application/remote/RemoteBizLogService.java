package com.idanchuang.cms.server.application.remote;

import com.idanchuang.bizlog.feign.client.BizLogFeignClient;
import com.idanchuang.bizlog.feign.dto.oplog.OpLogRequestDTO;
import com.idanchuang.bizlog.feign.dto.oplog.OpLogRequestPageQueryResponseDTO;
import com.idanchuang.bizlog.feign.dto.oplog.OpV2PageQueryDTO;
import com.idanchuang.component.base.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-12 15:50
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Service
@Slf4j
public class RemoteBizLogService {

    @Resource
    private BizLogFeignClient bizLogFeignClient;

    public void writeLog(OpLogRequestDTO operateLogRequest) {
        try {
            JsonResult<Void> jsonResult = bizLogFeignClient.opRequestReport(operateLogRequest);
            if (jsonResult == null || !jsonResult.isSuccess()) {
                log.warn("基础服务操作日志失败{}", Optional.ofNullable(jsonResult.getMsg()).orElse(""));
            }
        } catch (Exception e) {
            log.error("基础服务操作日志失败,{}", operateLogRequest, e);
        }
    }

    public OpLogRequestPageQueryResponseDTO logPageQuery(OpV2PageQueryDTO opV2PageQueryDTO) {
        JsonResult<OpLogRequestPageQueryResponseDTO> result = null;
        try {
            result = bizLogFeignClient.opV2PageQuery(opV2PageQueryDTO);
        } catch (Exception e) {
            log.error("RemoteBizLogService logPageQuery error e:{}", e.toString());
        }
        if (result != null && result.isSuccess() && result.getData() != null) {
            return result.getData();
        } else {
            return null;
        }
    }
}
