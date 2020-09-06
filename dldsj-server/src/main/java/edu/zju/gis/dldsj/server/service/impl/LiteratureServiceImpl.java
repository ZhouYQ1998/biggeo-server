package edu.zju.gis.dldsj.server.service.impl;

import edu.zju.gis.dldsj.server.base.BaseServiceImpl;
import edu.zju.gis.dldsj.server.entity.Literature;
import edu.zju.gis.dldsj.server.entity.searchPojo.LiteratureSearchPojo;
import edu.zju.gis.dldsj.server.mapper.LiteratureMapper;
import edu.zju.gis.dldsj.server.service.LiteratureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Jiarui
 * @date 2020/8/27
 */
@Service
@Slf4j
public class LiteratureServiceImpl extends BaseServiceImpl<LiteratureMapper, Literature,String> implements LiteratureService {

    //根据输入字段名称，返回结果的唯一不同值与对应数量
    public Map<String,String> getDistinctField(LiteratureSearchPojo param) {
        //field为查找的字段名，SOURCE、YEAR、AU_AFFILIATION
        String field = param.getDistinctField();
        //System.out.println(field);
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
        //System.out.println(list);
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

    //计算查询结果中出现次数最多的作者、关键词、机构
    public Map<String,Object> getSumOfField(LiteratureSearchPojo param,String field){
        //根据现有的条件查询的结果
        List<Literature> list= mapper.search(param);
        List<String> listAll = new ArrayList();
        String allRes;
        Map map = new HashMap();
        Map map2 = new LinkedHashMap();

        for (Literature literature :list) {
            //多个作者，用"; "分隔
            //根据输入的字段计算
            switch (field) {
                case "author":
                    allRes = literature.getAuthor();
                    if (allRes.contains(";")) {
                        String[] singleAuthors = allRes.split("; ");
                        for (String s : singleAuthors) {
                            listAll.add(s);
                        }
                    }
                    else listAll.add(allRes);
                    break;
                case "keywords":
                    allRes = literature.getKeywords();
                    if (allRes.contains(";")) {
                        String[] singleKeywords = allRes.split("; ");
                    for (String s : singleKeywords) {
                        listAll.add(s);
                        }
                    }
                    else listAll.add(allRes);
                    break;
                case "affiliation":
                    allRes = literature.getAuthorAffiliation();
                    if (allRes.contains(";")) {
                        String[] singleAffiliation = allRes.split("; ");
                    for (String s : singleAffiliation) {
                        listAll.add(s);
                        }
                    }
                    else listAll.add(allRes);
                    break;
                default:
                    return null;
            }
        }

            Set<String> uniqueSet = new HashSet(listAll);
            for (String temp : uniqueSet) {
                String name = temp;
                map.put(name,Collections.frequency(listAll,temp));
            }
            //按照value的数量排序
            List<Map.Entry<String, Integer>> list2 = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
            //list.sort()
            list2.sort(new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return o2.getValue().compareTo(o1.getValue());
                }
            });
            //collections.sort()
            Collections.sort(list2, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return o2.getValue().compareTo(o1.getValue());
                }
            });
            //for

            for (int i = 0; i < 10; i++) {
                if (list2.get(i).getKey()!=" ") {
                    map2.put(list2.get(i).getKey(),list2.get(i).getValue());
                }
                if(i>=list2.size()-1) break;
            }
        return map2;
    }

}
