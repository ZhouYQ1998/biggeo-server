package edu.zju.gis.dldsj.server.base;


import edu.zju.gis.dldsj.server.constant.CodeConstants;
import lombok.Getter;
import lombok.Setter;

/**
 * 自定义程序异常基类
 * 用来绑定返回 status code 以及 message
 */

public class BaseException extends RuntimeException {

    private static final String MESSAGE = "系统异常";
    @Getter
    @Setter
    protected int code;

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BaseException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BaseException(String message) {
        this(CodeConstants.SYSTEM_ERROR, message);
    }

    public BaseException code(int code) {
        this.code = code;
        return this;
    }

}
