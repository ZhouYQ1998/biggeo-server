package edu.zju.gis.dldsj.server.entity;

import edu.zju.gis.dldsj.server.base.Base;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Jiarui 2020/8/25
 * @update zyq 2020/11/4
 */

@Getter
@Setter
@ToString(callSuper = true)
public class Geodata extends Base<String> {

    private String title;
    private String author;
    private Date time;
    private String type1;
    private String type2;
    private String keywords;
    private String abstractInfo;
    private String reference;

    // 缩略图
    private String picture;

    // 存放路径
    private String format;
    private String path;
    private String ram;

    // 访问次数
    private int viewTimes;

}
