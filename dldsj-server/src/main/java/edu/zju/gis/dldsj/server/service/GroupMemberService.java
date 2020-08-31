package edu.zju.gis.dldsj.server.service;

import edu.zju.gis.dldsj.server.base.BaseService;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.entity.GroupMember;

import java.util.List;

/**
 * @author Jiarui
 * @date 2020/8/31
 */
public interface GroupMemberService extends BaseService<GroupMember,String> {
    Page<GroupMember> showAllMembers(Page page);
    Page<GroupMember> showByGroup(String group,Page page);
}
