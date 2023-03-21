package top.yueshushu.learn.assembler;

import org.mapstruct.Mapper;
import top.yueshushu.learn.domain.RoleDo;
import top.yueshushu.learn.entity.Role;
import top.yueshushu.learn.mode.vo.RoleVo;

import java.util.List;

/**
 * 角色
 *
 * @author yuejianli
 * @date 2023-03-16
 */
@Mapper(componentModel = "spring")
public interface RoleAssembler {

    Role doToEntity(RoleDo roleDo);

    List<Role> doToEntityList(List<RoleDo> roleDoList);

    RoleDo entityToDo(Role role);

    RoleVo entityToVo(Role role);

    List<RoleVo> entityToVoList(List<Role> roleList);
}
