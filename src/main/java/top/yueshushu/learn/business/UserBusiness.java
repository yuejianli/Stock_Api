package top.yueshushu.learn.business;

import top.yueshushu.learn.mode.ro.UserRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * @Description User用户的编排层处理
 * @Author 岳建立
 * @Date 2022/5/20 22:33
 **/
public interface UserBusiness {
    /**
     * 用户登录
     * @param userRo 登录用户的对象
     * @return 返回用户登录后的响应信息
     */
    OutputResult login(UserRo userRo);

    /**
     * 转换登录用户的密码
     * @param password 密码
     * @return 返回转换后的密码信息
     */
    OutputResult convertPassWord(String password);

    /**
     * 转换交易用户的密码
     *
     * @param password 交易用户密码
     * @return 转换交易用户的密码
     */
    OutputResult tradePassword(String password);

    /**
     * 将配置文件中的敏感信息加密
     *
     * @param text 要加密的信息
     * @return 将配置文件中的敏感信息加密
     */
    OutputResult encrypt(String text);

    /**
     * 将配置文件中的敏感信息解密
     *
     * @param text 要解密的信息
     * @return 将配置文件中的敏感信息解密
     */
    OutputResult decrypt(String text);
}
