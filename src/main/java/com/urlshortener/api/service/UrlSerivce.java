package com.urlshortener.api.service;

import com.urlshortener.api.domain.entity.Url;
import com.urlshortener.api.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlSerivce {
    private final UrlConvertService urlConvertService;
    private final UrlRepository urlRepository;

    @Transactional
    public Url saveAndUpdate(Url newUrl) {
        Url savedUrl = urlRepository.save(newUrl);
        String shortenUrl = urlConvertService.encode(savedUrl.getId());
        savedUrl.update(shortenUrl);
        return urlRepository.save(savedUrl);
    }

    @Transactional(readOnly = true)
    public Optional<Url> getByOriginalName(String originalUrl){
        return urlRepository.findByOriginalUrl(originalUrl);
    }

    public String redirectShortenUrl(String shortenUrl) {
        Url url = urlRepository.findById(urlConvertService.decode(shortenUrl))
                .orElseThrow(() -> new RuntimeException("존재하지 않는 단축 url 입니다."));
        return url.getOriginalUrl();
    }
}