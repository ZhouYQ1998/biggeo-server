package edu.zju.gis.dldsj.server.entity.workflow;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Jiarui
 * @date 2020/9/24
 */
@Getter
@Setter
public class Param {
    String defaultValue;
    String datatype;
    String in;
    String out;
    String name;
    String description;
    String spatialization;
    String label;
    String value;
}
