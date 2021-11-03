package com.urlshortener.api.repository;

import com.urlshortener.api.domain.entity.Url;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UrlRepository extends CrudRepository<Url, Long> {
    Optional<Url> findByOriginalUrl(String originalUrl);
}