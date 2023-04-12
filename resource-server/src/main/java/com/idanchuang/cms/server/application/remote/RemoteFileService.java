package com.idanchuang.cms.server.application.remote;

import com.google.common.collect.Lists;
import com.idanchuang.abmio.feign.client.FileServiceFeignClient;
import com.idanchuang.abmio.feign.dto.FileInfoDTO;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.resource.server.infrastructure.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-14 14:37
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Slf4j
@Service
public class RemoteFileService {

    @Resource
    private FileServiceFeignClient fileServiceFeignClient;

    private static Integer QUERY_NUM = 30;


    public Map<Long, FileInfoDTO> batchQuery(List<Long> imageIds) {
        Assert.notEmpty(imageIds, "图片id列表不能为空");

        List<FileInfoDTO> allFileInfos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(imageIds)) {
            List<List<Long>> imgList = Lists.partition(imageIds, QUERY_NUM);
            for (List<Long> imgs : imgList) {
                JsonResult<List<FileInfoDTO>> imageResult = fileServiceFeignClient.batchQuery(imgs);
                if (!imageResult.checkSuccess()) {
                    log.error("RemoteFileService batchQuery imageIds:{} imageResult:{}", imgs.toString(), imageResult.toString());
                    throw new BusinessException(imageResult.getCode(), imageResult.getMsg());
                }
                List<FileInfoDTO> fileInfoDTOS = imageResult.getData();
                if (CollectionUtils.isNotEmpty(fileInfoDTOS)) {
                    allFileInfos.addAll(fileInfoDTOS);
                }
            }
        }

        return this.convertGoodsSpuDTO(allFileInfos);
    }

    private Map<Long, FileInfoDTO> convertGoodsSpuDTO(List<FileInfoDTO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(FileInfoDTO::getId, e -> e, (k1, k2) -> k1));
    }


    public FileInfoDTO singleQuery(Long imgId) {
        JsonResult<FileInfoDTO> result = fileServiceFeignClient.singleQuery(imgId);
        if (!result.checkSuccess()) {
            log.error("RemoteFileService singleQuery imgId:{} result:{}", imgId.toString(), result.toString());
            return null;
        }
        return result.getData();
    }
}
