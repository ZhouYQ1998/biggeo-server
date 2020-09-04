package edu.zju.gis.dldsj.server.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Jiarui
 * @date 2020/8/25
 */

@Getter
@Setter
@ToString(callSuper = true)
public class Geodata {
    private String id;
    private String title;
    private String uploader;
    private Date time;
    private String type1;
    private String type2;
    private String tags;
    private String source;
    private String abstractInfo;
    private String reference;

    //缩略图
    private String pic;
    //存放路径
    private String path;
    private String newName;
    private String oldName;
    private String format;
    private String ram;

    //以下为第二步筛选用到的条件
    private String filterType1;
    private String filterType2;

    //排序所用属性
    private String orderField;
    private String orderType;

}
