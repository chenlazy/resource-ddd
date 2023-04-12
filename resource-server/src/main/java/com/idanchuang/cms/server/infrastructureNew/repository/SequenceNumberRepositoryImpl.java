package com.idanchuang.cms.server.infrastructureNew.repository;

import com.idanchuang.cms.server.domainNew.model.cms.number.SequenceNumberId;
import com.idanchuang.cms.server.domainNew.model.cms.number.SequenceNumberRepository;
import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.dataobject.SequenceNumberDO;
import com.idanchuang.cms.server.infrastructureNew.persistence.mybatis.mapper.SequenceNumberMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fym
 * @description :
 * @date 2021/12/23 上午9:58
 */
@Repository
@Slf4j
public class SequenceNumberRepositoryImpl implements SequenceNumberRepository {

    @Resource
    private SequenceNumberMapper sequenceNumberMapper;

    @Override
    public SequenceNumberId sequenceNumber(Integer numberType) {
        SequenceNumberDO sequenceNumberDO = new SequenceNumberDO();
        sequenceNumberDO.setNumberType(numberType);
        int insert = sequenceNumberMapper.insert(sequenceNumberDO);
        if (insert > 0) {
            return new SequenceNumberId(sequenceNumberDO.getId());
        }
        return new SequenceNumberId(insert);
    }

    @Override
    public List<SequenceNumberId> batchSequenceNumber(Integer constructionType, Integer num) {
        List<SequenceNumberDO> collect = Lists.newArrayList();
        for (int i = 0; i < num; i++) {
            SequenceNumberDO sequenceNumberDO = new SequenceNumberDO();
            sequenceNumberDO.setNumberType(constructionType);
            collect.add(sequenceNumberDO);
        }
        int insertBatch = sequenceNumberMapper.insertBatch(collect);
        List<SequenceNumberId> sequenceNumberIdList = Lists.newArrayList();
        if (insertBatch == collect.size()) {
            for (int i = 0; i < collect.size(); i++) {
                SequenceNumberDO sequenceNumberDO = collect.get(i);
                if (sequenceNumberDO != null) {
                    sequenceNumberIdList.add(new SequenceNumberId(sequenceNumberDO.getId()));
                }
            }
        }
        return sequenceNumberIdList;
    }


}
