package top.yueshushu.learn.assembler;

import top.yueshushu.learn.domain.RoleDo;
import top.yueshushu.learn.entity.Role;
import top.yueshushu.learn.mode.vo.RoleVo;

/**
 * 角色
 *
 * @author yuejianli
 * @date 2023-03-16
 */

public interface RoleAssembler {

    Role doToEntity(RoleDo roleDo);

    RoleDo entityToDo(Role role);

    RoleVo entityToVo(Role role);
}
