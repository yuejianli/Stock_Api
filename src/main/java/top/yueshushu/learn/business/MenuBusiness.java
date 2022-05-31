package top.yueshushu.learn.business;

import top.yueshushu.learn.response.OutputResult;

/**
 * @Description 菜单 编排层
 * @Author yuejianli
 * @Date 2022/5/20 23:52
 **/
public interface MenuBusiness {
    /**
     * 根据用户编号获取对应的菜单权限集合
     * @param userId 用户编号
     * @return 返回对应的菜单权限集合
     */
    OutputResult getMenuListByUid(Integer userId);

}
