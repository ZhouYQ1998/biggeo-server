package edu.zju.gis.dldsj.server.entity.searchPojo;

import edu.zju.gis.dldsj.server.base.BaseFilter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jiarui
 * @date 2020/8/26
 */
@Getter
@Setter
@ToString(callSuper = true)
public class LiteratureSearchPojo extends BaseFilter<String> {
    private String title;
    private String author;
    private String year;
    private String source;
    private String keywords;
    private String abstractInfo;
    private String classification;
    private String url;
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
