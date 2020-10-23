package edu.zju.gis.dldsj.server.entity;

import edu.zju.gis.dldsj.server.base.Base;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Jiarui
 * @date 2020/8/19
 */

@Getter
@Setter
@ToString(callSuper = true)
public class Lecture extends Base<String> {
    private String name;
    private String speaker;
    private String place;
    private Date time;
    private String detailTime;
    private String url;
}

