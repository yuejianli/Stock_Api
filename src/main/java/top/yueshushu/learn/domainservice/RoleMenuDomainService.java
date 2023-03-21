package top.yueshushu.learn.domainservice;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.domain.RoleMenuDo;

import java.util.List;

/**
 * <p>
 * 角色权限表 自定义的
 * </p>
 *
 * @author 岳建立
 * @since 2022-01-02
 */
public interface RoleMenuDomainService extends IService<RoleMenuDo> {
    /**
     * 根据角色id 查询对应的所拥有的资源信息
     *
     * @param roleId 角色id
     */
    List<RoleMenuDo> listByCondition(Integer roleId);

    /**
     * 根据角色id 删除配置信息
     *
     * @param roleId 角色id
     */
    void removeByRoleId(Integer roleId);
}
