package com.idanchuang.cms.server.interfaces.controller;

import com.idanchuang.cms.api.facade.SequenceNumberFacade;
import com.idanchuang.cms.api.request.CreateNumberReq;
import com.idanchuang.cms.server.domainNew.model.cms.number.SequenceNumberId;
import com.idanchuang.cms.server.domainNew.model.cms.number.SequenceNumberRepository;
import com.idanchuang.component.base.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author fym
 * @description :
 * @date 2021/12/23 上午11:15
 */
@RestController
@Slf4j
public class SequenceNumberFacadeImpl implements SequenceNumberFacade {

    @Resource
    private SequenceNumberRepository sequenceNumberRepository;

    @Override
    public JsonResult<Long> sequenceNumber(CreateNumberReq createNumberReq) {
        SequenceNumberId number = sequenceNumberRepository.sequenceNumber(createNumberReq.getNumberType());
        return JsonResult.success(number.getValue());
    }

    @Override
    public JsonResult<List<Long>> batchSequenceNumber(CreateNumberReq createNumberReq) {
        List<SequenceNumberId> sequenceNumberIdList = sequenceNumberRepository.batchSequenceNumber(createNumberReq.getNumberType(), createNumberReq.getNum());
        List<Long> ids = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(sequenceNumberIdList)) {
            sequenceNumberIdList.forEach(u -> ids.add(u.getValue()));
        }
        return JsonResult.success(ids);
    }
}
