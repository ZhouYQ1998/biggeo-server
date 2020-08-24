package edu.zju.gis.dldsj.server.entity.searchPojo;

import edu.zju.gis.dldsj.server.base.BaseFilter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Hu
 * @date 2020/8/24
 **/
@Getter
@Setter
@ToString(callSuper = true)
public class ExampleSearchPojo extends BaseFilter<String> {
  private String name;
}
