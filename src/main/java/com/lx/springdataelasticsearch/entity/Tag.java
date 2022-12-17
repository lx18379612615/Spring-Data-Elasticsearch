package com.lx.springdataelasticsearch.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

/**
 * @Author lengxu32110
 * @Date 2022/12/10 21:30
 */
@Data
@Builder
@TableName(value = "t_tag_cat")
public class Tag implements Serializable {

//    @TableId(value = "id", type = IdType.AUTO)

    private String cTagCategoryId;

    private String tenantId;

    private String branchCode;

    private String version;

    private String cTagCategoryVariety;

    private String cTagCategoryField;

    private String cTagCategoryType;

    private String cTagCategoryFamily;

    private String cTagCategoryChild;

    private String cTagCategoryName;

    private String cTagValueType;

    private String cTagLevel;

    private String cGroupCategory;

    private String cTagEnglishName;

    private Integer cHasOperator;

    private String cTagUnit;

    private String cTagFilter;

    private String cDictEntry;

    private Integer isDaily;

    private String operatorNo;

    private Date createTime;

    private String applicant;

    private String consumer;

    private String developer;

    private Integer checkStatus;

    private Integer tagStatus;

    private String tagSource;

    private String timeliness;

    private String cAlgoContent;

    private String cTagDefinition;

    private String portraitFlag;

    private String groupFlag;

    private String remark;

    private String spreadFlag;

    private String tagJobEnName;

    private String tagJobChName;

    private String tagDepedenceJobName;

    private String tagDevelopCaliber;

    private String tagDeveloperFirst;

    private String tagDeveloperSecond;

    private String tagCreaterName;

    private String tagCodeHistory;

    private String tagCodeDaily;

    private String tagBelong;

    private String tagUpdateTime;
}
