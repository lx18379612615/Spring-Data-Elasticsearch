package com.lx.springdataelasticsearch.service.impl;

import com.lx.springdataelasticsearch.service.ElasticsearchService;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexedObjectInformation;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Boolean existsIndex(String indexName) {
        return elasticsearchRestTemplate.indexOps(IndexCoordinates.of(indexName)).exists();
    }

    @Override
    public void saveDocument() {
        // 以map对象插入
        Map<String, Object> documentMap = new HashMap<>();
        documentMap.put("client_id", "110001");

        // withId()设置_id
        IndexQuery indexQuery = new IndexQueryBuilder().withId("110001").withObject(documentMap).build();
        elasticsearchRestTemplate.index(indexQuery, IndexCoordinates.of("create_data"));
    }

    @Override
    public void batchSaveDocument() {
        List<IndexQuery> list = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("client_id", "100002");
        IndexQuery indexQuery1 = new IndexQueryBuilder().withId("100001").withObject(map1).build();
        Map<String, Object> map2 = new HashMap<>();
        map2.put("client_id", "100003");
        IndexQuery indexQuery2 = new IndexQueryBuilder().withId("100002").withObject(map2).build();
        list.add(indexQuery1);
        list.add(indexQuery2);
        List<IndexedObjectInformation> informations = elasticsearchRestTemplate.bulkIndex(list, IndexCoordinates.of("create_data"));
        informations.forEach(System.out::println);
    }
}
