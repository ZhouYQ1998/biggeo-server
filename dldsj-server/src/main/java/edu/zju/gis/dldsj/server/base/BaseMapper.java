package edu.zju.gis.dldsj.server.base;

import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;


/**
 * @author Hu
 * dao接口基类
 * @update zyq 2020/09/23
 */
public interface BaseMapper<T , ID extends Serializable> {

    /**
     * 插入实体
     */
    int insert(T t);

    /**
     * 删除实体
     */
    void deleteByPrimaryKey(ID pk);

    /**
     * 查询实体
     */
    T selectByPrimaryKey(ID pk);

    /**
     * 更新实体
     */
    void updateByPrimaryKey(T t);

    /**
     * 根据参数查询对象
     */
    List<T> search(BaseFilter<ID> params);

    List<T> selectByPage(@Param("offset") int offset, @Param("size") int size);
}
