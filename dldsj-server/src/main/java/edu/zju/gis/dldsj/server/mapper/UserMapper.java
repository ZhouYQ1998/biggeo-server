package edu.zju.gis.dldsj.server.mapper;

import edu.zju.gis.dldsj.server.base.BaseMapper;
import edu.zju.gis.dldsj.server.entity.User;

/**
 * @author zyq 2020/09/23
 */
public interface UserMapper extends BaseMapper<User, String> {

    int deleteByName(String name);

    User selectByName(String name);

}