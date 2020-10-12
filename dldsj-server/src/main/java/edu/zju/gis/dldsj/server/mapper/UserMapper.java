package edu.zju.gis.dldsj.server.mapper;

import edu.zju.gis.dldsj.server.base.BaseMapper;
import edu.zju.gis.dldsj.server.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author zyq 2020/09/23
 */
public interface UserMapper extends BaseMapper<User, String> {

    int deleteByName(String name);

    User selectByName(String name);

    int addSign(String id);

    int selectAllSign();

    List<Map<String, String>> selectByCountry();

}