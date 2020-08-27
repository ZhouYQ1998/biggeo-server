package edu.zju.gis.dldsj.server.entity.searchPojo;

import edu.zju.gis.dldsj.server.base.BaseFilter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jiarui
 * @date 2020/8/25
 */
@Getter
@Setter
@ToString(callSuper = true)
public class GeodataSearchPojo extends BaseFilter<String> {
    private String title;
    private String uploader;
    private String source;
    private String type;
}
