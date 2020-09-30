package edu.zju.gis.dldsj.server.entity;

import edu.zju.gis.dldsj.server.base.Base;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author zyq 2020/09/23
 */
@Getter
@Setter
@ToString(callSuper = true)
public class User extends Base<String> {
    private String name;
    private String password;
    private String phone;
    private String email;
    private String icon;
    private String country;
    private String institute;
    private String institutetype;
    private String field;
    private String purpose;
    private String role;
}

