package edu.zju.gis.dldsj.server.base;

import com.github.pagehelper.PageHelper;
import edu.zju.gis.dldsj.server.common.Page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * @author Hu
 * Service基类，实现了数据的CRUD
 * @param <Mapper>
 * @param <T>
 * @param <ID>
 * @update zyq 2020/09/23
 */
public abstract class BaseServiceImpl<Mapper extends BaseMapper<T, ID>, T , ID extends Serializable> implements BaseService<T, ID> {

    private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    /**
     * 注入mapper
     */
    @Autowired
    public Mapper mapper;

    /**
     * 插入实体
     * @param t T
     */
    @Override
    public int insert(T t) {
        return mapper.insert(t);
    }

    /**
     * 删除实体
     * @param id ID
     */
    @Override
    public void delete(ID id) {
        mapper.deleteByPrimaryKey(id);
    }

    /**
     * 查询实体
     * @param pk ID
     */
    @Override
    public T select(ID pk) {
        return mapper.selectByPrimaryKey(pk);
    }

    /**
     * 更新实体
     * @param t T
     */
    @Override
    public void update(T t) {
        mapper.updateByPrimaryKey(t);
    }

    /**
     * 判断实体是否已经存在
     */
    @Override
    public boolean isExist(ID id) {
        T t = select(id);
        if (t == null) {
            return false;
        }
        return true;
    }

    /**
     * 根据查询条件获取列表
     */
    public Page<T> search(BaseFilter<ID> params, Page<T> page) {
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        return new Page<>(mapper.search(params));
    }

    @Override
    public List<T> getByPage(int offset, int size) {
        return mapper.selectByPage(offset, size);
    }
}
