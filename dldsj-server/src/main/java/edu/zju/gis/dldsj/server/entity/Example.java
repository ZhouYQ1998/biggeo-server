package edu.zju.gis.dldsj.server.entity;

import edu.zju.gis.dldsj.server.base.BaseFilter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Hu
 * @date 2020/8/23
 * 编码规范示范类
 **/
@Getter
@Setter
@ToString(callSuper = true)
public class Example extends BaseFilter<String> {
  private String name;
  private Integer age;
  private Date time;
  private Boolean gender;
}
