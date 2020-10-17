package edu.zju.gis.dldsj.server.controller;

import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.config.CommonSetting;
import edu.zju.gis.dldsj.server.constant.CodeConstants;
import edu.zju.gis.dldsj.server.utils.LoadUtils;
import edu.zju.gis.dldsj.server.utils.fs.FsManipulator;
import edu.zju.gis.dldsj.server.utils.fs.FsManipulatorFactory;
import edu.zju.gis.dldsj.server.utils.fs.HdfsManipulator;
import edu.zju.gis.dldsj.server.utils.fs.LocalFsManipulator;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.Path;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author Keran Sun (katus)
 * @version 1.0, 2020-10-17
 */
@Slf4j
@RestController
@RequestMapping("/userSpace")
public class UserSpaceController {
    @Autowired
    private CommonSetting setting;

    /**
     * 获取路径下全部的文件
     * @param userId 用户ID
     * @param requestBody 请求体
     * @return 标准结果体
     */
    @PostMapping("/getAllFiles")
    public Result<List<String>> getAllFiles(@SessionAttribute("userId") String userId, @RequestBody String requestBody) {
        Result<List<String>> result = new Result<>();
        List<String> fileList = new ArrayList<>();
        HdfsManipulator hdfsManipulator = (HdfsManipulator)FsManipulatorFactory.create(setting.getHdFsUri());
        try {
            JSONObject inputs = new JSONObject(requestBody);
            String userPath = inputs.optString("path", "/");
            String path = Paths.get(setting.getUserSpaceRootPath(), userId, userPath).toString();
            List<Path> filePaths = getAllFilesRecursion(hdfsManipulator, path);
            for (Path filePath : filePaths) {
                fileList.add(filePath.toString().replace(setting.getHdFsUri() + path, ""));
            }
            return result.setCode(CodeConstants.SUCCESS).setBody(fileList).setMessage("全部文件返回成功");
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.setCode(CodeConstants.SYSTEM_ERROR).setMessage("全部文件返回失败");
        }
    }

    /**
     * 打包下载指定的文件
     * @param userId 用户ID
     * @param requestBody 请求体
     * @param res Servlet回复体
     * @return 标准结果体
     */
    @PostMapping("/download")
    public Result<String> downloadFiles(@SessionAttribute("userId") String userId, @RequestBody String requestBody, HttpServletResponse res) {
        HdfsManipulator hdfsManipulator = (HdfsManipulator)FsManipulatorFactory.create(setting.getHdFsUri());
        LocalFsManipulator localFsManipulator = (LocalFsManipulator)FsManipulatorFactory.create(setting.getLFsUri());
        try {
            JSONObject inputs = new JSONObject(requestBody);
            JSONArray fileListArray = inputs.getJSONArray("fileList");
            // 创建临时路径
            String tmpPath = Paths.get(setting.getDownloadTempDirectory(), UUID.randomUUID().toString()).toString();
            localFsManipulator.mkdirs(tmpPath);
            // 将HDFS文件下载到临时路径
            String prefix = Paths.get(setting.getUserSpaceRootPath(), userId).toString();
            String[] files = new String[fileListArray.length()];
            for (int i = 0; i < fileListArray.length(); i++) {
                hdfsManipulator.downloadToLocal(Paths.get(prefix, fileListArray.getString(i)).toString(), tmpPath);
                files[i] = Paths.get(tmpPath, new File(fileListArray.getString(i)).getName()).toString();
            }
            // 压缩文件
            String zipFile = Paths.get(tmpPath, "results.zip").toString();
            localFsManipulator.compress(files, zipFile);
            LoadUtils.download(localFsManipulator, zipFile, "results.zip", res);
            localFsManipulator.deleteDir(tmpPath);
            return null;
        } catch (Exception e) {
            log.error(e.getMessage());
            Result<String> result = new Result<>();
            return result.setCode(CodeConstants.SYSTEM_ERROR).setBody("ERROR").setMessage("全部文件返回失败");
        }
    }

    private List<Path> getAllFilesRecursion(FsManipulator fsManipulator, String rootPath) throws IOException {
        List<Path> result = new ArrayList<>();
        ArrayList<Path> rootPaths = new ArrayList<>(Arrays.asList(fsManipulator.listFiles(rootPath)));
        for (Path path : rootPaths) {
            if (fsManipulator.isFile(path)) {
                result.add(path);
            } else {
                List<Path> childPath = getAllFilesRecursion(fsManipulator, path.toString());
                result.addAll(childPath);
            }
        }
        return result;
    }
}
