package edu.zju.gis.dldsj.server.entity;

import edu.zju.gis.dldsj.server.base.BaseFilter;
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
    private String type;
    private String tags;
    private String source;
    //缩略图
    private String pic;
    //存放路径
    private String path;


}
