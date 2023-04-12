package com.idanchuang.cms.server.domainNew.model.cms.number;

import com.idanchuang.cms.server.domain.shard.IdObject;

/**
 * @author fym
 * @description :
 * @date 2021/12/23 上午10:27
 */
public class SequenceNumberId extends IdObject {

    /**
     * Instantiates a new Id object.
     *
     * @param value the value
     */
    public SequenceNumberId(long value) {
        super(value);
    }
}
