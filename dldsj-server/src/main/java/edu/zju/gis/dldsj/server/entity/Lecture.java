package edu.zju.gis.dldsj.server.entity;

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
public class Lecture {
    private String id;
    private String name;
    private String speaker;
    private String time;
    private String place;
    private String href;
}

