package edu.zju.gis.dldsj.server.base;

import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.constant.CodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 * @author Hu 2019/04/29
 * 实现Controller的基础CURD方法
 * @update zyq 2020/09/23
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
            service.insert(t);
            result.setCode(CodeConstants.SUCCESS).setBody(t).setMessage("插入成功");
        }catch(RuntimeException e){
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("插入失败：" + e.getMessage());
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
    public Result<T> delete(@PathVariable ID id) {
        Result<T> result = new Result<>();
        try {
            service.delete(id);
            result.setCode(CodeConstants.SUCCESS).setMessage("删除成功");
        } catch (RuntimeException e) {
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("删除失败：" + e.getMessage());
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
                result.setCode(CodeConstants.VALIDATE_ERROR).setMessage("查询失败：无结果");
            }
        } catch (RuntimeException e) {
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("查询失败：" + e.getMessage());
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
            service.update(t);
            result.setCode(CodeConstants.SUCCESS).setBody(t).setMessage("更新成功");
        } catch (RuntimeException e) {
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("更新失败：" + e.getMessage());
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
