package edu.zju.gis.dldsj.server.entity;

import edu.zju.gis.dldsj.server.base.Base;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

/**
 * @author zlzhang
 * @date 2020/9/30
 * @update zyq 2020/10/20
 */

@Getter
@Setter
@ToString(callSuper = true)
public class Log extends Base<String> {

    private String actId; // 操作员id
    private String role; // 操作员类型
    private Timestamp time; // 对应的时间戳
    private String tableName; // 事件对应的表名
    private String objectId; // 表中对应的id
    private String type; // 操作的类型

}
