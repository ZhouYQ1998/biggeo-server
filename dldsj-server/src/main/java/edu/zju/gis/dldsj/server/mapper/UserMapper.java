package edu.zju.gis.dldsj.server.mapper;

import edu.zju.gis.dldsj.server.base.BaseMapper;
import edu.zju.gis.dldsj.server.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User, String> {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByName(String name);

    List<User> selectByRoleId(@Param("roleId") String roleId, @Param("offset") int offset, @Param("size") int size);

    int deleteByRoleId(String roleId);

    int ifUserNameExist(String userName);
}