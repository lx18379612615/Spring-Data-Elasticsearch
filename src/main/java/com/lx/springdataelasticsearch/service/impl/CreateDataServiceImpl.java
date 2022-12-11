package com.lx.springdataelasticsearch.service.impl;

import com.lx.springdataelasticsearch.dao.CollectConfMapper;
import com.lx.springdataelasticsearch.entity.Tag;
import com.lx.springdataelasticsearch.service.CreateDataService;
import com.lx.springdataelasticsearch.service.GetValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @Author lengxu32110
 * @Date 2022/12/10 21:42
 */
@Service
public class CreateDataServiceImpl implements CreateDataService {

    @Autowired
    CollectConfMapper collectConfMapper;

    @Autowired
    GetValueService getValueService;

    private final Map<String, Function<String, Object>> mapFunction = new HashMap<>();

    @PostConstruct
    public void mapFunctionInit() {
        mapFunction.put("string", tag -> getValueService.getStringValue());
        mapFunction.put("integer", tag -> getValueService.getIntegerValue());
        mapFunction.put("long", tag -> getValueService.getLongValue());
        mapFunction.put("double", tag -> getValueService.getDoubleValue());
    }

    @Override
    public void selectAll() {
        List<Tag> tags = collectConfMapper.selectList(null);
        List<Map<String, Object>> oneDocumnent = getOneDocumnent(tags);
        oneDocumnent.forEach(System.out::println);

    }

    public List<Map<String, Object>> getOneDocumnent(List<Tag> tags) {

        List<Map<String, Object>> returnList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        for (Tag tag : tags) {
            Function<String, Object> function = mapFunction.get(tag.getTagValueType());
            map.put(tag.getTagEnName(), function.apply(tag.getTagEnName()));
        }
        returnList.add(map);

        return returnList;
    }
}
