package com.lx.springdataelasticsearch.service.impl;

import com.lx.springdataelasticsearch.service.ElasticsearchService;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author lengxu32110
 * @Date 2022/12/11 22:13
 */
@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {

    @Resource
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public void createIndex() {
        boolean success = elasticsearchRestTemplate.indexOps(IndexCoordinates.of("create_data")).create();
        System.out.println(success);
    }
}
