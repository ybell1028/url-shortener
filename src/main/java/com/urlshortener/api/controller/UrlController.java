package com.urlshortener.api.controller;

import com.urlshortener.api.domain.entity.Url;
import com.urlshortener.api.dto.UrlDto;
import com.urlshortener.api.dto.UrlShortenRequest;
import com.urlshortener.api.mapper.UrlMapper;
import com.urlshortener.api.service.UrlSerivce;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@Api(tags = "Url 단축 API")
@RequestMapping("/api/v1/urls")
public class UrlController {
    private final UrlSerivce urlSerivce;
    private final UrlMapper urlMapper;

    @ApiOperation(value = "단축 Url 생성")
    @PostMapping
    public ResponseEntity<UrlDto> create(@RequestBody @Valid UrlShortenRequest request) {
        Url newUrl = urlMapper.toEntity(request);

        return urlSerivce.getByOriginalName(newUrl.getOriginalUrl())
                .map(url -> ResponseEntity.status(HttpStatus.OK)
                        .body(urlMapper.toDto(url)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(urlMapper.toDto(urlSerivce.saveAndUpdate(newUrl))));
    }
}