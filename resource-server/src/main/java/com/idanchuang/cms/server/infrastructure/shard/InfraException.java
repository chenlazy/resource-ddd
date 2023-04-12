package com.idanchuang.cms.server.infrastructure.shard;

import java.util.Optional;

public class InfraException extends RuntimeException {
    private static final long serialVersionUID = -1051691412723406354L;

    public InfraException(String message, Throwable e) {
        super(message, e);
    }


    public InfraException(String message) {
        super(message);
    }


    @Override
    public String getMessage() {
        return Optional.ofNullable(super.getMessage())
                .orElse("");
    }
}
