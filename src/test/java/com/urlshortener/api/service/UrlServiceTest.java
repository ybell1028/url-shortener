package com.urlshortener.api.service;

import com.urlshortener.api.domain.entity.Url;
import com.urlshortener.api.repository.UrlRepository;
import com.urlshortener.api.support.ShortenerError;
import com.urlshortener.api.support.ShortenerException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UrlServiceTest {
    @InjectMocks
    private UrlSerivce urlSerivce;

    @Mock
    private ConvertService convertService;

    @Mock
    private UrlRepository urlRepository;

    private static final Long ID = 1L;
    private static final String originalUrl = "http://smilegate-stovedevcamp.com/";
    private static final String hostUrl = "http://shorte.ner/";
    private static final String shortened = "qL";

    @Test
    public void 요청받은_원본_URL_단축() {
        //given
        Url newUrl = Url.builder()
                .id(ID)
                .originalUrl(originalUrl)
                .build();

        given(urlRepository.save(any())).willReturn(newUrl);
        given(convertService.encode(eq(ID))).willReturn(shortened);
        newUrl.update(hostUrl + shortened);
        given(urlRepository.save(any())).willReturn(newUrl);

        //when
        Url savedUrl = urlSerivce.saveAndUpdate(originalUrl, hostUrl);

        //then
        assertThat(savedUrl.getId()).isEqualTo(newUrl.getId());
        assertThat(savedUrl.getOriginalUrl()).isEqualTo(newUrl.getOriginalUrl());
        assertThat(savedUrl.getShortenedUrl()).isEqualTo(newUrl.getShortenedUrl());
    }

    @Test
    public void 원본_URL로_데이터_조회() {
        //given
        Url foundUrl = Url.builder()
                .id(ID)
                .originalUrl(originalUrl)
                .shortenedUrl(hostUrl + shortened)
                .build();

        given(urlRepository.findByOriginalUrl(eq(originalUrl))).willReturn(Optional.ofNullable(foundUrl));

        //when
        Url savedUrl = urlSerivce.getByOriginalName(originalUrl).get();

        //then
        assertThat(savedUrl.getId()).isEqualTo(foundUrl.getId());
        assertThat(savedUrl.getOriginalUrl()).isEqualTo(foundUrl.getOriginalUrl());
        assertThat(savedUrl.getShortenedUrl()).isEqualTo(foundUrl.getShortenedUrl());
    }

    @Test
    public void 단축된_URL로_원본_URL_조회() {
        //given
        Url foundUrl = Url.builder()
                .id(ID)
                .originalUrl(originalUrl)
                .shortenedUrl(hostUrl + shortened)
                .build();

        given(convertService.decode(eq(shortened))).willReturn(ID);
        given(urlRepository.findById(eq(ID))).willReturn(Optional.ofNullable(foundUrl));

        //when
        String redirectUrl = urlSerivce.redirectShortenedUrl(shortened);

        //then
        assertThat(redirectUrl).isEqualTo(foundUrl.getOriginalUrl());
    }

    @Test
    public void 단축된_URL에_대한_원본_URL_존재하지_않음() {
        //given
        ShortenerException exception = new ShortenerException(ShortenerError.BAD_REQUEST, "존재하지 않는 단축 URL 입니다.");

        given(convertService.decode(eq(shortened))).willReturn(ID);
        given(urlRepository.findById(eq(ID))).willThrow(exception);

        //when
        Throwable throwable = catchThrowable(() -> urlSerivce.redirectShortenedUrl(shortened));

        //then
        assertThat(throwable)
                .isInstanceOf(ShortenerException.class)
                .hasMessage(exception.getMessage());
    }
}
