package top.yueshushu.learn.business;

/**
 * 自动登录
 *
 * @author yuejianli
 * @date 2023-01-10
 */

public interface AutoLoginBusiness {
    /**
     * 自动登录
     *
     * @param userId 用户编号
     */
    boolean autoLogin(Integer userId);

}
