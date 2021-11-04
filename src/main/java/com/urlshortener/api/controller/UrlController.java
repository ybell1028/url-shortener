package com.urlshortener.api.controller;

import com.urlshortener.api.domain.entity.Url;
import com.urlshortener.api.dto.UrlDto;
import com.urlshortener.api.dto.UrlShortenRequest;
import com.urlshortener.api.service.UrlSerivce;
import com.urlshortener.api.support.ShortenerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Api(tags = "Url 단축 API")
@RequestMapping("/api/v1/urls")
public class UrlController {
    private final UrlSerivce urlSerivce;

    @ApiOperation(value = "단축 Url 생성")
    @PostMapping
    public ShortenerResponse<UrlDto> create(HttpServletRequest request, @RequestBody @Valid UrlShortenRequest urlShortenRequest) {

        Url newUrl = Url.builder().originalUrl(urlShortenRequest.getOriginalUrl()).build();

        return urlSerivce.getByOriginalName(newUrl.getOriginalUrl())
                .map(url -> new ShortenerResponse<>(
                        new UrlDto(url), HttpStatus.OK))
                .orElseGet(() -> {
                    String hostUrl = (String)request.getAttribute("hostUrl");
                    return new ShortenerResponse<>(
                            new UrlDto(urlSerivce.saveAndUpdate(newUrl, hostUrl)), HttpStatus.CREATED);
                });
    }
}