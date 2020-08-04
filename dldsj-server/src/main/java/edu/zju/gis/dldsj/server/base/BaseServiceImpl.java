package edu.zju.gis.dldsj.server.base;

import com.github.pagehelper.PageHelper;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.exception.DaoException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * Service基类，实现了数据的CRUD
 *
 * @param <Mapper>
 * @param <T>
 * @param <ID>
 * @author Hu
 */
public abstract class BaseServiceImpl<Mapper extends BaseMapper<T, ID>, T , ID extends Serializable> implements BaseService<T, ID> {

    private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    /**
     * 由子类注入mapper
     */
    @Autowired
    public Mapper mapper;

    /**
     * 通过主键查询实体
     *
     * @param pk
     * @return T
     */
    @Override
    public T select(ID pk) {
        return mapper.selectByPrimaryKey(pk);
    }

    /**
     * 通过参数查询实体
     *
     * @param params
     * @return
     */
    public Page<T> search(BaseFilter params, Page page) {
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        return new Page<>(mapper.search(params));
    }

    /**
     * 插入实体
     *
     * @param t
     */
    @Override
    public int insert(T t) {
        return mapper.insert(t);
    }


    /**
     * 更新实体
     *
     * @param t
     */
    @Override
    public void update(T t) {
        verifyRows(mapper.updateByPrimaryKeySelective(t), 1, "数据库更新失败");
    }


    /**
     * 通过主键删除实体
     *
     * @param id
     * @return T
     */
    @Override
    public void delete(ID id) {
        mapper.deleteByPrimaryKey(id);
    }

    public int delete(List<ID> ids) {
        return mapper.deleteBatch(ids);
    }


    @Override
    public boolean isExist(ID id) {
        T t = select(id);
        if (t == null) {
            return false;
        }
        return true;
    }

    @Override
    public List<T> getByPage(int offset, int size) {
        return mapper.selectByPage(offset, size);
    }

    /**
     * 验证更新数据库记录条数
     *
     * @param updateRows
     * @param rows
     * @param message
     */
    protected void verifyRows(int updateRows, int rows, String message) {
        if (updateRows != rows) {
            DaoException e = new DaoException(message);
            logger.error("need update is {}, but real update rows is {}.", rows, updateRows, e);
//            throw e;
        }
    }
}
