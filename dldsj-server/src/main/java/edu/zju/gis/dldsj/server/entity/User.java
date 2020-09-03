package edu.zju.gis.dldsj.server.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class User {
    private String id;
    private String roleId;
    private String name;
    private String fullName;
    private String password;
    private String psdPrompt;
    private String sex;
    private String description;
    private Date registrationTime;
    private String phone;
    private String email;
    private String organization;
    private String department;
    private String status;
    private String privateDir;
    private int dirSize;
    private String avatarPath;
    private int dirUsedSize;
}

