package com.gamjacoding.personalnewsletterapi.domain.subscriber;

import lombok.Getter;

@Getter
public enum OperationResult {
    SUCCESS(1),
    FAIL(0);                 

    private final int code;

    OperationResult(int code) {
        this.code = code;
    }
}
