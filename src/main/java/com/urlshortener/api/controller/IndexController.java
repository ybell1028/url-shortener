package com.urlshortener.api.controller;

import com.urlshortener.api.service.UrlSerivce;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Api(tags = "인덱스 API")
public class IndexController {
    private final UrlSerivce urlSerivce;

    @ApiOperation(value = "단축 Url 리다이렉트")
    @GetMapping({"/s/{shortened}"})
    public ResponseEntity<String> redirectToOriginalUrl(@PathVariable(value = "shortened") String shortened) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(urlSerivce.redirectShortenedUrl(shortened));
    }
}