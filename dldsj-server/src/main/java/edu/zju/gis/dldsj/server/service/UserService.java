package edu.zju.gis.dldsj.server.service;

import edu.zju.gis.dldsj.server.base.BaseService;
import edu.zju.gis.dldsj.server.entity.User;

/**
 * @author zyq 2020/09/23
 */
public interface UserService extends BaseService<User, String> {

    int insert(User user);

    void deleteByName(String name);

    User selectByName(String name);

}
