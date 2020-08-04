package edu.zju.gis.dldsj.server.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.zju.gis.dldsj.server.constant.CodeConstants;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hu
 * @date 2018年4月7日
 * updated by yanlong_lee@qq.com 2018/08/01
 */
public class Result<T> {
    private static final Logger log = LoggerFactory.getLogger(Result.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private T body;
    private Integer code;
    private String message;

    public Result() {
    }

    public Result(Integer code) {
        this.code = code;
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(T body, Integer code, String message) {
        this.body = body;
        this.code = code;
        this.message = message;
    }


    public static <T> Result<T> create(Integer code) {
        return new Result<>(code);
    }

    public static <T> Result<T> success() {
        return create(CodeConstants.SUCCESS);
    }

    public static <T> Result<T> error() {
        return create(CodeConstants.SYSTEM_ERROR);
    }

    public static Result<String> error(String message) {
        Result<String> result = create(CodeConstants.SYSTEM_ERROR);
        result.setMessage(message);
        return result;
    }

    public T getBody() {
        return body;
    }

    public Result<T> setBody(T body) {
        this.body = body;
        return this;
    }

    public int getCode() {
        return code;
    }

    public Result<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        try {
            if (body instanceof JSONObject || body instanceof JSONArray) {
                return "{\"code\":" + code + ",\"message\":\"" + message + "\",\"body\":" + body.toString() + "}";
            } else {
                return objectMapper.writeValueAsString(this);
            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return "{\"code\":" + CodeConstants.SYSTEM_ERROR + ",\"message\":\"" + e.getMessage() + "\"}";
        }
    }
}
