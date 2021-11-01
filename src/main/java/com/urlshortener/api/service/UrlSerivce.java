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
    private final UrlRepository urlRepository;

    @Transactional
    public Url create(Url newUrl) {
        // 단축 url 생성 후, db에 save -> return
        return urlRepository.save(newUrl);
    }

    @Transactional(readOnly = true)
    public Optional<Url> getByOriginalName(String originalUrl){
        return urlRepository.findByOriginalUrl(originalUrl);
    }
}