package top.yueshushu.learn.business.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.business.RoleBusiness;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.domain.RoleDo;
import top.yueshushu.learn.domain.RoleMenuDo;
import top.yueshushu.learn.domainservice.MenuDomainService;
import top.yueshushu.learn.domainservice.RoleMenuDomainService;
import top.yueshushu.learn.mode.ro.RoleRo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.RoleService;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色配置
 *
 * @author yuejianli
 * @date 2023-03-16
 */
@Service
public class RoleBusinessImpl implements RoleBusiness {
    @Resource
    private RoleService roleService;
    @Resource
    private RoleMenuDomainService roleMenuDomainService;
    @Resource
    private MenuDomainService menuDomainService;

    @Override
    public OutputResult list(RoleRo roleRo) {
        return roleService.list(roleRo);
    }

    @Override
    public OutputResult add(RoleRo roleRo) {
        return roleService.add(roleRo);
    }

    @Override
    public OutputResult update(RoleRo roleRo) {
        return roleService.update(roleRo);
    }

    @Override
    public OutputResult delete(RoleRo roleRo) {
        return roleService.delete(roleRo);
    }

    @Override
    public OutputResult findMenuByRoleId(RoleRo roleRo) {
        List<RoleMenuDo> roleMenuDoList = roleMenuDomainService.listByCondition(roleRo.getId());
        List<Integer> menuIdList = roleMenuDoList.stream().map(RoleMenuDo::getMenuId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(menuIdList)) {
            return OutputResult.buildSucc(Collections.emptyList());
        }
        return OutputResult.buildSucc(menuDomainService.listByIds(menuIdList));
    }

    @Override
    public OutputResult configMenuList(RoleRo roleRo) {
        // 如果有配置,则将之前的删除
        if (CollectionUtils.isEmpty(roleRo.getMenuIdList())) {
            return OutputResult.buildFail(ResultCode.ROLE_MENU_NOT_CONFIG);
        }
        RoleDo roleDo = roleService.getById(roleRo.getId());
        if (null == roleDo) {
            return OutputResult.buildFail(ResultCode.INVALID_PARAM);
        }
        roleMenuDomainService.removeByRoleId(roleRo.getId());

        // 进行配置查询
        LocalDateTime now = LocalDateTime.now();
        List<RoleMenuDo> roleMenuDoList = roleRo.getMenuIdList().stream().map(
                n -> {
                    RoleMenuDo roleMenuDo = new RoleMenuDo();
                    roleMenuDo.setRoleId(roleRo.getId());
                    roleMenuDo.setMenuId(n);
                    roleMenuDo.setCreateTime(now);
                    return roleMenuDo;
                }
        ).collect(Collectors.toList());
        roleMenuDomainService.saveBatch(roleMenuDoList);
        return OutputResult.buildSucc();
    }
}
