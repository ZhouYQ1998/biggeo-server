package edu.zju.gis.dldsj.server.entity;

import edu.zju.gis.dldsj.server.base.Base;
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
public class Geodata extends Base<String> {
    private String ID;
    private String title;
    private String uploader;
    private String userName;
    private Boolean downloadAuthority;
    private Date time;
    private String type1;
    private String type2;
    private String keywords;
    private String source;
    private String abstractInfo;
    private String reference;

    //缩略图
    private String picture;
    //存放路径
    private String newName;
    private String oldName;
    private String format;
    private String path;
    private String ram;

    //以下为第二步筛选用到的条件
    private String filterType1;
    private String filterType2;

    //排序所用属性
    private String orderField;
    private String orderType;

    //下载次数
    private int downloadTimes;

}
