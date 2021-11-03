package com.urlshortener.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UrlConvertService {
    @Value("${values.dummy}")
    private int dummy;
    @Value("${values.host}")
    private String host;
    private final int BASE = 62;
    private final char[] BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

    public String encode(Long id) {
        id += dummy;
        StringBuilder shorten = new StringBuilder();
        while (id > 0) {
            int idx = (int)(id % BASE);
            shorten.append(BASE62[idx]);
            id /= 62;
        }
        return host + shorten.reverse().toString(); // 뒤집어 주는 이유? decode는 반대순서로 해야 하기 때문
    }

    public Long decode(String shorten) {
        Long id = 0L;

        String[] splited = shorten.split("");

        for(int i = 0; i < splited.length; ++i) {
            char cs = shorten.charAt(i);
            if(splited[i].matches("^[0-9]*$")) {
                id += (cs - '0') + 52;
            } else if(splited[i].matches("^[A-Z]*$")) {
                id += (cs - 'A') + 26;
            } else {
                id += (cs - 'a');
            }
            if(i < splited.length - 1) id *= BASE;
        }

        return id - dummy;
    }
}
