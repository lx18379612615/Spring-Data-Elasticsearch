package com.lx.springdataelasticsearch.service;

import com.lx.springdataelasticsearch.entity.Tag;

/**
 * @Author lengxu32110
 * @Date 2022/12/11 21:07
 */
public interface GetValueService {

    String getStringValue(Tag tag);

    Long getLongValue(Tag tag);

    Double getDoubleValue(Tag tag);

    String getIdValue(Tag tag);
}
