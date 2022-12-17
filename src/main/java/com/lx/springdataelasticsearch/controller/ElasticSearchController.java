package com.lx.springdataelasticsearch.controller;

import com.lx.springdataelasticsearch.service.ElasticsearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author lengxu32110
 * @Date 2022/12/11 22:25
 */
@RestController
public class ElasticSearchController {

    @Resource
    ElasticsearchService elasticsearchService;

    @GetMapping(value = "createIndex")
    public void createIndex() {
        elasticsearchService.createIndex();
    }

    @GetMapping(value = "existsIndex")
    public Boolean existsIndex(String indexName) {
        return elasticsearchService.existsIndex(indexName);
    }

    @GetMapping(value = "saveDocument")
    public void saveDocument() {
        elasticsearchService.saveDocument();
    }

    @GetMapping(value = "batchSaveDocument")
    public void batchSaveDocument() {
        elasticsearchService.batchSaveDocument();
    }
}
