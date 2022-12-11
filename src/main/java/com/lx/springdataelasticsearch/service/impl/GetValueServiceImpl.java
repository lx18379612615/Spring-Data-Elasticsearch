package com.lx.springdataelasticsearch.service.impl;

import com.lx.springdataelasticsearch.service.GetValueService;
import org.springframework.stereotype.Service;

/**
 * @Author lengxu32110
 * @Date 2022/12/11 21:07
 */
@Service
public class GetValueServiceImpl implements GetValueService {

    @Override
    public String getStringValue() {
        return "1";
    }

    @Override
    public Integer getIntegerValue() {
        return 1;
    }

    @Override
    public Long getLongValue() {
        return 1L;
    }

    @Override
    public Double getDoubleValue() {
        return 1.0;
    }
}
