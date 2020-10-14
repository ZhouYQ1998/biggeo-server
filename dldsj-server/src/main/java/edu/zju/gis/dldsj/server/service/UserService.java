package edu.zju.gis.dldsj.server.service;

import edu.zju.gis.dldsj.server.base.BaseService;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author zyq 2020/09/23
 */
public interface UserService extends BaseService<User, String> {

    Result<String> deleteByName(String name);

    Result<User> selectByName(String name);

    int addSign(String id);

    int selectAllSign();

    List<Map<String, String>> selectByCountry();

}
