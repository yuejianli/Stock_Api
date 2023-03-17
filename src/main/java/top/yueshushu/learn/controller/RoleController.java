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
        return OutputResult.buildSucc();
    }
}
