package edu.zju.gis.dldsj.server.service.impl;

import com.github.pagehelper.PageHelper;
import edu.zju.gis.dldsj.server.base.BaseServiceImpl;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.entity.Geodata;
import edu.zju.gis.dldsj.server.mapper.GeodataMapper;
import edu.zju.gis.dldsj.server.service.GeodataService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jiarui
 * @date 2020/8/26
 */
@Service
public class GeodataServiceImpl extends BaseServiceImpl<GeodataMapper,Geodata,String> implements GeodataService {

    //数据目录功能，根据type、uploader或source等目录字段，返回数据(单选)
    public Page<Geodata> selectByType(String type,Page page) {
        PageHelper.startPage(page.getPageNo(),page.getPageSize());
        return new Page<>(mapper.selectByType(type));
    }

    public Page<Geodata> selectByUploader(String uploader,Page page) {
        PageHelper.startPage(page.getPageNo(),page.getPageSize());
        return new Page<>(mapper.selectByUploader(uploader));
    }

    public Page<Geodata> selectBySource(String source,Page page) {
        PageHelper.startPage(page.getPageNo(),page.getPageSize());
        return new Page<>(mapper.selectBySource(source));
    }

    //根据输入字段名称，返回结果的唯一不同值与对应数量
    public Map<String,String> getDistinctField(String field) {
        //field为查找的字段名，如TYPE、UPLOADER等
        List<Geodata> list = mapper.getDistinctField(field);
        List<String> res = new ArrayList<String>();
        //str为读取出来的唯一字段名称
        String str;
        //用来存储最后结果，样式如TIF 2
        Map<String,String> map = new HashMap<String, String>();
        for (Geodata s : list) {
            switch (field) {
                case "TYPE":
                    str = s.getType();
                    break;
                case "UPLOADER":
                    str = s.getUploader();
                    break;
                case "SOURCE":
                    str = s.getSource();
                default:
                    str = "xxx";
            }
            String count;
            count = mapper.getCountOfField(field,str);
            //System.out.println(str);
            //System.out.println(count);
            map.put(str,count);
        }
        return map;
    }
}
