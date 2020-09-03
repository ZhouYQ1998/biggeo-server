package edu.zju.gis.dldsj.server.base;

import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;


/**
 * dao接口基类.
 */
public interface BaseMapper<T , ID extends Serializable> {

    /**
     * 通过主键查询实体
     */
    T selectByPrimaryKey(ID pk);

    /**
     * 根据参数查询对象
     *
     * @param params
     * @return
     */
    List<T> search(BaseFilter<ID> params);

    /**
     * 插入实体
     */
    int insert(T t);

    int insertSelective(T t);

    int updateByPrimaryKeySelective(T t);

    /**
     * 更新实体
     */
    int updateByPrimaryKey(T t);

    /**
     * 删除实体
     */
    int deleteByPrimaryKey(ID pk);

    List<T> selectByPage(@Param("offset") int offset, @Param("size") int size);

    /**
     * 删除批量实体
     *
     * @param ts
     * @return
     */
    public int deleteBatch(List<ID> ts);
}
