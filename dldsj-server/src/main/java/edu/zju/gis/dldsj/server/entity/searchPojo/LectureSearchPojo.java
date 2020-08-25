package edu.zju.gis.dldsj.server.entity.searchPojo;

import edu.zju.gis.dldsj.server.base.BaseFilter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jiarui
 * @date 2020/8/24
 */

@Getter
@Setter
@ToString(callSuper = true)
public class LectureSearchPojo extends BaseFilter<String> {
    public String name;
}
