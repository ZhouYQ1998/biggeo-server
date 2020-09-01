package edu.zju.gis.dldsj.server.service.impl;

import com.github.pagehelper.PageHelper;
import edu.zju.gis.dldsj.server.base.BaseServiceImpl;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.entity.Geodata;
import edu.zju.gis.dldsj.server.entity.Literature;
import edu.zju.gis.dldsj.server.mapper.LiteratureMapper;
import edu.zju.gis.dldsj.server.service.LiteratureService;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.*;

/**
 * @author Jiarui
 * @date 2020/8/27
 */
@Service
public class LiteratureServiceImpl extends BaseServiceImpl<LiteratureMapper, Literature,String> implements LiteratureService {

    //数据目录
    public Page<Literature> selectBySource (String source,Page page){
        PageHelper.startPage(page.getPageNo(),page.getPageSize());
        return new Page<>(mapper.selectBySource(source));
    }

    //根据输入字段名称，返回结果的唯一不同值与对应数量
    public Map<String,String> getDistinctField(String field) {
        //field为查找的字段名，如TYPE、UPLOADER等
        List<Literature> list = mapper.getDistinctField(field);
        List<String> res = new ArrayList<String>();
        //str为读取出来的唯一字段名称
        String str;
        //用来存储最后结果，样式如TIF 2
        Map<String,String> map = new HashMap<String, String>();

        for (Literature literature : list){
            switch (field) {
                case "SOURCE":
                    str = literature.getSource();
                    break;
                default:
                    str = "xxx";
            }
            String count;
            count = mapper.getCountOfField(field,str);
            map.put(str,count);
        }
        return map;
    }

    //根据时间查询
    public Page<Literature> selectByTime(String time1,String time2,Page page){
        Integer start = Integer.parseInt(time1);
        Integer end = Integer.parseInt(time2);
        PageHelper.startPage(page.getPageNo(),page.getPageSize());
        return new Page<>(mapper.selectByTime(start,end,page));
    }
}
