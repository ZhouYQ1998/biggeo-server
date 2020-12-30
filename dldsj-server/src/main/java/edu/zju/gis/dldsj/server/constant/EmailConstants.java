package edu.zju.gis.dldsj.server.constant;

public class EmailConstants {

//    public static String EmailAccount = "dxdsj_admin@163.com";

//    public static String EmailPassword = "JPLGFRGKFPVJIXGU";

//    public static String EmailSMTPHost = "smtp.163.com";

    public static String EmailAccount = "21938053@zju.edu.cn";

    public static String EmailPassword = "f8e2jUJjaPs74pMB";

    public static String EmailSMTPHost = "smtp.zju.edu.cn";

    public static String EmailContent(String code){
        return "<p>尊敬的用户：</p>\n" +
                "<p style=\"text-indent:2em\">您好！</p>\n" +
                "<p style=\"text-indent:2em\">欢迎使用地球系统大数据平台！您的注册验证码为：</p>\n" +
                "<p style=\"font-size:40px; text-align:center\">" + code + "</p>\n" +
                "<p>地球系统大数据平台管理员</p>\n";
    }

}