package edu.zju.gis.dldsj.server.base;

import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.constant.CodeConstants;
import edu.zju.gis.dldsj.server.entity.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hu 2019/04/29
 * @update zyq 2020/09/23
 * 实现Controller的基础CURD方法
 * 实现Controller的基础批量CURD方法
 **/
public abstract class BaseController<T , Service extends BaseService<T, ID>, ID extends Serializable, Search extends BaseFilter<ID>> {

    /**
     * 注入Service
     */
    @Autowired
    public Service service;

    /**
     * 插入实体
     * @param t T
     * @return Result
     */
    @RequestMapping(value = "/insert", method = RequestMethod.PUT)
    @ResponseBody
    public Result<T> insert(@RequestBody T t) {
        Result<T> result = new Result<>();
        try{
            int num = service.insert(t);
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
     * @return Result
     */
    @RequestMapping(value = "/batchinsert", method = RequestMethod.PUT)
    @ResponseBody
    public Result<List<Batch<T>>> batchInsert(@RequestBody List<T> t) {
        Result<List<Batch<T>>> result = new Result<>();
        List<Batch<T>> batchList = new ArrayList<>();
        int successNum = 0;
        for(int i = 0; i < t.size(); i++){
            T tempT = t.get(i);
            Batch<T> batch = new Batch<>();
            batch.setT(tempT);
            try{
                int num = service.insert(tempT);
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
     * @return Result
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Result<ID> delete(@PathVariable ID id) {
        Result<ID> result = new Result<>();
        try {
            int num = service.delete(id);
            if(num == 1){
                result.setCode(CodeConstants.SUCCESS).setBody(id).setMessage("删除成功");
            }
            else{
                result.setCode(CodeConstants.USER_NOT_EXIST).setBody(id).setMessage("删除失败：用户不存在");
            }
        } catch (RuntimeException e) {
            result.setCode(CodeConstants.SERVICE_ERROR).setBody(id).setMessage("删除失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 删除实体（批量）
     * @param ids String
     * @return Result
     */
    @RequestMapping(value = "/batchdelete/{ids}", method = RequestMethod.DELETE)
    @ResponseBody
    public Result<List<Batch<ID>>> batchDelete(@PathVariable String ids) {
        String[] idArray = ids.split(",");
        Result<List<Batch<ID>>> result = new Result<>();
        List<Batch<ID>> batchList = new ArrayList<>();
        int successNum = 0;
        for(int i = 0; i < idArray.length; i++){
            ID id = (ID)idArray[i];
            Batch<ID> batch = new Batch<>();
            batch.setT(id);
            try {
                int num = service.delete(id);
                if(num == 1){
                    batch.setMessage("删除成功");
                }
                else{
                    batch.setMessage("删除失败：用户不存在");
                }
                successNum += num;
            } catch (RuntimeException e) {
                batch.setMessage("删除失败：" + e.getMessage());
            }
            batchList.add(batch);
        }
        if(successNum != 0){
            result.setCode(CodeConstants.SUCCESS).setBody(batchList).setMessage("删除成功：" + successNum + "/" + batchList.size());
        }
        else{
            result.setCode(CodeConstants.VALIDATE_ERROR).setBody(batchList).setMessage("删除失败" );
        }
        return result;
    }

    /**
     * 查询实体
     * @param id ID
     * @return Result
     */
    @RequestMapping(value = "/select/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result<T> select(@PathVariable ID id) {
        Result<T> result = new Result<>();
        try {
            T t = service.select(id);
            if(t != null){
                result.setCode(CodeConstants.SUCCESS).setBody(t).setMessage("查询成功");
            }
            else{
                result.setCode(CodeConstants.USER_NOT_EXIST).setMessage("查询失败：用户不存在");
            }
        } catch (RuntimeException e) {
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("查询失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 查询实体（批量）
     * @param ids String
     * @return Result
     */
    @RequestMapping(value = "/batchselect/{ids}", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<Batch<T>>> batchSelect(@PathVariable String ids) {
        String[] idArray = ids.split(",");
        Result<List<Batch<T>>> result = new Result<>();
        List<Batch<T>> batchList = new ArrayList<>();
        int successNum = 0;
        for(int i = 0; i < idArray.length; i++){
            ID id = (ID)idArray[i];
            Batch<T> batch = new Batch<>();
            try{
                T t = service.select(id);
                if(t != null){
                    batch.setT(t);
                    batch.setMessage("查询成功");
                    successNum++;
                }
                else{
                    batch.setMessage("查询失败：用户不存在");
                }
            }catch(RuntimeException e){
                batch.setMessage("查询失败：" + e.getMessage());
            }
            batchList.add(batch);
        }
        if(successNum != 0){
            result.setCode(CodeConstants.SUCCESS).setBody(batchList).setMessage("查询成功：" + successNum + "/" + batchList.size());
        }
        else{
            result.setCode(CodeConstants.VALIDATE_ERROR).setBody(batchList).setMessage("查询失败" );
        }
        return result;
    }

    /**
     * 更新实例
     * @param t T
     * @return Result
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result<T> update(@RequestBody T t) {
        Result<T> result = new Result<>();
        try {
            int num = service.update(t);
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
     * 更新实例（批量）
     * @param t List
     * @return Result
     */
    @RequestMapping(value = "/batchupdate", method = RequestMethod.POST)
    @ResponseBody
    public Result<List<Batch<T>>> batchUpdate(@RequestBody List<T> t) {
        Result<List<Batch<T>>> result = new Result<>();
        List<Batch<T>> batchList = new ArrayList<>();
        int successNum = 0;
        for(int i = 0; i < t.size(); i++){
            T tempT = t.get(i);
            Batch<T> batch = new Batch<>();
            batch.setT(tempT);
            try{
                int num = service.update(tempT);
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
     * 获取已经分页的数据结果
     * 默认pageNo=1, pageSize=20
     * @param param Search
     * @param page Page
     * @return Result
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseBody
    public Result getPage(@RequestBody Search param, Page page) {
        return Result.success().setBody(service.search(param, page));
    }

}
