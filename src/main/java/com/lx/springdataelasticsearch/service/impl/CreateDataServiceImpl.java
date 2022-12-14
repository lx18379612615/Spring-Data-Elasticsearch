package com.lx.springdataelasticsearch.service.impl;

import com.lx.springdataelasticsearch.constants.Constants;
import com.lx.springdataelasticsearch.dao.DictionaryMapper;
import com.lx.springdataelasticsearch.dao.TagMapper;
import com.lx.springdataelasticsearch.entity.Dictionary;
import com.lx.springdataelasticsearch.entity.Tag;
import com.lx.springdataelasticsearch.service.CreateDataService;
import com.lx.springdataelasticsearch.service.GetValueService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author lengxu32110
 * @Date 2022/12/10 21:42
 */
@Service
@Slf4j
public class CreateDataServiceImpl implements CreateDataService {

    @Resource
    TagMapper tagMapper;

    @Resource
    GetValueService getValueService;

    @Resource
    DictionaryMapper dictionaryMapper;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Value("${total.count}")
    Integer totalCount;

    @Value("${page.size}")
    Integer pageSize;

    @Value("${index.name}")
    String indexName;

    @Value("${thread.num.getOneDocument}")
    Integer threadNumGetOneDocument;

    @Value("${thread.num.esInsert}")
    Integer threadNumEsInsert;

    @Value("${union.english.name}")
    String unionEnglishName;

    private final Map<String, Function<Tag, Object>> mapFunction = new HashMap<>();

    public static List<Dictionary> dictionaryList = new ArrayList<>();

    @PostConstruct
    public void mapFunctionInit() {
        mapFunction.put("s", tag -> getValueService.getStringValue(tag));
        mapFunction.put("l", tag -> getValueService.getLongValue(tag));
        mapFunction.put("d", tag -> getValueService.getDoubleValue(tag));
        mapFunction.put("i", tag -> getValueService.getIdValue(tag));

        dictionaryList = dictionaryMapper.selectList(null);
        log.info("totalCount: {}", totalCount);
        log.info("pageSize: {}", pageSize);
        log.info("indexName: {}", indexName);
        log.info("threadNumGetOneDocument: {}", threadNumGetOneDocument);
        log.info("threadNumEsInsert: {}", threadNumEsInsert);
        log.info("unionEnglishName: {}", unionEnglishName);
    }

    @Override
    @Async
    public void createData() throws Exception {
        List<Tag> tags = tagMapper.selectList(null)
                .stream()
                .filter(tag -> StringUtils.isNotEmpty(tag.getCTagEnglishName()))
                .collect(Collectors.toList());

        int perSize = totalCount / pageSize;
        ExecutorService esInsertExecutor = Executors.newFixedThreadPool(threadNumEsInsert);
        for (int i = 0; i < perSize; i++) {
            ExecutorService executorService = Executors.newFixedThreadPool(threadNumGetOneDocument);
            CopyOnWriteArrayList<IndexQuery> indexQueryList = new CopyOnWriteArrayList<>();
            CountDownLatch countDownLatch = new CountDownLatch(pageSize);
            for (int j = 0; j < pageSize; j++) {
                executorService.execute(() -> {
                    Map<String, Object> oneDocument = getOneDocument(tags);
                    IndexQuery indexQuery = new IndexQueryBuilder().withId(oneDocument.get(unionEnglishName).toString())
                            .withObject(oneDocument)
                            .build();
                    indexQueryList.add(indexQuery);
                    countDownLatch.countDown();
                });
            }
            countDownLatch.await();
            StringBuilder progress = new StringBuilder().append(i+1).append("/").append(perSize);
            log.info("??????: {}", progress);
            esInsertExecutor.execute(() -> {
                elasticsearchRestTemplate.bulkIndex(indexQueryList, IndexCoordinates.of(indexName));
            });
        }
        threadPoolAwait(esInsertExecutor);
        log.info("??????");

    }

    public synchronized Map<String, Object> getOneDocument(List<Tag> tags) {
        stringRedisTemplate.opsForValue().increment(Constants.UNION_ID_INCREMENT);
        ConcurrentHashMap<String, Object>  map = new ConcurrentHashMap<>();
        for (Tag tag : tags) {
            Function<Tag, Object> function = mapFunction.get(tag.getCTagValueType());
            map.put(tag.getCTagEnglishName(), function.apply(tag));
        }
        return map;
    }

    /**
     * ????????????????????????
     *
     * @param threadPool ExecutorService-?????????
     */
    private void threadPoolAwait(ExecutorService threadPool) {
        threadPool.shutdown();
        // ?????????????????????????????????????????????????????????
        try {
            if (!threadPool.awaitTermination(2, TimeUnit.HOURS)) {
                log.error("??????????????????, ????????????");
                threadPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            log.error("??????????????????", e);
            Thread.currentThread().interrupt();
        }
    }
}
