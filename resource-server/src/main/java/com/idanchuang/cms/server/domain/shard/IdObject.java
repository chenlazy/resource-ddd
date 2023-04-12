package com.idanchuang.cms.server.domain.shard;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Objects;


/**
 * 数据库主键ID对象
 */
@Getter
public class IdObject implements ValueObject<IdObject> {

    private static final long serialVersionUID = 6940813260197305885L;

    private final long value;

    /**
     * Instantiates a new Id object.
     *
     * @param value the value
     */
    @JsonCreator
    public IdObject(long value) {
        if (value <= 0) {
            throw DomainException.error(DomainExceptionCode.ID_SHOULD_BE_POSITIVE);
        }
        this.value = value;
    }

    @JsonValue
    public long getValue() {
        return value;
    }

    @Override
    public boolean sameValueAs(IdObject other) {
        return other != null && this.value == other.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IdObject idObject = (IdObject) o;
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
