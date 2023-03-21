package top.yueshushu.learn.business;

import cn.hutool.core.lang.tree.Tree;
import top.yueshushu.learn.response.OutputResult;

import java.util.List;

/**
 * @Description 菜单 编排层
 * @Author yuejianli
 * @Date 2022/5/20 23:52
 **/
public interface MenuBusiness {
    /**
     * 根据用户编号获取对应的菜单权限集合
     *
     * @param userId 用户编号
     * @return 返回对应的菜单权限集合
     */
    OutputResult getMenuListByUid(Integer userId);

    /**
     * 获取所有的菜单权限
     */
    OutputResult getAllMenuList();

    /**
     * tree 形式展示菜单资源信息
     */
    OutputResult<List<Tree<String>>> treeAllMenuList();
}
