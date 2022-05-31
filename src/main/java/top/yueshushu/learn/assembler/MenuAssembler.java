package top.yueshushu.learn.assembler;

import org.mapstruct.Mapper;
import top.yueshushu.learn.domain.MenuDo;
import top.yueshushu.learn.domain.UserDo;
import top.yueshushu.learn.entity.Menu;
import top.yueshushu.learn.entity.User;
import top.yueshushu.learn.mode.vo.MenuVo;

/**
 * @Description 菜单实体转换器
 * @Author yuejianli
 * @Date 2022/5/20 23:01
 **/
@Mapper(componentModel = "spring" )
public interface MenuAssembler {
    /**
     * 菜单 domain 转换成实体entity
     * @param menuDo 菜单Do
     * @return 菜单 domain 转换成实体entity
     */
    Menu doToEntity(MenuDo menuDo);

    /**
     * 菜单 entity 转换成 domain
     * @param menu 菜单
     * @return 菜单 entity 转换成 domain
     */
    MenuDo entityToDo(Menu menu);

    /**
     * 菜单 entity 转换成 vo
     * @param menu 菜单
     * @return 菜单 entity 转换成 vo
     */
    MenuVo entityToVo(Menu menu);
}
