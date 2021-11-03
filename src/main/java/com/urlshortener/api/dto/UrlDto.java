package com.urlshortener.api.dto;

import com.urlshortener.api.domain.entity.Url;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@ApiModel(value = "Url 모델")
public class UrlDto {
    @ApiModelProperty(value = "단축 Url")
    private String shortenedUrl;

    public UrlDto(Url url) {
        this.shortenedUrl = url.getShortenedUrl();
    }
}