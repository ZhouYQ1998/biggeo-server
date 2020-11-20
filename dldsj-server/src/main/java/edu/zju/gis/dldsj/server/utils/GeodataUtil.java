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
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author zyq
 * @date 2020/10/12
 */
public class GeodataUtil {

    public static String codeRam(long ram){
        String ramString = "";
        if(ram < 1024 * 1024){
            ramString = String.format("%.2f", ram / 1024.0) + " KB";
        }
        else if(ram < 1024 * 1024 * 1024){
            ramString = String.format("%.2f", ram / 1024.0 / 1024.0) + "MB";
        }
        else{
            ramString = String.format("%.2f", ram / 1024.0 / 1024.0 / 1024.0) + "GB";
        }
        return ramString;
    }

}
