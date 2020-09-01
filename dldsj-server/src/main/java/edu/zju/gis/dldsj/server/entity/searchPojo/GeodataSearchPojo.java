package edu.zju.gis.dldsj.server.entity.searchPojo;

import edu.zju.gis.dldsj.server.base.BaseFilter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jiarui
 * @date 2020/8/25
 */
@Getter
@Setter
@ToString(callSuper = true)
public class GeodataSearchPojo extends BaseFilter<String> {
    private String title;
    private String uploader;
    private String source;
    private String type1;
    private String type2;
    private String tags;

    //以下为第二步筛选用到的条件
    private String filterType1;
    private String filterType2;

    //排序所用属性
    private String orderField;
    private String orderType;
}
