package edu.zju.gis.dldsj.server.service.impl;

import edu.zju.gis.dldsj.server.base.BaseServiceImpl;
import edu.zju.gis.dldsj.server.entity.GroupMember;
import edu.zju.gis.dldsj.server.mapper.GroupMemberMapper;
import edu.zju.gis.dldsj.server.service.GroupMemberService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jiarui
 * @date 2020/8/31
 */
@Service
public class GroupMemberServiceImpl extends BaseServiceImpl<GroupMemberMapper, GroupMember,String> implements GroupMemberService {

    public List<GroupMember> showAllMembers(){
        return mapper.showAllMembers();
    }
    public List<GroupMember> showByGroup(String group){
        return mapper.showByGroup(group);
    }
}
