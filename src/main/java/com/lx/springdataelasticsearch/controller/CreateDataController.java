package com.lx.springdataelasticsearch.controller;

import com.lx.springdataelasticsearch.service.CreateDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lengxu32110
 * @Date 2022/12/11 20:43
 */
@RestController
@Slf4j
public class CreateDataController {

    @Autowired
    CreateDataService createDataService;

    @GetMapping(value = "/createData")
    public void createData() {
        try {
            createDataService.createData();
        } catch (Exception e) {
            log.error("createData接口异常", e);
        }
    }
}
