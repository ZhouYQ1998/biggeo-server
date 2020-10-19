package edu.zju.gis.dldsj.server.service.impl;

import com.github.pagehelper.PageHelper;
import edu.zju.gis.dldsj.server.base.BaseServiceImpl;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.config.CommonSetting;
import edu.zju.gis.dldsj.server.constant.CodeConstants;
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
import java.util.*;

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

    // -----------------------------------------------------------------------------------------------------------------
    public Result<Geodata> insertAndUp2hdfs(Geodata t) {
        Result<Geodata> result = new Result<>();

        String resMessage = "";
        String filePath = t.getPath();

        // 上传操作
        if (new File(filePath).isFile()) {
            String regexPath = filePath.replace('\\', '/');
            String fileName = regexPath.substring(regexPath.lastIndexOf('/') + 1);
            String hdfsPath = setting.getGeoDataPath() + '/' + fileName;

            HdfsManipulator hdfsManipulator = (HdfsManipulator) FsManipulatorFactory.create(setting.getHdFsUri());
            if (hdfsManipulator.uploadFromLocal(regexPath, hdfsPath)) {
                t.setPath(setting.getHdFsUri() + hdfsPath);
                resMessage = "文件上传成功！";
            } else {
                t.setPath("");
                resMessage = "文件上传失败！";
            }
        } else {
            resMessage = "文件路径错误！";
        }

        // 插入操作
        try {
            t.setId((String) UUID.randomUUID().toString());
            int num = mapper.insertAndUp2hdfs(t);
            if (num == 1) {
                result.setCode(CodeConstants.SUCCESS).setBody(t).setMessage("插入成功 " + resMessage);
            } else {
                result.setCode(CodeConstants.VALIDATE_ERROR).setBody(t).setMessage("插入失败 " + resMessage);
            }
        } catch (RuntimeException e) {
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("插入失败：" + e.getMessage());
        }
        return result;
    }

    public Result<Geodata> downFromhdfs(String id, String fileDirectory) {
        Result<Geodata> result = new Result<>();
        try {
            Geodata t = mapper.selectByPrimaryKey(id);
            String resMessage = "下载失败！";
            if (t != null) {
                HdfsManipulator hdfsManipulator = (HdfsManipulator) FsManipulatorFactory.create(setting.getHdFsUri());
                String hdfsPath = t.getPath();

                if (hdfsManipulator.isFile(hdfsPath) && new File(fileDirectory).isDirectory()) {
                    String fileName = hdfsPath.substring(hdfsPath.lastIndexOf('/') + 1);
                    if (hdfsManipulator.downloadToLocal(hdfsPath, fileDirectory + '/' + fileName)){
                        resMessage = "下载成功！";
                    }else {
                        resMessage = "下载失败！";
                    }
                }
                downloadTimesPlus(id);

                result.setCode(CodeConstants.SUCCESS).setBody(t).setMessage("查询成功 " + resMessage);
            } else {
                result.setCode(CodeConstants.USER_NOT_EXIST).setMessage("查询失败：实体不存在 " + resMessage);
            }
        } catch (RuntimeException e) {
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("查询失败：" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String uploadFromLocal(String fsUri, String filePath) {
        String resMessage = "文件上传失败！";
        HdfsManipulator hdfsManipulator = (HdfsManipulator) FsManipulatorFactory.create(fsUri);

        if (new File(filePath).isFile()) {
            String regexPath = filePath.replace('\\', '/');
            String fileName = regexPath.substring(regexPath.lastIndexOf('/') + 1);
            String hdfsPath = setting.getGeoDataPath() + '/' + fileName;

            hdfsManipulator.uploadFromLocal(regexPath, hdfsPath);
            resMessage = "文件上传成功！";
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
