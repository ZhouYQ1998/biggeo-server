package edu.zju.gis.dldsj.server.service.impl;

import edu.zju.gis.dldsj.server.base.BaseServiceImpl;
import edu.zju.gis.dldsj.server.mapper.UserMapper;
import edu.zju.gis.dldsj.server.entity.User;
import edu.zju.gis.dldsj.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yanlo yanlong_lee@qq.com
 * @version 1.0 2018/08/09
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User, String> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User select(String pk) {
        return userMapper.selectByPrimaryKey(pk);
    }

    @Override
    public int insert(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void delete(String s) {
        userMapper.deleteByPrimaryKey(s);
    }

    @Override
    public boolean isExist(String s) {
        return userMapper.selectByPrimaryKey(s) != null;
    }

    @Override
    public List<User> getByPage(int offset, int size) {
        return userMapper.selectByPage(offset, size);
    }

    @Override
    public User getByName(String name) {
        return userMapper.selectByName(name);
    }

    @Override
    public List<User> getByRoleId(String roleId, int offset, int size) {
        return userMapper.selectByRoleId(roleId, offset, size);
    }

    @Override
    public void deleteByRoleId(String roleId) {
        userMapper.deleteByRoleId(roleId);
    }

    @Override
    public boolean ifUserNameExist(String userName) {
        int a = userMapper.ifUserNameExist(userName);
        return a > 0 ? true : false;
    }
}
