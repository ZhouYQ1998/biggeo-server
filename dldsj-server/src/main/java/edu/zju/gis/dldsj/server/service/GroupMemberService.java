package edu.zju.gis.dldsj.server.service;

import edu.zju.gis.dldsj.server.base.BaseService;
import edu.zju.gis.dldsj.server.entity.GroupMember;

import java.util.List;

/**
 * @author Jiarui
 * @date 2020/8/31
 */
public interface GroupMemberService extends BaseService<GroupMember,String> {
    List<GroupMember> showAllMembers();
    List<GroupMember> showByGroup(String group);
}
