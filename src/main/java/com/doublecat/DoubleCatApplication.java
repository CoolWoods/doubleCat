package com.doublecat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author Zongmin
 * @Date Create in 2021/7/31 9:25
 * @Modified By:
 */
@EnableAsync
@EnableScheduling
@SpringBootApplication
public class DoubleCatApplication {
    public static void main(String[] args) {
        SpringApplication.run(DoubleCatApplication.class, args);
    }
}
