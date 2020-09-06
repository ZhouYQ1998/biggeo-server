package edu.zju.gis.dldsj.server.service.impl;

import com.github.pagehelper.PageHelper;
import edu.zju.gis.dldsj.server.base.BaseServiceImpl;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.entity.Geodata;
import edu.zju.gis.dldsj.server.mapper.GeodataMapper;
import edu.zju.gis.dldsj.server.service.GeodataService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
    public Page<Geodata> selectByType1(String type,Page page) {
        PageHelper.startPage(page.getPageNo(),page.getPageSize());
        return new Page<>(mapper.selectByType1(type));
    }

    public Page<Geodata> selectByType2(String type,Page page) {
        PageHelper.startPage(page.getPageNo(),page.getPageSize());
        return new Page<>(mapper.selectByType2(type));
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
                case "TYPE_1":
                    str = s.getType1();
                    break;
                case "TYPE_2":
                    str = s.getType2();
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

    //下载数量+1
    public void downloadTimesPlus(String id) {
        mapper.downloadTimesPlus(id);
    }

    //统计下载量最高的5条数据
    public List<Geodata> getPopularData(){
    return mapper.getPopularData();
    }

}
