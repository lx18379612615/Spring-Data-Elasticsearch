package com.lx.springdataelasticsearch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author lengxu32110
 * @Date 2022/12/10 21:30
 */
@Data
@Builder
@TableName(value = "collect_conf")
public class Tag implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String tableName;

    private String tagEnName;

    private String tagValueType;
}
