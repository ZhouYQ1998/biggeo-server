package edu.zju.gis.dldsj.server.entity;

import edu.zju.gis.dldsj.server.base.Base;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Jiarui
 * @date 2020/10/12
 */
@Getter
@Setter
@ToString(callSuper = true)
public class TeachModel extends Base<String> {
    String name;
    String description;
    Date date;
    String keywords;
    String groupId;
    String authorId;
    String email;
    String fileTemplate;
    String filePath;
    String picPath;
    String fileType;
}
