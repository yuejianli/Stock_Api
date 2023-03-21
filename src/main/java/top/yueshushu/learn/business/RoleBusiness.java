package top.yueshushu.learn.business;

import top.yueshushu.learn.mode.ro.RoleRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * 角色信息处理
 *
 * @author Yue Jianli
 * @date 2023-03-16
 */

public interface RoleBusiness {
    /**
     * 查询角色列表
     *
     * @param roleRo 角色对象
     */
    OutputResult list(RoleRo roleRo);

    /**
     * 添加角色列表
     *
     * @param roleRo 角色对象
     */
    OutputResult add(RoleRo roleRo);

    /**
     * 修改角色列表
     *
     * @param roleRo 角色对象
     */
    OutputResult update(RoleRo roleRo);

    /**
     * 删除角色列表
     *
     * @param roleRo 角色对象
     */
    OutputResult delete(RoleRo roleRo);

    /**
     * 根据角色对象查询角色信息
     *
     * @param roleRo 角色对象
     */
    OutputResult findMenuByRoleId(RoleRo roleRo);

    /**
     * 配置资源列表
     *
     * @param roleRo 角色Ro
     */
    OutputResult configMenuList(RoleRo roleRo);
}
