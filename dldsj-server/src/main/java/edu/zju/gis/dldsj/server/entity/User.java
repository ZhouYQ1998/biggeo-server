package edu.zju.gis.dldsj.server.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String id;
    private String name;
    private String password;
    private String role;
    private String phone;
    private String email;
}

