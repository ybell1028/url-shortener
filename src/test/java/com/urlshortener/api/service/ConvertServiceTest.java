package com.urlshortener.api.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ConvertServiceTest {
    @Autowired
    private ConvertService convertService;

    @Test
    public void 암호화() {
        //given
        Long id = 1234L;

        //when
        String shortened = convertService.encode(id);

        //then
        assertThat(shortened).isEqualTo("KE");
    }

    @Test
    public void 복호화() {
        //given
        String shortend = "KE";

        //when
        Long id = convertService.decode(shortend);

        //then
        assertThat(id).isEqualTo(1234L);
    }
}
