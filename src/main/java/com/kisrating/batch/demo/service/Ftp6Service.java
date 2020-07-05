package com.kisrating.batch.demo.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 본드웹 프로그램 대체
 */
@Slf4j
@Component
public class Ftp6Service {
    private static int cnt = 0;
    @SneakyThrows
    public void run(String targetDate) {
        log.debug( "cnt: {}", cnt++ );
        if(cnt <10 ) throw new Exception("TEST");
    }
}
