package top.yueshushu.learn.business;

import top.yueshushu.learn.entity.User;
import top.yueshushu.learn.enumtype.DataFlagType;
import top.yueshushu.learn.mode.ro.QueryUserRo;
import top.yueshushu.learn.mode.ro.UserRo;
import top.yueshushu.learn.mode.vo.AdminOperateUserRequestVo;
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
     * @param text 交易用户信息
     * @return 转换交易用户的信息
     */
    OutputResult tradeUserText(String text);

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

    /**
     * 新增用户信息
     *
     * @param adminOperateUserRequestVo 新增用户信息
     * @param currentUser               当前用户
     */
    OutputResult addUser(AdminOperateUserRequestVo adminOperateUserRequestVo, User currentUser);

    /**
     * 更新用户信息
     *
     * @param adminOperateUserRequestVo 更新用户信息
     * @param currentUser               当前用户
     */
    OutputResult updateUser(AdminOperateUserRequestVo adminOperateUserRequestVo, User currentUser);

    /**
     * 删除用户
     *
     * @param id          要处理的用户id
     * @param currentUser 当前用户
     */
    OutputResult deleteUser(Integer id, User currentUser);

    /**
     * 是否是超级用户  id=1 为超级用户
     *
     * @param user 当前用户
     */
    boolean isSuperUser(User user);

    OutputResult list(QueryUserRo queryUserRo, User user);

    OutputResult changeStatus(Integer id, DataFlagType statusType, User user);

    /**
     * 获取当前用户的信息
     *
     * @param account 账号
     * @param user    当前用户
     */
    OutputResult getInfo(String account, User user);
}
