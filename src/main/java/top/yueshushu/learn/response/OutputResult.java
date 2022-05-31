package top.yueshushu.learn.response;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

/**
 * @ClassName:OutputResult
 * @Description 返回的响应实体信息
 * @Author 岳建立
 * @Date 2021/1/1 10:09
 * @Version 1.0
 **/
@Data
@Getter
public class OutputResult<T> implements Serializable {
    /**
     * @param code 响应代码
     * @param success 是否成功  true 为成功  false 为不成功
     * @param message 响应信息
     * @param exceptionMessage 异常信息
     * @param data 响应的数据
     */
    private Integer code;
    private Boolean success;
    private String message;
    private String exceptionMessage;
    private T data ;

    private static BaseResultCode DEFAULT_SUCCESS = BaseResultCode.SUCCESS;
    private static BaseResultCode DEFAULT_ALERT = BaseResultCode.ALERT;
    private static BaseResultCode DEFAULT_FAIL = BaseResultCode.FAIL;

    /**
     * 构造方法 私有。 避免外部构造
     */
    public OutputResult() {

    }

    public OutputResult(BaseResultCode baseResultCode) {
        this(baseResultCode, null);
    }

    public OutputResult(BaseResultCode resultCode, T data) {
        this(resultCode.isSuccess(), resultCode.getCode(), resultCode.getMessage(), data);
    }

    public OutputResult(boolean success, int code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> OutputResult<T> buildFail() {
        return buildFail(BaseResultCode.FAIL);
    }

    public static <T> OutputResult<T> buildFail(String message) {
        return buildFail(BaseResultCode.FAIL.getCode(), message);
    }

    public static <T> OutputResult<T> buildFail(BaseResultCode resultCode) {
        return buildFail(resultCode.getCode(), resultCode.getMessage());
    }

    public static <T> OutputResult<T> buildFail(int code, String message) {
        return build(false, code, message, null);
    }

    public static <T> OutputResult<T> buildFail(BaseResultCode resultCode, T data) {
        return buildFail(resultCode, null, data);
    }

    public static <T> OutputResult<T> buildFail(BaseResultCode resultCode, String message) {
        return new OutputResult<>(resultCode.isSuccess(), resultCode.getCode(), message, null);
    }

    public static <T> OutputResult<T> buildFail(BaseResultCode resultCode, String message, T data) {
        return buildFail(resultCode.getCode(), message, data);
    }

    public static <T> OutputResult<T> buildFail(int code, String message, T data) {
        return build(false, code, message, data);
    }


    public static <T> OutputResult<T> buildAlert() {
        return buildAlert(BaseResultCode.ALERT);
    }

    public static <T> OutputResult<T> buildAlert(String message) {
        return buildAlert(BaseResultCode.ALERT.getCode(), message);
    }

    public static <T> OutputResult<T> buildAlert(BaseResultCode resultCode) {
        return buildAlert(resultCode.getCode(), resultCode.getMessage());
    }

    public static <T> OutputResult<T> buildAlert(int code, String message) {
        return build(false, code, message, null);
    }

    public static <T> OutputResult<T> buildAlert(BaseResultCode resultCode, T data) {
        return buildAlert(resultCode, null, data);
    }

    public static <T> OutputResult<T> buildAlert(BaseResultCode resultCode, String message) {
        return new OutputResult<>(resultCode.isSuccess(), resultCode.getCode(), message, null);
    }

    public static <T> OutputResult<T> buildAlert(BaseResultCode resultCode, String message, T data) {
        return buildFail(resultCode.getCode(), message, data);
    }

    public static <T> OutputResult<T> buildAlert(int code, String message, T data) {
        return build(false, code, message, data);
    }


    public static <T> OutputResult<T> buildSucc() {
        return buildSucc(BaseResultCode.SUCCESS);
    }

    public static <T> OutputResult<T> buildSucc(T data) {
        return buildSucc(BaseResultCode.SUCCESS, data);
    }

    public static <T> OutputResult<T> buildSucc(BaseResultCode resultCode) {
        return buildSucc(resultCode.getCode(), resultCode.getMessage());
    }

    public static <T> OutputResult<T> buildSucc(int code, String message) {
        return buildSucc(code, message, null);
    }

    public static <T> OutputResult<T> buildSucc(BaseResultCode resultCode, T data) {
        return buildSucc(resultCode, null, data);
    }

    public static <T> OutputResult<T> buildSucc(BaseResultCode resultCode, String message, T data) {
        return buildSucc(resultCode.getCode(), message, data);
    }

    public static <T> OutputResult<T> buildSucc(int code, String message, T data) {
        return build(true, code, message, data);
    }

    private static <T> OutputResult<T> build(boolean success, int code, String message, T data) {
        return new OutputResult<>(success, code, message, data);
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
