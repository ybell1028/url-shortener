package com.urlshortener.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Url 단축 요청 모델")
public class UrlShortenRequest {
    @URL(message = "URL 형식에 맞게 입력해주세요.")
    @ApiModelProperty(value = "원본 Url")
    private String originalUrl;
}