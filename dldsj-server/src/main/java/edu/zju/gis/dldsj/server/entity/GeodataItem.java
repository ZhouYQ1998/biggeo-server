package edu.zju.gis.dldsj.server.entity;

import edu.zju.gis.dldsj.server.base.Base;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @update zyq 2020/11/4
 */

@Getter
@Setter
@ToString(callSuper = true)
public class GeodataItem extends Base<String> {

    private String dataset;
    private String title;
    private String format;
    private String ram;
    private String remark;

}
