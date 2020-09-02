package edu.zju.gis.dldsj.server.service.impl;

import edu.zju.gis.dldsj.server.base.BaseServiceImpl;
import edu.zju.gis.dldsj.server.entity.Literature;
import edu.zju.gis.dldsj.server.entity.searchPojo.LiteratureSearchPojo;
import edu.zju.gis.dldsj.server.mapper.LiteratureMapper;
import edu.zju.gis.dldsj.server.service.LiteratureService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Jiarui
 * @date 2020/8/27
 */
@Service
public class LiteratureServiceImpl extends BaseServiceImpl<LiteratureMapper, Literature,String> implements LiteratureService {

    //根据输入字段名称，返回结果的唯一不同值与对应数量
    public Map<String,String> getDistinctField(LiteratureSearchPojo param) {
        //field为查找的字段名，SOURCE、YEAR、AU_AFFILIATION
        String field = param.getDistinctField();
        System.out.println(field);
        List<Literature> list = new ArrayList<>();
        switch (field){
            case "SOURCE":
                list = mapper.getDistinctField(param);
                break;
            case "YEAR":
                list = mapper.getDistinctField(param);
                break;
            case "AU_AFFILIATION":
                list = mapper.getDistinctField(param);
                break;
            default:
                list = null;
        }
        System.out.println(list);
        List<String> res = new ArrayList<String>();
        //str为读取出来的唯一字段名称
        String str;
        //用来存储最后结果，样式如2020 5
        Map<String,String> map = new HashMap<String, String>();

        for (Literature literature : list){
            String count;
            switch (field) {
                case "SOURCE":
                    str = literature.getSource();
                    param.setSourceDistinct(str);
                    count = mapper.getCountOfField(param);
                    map.put(str,count);
                    break;
                case "YEAR":
                    str = literature.getYear();
                    param.setYearDistinct(str);
                    count = mapper.getCountOfField(param);
                    map.put(str,count);
                    break;
                case "AU_AFFILIATION":
                    str = literature.getAuthorAffiliation();
                    param.setAuthorAffiliationFilterDistinct(str);
                    count = mapper.getCountOfField(param);
                    map.put(str,count);
                default:
                    str = "xxx";
            }
        }
        return map;
    }
}
