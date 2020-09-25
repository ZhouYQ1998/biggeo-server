package edu.zju.gis.dldsj.server.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author zyq 2020/09/23
 */
@Getter
@Setter
@ToString(callSuper = true)
public class User {
    private String id;
    private String name;
    private String password;
    private String role;
    private String phone;
    private String email;
    private String icon;
}

