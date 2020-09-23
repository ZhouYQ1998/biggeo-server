package edu.zju.gis.dldsj.server.base;

import edu.zju.gis.dldsj.server.common.Page;

import java.io.Serializable;
import java.util.List;

/**
 * @author Hu
 * Service接口基类
 * @update zyq 2020/09/23
 */
public interface BaseService<T , ID extends Serializable> {

    /**
     * 插入实体
     */
    int insert(T t);

    /**
     * 删除实体
     */
    void delete(ID id);

    /**
     * 查询实体
     */
    T select(ID pk);

    /**
     * 更新实体
     */
    void update(T t);

    /**
     * 判断实体是否已经存在
     */
    boolean isExist(ID id);

    /**
     * 根据查询条件获取列表
     */
    Page<T> search(BaseFilter<ID> params, Page<T> page);

    List<T> getByPage(int offset, int size);

}
