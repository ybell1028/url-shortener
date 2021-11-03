
package com.urlshortener.api.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShortenerResponse<T> {
    private T data;
    private HttpStatus status;
    private int statusCode;
    private String message;

    public ShortenerResponse(T data) {
        this.data = data;
        this.status = HttpStatus.OK;
        this.statusCode = 200;
    }

    public ShortenerResponse(T data, HttpStatus status) {
        this.data = data;
        this.status = status;
        this.statusCode = status.value();
    }

    public ShortenerResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
        this.statusCode = status.value();
    }
}