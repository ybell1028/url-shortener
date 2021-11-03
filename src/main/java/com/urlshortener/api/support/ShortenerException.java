package com.urlshortener.api.support;

import lombok.Getter;

@Getter
public class ShortenerException extends RuntimeException {
    private final ShortenerError error;

    public ShortenerException(ShortenerError error) {
        super(error.getDesc());
        this.error = error;
    }

    public ShortenerException(ShortenerError error, String message) {
        super(error.getDesc() + " : " + message);
        this.error = error;
    }

    public ShortenerException(ShortenerError error, Throwable cause) {
        super(error.getDesc(), cause);
        this.error = error;
    }
}