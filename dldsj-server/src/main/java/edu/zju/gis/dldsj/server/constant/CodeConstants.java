package edu.zju.gis.dldsj.server.constant;

public class CodeConstants {

    public final static int SUCCESS = 200;// 成功

    // 通用错误以9开头
    public static final int SYSTEM_ERROR = 9000; //未知系统错误

    public static final int VALIDATE_ERROR = 9001;// 参数验证错误

    public static final int SERVICE_ERROR = 9002;// 业务逻辑验证错误

    public final static int CACHE_ERROR = 9003;// 缓存访问错误

    public final static int DAO_ERROR = 9004;// 数据访问错误

    public final static int USERNAME_OR_PASSWORD_ERROR = 1001; // 用户名或密码错误

    public final static int USER_NOT_EXIST = 1002; // 用户不存在

    public final static int USER_PERMISSION_ERROR = 1003; // 用户权限不足

    public final static int SSO_TOKEN_ERROR = 2001; // TOKEN未授权或已过期

    public final static int SSO_PERMISSION_ERROR = 2002; // 没有访问权限

    public static final int QUEUE_ERROR = 8001; //队列溢出错误

}
