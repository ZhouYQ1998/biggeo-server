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
public class OnlineTool extends Base<String> {
    private String NAME;
    private String ABSTRACTS;
    private String PICTURE;
    private String URL;
}
