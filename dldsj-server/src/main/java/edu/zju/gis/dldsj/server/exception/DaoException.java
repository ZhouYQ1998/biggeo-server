package edu.zju.gis.dldsj.server.exception;


import edu.zju.gis.dldsj.server.constant.CodeConstants;

/**
 * 数据访问异常
 */
public class DaoException extends ApplicationException {

    public static final String MESSAGE = "数据访问异常";
    private static final long serialVersionUID = -7980532772047897013L;

    public DaoException() {
        super(MESSAGE);
    }

    public DaoException(String message) {
        super(message);
        this.code = CodeConstants.DAO_ERROR;
    }

    public DaoException(int code, String message) {
        super(message);
        this.code = code;
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
        this.code = CodeConstants.DAO_ERROR;
    }

    public DaoException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public DaoException(Throwable cause) {
        super(cause);
        this.code = CodeConstants.DAO_ERROR;
    }
}
