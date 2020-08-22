package edu.zju.gis.dldsj.server.base;

import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;

/**
 * @author Hu
 * 实现Controller的基础CURD方法
 * @date 2019/4/29
 **/
public abstract class BaseController<T , Service extends BaseService, ID extends Serializable, Search extends BaseFilter> {

    /**
     * 注入Service
     */
    @Autowired
    public Service service;

    /**
     * 获取已经分页的数据结果
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

    /**
     * 删除对象
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public Result delete(ID id) {
        service.delete(id);
        return Result.success();
    }

    /**
     * 获取实例
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public Result get(ID id) {
        return Result.success().setBody(service.select(id));
    }

    /**
     * 更新实例
     *
     * @param t
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseBody
    public Result update(@RequestBody T t) {
        service.update(t);
        return Result.success();
    }

    /**
     * 新增实例
     *
     * @param t
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Result create(@RequestBody T t) {
        return Result.success().setBody(service.insert(t));
    }

}
