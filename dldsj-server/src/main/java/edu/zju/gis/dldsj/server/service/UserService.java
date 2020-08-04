package edu.zju.gis.dldsj.server.service;

import edu.zju.gis.dldsj.server.base.BaseService;
import edu.zju.gis.dldsj.server.entity.User;

import java.util.List;

/**
 * @author yanlo yanlong_lee@qq.com
 * @version 1.0 2018/08/09
 */
public interface UserService extends BaseService<User, String> {
    User getByName(String name);

    List<User> getByRoleId(String roleId, int offset, int size);

    void deleteByRoleId(String roleId);

    boolean ifUserNameExist(String userName);
}
