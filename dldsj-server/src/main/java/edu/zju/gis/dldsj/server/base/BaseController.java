package edu.zju.gis.dldsj.server.base;

import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.entity.Batch;
import edu.zju.gis.dldsj.server.entity.Lecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author Hu 2019/04/29
 * @update zyq 2020/09/23
 * 实现Controller的基础CURD方法
 * 实现Controller的基础批量CURD方法
 **/
@CrossOrigin
public abstract class BaseController<T extends Base<ID>, Service extends BaseService<T, ID>, ID extends Serializable, Search extends BaseFilter<ID>> {

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
        return service.insert(t);
    }

    /**
     * 插入实体（批量）
     * @param t List
     * @return Result
     */
    @RequestMapping(value = "/batchinsert", method = RequestMethod.PUT)
    @ResponseBody
    public Result<List<Batch<T>>> batchInsert(@RequestBody List<T> t) {
        return service.batchInsert(t);
    }

    /**
     * 删除实体
     * @param id ID
     * @return Result
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Result<ID> delete(@PathVariable ID id) {
        return service.delete(id);
    }

    /**
     * 删除实体（批量）
     * @param ids String
     * @return Result
     */
    @RequestMapping(value = "/batchdelete/{ids}", method = RequestMethod.DELETE)
    @ResponseBody
    public Result<List<Batch<T>>> batchDelete(@PathVariable String ids) {
        return service.batchDelete(ids);
    }

    /**
     * 查询实体
     * @param id ID
     * @return Result
     */
    @RequestMapping(value = "/select/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result<T> select(@PathVariable ID id) {
        return service.select(id);
    }

    /**
     * 查询实体（批量）
     * @param ids String
     * @return Result
     */
    @RequestMapping(value = "/batchselect/{ids}", method = RequestMethod.GET)
    @ResponseBody
    public Result<Page<T>> batchSelect(@PathVariable String ids, Page<T> page) {
        return service.batchSelect(ids, page);
    }

    /**
     * 查询实体（所有）
     * @return Result
     */
    @RequestMapping(value = "/allselect", method = RequestMethod.GET)
    @ResponseBody
    public Result<Page<T>> allSelect(Page<T> page) {
        return service.allSelect(page);
    }

    /***
     * 查询实体（最新）
     * @return result
     */
    @RequestMapping(value = "/selectnew", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<T>> selectNew() {
        return service.selectNew();
    }

    /***
     * 查询实体（模糊搜索）
     * @param key String
     * @return result
     */
    @RequestMapping(value = "/fuzzyname/{key}", method = RequestMethod.GET)
    @ResponseBody
    public Result<Page<T>> selectFuzzyName(@PathVariable String key, Page<T> page) {
        return service.selectFuzzyName(key, page);
    }

    /**
     * 更新实例
     * @param t T
     * @return Result
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result<T> update(@RequestBody T t) {
        return service.update(t);
    }

    /**
     * 更新实例（批量）
     * @param t List
     * @return Result
     */
    @RequestMapping(value = "/batchupdate", method = RequestMethod.POST)
    @ResponseBody
    public Result<List<Batch<T>>> batchUpdate(@RequestBody List<T> t) {
        return service.batchUpdate(t);
    }

    /**
     * 将根据查询条件获取的列表结果分页
     * 默认pageNo=1, pageSize=20
     * @param param
     * @param page
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseBody
    public Result getPage(@RequestBody Search param, Page page) {
        return Result.success().setBody(service.search(param, page));
    }

}
