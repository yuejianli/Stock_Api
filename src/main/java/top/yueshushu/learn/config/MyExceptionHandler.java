package top.yueshushu.learn.config;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.exception.TradeUserException;
import top.yueshushu.learn.response.OutputResult;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 *
 * @author yuejianli
 * @date 2023-03-23
 */
@RestControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(TradeUserException.class)
    public OutputResult tradeUserException(HttpServletRequest req, TradeUserException e) {
        return OutputResult.buildFail(ResultCode.TRADE_USER_NO_LOGIN, e.getMessage());

    }
}
