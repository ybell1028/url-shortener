package com.urlshortener.api.mapper;

import com.urlshortener.api.domain.entity.Url;
import com.urlshortener.api.dto.UrlDto;
import com.urlshortener.api.dto.UrlShortenRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface UrlMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "shortenedUrl", ignore = true)
    Url toEntity(UrlShortenRequest urlShortenRequest);

    UrlDto toDto(Url url);
}