package edu.zju.gis.dldsj.server.service.impl;

import com.github.pagehelper.PageHelper;
import edu.zju.gis.dldsj.server.base.BaseServiceImpl;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.entity.GroupMember;
import edu.zju.gis.dldsj.server.mapper.GroupMemberMapper;
import edu.zju.gis.dldsj.server.service.GroupMemberService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jiarui 2020/08/31
 * @update zyq 2020/09/25
 */
@Service
public class GroupMemberServiceImpl extends BaseServiceImpl<GroupMemberMapper, GroupMember,String> implements GroupMemberService {

    public Page<GroupMember> showAllMembers(Page<GroupMember> page){
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        return new Page<>(mapper.showAllMembers());
    }

    public Page<GroupMember> showTeachers(Page<GroupMember> page){
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        return new Page<>(mapper.showTeachers());
    }

    public Page<GroupMember> showByVersion(String version,Page<GroupMember> page){
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        return new Page<>(mapper.showByVersion(version));
    }

    public Page<GroupMember> showByTeam(String version, String team,Page<GroupMember> page){
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        return new Page<>(mapper.showByTeam(version, team));
    }

}
