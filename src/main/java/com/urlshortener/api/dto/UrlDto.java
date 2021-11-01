package com.urlshortener.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Url 모델")
public class UrlDto {
    @ApiModelProperty(value = "Url id")
    private Long id;

    @ApiModelProperty(value = "기존 Url")
    private String originalUrl;

    @ApiModelProperty(value = "단축 Url")
    private String shortenedUrl;
}