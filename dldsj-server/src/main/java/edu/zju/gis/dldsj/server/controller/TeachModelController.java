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
import java.io.*;
import java.util.Enumeration;
import java.util.Random;
import java.util.UUID;

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

    @Autowired
    private TeachModelService teachModelService;
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
                        .setMessage("上传表单不符合要求");
            }
            //包含文件
            MultipartFile file = ((MultipartHttpServletRequest)request).getFile("file");
            if(file==null)
                return result.setCode(CodeConstants.VALIDATE_ERROR).setBody("ERROR")
                    .setMessage("上传文件为空");

            String fileType = file.getContentType();
            if(!fileType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
                return result.setCode(CodeConstants.VALIDATE_ERROR).setBody("ERROR")
                        .setMessage("请上传Word文件");

            FileInputStream docxIunputStream = DocxToMd.transferToFileInputStream(file);

            Enumeration<String> er = request.getParameterNames();
            String folderName = request.getParameter("name");
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
                Thread.sleep(100);// 因为异步执行，此时需要等待shell退出
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//            Method 2
//            try {
//                Process exec = Runtime.getRuntime().exec(SHELL_FILE_DIR + RUNNING_SHELL_FILE + " " + folderName);
//                runningStatus = exec.waitFor();
//            }catch (InterruptedException e){
//                e.printStackTrace();
//            }

            if (runningStatus != 0) {
                InputStream is = p.getErrorStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "GBK"));
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                is.close();
                String message = sb.toString();
                return result.setCode(CodeConstants.VALIDATE_ERROR).setBody("ERROR")
                        .setMessage("脚本运行错误:"+message);
            }
            String outputPath = setting.getEduCasePath()+"/"+folderName+"/"+folderName+".txt";
            //将传入的Docx文件转成Markdown并存入服务器对应文件夹当中
            DocxToMd docxToMd = new DocxToMd();
            docxToMd.docxToMarkdown(docxIunputStream,outputPath);

            TeachModel teachModel = new TeachModel();

            String id = UUID.randomUUID().toString();
            teachModel.setTeachmodelId(id);
            teachModel.setName(folderName);
            teachModel.setKeywords("markdown");
            teachModel.setFileType("1");
            teachModel.setFilePath(setting.getEduCaseNginxPath()+"/"+folderName+"/"+folderName+".txt");
            teachModel.setPicPath(setting.getEduCaseNginxPath()+"/"+folderName+"/pic0.png");

            int resNumber = teachModelService.insertwll(teachModel);
//            Result<TeachModel> res = service.insert(teachModel);
            return result.setCode(CodeConstants.SUCCESS).setBody(id).setMessage("案例上传成功");
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
