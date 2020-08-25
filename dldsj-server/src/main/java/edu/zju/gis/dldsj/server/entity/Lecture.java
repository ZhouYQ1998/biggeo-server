package edu.zju.gis.dldsj.server.entity;

import edu.zju.gis.dldsj.server.base.BaseFilter;
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
public class Lecture {
    String id;
    String name;
    String speaker;
    String time;
    String place;
    String href;
}

