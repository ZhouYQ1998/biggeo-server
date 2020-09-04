package edu.zju.gis.dldsj.server.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Jiarui
 * @date 2020/8/20 3:20 下午
 */

@Getter
@Setter
@ToString(callSuper = true)
public class Literature {
    private String id;
    private String title;
    private String author;
    private String year;
    private String source;
    private String volume;
    private String issue;
    private String pages;
    private String keywords;
    private String abstractInfo;
    private String classification;
    private String url;
    private Date date;
    private String authorAffiliation;

    //根据年份来筛选
    private Integer startTime;
    private Integer endTime;

    //根据搜索结果的第二重筛选属性
    private String yearFilter;
    private String sourceFilter;
    private String authorAffiliationFilter;

    //根据搜索结果对某些字段对唯一值进行计数,distinctField为检索时所选字段，xxxDistinct用来存储该类下的唯一值
    private String distinctField;
    private String yearDistinct;
    private String sourceDistinct;
    private String authorAffiliationFilterDistinct;

    //排序所用属性
    private String orderField;
    private String orderType;
}
