package edu.zju.gis.dldsj.server.entity;

import edu.zju.gis.dldsj.server.base.Base;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    private String time;
    private String place;
    private String href;
}

