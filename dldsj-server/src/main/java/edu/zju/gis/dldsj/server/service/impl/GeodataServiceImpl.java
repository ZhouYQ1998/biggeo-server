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


    public String FileDownload(HttpServletResponse response,String id) throws FileNotFoundException {
        Geodata geodata = mapper.selectByPrimaryKey(id);
        //通过id来查找某个文件的存储路径、在服务器上的newName，初始文件名，和文件格式
        String realPath = geodata.getPath();
        String newName = geodata.getNewName();
        String oldName = geodata.getOldName();
        String format = geodata.getFormat();
        //获取文件输入流
        FileInputStream inputStream = new FileInputStream(new File(realPath,newName));
        //附件下载
        String fileName = oldName+format;
        //设置文件信息
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + oldName);

        byte[] buff = new byte[1024];
        //创建缓冲输入流
        BufferedInputStream bis = null;
        OutputStream outputStream = null;

        try {
            //文件输出流
            outputStream = response.getOutputStream();
            IOUtils.copy(inputStream,outputStream);
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return oldName;
    }

}
