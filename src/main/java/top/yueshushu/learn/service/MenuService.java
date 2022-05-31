package top.yueshushu.learn.service;

import top.yueshushu.learn.mode.vo.MenuVo;
import top.yueshushu.learn.domain.MenuDo;
import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.response.OutputResult;

import java.util.List;

/**
 * <p>
 * 菜单表 自定义的
 * </p>
 *
 * @author 岳建立
 * @since 2022-01-02
 */
public interface MenuService {
    /**
     * 根据用户id, 获取对应的权限集合
     * @param userId
     * @return
     */
    OutputResult getMenuListByUid(Integer userId);

    /**
     * 根据用户id, 获取对应的权限集合
     * @param userId
     * @return
     */
    List<MenuVo> listMenuListByUid(Integer userId);
}
