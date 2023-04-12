package com.idanchuang.cms.server.domain.shard;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;


/**
 * 数据库主键ID对象
 */
@Getter
public class StringIdObject implements ValueObject<StringIdObject> {

    private static final long serialVersionUID = 6940813260197305885L;

    private final String value;

    /**
     * Instantiates a new Id object.
     *
     * @param value the value
     */
    @JsonCreator
    public StringIdObject(String value) {
        if (StringUtils.isEmpty(value)) {
            throw com.idanchuang.cms.server.domain.shard.DomainException.error(DomainExceptionCode.ID_SHOULD_BE_EXIST);
        }
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public boolean sameValueAs(StringIdObject other) {
        return other != null && this.value.equals(other.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StringIdObject idObject = (StringIdObject) o;
        return sameValueAs(idObject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
