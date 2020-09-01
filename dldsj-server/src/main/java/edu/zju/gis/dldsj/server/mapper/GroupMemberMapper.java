package edu.zju.gis.dldsj.server.mapper;

import edu.zju.gis.dldsj.server.base.BaseMapper;
import edu.zju.gis.dldsj.server.entity.GroupMember;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Jiarui
 * @date 2020/8/31
 */

public interface GroupMemberMapper extends BaseMapper<GroupMember,String> {

    List<GroupMember> showAllMembers();
    List<GroupMember> showByGroup(String group);
}
