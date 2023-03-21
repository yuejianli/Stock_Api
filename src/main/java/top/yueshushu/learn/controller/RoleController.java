package top.yueshushu.learn.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yueshushu.learn.annotation.AuthToken;
import top.yueshushu.learn.business.RoleBusiness;
import top.yueshushu.learn.mode.ro.RoleRo;
import top.yueshushu.learn.response.OutputResult;

import javax.annotation.Resource;

/**
 * <p>
 * 角色表 我是自定义的
 * </p>
 *
 * @author 岳建立
 * @date 2022-01-02
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private RoleBusiness roleBusiness;

    @ApiOperation("查询角色列表")
    @PostMapping("/list")
    @AuthToken
    public OutputResult list(@RequestBody RoleRo roleRo) {
        return roleBusiness.list(roleRo);
    }

    @ApiOperation("添加角色列表")
    @PostMapping("/add")
    @AuthToken
    public OutputResult add(@RequestBody RoleRo roleRo) {
        return roleBusiness.add(roleRo);
    }

    @ApiOperation("修改角色列表")
    @PostMapping("/update")
    @AuthToken
    public OutputResult update(@RequestBody RoleRo roleRo) {
        return roleBusiness.update(roleRo);
    }

    @ApiOperation("删除角色列表")
    @PostMapping("/delete")
    @AuthToken
    public OutputResult delete(@RequestBody RoleRo roleRo) {
        return roleBusiness.delete(roleRo);
    }

    @ApiOperation("查询角色id 配置的资源列表")
    @PostMapping("/findMenuByRoleId")
    @AuthToken
    public OutputResult findMenuByRoleId(@RequestBody RoleRo roleRo) {
        return roleBusiness.findMenuByRoleId(roleRo);
    }

    @ApiOperation("配置角色资源列表")
    @PostMapping("/configMenuList")
    @AuthToken
    public OutputResult configMenuList(@RequestBody RoleRo roleRo) {
        return roleBusiness.configMenuList(roleRo);
    }

}
