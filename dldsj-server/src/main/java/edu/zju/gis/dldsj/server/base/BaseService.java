package edu.zju.gis.dldsj.server.base;

import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.entity.Batch;

import java.io.Serializable;
import java.util.List;

/**
 * @author Hu
 * Service接口基类
 * @update zyq 2020/09/23
 */
public interface BaseService<T extends Base, ID extends Serializable> {

    /**
     * 插入实体
     */
    Result<T> insert(T t);

    /**
     * 插入实体（批量）
     */
    Result<List<Batch<T>>> batchInsert(List<T> t);

    /**
     * 删除实体
     */
    Result<ID> delete(ID id);

    /**
     * 删除实体（批量）
     */
    Result<String> batchDelete(String ids);

    /**
     * 查询实体
     */
    Result<T> select(ID id);

    /**
     * 查询实体（批量）
     */
    Result<Page<T>> batchSelect(String ids, Page<T> page);

    /**
     * 查询实体（所有）
     */
    Result<Page<T>> allSelect(Page<T> page);

    /**
     * 更新实体
     */
    Result<T> update(T t);

    /**
     * 更新实体（批量）
     */
    Result<List<Batch<T>>> batchUpdate(List<T> t);

    /**
     *
     */
    List<T> getByPage(int offset, int size);

}
