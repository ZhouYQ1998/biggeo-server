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
    private String keyword;
    private Date time;
    private String classification;
    private String source;
    private String downloads;
    private String path;
    private String url;
}
