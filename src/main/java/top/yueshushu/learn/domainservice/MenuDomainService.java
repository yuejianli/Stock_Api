package top.yueshushu.learn.domainservice;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.domain.MenuDo;

import java.util.List;

/**
 * @Description 菜单权限
 * @Author yuejianli
 * @Date 2022/5/21 0:01
 **/
public interface MenuDomainService extends IService<MenuDo> {

    /**
     * 根据用户id和角色id查询相应的菜单信息
     * @param userId 用户id
     * @param ruleId 角色id
     * @return 根据用户id和角色id查询相应的菜单信息
     */
    List<MenuDo> listPermissionByUidAndRoleId(Integer userId, Integer ruleId);

}
