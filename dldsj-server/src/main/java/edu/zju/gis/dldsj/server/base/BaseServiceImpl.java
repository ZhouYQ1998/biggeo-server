package edu.zju.gis.dldsj.server.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.autoconfigure.PageHelperProperties;
import edu.zju.gis.dldsj.server.common.Page;

import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.constant.CodeConstants;
import edu.zju.gis.dldsj.server.entity.Batch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author Hu
 * Service基类，实现了数据的CRUD
 * @param <Mapper>
 * @param <T>
 * @param <ID>
 * @update zyq 2020/09/23
 */
public abstract class BaseServiceImpl<Mapper extends BaseMapper<T, ID>, T extends Base<ID>, ID extends Serializable> implements BaseService<T, ID> {

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
    public Result<T> insert(T t) {
        Result<T> result = new Result<>();
        try{
            t.setId((ID) UUID.randomUUID().toString());
            int num = mapper.insert(t);
            if(num == 1){
                result.setCode(CodeConstants.SUCCESS).setBody(t).setMessage("插入成功");
            }
            else{
                result.setCode(CodeConstants.VALIDATE_ERROR).setBody(t).setMessage("插入失败");
            }
        }catch(RuntimeException e){
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("插入失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 插入实体（批量）
     * @param t List
     */
    @Override
    public Result<List<Batch<T>>> batchInsert(List<T> t){
        Result<List<Batch<T>>> result = new Result<>();
        List<Batch<T>> batchList = new ArrayList<>();
        int successNum = 0;
        for(int i = 0; i < t.size(); i++){
            T tempT = t.get(i);
            tempT.setId((ID)UUID.randomUUID().toString());
            Batch<T> batch = new Batch<>();
            batch.setT(tempT);
            try{
                int num = mapper.insert(tempT);
                if(num == 1){
                    batch.setMessage("插入成功");
                }
                else{
                    batch.setMessage("插入失败");
                }
                successNum += num;
            }catch(RuntimeException e){
                batch.setMessage("插入失败：" + e.getMessage());
            }
            batchList.add(batch);
        }
        if(successNum != 0){
            result.setCode(CodeConstants.SUCCESS).setBody(batchList).setMessage("插入成功：" + successNum + "/" + batchList.size());
        }
        else{
            result.setCode(CodeConstants.VALIDATE_ERROR).setBody(batchList).setMessage("插入失败" );
        }
        return result;
    }

    /**
     * 删除实体
     * @param id ID
     */
    @Override
    public Result<ID> delete(ID id) {
        Result<ID> result = new Result<>();
        try {
            int num = mapper.deleteByPrimaryKey(id);
            if(num == 1){
                result.setCode(CodeConstants.SUCCESS).setBody(id).setMessage("删除成功");
            }
            else{
                result.setCode(CodeConstants.USER_NOT_EXIST).setBody(id).setMessage("删除失败：实体不存在");
            }
        } catch (RuntimeException e) {
            result.setCode(CodeConstants.SERVICE_ERROR).setBody(id).setMessage("删除失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 删除实体（批量）
     * @param ids String
     */
    @Override
    public Result<String> batchDelete(String ids) {
        Result<String> result = new Result<>();
        try{
            List<ID> idList = new ArrayList<>();
            for(int i=0; i<Arrays.asList(ids.split(",")).size(); i++){
                idList.add((ID)Arrays.asList(ids.split(",")).get(i));
            }
            // modified for
            int successNum = mapper.batchDelete(idList);
            if(successNum != 0){
                result.setCode(CodeConstants.SUCCESS).setMessage("删除成功：" + successNum + "/" + idList.size());
            }
            else{
                result.setCode(CodeConstants.VALIDATE_ERROR).setMessage("删除失败：实体不存在");
            }
        }catch (RuntimeException e){
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("删除失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 查询实体
     * @param id ID
     */
    @Override
    public Result<T> select(ID id) {
        Result<T> result = new Result<>();
        try {
            T t = mapper.selectByPrimaryKey(id);
            if(t != null){
                result.setCode(CodeConstants.SUCCESS).setBody(t).setMessage("查询成功");
            }
            else{
                result.setCode(CodeConstants.USER_NOT_EXIST).setMessage("查询失败：实体不存在");
            }
        } catch (RuntimeException e) {
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("查询失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 查询实体（批量）
     * @param ids String
     */
    @Override
    public Result<Page<T>> batchSelect(String ids, Page<T> page){
        Result<Page<T>> result = new Result<>();
        try{
            PageHelper.startPage(page.getPageNo(), page.getPageSize());
            List<ID> idList = new ArrayList<>();
            for(int i=0; i<Arrays.asList(ids.split(",")).size(); i++){
                idList.add((ID)Arrays.asList(ids.split(",")).get(i));
            }
            List<T> all = mapper.batchSelect(idList);
            Page<T> pageResult = new Page<T>(all);
            result.setCode(CodeConstants.SUCCESS).setBody(pageResult).setMessage("查询成功");
        }catch (RuntimeException e){
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("查询失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 查询实体（所有）
     */
    public Result<Page<T>> allSelect(Page<T> page){
        Result<Page<T>> result = new Result<>();
        try{
            PageHelper.startPage(page.getPageNo(), page.getPageSize());
            List<T> all = mapper.allSelect();
            List<T> all1 = mapper.allSelect();
            Page<T> pageResult = new Page<T>(all);
            if(all.size() != 0){
                result.setCode(CodeConstants.SUCCESS).setBody(pageResult).setMessage("查询成功");
            }
            else{
                result.setCode(CodeConstants.USER_NOT_EXIST).setMessage("查询失败：实体不存在");
            }
        }catch (RuntimeException e){
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("查询失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 更新实体
     * @param t T
     */
    @Override
    public Result<T> update(T t) {
        Result<T> result = new Result<>();
        try {
            int num = mapper.updateByPrimaryKey(t);
            if(num == 1){
                result.setCode(CodeConstants.SUCCESS).setBody(t).setMessage("更新成功");
            }
            else{
                result.setCode(CodeConstants.USER_NOT_EXIST).setBody(t).setMessage("更新失败：用户不存在");
            }
        } catch (RuntimeException e) {
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("更新失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 更新实体（批量）
     * @param t List
     */
    @Override
    public Result<List<Batch<T>>> batchUpdate(List<T> t){
        Result<List<Batch<T>>> result = new Result<>();
        List<Batch<T>> batchList = new ArrayList<>();
        int successNum = 0;
        for(int i = 0; i < t.size(); i++){
            T tempT = t.get(i);
            Batch<T> batch = new Batch<>();
            batch.setT(tempT);
            try{
                int num = mapper.updateByPrimaryKey(tempT);
                if(num == 1){
                    batch.setMessage("更新成功");
                }
                else{
                    batch.setMessage("更新失败：用户不存在");
                }
                successNum += num;
            }catch(RuntimeException e){
                batch.setMessage("更新失败：" + e.getMessage());
            }
            batchList.add(batch);
        }
        if(successNum != 0){
            result.setCode(CodeConstants.SUCCESS).setBody(batchList).setMessage("更新成功：" + successNum + "/" + batchList.size());
        }
        else{
            result.setCode(CodeConstants.VALIDATE_ERROR).setBody(batchList).setMessage("更新失败" );
        }
        return result;
    }

    /**
     *
     */
    @Override
    public List<T> getByPage(int offset, int size) {
        return mapper.selectByPage(offset, size);
    }
}
