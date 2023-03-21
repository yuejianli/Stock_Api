package top.yueshushu.learn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.domain.UserRoleDo;

import java.util.List;

/**
 * <p>
 * 员工角色关联表 自定义的
 * </p>
 *
 * @author 岳建立
 * @since 2022-01-02
 */
public interface UserRoleService extends IService<UserRoleDo> {
    /**
     * 根据用户id 和角色id 查询配置的信息
     *
     * @param userId 用户id
     * @param roleId 角色id
     */
    List<UserRoleDo> listByUserIdAndRoleId(Integer userId, Integer roleId);

    /**
     * 配置用户与角色关系
     *
     * @param userId 用户id
     * @param roleId 角色id
     */
    void configRole(Integer userId, Integer roleId);
}
