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
public class GroupMemberServiceImpl extends BaseServiceImpl<GroupMemberMapper, GroupMember, Integer> implements GroupMemberService {

    public Page<GroupMember> selectTeachers(Page<GroupMember> page){
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        return new Page<>(mapper.selectTeachers());
    }

    public Page<GroupMember> selectByVersion(String version,Page<GroupMember> page){
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        return new Page<>(mapper.selectByVersion(version));
    }

    public Page<GroupMember> selectByTeam(String version, String team,Page<GroupMember> page){
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        return new Page<>(mapper.selectByTeam(version, team));
    }

}
