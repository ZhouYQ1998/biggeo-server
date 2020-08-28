package edu.zju.gis.dldsj.server.controller;

import com.google.gson.*;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.config.CommonSetting;
import edu.zju.gis.dldsj.server.entity.mapProject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author moral moral@zju.edu.cn
 * @version 1.0 2020/08/17
 */
@Slf4j
@CrossOrigin
@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    CommonSetting setting;

    /**
     * 上传用户指定的多个文件至临时目录
     *
     * @param files 文件集合
     * @return Result
     */
    @RequestMapping(value = "/temp/submit", method = RequestMethod.POST)
    @ResponseBody
    public Result submitTempFile(@RequestParam("file") List<MultipartFile> files) {
        if (files.size() == 0)
            return Result.error("获取文件失败。");
        File tempDir = new File(setting.getMapfileSavepath());
        System.out.println(tempDir);
        if (!tempDir.exists())
            return Result.error("临时存放目录不存在:" + tempDir.getAbsolutePath());
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            if (fileName == null)
                return Result.error("文件名获取失败。");
            File localFile = new File(tempDir, fileName);
            System.out.println(localFile.getAbsolutePath());
            if (localFile.exists()) {
                if (!localFile.delete())
                    return Result.error("文件已存在，覆盖失败。");
            }
            try {
                file.transferTo(localFile);
            } catch (IOException e) {
                return Result.error("文件上传失败：" + e.getMessage());
            }
        }
        return Result.success();
    }

    /**
     * 获取临时路径下的子文件
     * type=1 访问工程文件夹 type=2 访问数据文件夹
     * @return 文件路径集合-不包含目录
     */
    @RequestMapping(value = "/temp/list", method = RequestMethod.GET)
    @ResponseBody
    public Result getTempFileList(@RequestParam("type") Integer type) {
        String filepath="projectJson";
        if(type==2) filepath="dataJson";
        File tempDir = new File(setting.getMapfileSavepath()+File.separator+filepath);
        if (!tempDir.exists())
            return Result.error("临时存放目录不存在:" + tempDir.getAbsolutePath());
        List<String> fileNameList = new ArrayList<>();
        File[] filesList = tempDir.listFiles();
        if (filesList != null) {
            for (File file : filesList) {
                if (file.isFile())
                    fileNameList.add(file.getName());
            }
            return Result.success().setBody(fileNameList);
        } else
            return Result.error("临时目录为空");
    }

    /**
     * 获取存储工程和数据json路径下的子文件，通过type判别当前访问的是数据文件夹或者工程文件夹
     * type=1 访问工程文件夹 type=2 访问数据文件夹
     * @return json文件内容
     */
    @RequestMapping(value = "/temp/{fileName}", method = RequestMethod.GET)
    @ResponseBody
    public Result readFile(@PathVariable String fileName, @RequestParam("type") Integer type) {
        String filepath="projectJson";
        if(type==2) filepath="dataJson";
        File tempFile = new File(Paths.get(setting.getMapfileSavepath()+File.separator+filepath, fileName).toString());
        if (!tempFile.exists())
            return Result.error("文件不存在:" + tempFile.getAbsolutePath());
        else {
            String suffix = tempFile.getName().substring(tempFile.getName().lastIndexOf("."));
            switch (suffix) {
                case ".json":
                    return readJSONArray(tempFile);
                case ".shp":
                    return Result.error("暂不支持shp文件。");
                default:
                    return Result.error("暂不支持该文件类型。");
            }

        }
    }

    //读取JSON文件内容
    private Result readJSONArray(File jsonFile) {
        try {
            FileReader fileReader = new FileReader(jsonFile);
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = fileReader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            JsonParser jsonParser = new JsonParser();
            return Result.success().setBody(jsonParser.parse(sb.toString()));
        } catch (Exception e) {
            return Result.error("文件读取失败：" + e.getMessage());
        }
    }
    /**
     * 向存储工程json路径提交工程json
     *
     * @return json文件路径
     */
    @RequestMapping(value = "/temp/projectSubmit", method = RequestMethod.POST)
    @ResponseBody
    //前端请求提供json数据以及文件名，后端将其写入指定路径
    private Result projectSubmit(@RequestBody mapProject mapJson, @RequestParam("name") String name) {
        try {
            String path=setting.getMapfileSavepath();
            File file = new File(path+File.separator+"projectJson"+File.separator+name);
            // 创建文件
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write("[{layers : "+mapJson.getLayers()+",style : "+mapJson.getStyle()+"}]");
            writer.close();
            return Result.success().setBody(file.getAbsoluteFile());
        } catch (Exception e) {
            return Result.error("文件写入失败：" + e.getMessage());
        }
    }
    /**
     * 向存储数据json路径提交json数据
     *
     * @return 错误信息，成功则无
     */
    @RequestMapping(value = "/temp/dataSubmit", method = RequestMethod.POST)
    @ResponseBody
    //前端请求提供json数据以及文件名，后端将其写入指定路径
    public Result dataSubmit(@RequestParam("file") List<MultipartFile> files) {
        if (files.size() == 0) return Result.error("获取文件失败。");
        File tempDir = new File(setting.getMapfileSavepath()+File.separator+"dataJson");
        if (!tempDir.exists())
            return Result.error("临时存放目录不存在:" + tempDir.getAbsolutePath());
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            if (fileName == null)
                return Result.error("文件名获取失败。");
            File localFile = new File(tempDir, fileName);
            System.out.println(localFile.getAbsolutePath());
            if (localFile.exists()) {
                if (!localFile.delete())
                    return Result.error("文件已存在，覆盖失败。");
            }
            try {
                file.transferTo(localFile);
            } catch (IOException e) {
                return Result.error("文件上传失败：" + e.getMessage());
            }
        }
        return Result.success();
    }
    /**
     * 在存储数据json路径删除指定文件
     *
     * @return 错误信息，成功则无
     */
    @RequestMapping(value = "/temp/dataDelete/{fileName}", method = RequestMethod.GET)
    @ResponseBody
    //删除数据存储路径下对应的文件
    public Result deleteData(@PathVariable String fileName) {

        File tempDir = new File(setting.getMapfileSavepath()+File.separator+"dataJson");
        if (!tempDir.exists())
            return Result.error("临时存放目录不存在:" + tempDir.getAbsolutePath());
            File localFile = new File(tempDir, fileName);
            if (localFile.exists()) {
                if (!localFile.delete())
                    return Result.error("文件删除失败");
            }
            return Result.success();
    }
}



