package com.idanchuang.cms.server.application.remote;

import com.idanchuang.cms.server.infrastructure.shard.InfraException;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.trade.goods.hulk.api.service.CircleGoodsApi;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author fym
 * @description :
 * @date 2022/5/7 上午11:46
 */
@Slf4j
@Service
public class RemoteCircleGoodsService {

    @Resource
    private CircleGoodsApi circleGoodsApi;

    public List<Long> getSpuIdListByBizId(Long bizId) {
        if (null == bizId) {
            return Lists.newArrayList();
        }
        JsonResult<List<Long>> spuIdListByBizId;
        try {
            spuIdListByBizId = circleGoodsApi.getSpuIdListByBizId(bizId.intValue());
        } catch (Exception e) {
            log.error("getSpuIdListByBizId error bizId:{} e:{}", bizId, e);
            throw new InfraException("标签权益同步异常");
        }
        if (null != spuIdListByBizId && spuIdListByBizId.isSuccess() && !CollectionUtils.isEmpty(spuIdListByBizId.getData())) {
            return spuIdListByBizId.getData();
        } else {
            log.warn("getSpuIdListByBizId warn bizId:{} spuIdListByBizId:{}", bizId, spuIdListByBizId);
        }
        return Lists.newArrayList();
    }
}
