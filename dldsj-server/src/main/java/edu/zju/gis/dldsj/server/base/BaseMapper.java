package edu.zju.gis.dldsj.server.base;

import java.io.Serializable;
import java.util.List;


/**
 * @author Hu
 * dao接口基类
 * @update zyq 2020/09/23
 */
public interface BaseMapper<T extends Base, ID extends Serializable> {

    /**
     * 插入实体
     */
    int insert(T t);

    /**
     * 删除实体
     */
    int deleteByPrimaryKey(ID id);

    /**
     * 更新实体
     */
    int updateByPrimaryKey(T t);

    /**
     * 查询实体
     */
    T selectByPrimaryKey(ID id);

    /**
     * 查询实体（批量）
     */
    List<T> batchSelect(List<ID> ids);

    /**
     * 查询实体（所有）
     */
    List<T> allSelect();

    /**
     * 查询实体（所有排序）
     */
    List<T> allSelectOrder(String order);

    /**
     * 查询实体（最新）
     */
    List<T> selectNew();

    /**
     * 查询实体（模糊搜索）
     */
    List<T> selectFuzzyName(String key);

    /**
     * 根据参数查询对象
     */
    List<T> search(BaseFilter<ID> params);


}
