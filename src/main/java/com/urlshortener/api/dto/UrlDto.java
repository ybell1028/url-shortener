package com.urlshortener.api.dto;

import com.urlshortener.api.domain.entity.Url;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "Url 통신 모델")
public class UrlDto {
    @ApiModelProperty(value = "단축 Url")
    private String shortenedUrl;

    public UrlDto(Url url) {
        this.shortenedUrl = url.getShortenedUrl();
    }
}