package edu.zju.gis.dldsj.server.service;

import edu.zju.gis.dldsj.server.base.BaseService;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.entity.GroupMember;

import java.util.List;

/**
 * @author Jiarui 2020/08/31
 * @update zyq 2020/09/25
 */
public interface GroupMemberService extends BaseService<GroupMember,String> {

    Page<GroupMember> selectTeachers(Page<GroupMember> page);

    Page<GroupMember> selectByVersion(String version, Page<GroupMember> page);

    Page<GroupMember> selectByTeam(String version, String team, Page<GroupMember> page);

}
