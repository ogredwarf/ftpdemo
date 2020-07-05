package com.kisrating.batch.demo;

import com.kisrating.batch.demo.service.Ftp6Service;
import lombok.extern.slf4j.Slf4j;
import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.ConnectException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@SpringBootApplication
public class BatchApplication implements CommandLineRunner {

    final Ftp6Service ftp6Service;

    @Autowired
    public BatchApplication(Ftp6Service ftp6Service) {
        this.ftp6Service = ftp6Service;
    }

    public static void main(String[] args) {
        SpringApplication.run(BatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // 오늘 일자 구하기
        final String targetDate = (args.length>0)? args[0] : LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        log.info("Program START: {}", targetDate);

        RetryPolicy<Object> retryPolicy = new RetryPolicy<>()
                .handle(ConnectException.class)
                .withDelay(Duration.ofSeconds(1))
                .withMaxRetries(3);

        Failsafe.with(retryPolicy).run(() -> ftp6Service.run(targetDate));

    }
}
