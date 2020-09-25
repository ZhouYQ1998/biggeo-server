package edu.zju.gis.dldsj.server.service.impl;

import edu.zju.gis.dldsj.server.base.BaseServiceImpl;
import edu.zju.gis.dldsj.server.mapper.UserMapper;
import edu.zju.gis.dldsj.server.entity.User;
import edu.zju.gis.dldsj.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zyq 2020/09/23
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User, String> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void deleteByName(String name){
        userMapper.deleteByName(name);
    }

    @Override
    public User selectByName(String name){
        return userMapper.selectByName(name);
    }

}
