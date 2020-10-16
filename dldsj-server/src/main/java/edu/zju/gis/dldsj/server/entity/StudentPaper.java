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
public class StudentPaper extends Base<String> {
    private String title;
    private String englishTitle;
    private String author;
    private String publisher;
    private String tertiaryAuthor;
    private String year;
    private String type;
    private String keywords;
    private String abstract_;
    private String url;
}

