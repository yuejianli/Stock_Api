package top.yueshushu.learn.assembler;

import org.mapstruct.Mapper;
import top.yueshushu.learn.domain.UserDo;
import top.yueshushu.learn.entity.User;
import top.yueshushu.learn.mode.vo.AddUserRequestVo;

/**
 * @Description 用户转换器
 * @Author yuejianli
 * @Date 2022/5/20 23:01
 **/
@Mapper(componentModel = "spring" )
public interface UserAssembler {
    /**
     * 用户 domain 转换成实体entity
     * @param userDo 用户Do
     * @return 用户 domain 转换成实体entity
     */
    User doToEntity(UserDo userDo);

    /**
     * 用户 entity 转换成 domain
     *
     * @param user 用户
     * @return 用户 entity 转换成 domain
     */
    UserDo entityToDo(User user);

    /**
     * 添加用户时转换信息
     *
     * @param addUserRequestVo
     */
    User addUserToEntity(AddUserRequestVo addUserRequestVo);

}
