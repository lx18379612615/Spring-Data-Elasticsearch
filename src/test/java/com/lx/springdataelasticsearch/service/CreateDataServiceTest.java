package com.lx.springdataelasticsearch.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author lengxu32110
 * @Date 2022/12/10 21:46
 */

@SpringBootTest
public class CreateDataServiceTest {

    @Autowired
    CreateDataService createDataService;

    @Test
    public void selectAll() {
        createDataService.selectAll();
    }
}