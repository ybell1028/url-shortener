package com.urlshortener.api.controller;

import com.urlshortener.api.config.AbstractControllerTest;
import com.urlshortener.api.service.UrlSerivce;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class IndexControllerTest extends AbstractControllerTest {
    @MockBean
    private UrlSerivce urlSerivce;

    private static final String originalUrl = "http://smilegate-stovedevcamp.com/";
    private static final String protocol = "http";
    private static final String host = "shorte.ner";
    private static final String shortened = "qL";

    @Test
    public void 단축_URL로_리다이렉트() throws Exception {
        //given
        given(urlSerivce.redirectShortenedUrl(eq(shortened))).willReturn(originalUrl);

        //when
        mockMvc.perform(
                get("/" + shortened)
                        .with(mockHttpServletRequest -> {
                            mockHttpServletRequest.setScheme(protocol);
                            mockHttpServletRequest.addHeader("host", host);
                            return mockHttpServletRequest;
                        })
                        .contentType(MediaType.APPLICATION_JSON))
        //then
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(originalUrl));
    }
}