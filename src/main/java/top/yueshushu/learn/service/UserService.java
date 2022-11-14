package top.yueshushu.learn.service;

import top.yueshushu.learn.entity.User;
import top.yueshushu.learn.mode.ro.UserRo;
import top.yueshushu.learn.response.OutputResult;

import java.util.List;

/**
 * <p>
 * 登录用户表 自定义的
 * </p>
 *
 * @author 岳建立
 * @since 2022-01-02
 */
public interface UserService  {
    /**
     * 用户登录成功后，修改相应的用户信息
     * @param userRo 用户登录时请求的信息
     * @return 返回登录成功，新的用户对象信息
     */
    User login(UserRo userRo);

    /**
     * 转换登录用户的密码信息
     * @param password 要转换的密码
     * @return 转换登录用户的密码信息
     */
    OutputResult convertPassWord(String password);

    /**
     * 用户登录时，验证用户的账号和密码信息
     * @param userRo 用户登录时的对象封装
     * @return 返回验证的相关信息
     */
    OutputResult validateUserRo(UserRo userRo);

    /**
     * 通过账号查询相应的用户信息
     *
     * @param account 账号
     * @return 返回相应的用户信息，查询不出来，返回 null
     */
    User getUserByAccount(String account);

    /**
     * 获取默认的用户
     *
     * @return 返回相应的用户信息，查询不出来，返回 null
     */
    User getDefaultUser();

    /**
     * 获取默认的用户
     *
     * @param id 用户的id
     * @return 返回相应的用户信息，查询不出来，返回 null
     */
    User getById(Integer id);

    /**
     * 查询所有的用户id信息
     *
     * @return 返回所有的用户id集合
     */
    List<Integer> listUserId();

    /**
     * 转换交易用户的密码
     *
     * @param password 交易用户密码
     * @return 转换交易用户的密码
     */
    OutputResult tradePassword(String password);

    /**
     * 查询需要通知的用户列表
     *
     * @return 查询需要通知的用户列表
     */
    List<User> listNotice();

    /**
     * 添加用户操作
     *
     * @param addUserRequestVo 用户信息
     */
    User operateUser(User user);
}
