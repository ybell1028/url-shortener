package com.urlshortener.api.controller;

import com.urlshortener.api.service.UrlSerivce;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
@Api(tags = "인덱스 API")
public class IndexController {
    private final UrlSerivce urlSerivce;

    @GetMapping
    public String index() {
        return "index";
    }

    @ApiOperation(value = "단축 Url 리다이렉트")
    @GetMapping({"/{shortened}"})
    public String redirectToOriginalUrl(@PathVariable(value = "shortened") String shortened) {
        return "redirect:" + urlSerivce.redirectShortenedUrl(shortened);
    }
}