package com.lx.springdataelasticsearch.controller;

import com.lx.springdataelasticsearch.service.CreateDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lengxu32110
 * @Date 2022/12/11 20:43
 */
@RestController
public class CreateDataController {

    @Autowired
    CreateDataService createDataService;

    @GetMapping(value = "/createData")
    public void createData() {
        createDataService.selectAll();
    }
}
