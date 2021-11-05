package com.urlshortener.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "Alert 통신 모델")
public class ErrorDto {
    @ApiModelProperty(value = "Alert 메시지")
    private String message;

    public ErrorDto(String message) {
        this.message = message;
    }
}
