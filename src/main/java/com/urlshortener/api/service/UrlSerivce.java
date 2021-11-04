package com.urlshortener.api.service;

import com.urlshortener.api.domain.entity.Url;
import com.urlshortener.api.repository.UrlRepository;
import com.urlshortener.api.support.ShortenerError;
import com.urlshortener.api.support.ShortenerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlSerivce {
    private final ConvertService convertService;
    private final UrlRepository urlRepository;

    @Transactional
    public Url saveAndUpdate(String originalUrl, String hostUrl) {
        Url newUrl = Url.builder().originalUrl(originalUrl).build();
        Url savedUrl = urlRepository.save(newUrl);
        String shortenUrl = convertService.encode(savedUrl.getId());
        savedUrl.update(hostUrl + shortenUrl);
        return urlRepository.save(savedUrl);
    }

    @Transactional(readOnly = true)
    public Optional<Url> getByOriginalName(String originalUrl){
        return urlRepository.findByOriginalUrl(originalUrl);
    }

    @Transactional(readOnly = true)
    public String redirectShortenedUrl(String shortened) {
        Url url = urlRepository.findById(convertService.decode(shortened))
                .orElseThrow(() -> new ShortenerException(ShortenerError.BAD_REQUEST, "존재하지 않는 단축 URL 입니다."));
        return url.getOriginalUrl();
    }
}