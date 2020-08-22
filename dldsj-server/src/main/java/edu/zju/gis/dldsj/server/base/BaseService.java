package edu.zju.gis.dldsj.server.base;

import edu.zju.gis.dldsj.server.common.Page;

import java.io.Serializable;
import java.util.List;

/**
 * Service接口基类
 */
public interface BaseService<T , ID extends Serializable> {

    /**
     * 根据查询条件获取列表
     * @return
     */
    Page<T> search(BaseFilter params, Page page);

    /**
     * 通过主键查询实体
     */
    T select(ID pk);

    /**
     * 插入/更新实体
     */
    int insert(T t);

    /**
     * 更新实体
     */
    void update(T t);

    /**
     * 通过主键删除实体
     */
    void delete(ID id);

    /**
     * 判断实体是否已经存在
     */
    boolean isExist(ID id);

    List<T> getByPage(int offset, int size);

}
