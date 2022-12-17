package com.lx.springdataelasticsearch.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;

/**
 * @Author lengxu32110
 * @Date 2022/12/13 14:17
 */
@Data
@Builder
@TableName(value = "t_dictionary_data")
public class Dictionary {

    private String cDictEntry;

    private String cEntryName;

    private String cDictType;

    private String cSubEntry;

    private String cDictPrompt;

    private Date createTime;
}
