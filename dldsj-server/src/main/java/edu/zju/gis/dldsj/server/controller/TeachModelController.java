package edu.zju.gis.dldsj.server.controller;

import edu.zju.gis.dldsj.server.base.BaseController;
import edu.zju.gis.dldsj.server.base.BaseFilter;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.config.CommonSetting;
import edu.zju.gis.dldsj.server.constant.CodeConstants;
import edu.zju.gis.dldsj.server.entity.TeachModel;
import edu.zju.gis.dldsj.server.pojo.DocxToMd;
import edu.zju.gis.dldsj.server.service.TeachModelService;
import lombok.extern.log4j.Log4j;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Jiarui
 * @date 2020/10/13
 */

@Log4j
@Controller
@RequestMapping("/teachModel")
public class TeachModelController extends BaseController<TeachModel, TeachModelService,String, BaseFilter<String>> {

    @Autowired
    private CommonSetting setting;
    /**
     * 管理员上传教学案例
     * @param role
     * @param request Servlet请求
     * @return 标准结果体
     */
    @RequestMapping(value = "/uploadTeachModel",method = RequestMethod.POST)
    @ResponseBody
    public Result uploadTeachModel(@SessionAttribute("role") String role, HttpServletRequest request) throws IOException {
        if (role.equals("visitor")){
            return Result.error().setMessage("游客无此权限");
        }
        if  (role.equals("manager")){
            Result<String> result = new Result<>();
            // 如果请求数据中不包括文件
            if (!ServletFileUpload.isMultipartContent(request)) {   // 判断是否是包含文件的表单数据
                return result.setCode(CodeConstants.VALIDATE_ERROR).setBody("ERROR")
                        .setMessage("上传文件为空");
            }
            //包含文件
            MultipartFile file = ((MultipartHttpServletRequest)request).getFile("file");
            String fileType = file.getContentType();
            //TODO:如果文件类型不是DOCX
            FileInputStream docxIunputStream = DocxToMd.transferToFileInputStream(file);

            String folderName = "";//TODO 从req中读取教学案例名;
            String RUNNING_SHELL_FILE = "makeDir.sh";
            String SHELL_FILE_DIR = setting.getEduCasePath();

            //使用Shell脚本新建一个该教学案例的文件夹
            //Method 1
            ProcessBuilder pb = new ProcessBuilder("./" + RUNNING_SHELL_FILE, folderName);
            pb.directory(new File(SHELL_FILE_DIR));
            int runningStatus = 0;
            Process p = pb.start();
            try {
                runningStatus = p.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            //Method 2
//            try {
//                Process exec = Runtime.getRuntime().exec(SHELL_FILE_DIR + RUNNING_SHELL_FILE + " " + folderName);
//                runningStatus = exec.waitFor();
//            }catch (InterruptedException e){
//                e.printStackTrace();
//            }

            if (runningStatus != 0) {
                //TODO 脚本运行存在问题
            }

            //将传入的Docx文件转成Markdown并存入服务器对应文件夹当中
            DocxToMd.docxToMarkdown(docxIunputStream,setting.getEduCasePath()+folderName);

            TeachModel teachModel = new TeachModel();
            return Result.success().setBody(service.insert(teachModel));
        }
        else return Result.error();
    }

    /**
     * 管理员删除teachModel
     * @param role
     * @param teachModelId
     * @return
     */
    @RequestMapping(value = "/unregisterTeachModel/{teachModelId}",method = RequestMethod.DELETE)
    @ResponseBody
    public Result unregisterTeachModel(@SessionAttribute("role") String role,@PathVariable String teachModelId){
        if (role.equals("visitor")){
            return Result.error().setMessage("游客无此权限");
        }
        if  (role.equals("manager")){
            return Result.success().setBody(service.delete(teachModelId));
        }
        else return Result.error();
    }

}
