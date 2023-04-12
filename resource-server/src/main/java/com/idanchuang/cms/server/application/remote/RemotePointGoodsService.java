package com.idanchuang.cms.server.application.remote;

import com.idanchuang.cms.server.infrastructure.shard.InfraException;
import com.idanchuang.component.base.JsonResult;
import com.idanchuang.member.point.api.entity.dto.PointGoodsCouponDTO;
import com.idanchuang.member.point.api.entity.query.PointGoodsCouponDetailQuery;
import com.idanchuang.member.point.api.enums.GoodsCouponTypeEnum;
import com.idanchuang.member.point.api.service.PointGoodsCouponFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-12 17:17
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Service
@Slf4j
public class RemotePointGoodsService {

    @Resource
    private PointGoodsCouponFeign pointGoodsCouponFeign;

    public PointGoodsCouponDTO getPointGoodsDetailQuery(Long goodsId) {
        PointGoodsCouponDetailQuery query = new PointGoodsCouponDetailQuery();
        query.setType(1);
        query.setIgnoreDeleteStatus(false);
        query.setGoodsOrCouponId(goodsId);
        JsonResult<PointGoodsCouponDTO> result = pointGoodsCouponFeign.getPointGoodsCouponInfo(query);
        if (!result.isSuccess()) {
            return null;
        }
        return result.getData();
    }

    public void getActivityInfoByIdOpinionValidity(Long id) {
        JsonResult<PointGoodsCouponDTO> activityInfoById;
        try {
            activityInfoById = pointGoodsCouponFeign.getActivityInfoById(id);
        } catch (Exception e) {
            log.error("getActivityInfoByIdOpinionValidity error id:{} e:{}", id, e);
            throw new InfraException("权益活动服务查询异常,id:" + id);
        }
        if (activityInfoById == null || !activityInfoById.isSuccess()) {
            log.error("getActivityInfoByIdOpinionValidity error activityInfoById:{} id:{}", activityInfoById, id);
            throw new InfraException("权益组件配置权益活动id查询失败,id:" + id);
        }
        PointGoodsCouponDTO data = activityInfoById.getData();
        if (data == null) {
            throw new InfraException("权益组件配置权益活动id不存在，请检查权益id,id:" + id);
        }
        if (!GoodsCouponTypeEnum.PRIVILEGE_TYPE.getCode().equals(data.getType())) {
            throw new InfraException("权益组件配置id不是会员权益活动,id:" + id);
        }
        if (data.getStatus() == -1) {
            throw new InfraException("权益组件配置权益活动id过期，请先修改或删除,id:" + id);
        }
    }
}
