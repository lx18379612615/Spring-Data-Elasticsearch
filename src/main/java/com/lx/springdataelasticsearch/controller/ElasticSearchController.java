package com.lx.springdataelasticsearch.controller;

import com.lx.springdataelasticsearch.service.ElasticsearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lengxu32110
 * @Date 2022/12/11 22:25
 */
@RestController
public class ElasticSearchController {

    @Autowired
    ElasticsearchService elasticsearchService;

    @GetMapping(value = "createIndex")
    public void createIndex() {
        elasticsearchService.createIndex();
    }
}
