package com.css;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Create by wx on 2020-08-24
 */
@SpringBootApplication
@MapperScan("com.css.im.*.mapper")
public class ImServer {

    public static void main(String[] args) {
        SpringApplication.run(ImServer.class, args);
    }

}
