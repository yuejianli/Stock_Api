package top.yueshushu.learn.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.assembler.MenuAssembler;
import top.yueshushu.learn.domain.MenuDo;
import top.yueshushu.learn.domainservice.MenuDomainService;
import top.yueshushu.learn.entity.Menu;
import top.yueshushu.learn.mode.vo.MenuVo;
import top.yueshushu.learn.mode.vo.TreeNode;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.MenuService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 自定义的
 * </p>
 *
 * @author 岳建立
 * @since 2022-01-02
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Resource
    private MenuDomainService menuDomainService;
    @Resource
    private MenuAssembler menuAssembler;
    @Override
    public OutputResult getMenuListByUid(Integer userId) {
        //如果不为空的话，则进行转换后返回
        return OutputResult.buildSucc(
                listMenuListByUid(userId)
        );
    }

    @Override
    public List<MenuVo> listMenuListByUid(Integer userId) {
        //获取所有的权限
        return convertToVoList(menuDomainService.listPermissionByUidAndRoleId(userId, null));
    }

    @Override
    public OutputResult getAllMenuList() {
        return OutputResult.buildSucc(convertToVoList(menuDomainService.list()));

    }

    @Override
    public OutputResult<List<Tree<String>>> treeAllMenuList() {
        List<MenuDo> allMenuDoList = menuDomainService.list();
        List<MenuDo> orderAllMenuDoList = allMenuDoList.stream().sorted(Comparator.comparing(MenuDo::getShowIndex)).collect(Collectors.toList());
        // 转换成 treeNode
        List<TreeNode> treeNodeList = orderAllMenuDoList.stream().map(
                n -> {
                    TreeNode treeNode = new TreeNode();
                    treeNode.setId(n.getId() + "");
                    treeNode.setPid(n.getPid() + "");
                    treeNode.setLabel(n.getName());
                    return treeNode;
                }
        ).collect(Collectors.toList());

        // 通过 hutool 的 Tree 方法进行构建树.
        List<Tree<String>> buildTreeList = TreeUtil.build(treeNodeList, "0", null, (object, tree) -> {
            tree.putExtra("id", object.getId());
            tree.putExtra("parentId", object.getPid());
            // 上面两个字段必须要有
            tree.putExtra("pid", object.getPid());
            tree.putExtra("label", object.getLabel());
        });

        return OutputResult.buildSucc(buildTreeList);
    }

    public List<MenuVo> convertToVoList(List<MenuDo> menuDoList) {
        if (CollectionUtils.isEmpty(menuDoList)) {
            return Collections.emptyList();
        }

        List<Menu> menuList = new ArrayList<>();
        menuDoList.forEach(
                n -> menuList.add(
                        menuAssembler.doToEntity(
                                n
                        )
                )
        );

        // 再转换成相应的 VO 对象

        List<MenuVo> menuVoList = new ArrayList<>();
        menuList.forEach(
                n-> menuVoList.add(
                        menuAssembler.entityToVo(
                                n
                        )
                )
        );
        return menuVoList;
    }
}
