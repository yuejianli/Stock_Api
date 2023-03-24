package top.yueshushu.learn.exception;

/**
 * 交易用户异常
 *
 * @author yuejianli
 * @date 2023-03-23
 */

public class TradeUserException extends Exception {
    String message = "";

    public TradeUserException(String message) {
        super(message);
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}