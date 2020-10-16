package edu.zju.gis.dldsj.server.service.impl;

import com.github.pagehelper.PageHelper;
import edu.zju.gis.dldsj.server.base.BaseServiceImpl;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.config.CommonSetting;
import edu.zju.gis.dldsj.server.entity.Geodata;
import edu.zju.gis.dldsj.server.mapper.GeodataMapper;
import edu.zju.gis.dldsj.server.service.GeodataService;
import edu.zju.gis.dldsj.server.utils.fs.FsManipulatorFactory;
import edu.zju.gis.dldsj.server.utils.fs.HdfsManipulator;
import edu.zju.gis.dldsj.server.utils.fs.LocalFsManipulator;
import lombok.SneakyThrows;
import org.apache.hadoop.fs.Hdfs;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jiarui
 * @date 2020/8/26
 * @author: zjh
 * @date: 20201012
 */
@Service
public class GeodataServiceImpl extends BaseServiceImpl<GeodataMapper, Geodata, String> implements GeodataService {

    @Autowired
    private CommonSetting setting;

    //数据目录功能，根据type、uploader或source等目录字段，返回数据(单选)
    public Page<Geodata> selectByType1(String type, Page page) {
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        return new Page<>(mapper.selectByType1(type));
    }

    public Page<Geodata> selectByType2(String type, Page page) {
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        return new Page<>(mapper.selectByType2(type));
    }

    public Page<Geodata> selectByUploader(String uploader, Page page) {
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        return new Page<>(mapper.selectByUploader(uploader));
    }

    public Page<Geodata> selectBySource(String source, Page page) {
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        return new Page<>(mapper.selectBySource(source));
    }

    @Override
    public Page<Geodata> selectByUserName(String userName, Page page) {
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        return new Page<>(mapper.selectByUserName(userName));
    }

    //根据输入字段名称，返回结果的唯一不同值与对应数量
    public Map<String, String> getDistinctField(String field) {
        //field为查找的字段名，如TYPE、UPLOADER等
        List<Geodata> list = mapper.getDistinctField(field);
        //str为读取出来的唯一字段名称
        String str;
        //用来存储最后结果，样式如TIF 2
        Map<String, String> map = new HashMap<String, String>();
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
            count = mapper.getCountOfField(field, str);
            map.put(str, count);
        }
        return map;
    }

    //下载数量+1
    public void downloadTimesPlus(String id) {
        mapper.downloadTimesPlus(id);
    }

    //统计下载量最高的5条数据
    public List<Geodata> getPopularData() {
        return mapper.getPopularData();
    }

    @Override
    public String uploadFromLocal(String fsUri, String filePath) {
        String resMessage = "上传失败！";
        HdfsManipulator hdfsManipulator = (HdfsManipulator) FsManipulatorFactory.create(fsUri);

        if (new File(filePath).isFile()) {
            String regexPath = filePath.replace('\\', '/');
            String fileName = regexPath.substring(regexPath.lastIndexOf('/') + 1);
            String hdfsPath = setting.getGeoDataPath() + '/' + fileName;

            hdfsManipulator.uploadFromLocal(regexPath, hdfsPath);
            resMessage = "上传成功！";
        } else {
            resMessage = "文件路径错误！";
        }

        return resMessage;
    }

    @Override
    public String downloadFromHDFS(String fsUri, String hdfsPath, String fileDirectory) {
        String resMessage = "下载失败！";
        HdfsManipulator hdfsManipulator = (HdfsManipulator) FsManipulatorFactory.create(fsUri);

        try {
            if (hdfsManipulator.isFile(hdfsPath) && new File(fileDirectory).isDirectory()) {
                String fileName = hdfsPath.substring(hdfsPath.lastIndexOf('/') + 1);
                hdfsManipulator.downloadToLocal(hdfsPath, fileDirectory + '/' + fileName);
                resMessage = "下载成功！";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resMessage;
    }

    public String test(String uri) {
        HdfsManipulator hdfsManipulator = (HdfsManipulator) FsManipulatorFactory.create(uri);

        hdfsManipulator.uploadFromLocal(
                "F:\\EnglishPath\\7ThreeS\\goeDataTest\\README.md",
                "/gis/3S/geoData/dh.md");


        return "上传、下载成功";
    }

}
