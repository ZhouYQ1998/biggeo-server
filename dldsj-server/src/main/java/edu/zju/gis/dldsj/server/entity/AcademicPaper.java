package edu.zju.gis.dldsj.server.entity;

import edu.zju.gis.dldsj.server.base.Base;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author zyq 2020/10/16
 */
@Getter
@Setter
@ToString(callSuper = true)
public class AcademicPaper extends Base<String> {
    private String title;
    private String englishTitle;
    private String type;
    private String author;
    private String authorAffiliation;
    private String year;
    private String sourceName;
    private String volume;
    private String issue;
    private String pages;
    private String keywords;
    private String abstract_;
    private String doi;
    private String issu;
    private String url;
}

