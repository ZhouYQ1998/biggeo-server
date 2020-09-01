package edu.zju.gis.dldsj.server.entity.searchPojo;

import edu.zju.gis.dldsj.server.base.BaseFilter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jiarui
 * @date 2020/8/26
 */
@Getter
@Setter
@ToString(callSuper = true)
public class LiteratureSearchPojo extends BaseFilter<String> {
    private String title;
    private String author;
    private String keyword;
    private String classification;
    private String source;
}
