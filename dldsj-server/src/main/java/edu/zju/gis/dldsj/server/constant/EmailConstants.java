package edu.zju.gis.dldsj.server.constant;

public class EmailConstants {

    public static String EmailAccount = "dxdsj_admin@163.com";

    public static String EmailPassword = "JPLGFRGKFPVJIXGU";

    public static String EmailSMTPHost = "smtp.163.com";

    public static String EmailContent(String code){
        return "<p>尊敬的用户：</p>\n" +
                "<p style=\"text-indent:2em\">您好！</p>\n" +
                "<p style=\"text-indent:2em\">欢迎使用地学大数据教学平台！您的注册验证码为：</p>\n" +
                "<p style=\"font-size:40px; text-align:center\">" + code + "</p>\n" +
                "<p>地理大数据教学平台管理员</p>\n";
    }

}