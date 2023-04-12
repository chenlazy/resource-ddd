package com.idanchuang.cms.server.infrastructureNew.external.feign;

import com.idanchuang.cms.server.application.constant.RedisKeyConstant;
import com.idanchuang.cms.server.application.remote.RemotePointGoodsService;
import com.idanchuang.cms.server.application.remote.RemotePrivilegeService;
import com.idanchuang.cms.server.domainNew.model.cms.external.equity.EquityKey;
import com.idanchuang.cms.server.domainNew.model.cms.external.equity.EquityService;
import com.idanchuang.cms.server.domainNew.model.cms.external.equity.EquityType;
import com.idanchuang.cms.server.domainNew.model.cms.masterplate.MasterplateId;
import com.idanchuang.component.redis.util.RedisUtil;
import com.idanchuang.member.point.api.entity.query.PrivilegeSyncTagIdRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.String.format;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-05-20 14:47
 * @Desc: 会员权益服务类
 * @Copyright VTN Limited. All rights reserved.
 */
@Slf4j
@Service
public class EquityServiceImpl implements EquityService {

    @Resource
    private RemotePrivilegeService remotePrivilegeService;

    @Resource
    private RemotePointGoodsService remotePointGoodsService;

    @Override
    public void checkEquityValid(Set<EquityKey> equityKeys) {

        if (CollectionUtils.isEmpty(equityKeys)) {
            return;
        }
        //校验权益信息
        equityKeys.forEach(p -> {

            if (EquityType.EQUITY_ACTIVITY.getVal().equals(p.getEquityType())) {
                remotePointGoodsService.getActivityInfoByIdOpinionValidity(p.getEquityId());
            } else {
                remotePrivilegeService.getEquityDetail(p.getEquityId());
            }
        });

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncEquityTag(Map<EquityKey, Long> equityTagMap, MasterplateId masterplateId) {

        //如果存在权益组件 同步权益id与标签id到会员服务
        if (MapUtils.isEmpty(equityTagMap) || null == masterplateId) {
            return;
        }

        //根据权益类型进行分组
        Map<Long, Long> equityActivityMap = new HashMap<>();
        Map<Long, Long> equityMap = new HashMap<>();

        List<PrivilegeSyncTagIdRequest> list = Lists.newArrayList();
        for (Map.Entry<EquityKey, Long> entry : equityTagMap.entrySet()) {

            Long equityId = entry.getKey().getEquityId();
            Integer equityType = entry.getKey().getEquityType();

            if (EquityType.EQUITY_ACTIVITY.getVal().equals(equityType)) {
                equityActivityMap.put(equityId, entry.getValue());
            } else {
                equityMap.put(equityId, entry.getValue());
            }

            PrivilegeSyncTagIdRequest privilegeSyncTagIdRequest = new PrivilegeSyncTagIdRequest();
            privilegeSyncTagIdRequest.setId(equityId);
            privilegeSyncTagIdRequest.setType(equityType);
            privilegeSyncTagIdRequest.setTagId(entry.getValue());
            list.add(privilegeSyncTagIdRequest);
        }
        //同步权益和商品标签信息
        remotePrivilegeService.syncTagIds(list);

        //记录权益活动关联的标签信息
        if (MapUtils.isNotEmpty(equityActivityMap)) {
            String equityIdsKey = format(RedisKeyConstant.SUBJECT_EQUITY_ID_KEY, masterplateId);
            RedisUtil.getInstance().setObj(equityIdsKey, equityActivityMap);
        }

        //记录权益关联的标签信息
        if (MapUtils.isNotEmpty(equityMap)) {
            String equityIdsKey = format(RedisKeyConstant.SUBJECT_EQUITY_KEY, masterplateId);
            RedisUtil.getInstance().setObj(equityIdsKey, equityMap);
        }

    }
}
