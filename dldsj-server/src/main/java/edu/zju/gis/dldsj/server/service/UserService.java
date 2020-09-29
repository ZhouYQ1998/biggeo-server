package edu.zju.gis.dldsj.server.service;

import edu.zju.gis.dldsj.server.base.BaseService;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.entity.User;

/**
 * @author zyq 2020/09/23
 */
public interface UserService extends BaseService<User, String> {

    Result<String> deleteByName(String name);

    Result<User> selectByName(String name);

}
