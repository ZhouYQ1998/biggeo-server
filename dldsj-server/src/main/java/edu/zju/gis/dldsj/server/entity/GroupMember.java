package edu.zju.gis.dldsj.server.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jiarui 2020/08/31
 * @update zyq 2020/09/25
 */
@Getter
@Setter
@ToString(callSuper = true)
public class GroupMember {
    private int id;
    private String name;
    private String version;
    private String team;
    private String role;
    private String email;
    private String photo;
}
