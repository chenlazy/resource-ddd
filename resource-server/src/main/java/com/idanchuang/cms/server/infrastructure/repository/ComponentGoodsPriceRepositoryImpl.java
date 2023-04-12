package com.idanchuang.cms.server.infrastructure.repository;

import com.idanchuang.cms.server.domain.model.cms.ComponentGoodsPrice;
import com.idanchuang.cms.server.domain.model.cms.ComponentGoodsPriceCondition;
import com.idanchuang.cms.server.domain.model.cms.factory.GoodsPriceFactory;
import com.idanchuang.cms.server.domain.repository.ComponentGoodsPriceRepository;
import com.idanchuang.cms.server.infrastructure.persistence.mapper.ComponentGoodsPriceMapper;
import com.idanchuang.cms.server.infrastructure.persistence.model.ComponentGoodsPriceDO;
import com.idanchuang.cms.server.infrastructure.persistence.model.ContainerComponentDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-09-01 17:32
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Repository
@Slf4j
public class ComponentGoodsPriceRepositoryImpl implements ComponentGoodsPriceRepository {

    @Resource
    private ComponentGoodsPriceMapper componentGoodsPriceMapper;

    @Override
    public boolean insert(ComponentGoodsPrice price) {
        return componentGoodsPriceMapper.insert(convertOf(price)) > 0;
    }

    @Override
    public boolean batchInsert(List<ComponentGoodsPrice> componentGoodsPrices) {
        if (CollectionUtils.isEmpty(componentGoodsPrices)) {
            return false;
        }
        List<ComponentGoodsPriceDO> collect = componentGoodsPrices.stream().map(this::convertOf).collect(Collectors.toList());
        int result = componentGoodsPriceMapper.insertBatch(collect);
        if (result <= 0) {
            return false;
        }
        return true;
    }

    @Override
    public boolean insert(List<ComponentGoodsPrice> priceList) {
        if (CollectionUtils.isEmpty(priceList)) {
            return false;
        }

        List<ComponentGoodsPriceDO> entityList = priceList.stream().map(this::convertOf).collect(Collectors.toList());
        int result = componentGoodsPriceMapper.insertBatch(entityList);
        if (result <= 0) {
            return false;
        }

        int size = priceList.size();
        for (int i = 0; i < size; i++) {
            ComponentGoodsPriceDO entity = entityList.get(i);
            if (entity != null) {
                priceList.get(i).setId(entity.getId());
            }
        }

        return true;
    }

    @Override
    public boolean deleteById(Integer id, Integer operatorId, LocalDateTime updateTime) {
        return componentGoodsPriceMapper.deleteById(id, operatorId, updateTime) > 0;
    }

    @Override
    public boolean updateById(ComponentGoodsPrice price) {
        price.setUpdateTime(LocalDateTime.now());
        return componentGoodsPriceMapper.updateById(convertOf(price)) > 0;
    }

    @Override
    public List<ComponentGoodsPrice> selectByCondition(ComponentGoodsPriceCondition condition) {
        List<ComponentGoodsPriceDO> entityList = componentGoodsPriceMapper.selectByCondition(condition);
        return entityList == null ? null : entityList.stream().map(this::convertOf).collect(Collectors.toList());
    }

    private ComponentGoodsPriceDO convertOf(ComponentGoodsPrice source) {
        return ComponentGoodsPriceDO.builder()
                .componentId(source.getComponentId())
                .componentGoodsId(source.getComponentGoodsId())
                .type(source.getType())
                .priceData(source.getPriceData())
                .operatorId(source.getOperatorId())
                .createTime(source.getCreateTime())
                .updateTime(source.getUpdateTime())
                .build();
    }

    private ComponentGoodsPrice convertOf(ComponentGoodsPriceDO source) {

        return GoodsPriceFactory.createGoodsPrice(source.getId(), source.getOperatorId(), source.getComponentGoodsId(),
                source.getType(), source.getPriceData(), source.getComponentId(), source.getCreateTime(), source.getUpdateTime());
    }
}
