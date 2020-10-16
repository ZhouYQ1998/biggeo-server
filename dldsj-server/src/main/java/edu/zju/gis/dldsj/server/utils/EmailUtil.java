package edu.zju.gis.dldsj.server.utils;

import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.constant.CodeConstants;
import edu.zju.gis.dldsj.server.constant.EmailConstants;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author zyq
 * @date 2020/10/12
 */

/**
 * Run local command 调用本地的command
 *
 * @return exit status
 */
public class EmailUtil {

    public static Result<Map<String, String>> sendEmail(String email){
        Result<Map<String, String>> result = new Result<>();

        Map<String, String> map = new HashMap<>();
        map.put("email", email);

        StringBuilder codeBuilder = new StringBuilder();
        for(int i=0; i<6; i++){
            codeBuilder.append(String.valueOf((int)Math.floor(Math.random()*10)));
        }
        String code = codeBuilder.toString();
        map.put("code", code);

        result.setBody(map);

        try{
            Properties props = new Properties();
            props.setProperty("mail.transport.protocol", "smtp");
            props.setProperty("mail.smtp.host", EmailConstants.EmailSMTPHost);
            props.setProperty("mail.smtp.auth", "true");

            Authenticator auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(EmailConstants.EmailAccount, EmailConstants.EmailPassword);
                }
            };

            Session session = Session.getInstance(props, auth);
            // session.setDebug(true); // 开启Debug模式

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EmailConstants.EmailAccount, "地球系统大数据平台", "UTF-8"));
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email));
            message.setSubject("注册验证码", "UTF-8");
            message.setContent(EmailConstants.EmailContent(code), "text/html;charset=UTF-8");
            message.setSentDate(new Date());

            Transport transport = session.getTransport("smtp");
            transport.connect(EmailConstants.EmailSMTPHost, EmailConstants.EmailAccount, EmailConstants.EmailPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            result.setCode(CodeConstants.SUCCESS).setMessage("验证码发送成功");

        }catch(Exception e){
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("验证码发送失败：" + e.getMessage());
        }

        return result;
    }

}
