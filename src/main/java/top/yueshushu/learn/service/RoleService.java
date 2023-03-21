package top.yueshushu.learn.service;

import top.yueshushu.learn.domain.RoleDo;
import top.yueshushu.learn.mode.ro.RoleRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * <p>
 * 角色表 自定义的
 * </p>
 *
 * @author 岳建立
 * @since 2022-01-02
 */
public interface RoleService {

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
     * 根据角色id 查询
     *
     * @param id 角色id
     */
    RoleDo getById(Integer id);
}
