package com.idanchuang.cms.server.application.remote;

import com.idanchuang.cms.api.response.LuckyComponentActivityDTO;
import com.idanchuang.cms.api.response.LuckyComponentPrizeDTO;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.market.lucky.api.facade.AdminAwardActFacade;
import com.idanchuang.market.lucky.api.facade.AwardPrizeFacade;
import com.idanchuang.market.lucky.api.response.admin.activity.AwardActDetailsDTO;
import com.idanchuang.market.lucky.api.response.prize.ActPrizeListDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-12 15:32
 * @Desc: 远程调用抽奖业务服务
 * @Copyright VTN Limited. All rights reserved.
 */
@Service
@Slf4j
public class RemoteLuckyService {

    @Resource
    private AdminAwardActFacade adminAwardActFacade;

    @Resource
    private AwardPrizeFacade awardPrizeFacade;

    public LuckyComponentActivityDTO getActivityDetail(Long activityId) {
        JsonResult<AwardActDetailsDTO> result = adminAwardActFacade.getAwardActDetails(activityId);
        if (!result.isSuccess()) {
            return null;
        }

        if (result.getData() == null) {
            return null;
        }

        return LuckyComponentActivityDTO.builder().activityId(activityId).activityName(result.getData().getActName()).startTime(result.getData().getDrawStartTime()).endTime(result.getData().getDrawEndTime()).build();
    }

    public List<LuckyComponentPrizeDTO> getActivityPrize(Long activityId) {
        JsonResult<List<ActPrizeListDTO>> result = awardPrizeFacade.actPrizeList(activityId);
        if (!result.isSuccess()) {
            return null;
        }

        if (CollectionUtils.isEmpty(result.getData())) {
            return null;
        }


        List<LuckyComponentPrizeDTO> data = new ArrayList<>();
        for (ActPrizeListDTO dto : result.getData()) {
            data.add(convertOf(activityId, dto));
        }
        return data;
    }

    private LuckyComponentPrizeDTO convertOf(Long activityId, ActPrizeListDTO source) {
        return LuckyComponentPrizeDTO.builder()
                .activityId(activityId)
                .prizeMarkId(source.getPrizeMarkId())
                .prizeName(source.getPrizeName())
                .prizeType(source.getPrizeType())
                .build();
    }
}
