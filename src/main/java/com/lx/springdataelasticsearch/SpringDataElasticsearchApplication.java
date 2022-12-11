package com.lx.springdataelasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author lengxu
 */
@SpringBootApplication
@ComponentScan(value = "com.lx.springdataelasticsearch")
public class SpringDataElasticsearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataElasticsearchApplication.class, args);
    }

}
