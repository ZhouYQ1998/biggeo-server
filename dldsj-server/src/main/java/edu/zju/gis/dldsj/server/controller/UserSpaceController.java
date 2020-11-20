package edu.zju.gis.dldsj.server.controller;

import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.config.CommonSetting;
import edu.zju.gis.dldsj.server.constant.CodeConstants;
import edu.zju.gis.dldsj.server.entity.vo.FileInfo;
import edu.zju.gis.dldsj.server.entity.vo.VizData;
import edu.zju.gis.dldsj.server.service.GeodataService;
import edu.zju.gis.dldsj.server.utils.GeometryUtil;
import edu.zju.gis.dldsj.server.utils.LoadUtils;
import edu.zju.gis.dldsj.server.utils.ShpUtil;
import edu.zju.gis.dldsj.server.utils.fs.FsManipulator;
import edu.zju.gis.dldsj.server.utils.fs.FsManipulatorFactory;
import edu.zju.gis.dldsj.server.utils.fs.LocalFsManipulator;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.Path;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author Keran Sun (katus)
 * @version 2.0, 2020-10-17
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/userSpace")
public class UserSpaceController {
    @Autowired
    private CommonSetting setting;

    @Autowired
    private GeodataService geodataService;

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
        FsManipulator fsManipulator = FsManipulatorFactory.create();
        try {
            JSONObject inputs = new JSONObject(requestBody);
            String userPath = inputs.optString("path", "/");
            String path = Paths.get(setting.getUserSpaceRootPath(), userId, userPath).toString();
            List<Path> filePaths = getAllFilesRecursion(fsManipulator, path);
            for (Path filePath : filePaths) {
                fileList.add(filePath.toString().replace(setting.getLFsUri() + path, ""));
            }
            return result.setCode(CodeConstants.SUCCESS).setBody(fileList).setMessage("全部文件返回成功");
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.setCode(CodeConstants.SYSTEM_ERROR).setMessage("全部文件返回失败");
        }
    }

    /**
     * 获取文件信息
     * @param userId 用户ID
     * @param requestBody 请求体
     * @return 标准结果体
     */
    @PostMapping("/getFileInfoList")
    public Result<List<FileInfo>> getFileInfoList(@SessionAttribute("userId") String userId, @RequestBody String requestBody) {
        Result<List<FileInfo>> result = new Result<>();
        List<FileInfo> fileList = new ArrayList<>();
        FsManipulator fsManipulator = FsManipulatorFactory.create();
        try {
            String userRoot = Paths.get(setting.getUserSpaceRootPath(), userId).toString();
            if (!fsManipulator.exists(userRoot)) {
                fsManipulator.mkdirs(userRoot);
            }
            JSONObject inputs = new JSONObject(requestBody);
            String userPath = inputs.optString("path", "/");
            String path = Paths.get(userRoot, userPath).toString();
            Path[] paths = fsManipulator.listFiles(path);
            for (Path filePath : paths) {
                FileInfo fileInfo = fsManipulator.getFileInfo(filePath);
                fileInfo.setPath(fileInfo.getPath().replace(setting.getLFsUri() + userRoot, ""));
                fileList.add(fileInfo);
            }
            return result.setCode(CodeConstants.SUCCESS).setBody(fileList).setMessage("文件列表返回成功");
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.setCode(CodeConstants.SYSTEM_ERROR).setMessage("文件列表返回失败");
        }
    }

    /**
     * 创建目录
     * @param userId 用户ID
     * @param requestBody 请求体
     * @return 标准结果体
     */
    @PostMapping("/mkdir")
    public Result<String> makeDir(@SessionAttribute("userId") String userId, @RequestBody String requestBody) {
        Result<String> result = new Result<>();
        FsManipulator fsManipulator = FsManipulatorFactory.create();
        try {
            JSONObject inputs = new JSONObject(requestBody);
            String userPath = inputs.getString("path");
            String currentPath = Paths.get(setting.getUserSpaceRootPath(), userId, userPath).toString();
            if (!fsManipulator.exists(currentPath)) {
                fsManipulator.mkdirs(currentPath);
                result.setCode(CodeConstants.SUCCESS).setMessage("目录创建成功").setBody("SUCCESS");
            } else {
                result.setCode(CodeConstants.SYSTEM_ERROR).setMessage("目录已存在").setBody("ERROR");
            }
        } catch (Exception e) {
            result.setCode(CodeConstants.SYSTEM_ERROR).setMessage("目录创建失败").setBody("ERROR");
        }
        return result;
    }

    /**
     * 上传文件
     * @param userId 用户ID
     * @param request 请求体
     * @return 标准结果体
     */
    @PostMapping("/upload")
    public Result<String> uploadFiles(@SessionAttribute("userId") String userId, HttpServletRequest request) {
        Result<String> result = new Result<>();
        // 如果请求数据中不包括文件
        if (!ServletFileUpload.isMultipartContent(request)) {   // 判断是否是包含文件的表单数据
            return result.setCode(CodeConstants.VALIDATE_ERROR).setBody("ERROR").setMessage("尚未选择文件");
        }
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        String userPath = request.getParameter("path");
        String currentPath = Paths.get(setting.getUserSpaceRootPath(), userId, userPath).toString();
        try {
            for (MultipartFile file : files) {
                file.transferTo(new File(Paths.get(currentPath, file.getOriginalFilename()).toString()));
            }
            result.setCode(CodeConstants.SUCCESS).setBody("SUCCESS").setMessage("文件上传成功");
        } catch (Exception e) {
            result.setCode(CodeConstants.SYSTEM_ERROR).setBody("ERROR").setMessage("文件上传失败");
        }
        return result;
    }

    /**
     * 删除文件/目录
     * @param userId 用户ID
     * @param requestBody 请求体
     * @return 标准结果体
     */
    @DeleteMapping("/delete")
    public Result<String> delete(@SessionAttribute("userId") String userId, @RequestBody String requestBody) {
        Result<String> result = new Result<>();
        try {
            JSONObject inputs = new JSONObject(requestBody);
            String userPath = inputs.getString("path");
            String currentPath = Paths.get(setting.getUserSpaceRootPath(), userId, userPath).toString();
            FsManipulator fsManipulator = FsManipulatorFactory.create();
            if (fsManipulator.exists(currentPath)) {
                fsManipulator.deleteDir(currentPath);
                result.setCode(CodeConstants.SUCCESS).setMessage("文件删除成功").setBody("SUCCESS");
            } else {
                result.setCode(CodeConstants.SYSTEM_ERROR).setMessage("文件不存在").setBody("ERROR");
            }
        } catch (Exception e) {
            result.setCode(CodeConstants.SYSTEM_ERROR).setMessage("文件删除失败").setBody("ERROR");
        }
        return result;
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
                files[i] = Paths.get(prefix, new File(fileListArray.getString(i)).getName()).toString();
            }
            // 压缩文件
            String zipFile = Paths.get(tmpPath, "files.zip").toString();
            localFsManipulator.compress(files, zipFile);
            LoadUtils.download(localFsManipulator, zipFile, "files.zip", res);
            localFsManipulator.deleteDir(tmpPath);
            return null;
        } catch (Exception e) {
            log.error(e.getMessage());
            Result<String> result = new Result<>();
            return result.setCode(CodeConstants.SYSTEM_ERROR).setBody("ERROR").setMessage("全部文件返回失败");
        }
    }

    /**
     * 用户个人空间文本类型文件预览 (同时用作结果预览)
     * @param userId 用户ID
     * @param vizType 预览类型 table/map
     * @param requestBody 请求体
     * @return 标准结果体
     */
    @PostMapping("/preview/{vizType}")
    public Result<VizData> preview(@SessionAttribute("userId") String userId, @PathVariable String vizType, @RequestBody String requestBody) {
        FsManipulator fsManipulator = FsManipulatorFactory.create();
        Result<VizData> result = new Result<>();
        try {
            JSONObject requestJSON = new JSONObject(requestBody);
            String path = requestJSON.optString("path", "");
            String currentPath;
            if (path.isEmpty()) {
                currentPath = geodataService.getWholePathOfDataItem(requestJSON.getString("dataId")).replace(setting.getHdFsUri(), "");
            } else if (path.startsWith("public:")) {
                currentPath = Paths.get(setting.getPublicDataRootPath(), path.replace("public:", "")).toString();
            } else {
                currentPath = Paths.get(setting.getUserSpaceRootPath(), userId, path).toString();
            }
            int size = Math.max(Math.min(requestJSON.optInt("size", 2000), 100000), 0);
            int offset = Math.max(requestJSON.optInt("offset", 0), 0);
            if (fsManipulator.isFile(currentPath)) {
                VizData vizData;
                if (currentPath.endsWith(".shp")) {
                    switch (vizType) {
                        case "table":
                            String[] fieldNames = ShpUtil.getFieldNames(currentPath);
                            List<Map<String, Object>> attributes = ShpUtil.getAttributes(currentPath, size, offset);
                            StringBuilder builder = new StringBuilder();
                            builder.append("{\"table\": [");
                            for (Map<String, Object> attribute : attributes) {
                                builder.append("{");
                                for (String fieldName : fieldNames) {
                                    builder.append("\"").append(fieldName).append("\": \"").append(attribute.get(fieldName)).append("\",");
                                }
                                if (fieldNames.length > 0) builder.deleteCharAt(builder.length() - 1);
                                builder.append("},");
                            }
                            if (attributes.size() > 0) builder.deleteCharAt(builder.length() - 1);
                            builder.append("]}");
                            vizData = new VizData(builder.toString());
                            result.setCode(CodeConstants.SUCCESS).setBody(vizData).setMessage("获取表格成功");
                            break;
                        case "map":
                            List<String> wktList = ShpUtil.getGeometriesWkt(currentPath, size, offset);
                            vizData = new VizData(GeometryUtil.getGeomTypeByWkt(wktList.get(0)), GeometryUtil.wktToGeoJson(wktList, null));
                            GeometryUtil.getBboxOfWkt(wktList, vizData.getBbox());
                            result.setCode(CodeConstants.SUCCESS).setBody(vizData).setMessage("获取GeoJson成功");
                            break;
                        default:
                            result.setCode(CodeConstants.SYSTEM_ERROR).setMessage("未知预览类型").setBody(null);
                    }
                } else {
                    List<String> lines = fsManipulator.readToText(currentPath, size, offset);
                    if (lines == null || lines.size() <= 0) {
                        throw new RuntimeException("无内容可预览");
                    }
                    String sep = lines.get(0).contains("\t") ? "\t" : ",";
                    switch (vizType) {
                        case "table":
                            StringBuilder builder = new StringBuilder();
                            builder.append("{\"table\": [");
                            for (String line : lines) {
                                String[] items = line.split(sep);
                                builder.append("{");
                                for (int i = 0; i < items.length; i++) {
                                    builder.append("\"COL_").append(i).append("\": \"").append(items[i]).append("\",");
                                }
                                if (items.length > 0) builder.deleteCharAt(builder.length() - 1);
                                builder.append("},");
                            }
                            builder.deleteCharAt(builder.length() - 1);
                            builder.append("]}");
                            vizData = new VizData(builder.toString());
                            result.setCode(CodeConstants.SUCCESS).setBody(vizData).setMessage("获取表格成功");
                            break;
                        case "map":
                            int geomIndex = requestJSON.optInt("geomIndex", 0);
                            List<String> wktList = new ArrayList<>();
                            List<List<String>> propList = new ArrayList<>();
                            for (String line : lines) {
                                String[] items = line.split(sep);
                                wktList.add(items[geomIndex]);
                                List<String> prop = new ArrayList<>();
                                for (int i = 0; i < items.length; i++) {
                                    if (i == geomIndex) continue;
                                    prop.add(items[i]);
                                }
                                propList.add(prop);
                            }
                            vizData = new VizData(GeometryUtil.getGeomTypeByWkt(wktList.get(0)), GeometryUtil.wktToGeoJson(wktList, propList));
                            GeometryUtil.getBboxOfWkt(wktList, vizData.getBbox());
                            result.setCode(CodeConstants.SUCCESS).setBody(vizData).setMessage("获取GeoJson成功");
                            break;
                        default:
                            result.setCode(CodeConstants.SYSTEM_ERROR).setMessage("未知预览类型").setBody(null);
                    }
                }
            } else {
                throw new RuntimeException("目录路径无法预览");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(CodeConstants.SYSTEM_ERROR).setMessage("文件预览失败").setBody(null);
        }
        return result;
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
