package com.urlshortener.api.support;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ShortenerError {
    UNKNOWN_ERROR("알 수 없는 에러입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST("잘못된 입력입니다.", HttpStatus.BAD_REQUEST);

    private final String desc;
    private final HttpStatus status;

    ShortenerError(String desc, HttpStatus status) {
        this.desc = desc;
        this.status = status;
    }
}