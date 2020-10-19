package edu.zju.gis.dldsj.server.service;

import edu.zju.gis.dldsj.server.config.RunCodeConfig;
import edu.zju.gis.dldsj.server.dto.ProcessResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


/**
 * @author : Shaojian
 * @date : 20201016
 */
@Component
public class RunCodeService {

    @Autowired
    private final RunCodeConfig runCodeConfig;

    private static String javaExec = null;


    private static AtomicLong nextLong = new AtomicLong(System.currentTimeMillis());

    @Autowired
    public RunCodeService(RunCodeConfig runCodeConfig) {
        this.runCodeConfig = runCodeConfig;
    }


    public ProcessResult runCode(String type, String code) throws IOException, InterruptedException {
        // 获取系统缓存文件的位置
        String tmpDir = System.getProperty("java.io.tmpdir");
        // 随机文件夹的名字
        File pwd = Paths.get(tmpDir, String.format("%016x", nextLong.incrementAndGet())).toFile();
        // 新建文件夹
        pwd.mkdirs();
        ProcessBuilder pb = null;
        switch (type) {
            case "C":

                //  TODO  Cannot resolve constructor 'FileWriter(java.io.File, java.nio.charset.Charset)'

                try (Writer writer = new BufferedWriter(new FileWriter(new File(pwd, "Main.c")))) {
                    writer.write(code);
                }
                pb = new ProcessBuilder().command(runCodeConfig.getC()).directory(pwd);
                break;
            case "CPP":
                try (Writer writer = new BufferedWriter(new FileWriter(new File(pwd, "Main.cpp")))) {
                    writer.write(code);
                }
                pb = new ProcessBuilder().command(runCodeConfig.getCpp()).directory(pwd);
                break;
            case "JAVA":
                try (Writer writer = new BufferedWriter(new FileWriter(new File(pwd, "Main.java")))) {
                    writer.write(code);
                }

                // javac -encoding UTF8 C:\Users\11730\AppData\Local\Temp\00000174f2d79cad\Main.java
                // java Main
//                String[] command = new String[]{getJavaExecutePath(), "-encoding UTF-8",  "Main.java"};
//                String[] command = new String[]{getJavaExecutePath(), "-Dfile.encoding=" + Charset.defaultCharset(), "--source", "11", "--enable-preview", "Maain.java"};

                pb = new ProcessBuilder().command(runCodeConfig.getJava()).directory(pwd);
                break;
            case "PYTHON":
                try (Writer writer = new BufferedWriter(new FileWriter(new File(pwd, "Main.py")))) {
                    writer.write(code);
                }
                pb = new ProcessBuilder().command(runCodeConfig.getPython(), "Main.py").directory(pwd);
                break;
            default:
                break;
        }


        pb.redirectErrorStream(true);
        Process p = pb.start();
        if (p.waitFor(5, TimeUnit.SECONDS)) {
            String result = null;
            try (InputStream input = p.getInputStream()) {
                result = readAsString(input, Charset.defaultCharset(), type);
            }
            return new ProcessResult(p.exitValue(), result);
        } else {
            System.err.println(String.format("Error: process %s timeout. destroy forcibly."));
            p.destroyForcibly();
            return new ProcessResult(p.exitValue(), "运行超时");
        }
    }



    private String getJavaExecutePath() {
        if (javaExec == null) {
            String javaHome = System.getProperty("java.home");
            String os = System.getProperty("os.name");
            boolean isWindows = os.toLowerCase().startsWith("windows");
            Path javaPath = Paths.get(javaHome, "bin", isWindows ? "java.exe" : "java");
            javaExec = javaPath.toString();
        }
        return javaExec;
    }

    public String readAsString(InputStream input, Charset charset, String type) throws IOException {
        ByteArrayOutputStream bos = null;
        try{
            bos = new ByteArrayOutputStream();
            byte[] arr = new byte[1024];
            int len;
            while(-1 != (len = input.read(arr))){
                bos.write(arr,0,len);
            }
//            return bos.toString(String.valueOf(charset));
//            return bos.toString("GBK");
            if (type.equals("PYTHON") || type.equals("JAVA")){
                return bos.toString("GBK");
            }
            else {
                return bos.toString(String.valueOf(charset));
            }
        } catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException("提取requestBody异常",e);
        } finally {
            if(null != bos) {
                try {
                    bos.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

//        return output.toString(String.valueOf(charset));
    }
}
