package edu.zju.gis.dldsj.server.service.impl;

import edu.zju.gis.dldsj.server.base.BaseServiceImpl;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.constant.CodeConstants;
import edu.zju.gis.dldsj.server.mapper.UserMapper;
import edu.zju.gis.dldsj.server.entity.User;
import edu.zju.gis.dldsj.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author zyq 2020/09/23
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User, String> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<String> deleteByName(String name){
        Result<String> result = new Result<>();
        try {
            int num = userMapper.deleteByName(name);
            if(num == 1){
                result.setCode(CodeConstants.SUCCESS).setBody(name).setMessage("删除成功");
            }
            else{
                result.setCode(CodeConstants.USER_NOT_EXIST).setBody(name).setMessage("删除失败：用户不存在");
            }
        } catch (RuntimeException e) {
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("删除失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public Result<User> selectByName(String name){
        Result<User> result = new Result<>();
        try {
            User user = userMapper.selectByName(name);
            if(user != null){
                result.setCode(CodeConstants.SUCCESS).setBody(user).setMessage("查询成功");
            }
            else{
                result.setCode(CodeConstants.VALIDATE_ERROR).setMessage("查询失败：无结果");
            }
        } catch (RuntimeException e) {
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("查询失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public int addSign(String id){
        return userMapper.addSign(id);
    }

    @Override
    public int selectAllSign(){
        return userMapper.selectAllSign();
    }

    @Override
    public List<Map<String, String>> selectByCountry(){
        return userMapper.selectByCountry();
    }

}
