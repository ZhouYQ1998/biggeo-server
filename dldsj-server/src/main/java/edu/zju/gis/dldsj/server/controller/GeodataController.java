package edu.zju.gis.dldsj.server.controller;

import edu.zju.gis.dldsj.server.base.BaseController;
import edu.zju.gis.dldsj.server.base.BaseFilter;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.config.CommonSetting;
import edu.zju.gis.dldsj.server.constant.CodeConstants;
import edu.zju.gis.dldsj.server.entity.Geodata;
import edu.zju.gis.dldsj.server.entity.GeodataItem;
import edu.zju.gis.dldsj.server.entity.vo.FileInfo;
import edu.zju.gis.dldsj.server.service.GeodataItemService;
import edu.zju.gis.dldsj.server.service.GeodataService;
import edu.zju.gis.dldsj.server.utils.GeodataUtil;
import edu.zju.gis.dldsj.server.utils.fs.FsManipulator;
import edu.zju.gis.dldsj.server.utils.fs.FsManipulatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.Path;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Jiarui 2020/8/26
 * @author zjh 2020/10/12
 * @update zyq 2020/11/4
 * @update Keran Sun (katus) 2020/11/12
 */
@Controller
@CrossOrigin
@RequestMapping("/geodata")
@Slf4j
public class GeodataController extends BaseController<Geodata, GeodataService, String, BaseFilter<String>> {

    @Autowired
    private GeodataService geodataService;

    @Autowired
    private GeodataItemService geodataItemService;

    @Autowired
    private CommonSetting setting;

    /**
     * 插入数据集（新建目录）
     * @param geodata Geodata
     * @return Result
     */
    @RequestMapping(value = "/insert", method = RequestMethod.PUT)
    @ResponseBody
    @Override
    public Result<Geodata> insert(@RequestBody Geodata geodata) {
        File dir = new File(geodata.getPath());
        if(dir.mkdir()){
            geodata.setFormat("");
            geodata.setRam("0 KB");
            return service.insert(geodata);
        }
        else{
            return new Result<Geodata>().setCode(CodeConstants.SERVICE_ERROR).setMessage("插入失败：无法新建数据集");
        }
    }

    /**
     * 删除数据集
     * @param id String
     * @return Result
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @Override
    public Result<String> delete(@PathVariable String id) {
        Result<String> result = new Result<>();
        try{
            String datasetPath = geodataService.select(id).getBody().getPath();
            File datasetDir = new File(datasetPath);
            File[] files = datasetDir.listFiles();
            for (File f: files){
                f.delete();
            }
            datasetDir.delete();
            return geodataService.delete(id);
        }
        catch (Exception e){
            return result.setCode(CodeConstants.SERVICE_ERROR).setMessage("删除数据集目录失败");
        }
    }

    /**
     * 向数据集中上传数据
     * @param
     * @return Result
     */
    @RequestMapping(value = "/uploaditem/{dataset}", method = RequestMethod.POST)
    @ResponseBody
    public Result<GeodataItem> uploadItem(@PathVariable String dataset, @RequestParam String format, HttpServletRequest request) {
        Result<GeodataItem> result = new Result<>();
        // 判断请求数据中是否包括数据文件
        if (!ServletFileUpload.isMultipartContent(request)) {
            return result.setCode(CodeConstants.VALIDATE_ERROR).setMessage("数据上传失败：尚未选择数据");
        }
        // 获取数据文件
        List<MultipartFile> multipartFiles = ((MultipartHttpServletRequest) request).getFiles("file");
        // 获取数据集及路径
        Geodata geodata = geodataService.select(dataset).getBody();
        String datasetPath = geodata.getPath();
        // 上传数据
        try {
            for (MultipartFile multipartFile : multipartFiles) {
                String transferPath = Paths.get(datasetPath, multipartFile.getOriginalFilename()).toString();
                multipartFile.transferTo(new File(transferPath));
            }
            // 更新数据集数据格式
            String datasetFormat = geodata.getFormat();
            if(!datasetFormat.contains(format)){
                if (datasetFormat.equals("")) datasetFormat += format;
                else datasetFormat += "," + format;
            }
            geodata.setFormat(datasetFormat);
            // 更新数据集大小
            File datasetDir = new File(datasetPath);
            long size = 0;
            for(File itemFile: datasetDir.listFiles()){
                size += itemFile.length();
            }
            geodata.setRam(GeodataUtil.codeRam(size));
            // 更新数据集
            geodataService.update(geodata);
            // 插入dataItem
            GeodataItem geodataItem = new GeodataItem();
            geodataItem.setDataset(dataset);
            geodataItem.setFormat(format);
            long itemSize = 0;
            for (MultipartFile multipartFile : multipartFiles) {
                File itemFile = new File(Paths.get(datasetPath, multipartFile.getOriginalFilename()).toString());
                itemSize += itemFile.length();
                if(multipartFile.getOriginalFilename().contains(format)){
                    geodataItem.setTitle(multipartFile.getOriginalFilename().replace(format, ""));
                }
            }
            geodataItem.setRam(GeodataUtil.codeRam(itemSize));
            geodataItemService.insert(geodataItem);
            // csv返回json
            if(format.equals(".csv")){
                File itemFile = new File(Paths.get(datasetPath, multipartFiles.get(0).getOriginalFilename()).toString());
                FileReader fr = new FileReader(itemFile);
                BufferedReader bf = new BufferedReader(fr);
                geodataItem.setRemark(bf.readLine());
            }
            result.setCode(CodeConstants.SUCCESS).setBody(geodataItem).setMessage("数据上传成功");
        } catch (Exception e) {
            result.setCode(CodeConstants.SYSTEM_ERROR).setMessage("数据上传失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 更新数据
     * @param geodataItem GeodataItem
     * @return Result
     */
    @RequestMapping(value = "/updateitem", method = RequestMethod.POST)
    @ResponseBody
    public Result<GeodataItem> updateItem(@RequestBody GeodataItem geodataItem) {
        return geodataItemService.update(geodataItem);
    }

    /**
     * 根据文件系统目录树获取公共数据文件信息
     * @param requestBody 请求体
     * @return 标准结果体
     */
    @PostMapping("/getFileInfoList")
    @ResponseBody
    public Result<List<FileInfo>> getFileInfoList(@RequestBody String requestBody) {
        Result<List<FileInfo>> result = new Result<>();
        List<FileInfo> fileList = new ArrayList<>();
        FsManipulator fsManipulator = FsManipulatorFactory.create();
        try {
            String publicRoot = setting.getPublicDataRootPath();
            JSONObject inputs = new JSONObject(requestBody);
            String publicPath = inputs.optString("path", "/");
            String path = Paths.get(publicRoot, publicPath).toString();
            Path[] paths = fsManipulator.listFiles(path);
            for (Path filePath : paths) {
                FileInfo fileInfo = fsManipulator.getFileInfo(filePath);
                fileInfo.setPath(fileInfo.getPath().replace(setting.getLFsUri() + publicRoot.substring(0, publicRoot.length()-1), ""));
                String name = fileInfo.getName();
                String slm = name.contains(".") ? name.substring(name.lastIndexOf(".")) : "";
                if (!(slm.equals(".prj")||slm.equals(".cpg")||slm.equals(".shx")||slm.equals(".dbf"))) fileList.add(fileInfo);
            }
            return result.setCode(CodeConstants.SUCCESS).setBody(fileList).setMessage("文件列表返回成功");
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.setCode(CodeConstants.SYSTEM_ERROR).setMessage("文件列表返回失败");
        }
    }

    /**
     * 数据目录功能：按照type1 第一级目录分类
     *
     * @param type String
     * @param page Page
     */
    @RequestMapping(value = "/bytype1", method = RequestMethod.GET)
    @ResponseBody
    public Result<Page<Geodata>> getByType1(@RequestParam String type, Page<Geodata> page) {
        return geodataService.selectByType1(type, page);
    }

    /**
     * 数据目录功能：按照type2 第二级目录分类
     *
     * @param type String
     * @param page Page
     */
    @RequestMapping(value = "/bytype2", method = RequestMethod.GET)
    @ResponseBody
    public Result<Page<Geodata>> getByType2(@RequestParam String type, Page<Geodata> page) {
        return geodataService.selectByType2(type, page);
    }

    /**
     * 数据目录功能：根据type字段返回唯一不同值与对应数量
     *
     * @param field String
     * @param page Page
     */
    @RequestMapping(value = "/dis", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String, String>> getdis(@RequestParam String field, Page<Geodata> page) {
        return geodataService.getDistinctField(field, page);
    }

    /**
     * 查询下载数量最多的5条数据
     */
    @RequestMapping(value = "/populardata", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<Geodata>> getPopularData() {
        return geodataService.getPopularData();
    }

    /**
     * 查询数据集详情
     *
     * @param id String
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<GeodataItem>> getDetail(@PathVariable String id){
        return geodataService.getDatail(id);
    }

}
