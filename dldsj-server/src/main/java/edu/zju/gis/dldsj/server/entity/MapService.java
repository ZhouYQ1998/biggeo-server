package edu.zju.gis.dldsj.server.entity;

import edu.zju.gis.dldsj.server.base.Base;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author: zjh
 * @date: 20201012
 */

@Getter
@Setter
@ToString(callSuper = true)
public class MapService extends Base<String> {
    private String ID;
    private String NAME;
    private String COMPANY;
    private String REGION;
    private String SERVER;
    private String LIMITED;
    private String PICTURE;
    private String DESCRIBE;
    private String URL;
}
