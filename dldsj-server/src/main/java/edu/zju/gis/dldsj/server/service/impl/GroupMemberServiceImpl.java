package edu.zju.gis.dldsj.server.service.impl;

import edu.zju.gis.dldsj.server.base.BaseServiceImpl;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.entity.GroupMember;
import edu.zju.gis.dldsj.server.mapper.GroupMemberMapper;
import edu.zju.gis.dldsj.server.service.GroupMemberService;
import org.springframework.stereotype.Service;

/**
 * @author Jiarui
 * @date 2020/8/31
 */
@Service
public class GroupMemberServiceImpl extends BaseServiceImpl<GroupMemberMapper, GroupMember,String> implements GroupMemberService {

    public Page<GroupMember> showAllMembers(){
        return new Page<>(mapper.showAllMembers());
    }
    public Page<GroupMember> showByGroup(String group){
        return new Page<>(mapper.showByGroup(group));
    }
}
