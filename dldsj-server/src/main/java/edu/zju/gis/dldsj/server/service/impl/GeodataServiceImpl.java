package edu.zju.gis.dldsj.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.sun.org.apache.xpath.internal.operations.Bool;
import edu.zju.gis.dldsj.server.base.BaseServiceImpl;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.config.CommonSetting;
import edu.zju.gis.dldsj.server.constant.CodeConstants;
import edu.zju.gis.dldsj.server.constant.CodeType;
import edu.zju.gis.dldsj.server.entity.Geodata;
import edu.zju.gis.dldsj.server.entity.ParallelModelWithBLOBs;
import edu.zju.gis.dldsj.server.mapper.GeodataMapper;
import edu.zju.gis.dldsj.server.service.GeodataService;
import edu.zju.gis.dldsj.server.utils.LoadUtils;
import edu.zju.gis.dldsj.server.utils.XMLReader;
import edu.zju.gis.dldsj.server.utils.fs.FsManipulator;
import edu.zju.gis.dldsj.server.utils.fs.FsManipulatorFactory;
import edu.zju.gis.dldsj.server.utils.fs.HdfsManipulator;
import edu.zju.gis.dldsj.server.utils.fs.LocalFsManipulator;
import lombok.SneakyThrows;
import org.apache.hadoop.fs.Hdfs;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
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
    public Result<Geodata> insertByForm(String role, HttpServletRequest request) {
        Result<Geodata> result = new Result();
        if (role.equals("manager")) {
            // 用于判断是普通表单，还是带文件上传的表单，起了辨别的作用。
            if (!ServletFileUpload.isMultipartContent(request)) {
                return result.setCode(CodeConstants.VALIDATE_ERROR)
                        .setMessage("表单中必须包含数据文件");
            }

            String hdFsPath = "";
            Long fileSize = 0l;
            // 上传并导入数据库
            try {
                // 获取name为path的文件
                List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("path");
                LocalFsManipulator localFsManipulator = (LocalFsManipulator) FsManipulatorFactory.create(setting.getLFsUri());
                HdfsManipulator hdfsManipulator = (HdfsManipulator) FsManipulatorFactory.create(setting.getHdFsUri());
                String randomUUID = UUID.randomUUID().toString();
//                String templatePath = "F:\\EnglishPath\\7ThreeS\\goeDataTest\\templatePath";
                String templatePath = setting.getTemplatePath();
                String tempZipFileName = randomUUID + ".zip";
                String wholePath = "";

                // 压缩并上传文件
                for (MultipartFile file : files) {
                    String fileName = file.getOriginalFilename();
                    String tempDirectory = templatePath + '/' + randomUUID;
                    if (!new File(tempDirectory).exists()) localFsManipulator.mkdirs(tempDirectory);
                    File tempFile = new File(tempDirectory, fileName);
                    if (tempFile.exists()) localFsManipulator.deleteFile(tempFile.getAbsolutePath());

                    file.transferTo(tempFile);
                    fileSize += file.getSize();
                    wholePath += tempFile.getAbsolutePath() + ",";
                }
                if (wholePath.length() > 1) wholePath = wholePath.substring(0, wholePath.length() - 1);

                File tempZipFile = new File(templatePath + '/' + tempZipFileName);
                localFsManipulator.compress(wholePath.split(","), tempZipFile.getAbsolutePath());

                // 上传至 HDFS
                hdFsPath = setting.getGeoDataPath() + '/' + tempZipFileName;
                boolean isSuccess = hdfsManipulator.uploadFromLocal(tempZipFile.getAbsolutePath(), hdFsPath);
                if (!isSuccess) return result.setCode(CodeConstants.SERVICE_ERROR).setMessage("数据上传至 HDFS 失败");
            } catch (Exception e) {
                return result.setCode(CodeConstants.SERVICE_ERROR).setMessage("数据上传失败：" + e.getMessage());
            }

            try {
                // 插入数据库
                Geodata geodata = new Geodata();
                geodata.setId(UUID.randomUUID().toString());
                geodata.setTitle(request.getParameter("title"));
                geodata.setUploader(request.getParameter("uploader"));
                geodata.setUserName(request.getParameter("username"));
                geodata.setDownloadAuthority(request.getParameter("downloadauthority").equals("true"));
//                geodata.setTime(new SimpleDateFormat("yyyy-mm-dd").parse(request.getParameter("re_time")));
                geodata.setTime(new Date());
                geodata.setType1(request.getParameter("type1"));
                geodata.setType2(request.getParameter("type2"));
                geodata.setKeywords(request.getParameter("keywords"));
                // geodata.setSource(request.getParameter("source"));
                geodata.setAbstractInfo(request.getParameter("abstract"));
                geodata.setReference(request.getParameter("reference"));
                geodata.setPicture(request.getParameter("picture"));
                geodata.setOldName(request.getParameter("old_filename"));
                geodata.setNewName(request.getParameter("new_filename"));
                geodata.setFormat(".zip");
                geodata.setPath(setting.getHdFsUri() + hdFsPath);
                geodata.setRam((int) (1.0 * fileSize / 1024 / 1024 * 100) / 100.0 + " MB"); // 压缩之前的大小
                geodata.setDownloadTimes(0);
                if (mapper.insert(geodata) == 1) {
                    result.setCode(CodeConstants.SUCCESS).setBody(geodata).setMessage("插入成功 数据上传成功");
                } else {
                    result.setCode(CodeConstants.VALIDATE_ERROR).setBody(geodata)
                            .setMessage("插入失败 表单必须包含 标题、用户名称、类型1、路径 参数。");
                }
            } catch (Exception e) {
                result.setCode(CodeConstants.SERVICE_ERROR).setMessage("数据插入失败：" + e.getMessage());
            }
        } else {
            result.setCode(CodeConstants.USER_PERMISSION_ERROR).setMessage("当前用户没有上传权限！");
        }

        return result;
    }

    @Override
    public Result downloadByid(String id, HttpServletResponse response) {
        Result<String> result = new Result<>();
        Geodata geodata = mapper.selectByPrimaryKey(id);
        if (geodata == null) return result.setCode(CodeConstants.DAO_ERROR).setMessage("未找到ID记录");

        // 各种路径
        String tempZipFileName = UUID.randomUUID().toString() + ".zip";
        String hdfsPath = geodata.getPath();
//        String tempZipPath = "F:\\EnglishPath\\7ThreeS\\goeDataTest\\templatePath" + '/' + tempZipFileName;
        String tempZipPath = setting.getTemplatePath() + '/' + tempZipFileName;

        // 下载到 Linux，发送至 HttpServletResponse
        LocalFsManipulator localFsManipulator = (LocalFsManipulator) FsManipulatorFactory.create(setting.getLFsUri());
        HdfsManipulator hdfsManipulator = (HdfsManipulator) FsManipulatorFactory.create(setting.getHdFsUri());
        try {
            // 从 HDFS 下载到本地
            boolean isHDFSSuccess = hdfsManipulator.downloadToLocal(hdfsPath, tempZipPath);
            if (!isHDFSSuccess) return result.setCode(CodeConstants.SERVICE_ERROR).setMessage("HDFS 数据下载失败");
            // 从本地上传到 HttpServletResponse
            LoadUtils.download(localFsManipulator, tempZipPath, tempZipFileName, response);
            // 下载量加一
            downloadTimesPlus(id);
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("数据下载成功");
        } catch (Exception e) {
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("数据下载失败：" + e.getMessage());
        }

        return result;
    }


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
                    if (hdfsManipulator.downloadToLocal(hdfsPath, fileDirectory + '/' + fileName)) {
                        resMessage = "下载成功！";
                    } else {
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
