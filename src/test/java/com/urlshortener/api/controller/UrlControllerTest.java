package com.urlshortener.api.controller;

import com.urlshortener.api.config.AbstractControllerTest;
import com.urlshortener.api.domain.entity.Url;
import com.urlshortener.api.service.UrlSerivce;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UrlControllerTest extends AbstractControllerTest {
    @MockBean
    private UrlSerivce urlSerivce;

    private static final Long ID = 1L;
    private static final String originalUrl = "http://smilegate-stovedevcamp.com/";
    private static final String hostUrl = "http://shorte.ner/";
    private static final String protocol = "http";
    private static final String host = "shorte.ner";
    private static final String shortened = "qL";

    @Test
    public void 단축_URL이_DB에_존재하지_않을때_생성() throws Exception {
        //given
        String shortenedUrl = hostUrl + shortened;
        Url savedUrl = Url.builder()
                .id(ID)
                .originalUrl(originalUrl)
                .shortenedUrl(shortenedUrl)
                .build();

        given(urlSerivce.getByOriginalName(eq(originalUrl))).willReturn(Optional.empty());
        given(urlSerivce.saveAndUpdate(eq(originalUrl), eq(hostUrl))).willReturn(savedUrl);

        //when
        mockMvc.perform(
                post("/api/v1/urls")
                        .with(mockHttpServletRequest -> {
                            mockHttpServletRequest.setScheme(protocol);
                            mockHttpServletRequest.addHeader("host", host);
                            return mockHttpServletRequest;
                        })
                        .content("{\"originalUrl\" : \"" + originalUrl + "\"}")
                        .contentType(MediaType.APPLICATION_JSON))
        //then
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(HttpStatus.CREATED.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.shortenedUrl").value(shortenedUrl));
    }

    @Test
    public void 단축_URL이_DB에_존재할때_조회() throws Exception {
        //given
        String shortenedUrl = hostUrl + shortened;
        Url savedUrl = Url.builder()
                .id(ID)
                .originalUrl(originalUrl)
                .shortenedUrl(shortenedUrl)
                .build();

        given(urlSerivce.getByOriginalName(eq(originalUrl))).willReturn(Optional.ofNullable(savedUrl));

        //when
        mockMvc.perform(
                post("/api/v1/urls")
                        .with(mockHttpServletRequest -> {
                            mockHttpServletRequest.setScheme(protocol);
                            mockHttpServletRequest.addHeader("host", host);
                            return mockHttpServletRequest;
                        })
                        .content("{\"originalUrl\" : \"" + originalUrl + "\"}")
                        .contentType(MediaType.APPLICATION_JSON))
        //then
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.shortenedUrl").value(shortenedUrl));
    }
}