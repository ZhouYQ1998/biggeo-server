package edu.zju.gis.dldsj.server.mapper;

import edu.zju.gis.dldsj.server.base.BaseMapper;
import edu.zju.gis.dldsj.server.entity.GroupMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Jiarui 2020/08/31
 * @update zyq 2020/09/25
 */
public interface GroupMemberMapper extends BaseMapper<GroupMember,String> {

    List<GroupMember> showAllMembers();

    List<GroupMember> showTeachers();

    List<GroupMember> showByVersion(String version);

    List<GroupMember> showByTeam(@Param("version") String version, @Param("team") String team);
}
