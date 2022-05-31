package top.yueshushu.learn.mapper;

import org.apache.ibatis.annotations.Param;
import top.yueshushu.learn.domain.MenuDo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author 岳建立  自定义的
 * @since 2022-01-02
 */
public interface MenuDoMapper extends BaseMapper<MenuDo> {
    /**
     * 根据用户编号和角色编号，查询对应的菜单id
     *
     * @param userId 用户编号
     * @param roleId 角色编号
     * @return 根据用户编号和角色编号，查询对应的菜单id
     */
    List<MenuDo> listPermissionByUidAndRoleId(@Param("userId") Integer userId, @Param("roleId") Integer roleId);


}
