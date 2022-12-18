package com.lx.springdataelasticsearch.service.impl;

import com.lx.springdataelasticsearch.entity.Dictionary;
import com.lx.springdataelasticsearch.entity.Tag;
import com.lx.springdataelasticsearch.service.GetValueService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import static com.lx.springdataelasticsearch.constants.Constants.*;
import static com.lx.springdataelasticsearch.service.impl.CreateDataServiceImpl.dictionaryList;

/**
 * @Author lengxu32110
 * @Date 2022/12/11 21:07
 */
@Service
public class GetValueServiceImpl implements GetValueService {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public String getStringValue(Tag tag) {
        String englishName = tag.getCTagEnglishName();
        String value;
        switch (englishName) {
            case CLIENT_ID_EN:
            case FUND_ACCOUNT_EN:
            case PHONE_EN:
                value = String.valueOf(Long.parseLong(Objects.requireNonNull(stringRedisTemplate.opsForValue().get(UNION_ID_INCREMENT))) + UNION_ID_PREFIX);
                break;
            case CLIENT_NAME_EN:
            case NME_EN:
                value = UNION_NAME_PREFIX + (Long.parseLong(Objects.requireNonNull(stringRedisTemplate.opsForValue().get(UNION_ID_INCREMENT))) + UNION_ID_PREFIX);
                break;
            default:
                value = String.valueOf(new Random().nextInt(100000));

        }
        return value;
    }

    @Override
    public Long getLongValue(Tag tag) {
        return new Random().nextLong() % 100000;
    }

    @Override
    public Double getDoubleValue(Tag tag) {
        return (double) Math.round(new Random().nextDouble() * 10);
    }

    @Override
    public String getIdValue(Tag tag) {
        String value;
        String cDictEntry = tag.getCDictEntry();
        List<Dictionary> entryList = dictionaryList.stream()
                .filter(dictionary -> cDictEntry.equals(dictionary.getCDictEntry()))
                .collect(Collectors.toList());
        if (entryList.size() != 0) {
            int index = new Random().nextInt(entryList.size());
            value = entryList.get(index).getCSubEntry();
        } else {
            value = String.valueOf(new Random().nextInt(100000));
        }
        return value;
    }
}
