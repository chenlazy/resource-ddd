package com.idanchuang.cms.server.domainNew.model.cms.number;

import java.util.List;

/**
 * @author fym
 * @description :
 * @date 2021/12/23 上午9:55
 */
public interface SequenceNumberRepository {

    /**
     * 单个发号
     *
     * @param numberType
     * @return
     */
    SequenceNumberId sequenceNumber(Integer numberType);

    /**
     * 批量发号
     *
     * @param constructionType 结构类型
     * @param num              申请数量
     * @return
     */
    List<SequenceNumberId> batchSequenceNumber(Integer constructionType, Integer num);

}
