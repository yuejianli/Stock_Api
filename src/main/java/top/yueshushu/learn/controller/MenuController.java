package top.yueshushu.learn.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yueshushu.learn.annotation.AuthToken;
import top.yueshushu.learn.business.MenuBusiness;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.util.ThreadLocalUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 菜单表 我是自定义的
 * </p>
 *
 * @author 岳建立
 * @date 2022-01-02
 */
@RestController
@RequestMapping("/menu")
@Api("菜单")
public class MenuController {
    @Resource
    private MenuBusiness menuBusiness;
    @GetMapping("/getMenuList")
    @ApiOperation("获取用户的菜单信息")
    @AuthToken
    public OutputResult getMenuListByUid(){
        Integer userId = ThreadLocalUtils.getUserId();
        return menuBusiness.getMenuListByUid(userId);
    }
}
