package edu.zju.gis.dldsj.server.controller;

import com.google.gson.*;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.config.CommonSetting;
import edu.zju.gis.dldsj.server.entity.mapProject;
import edu.zju.gis.dldsj.server.utils.StringUtil;
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
        File tempDir = new File(setting.getDir4TempFile());
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
     *
     * @return 文件路径集合-不包含目录
     */
    @RequestMapping(value = "/temp/list", method = RequestMethod.GET)
    @ResponseBody
    public Result getTempFileList() {
        File tempDir = new File(setting.getDir4TempFile());
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
     * 获取临时路径下的子文件
     *
     * @return 文件路径集合-不包含目录
     */
    @RequestMapping(value = "/temp/{fileName}", method = RequestMethod.GET)
    @ResponseBody
    public Result readFile(@PathVariable String fileName) {
        File tempFile = new File(Paths.get(setting.getDir4TempFile(), fileName).toString());
        if (!tempFile.exists())
            return Result.error("文件不存在:" + tempFile.getAbsolutePath());
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
            JsonArray jsonObject = jsonParser.parse(sb.toString()).getAsJsonArray();
            Gson gson = new GsonBuilder().setLenient().create();
            return Result.success().setBody(gson.toJson(jsonObject));
        } catch (Exception e) {
            return Result.error("文件读取失败：" + e.getMessage());
        }
    }
    @RequestMapping(value = "/temp/jsonSubmit", method = RequestMethod.POST)
    @ResponseBody
    //读取JSON文件内容
    private Result jsontoFile(@RequestBody mapProject mapJson, @RequestParam("name") String name) {
        try {
            String path=setting.getDir4TempFile();
            File file = new File(path+"\\projectJson\\"+name);
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
}


