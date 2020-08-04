package edu.zju.gis.dldsj.server.exception;


import edu.zju.gis.dldsj.server.constant.CodeConstants;

/**
 * 自定义程序异常.
 * <p>
 * 继承RuntimeException
 * </p>
 */
public class ApplicationException extends RuntimeException {

    public static final String MESSAGE = "应用异常";
    private static final long serialVersionUID = 2134223938478410255L;
    protected int code = CodeConstants.SYSTEM_ERROR;

    public ApplicationException(int i) {
        code = i;
    }

    public ApplicationException() {
        super(MESSAGE);
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
