package top.yueshushu.learn.business.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.business.UserBusiness;
import top.yueshushu.learn.entity.User;
import top.yueshushu.learn.mode.ro.UserRo;
import top.yueshushu.learn.mode.vo.MenuVo;
import top.yueshushu.learn.mode.vo.UserVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.MenuService;
import top.yueshushu.learn.service.UserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 用户编排层实现
 * @Author 岳建立
 * @Date 2022/5/20 22:33
 **/
@Service
@Slf4j
public class UserBusinessImpl implements UserBusiness {
    @Resource
    private UserService userService;
    @Resource
    private MenuService menuService;

    @Override
    public OutputResult login(UserRo userRo) {
        OutputResult userValidateResult = userService.validateUserRo(
                userRo
        );
        //如果验证不通过，就招聘相应的提示.
        if (!userValidateResult.getSuccess()){
            return userValidateResult;
        }

        // 用户名和密码均正确，就处理用户的信息.
        User user = userService.login(userRo);

        //获取权限信息
        List<MenuVo> menuVoList = menuService.listMenuListByUid(user.getId());

        UserVo userVo = new UserVo();
        userVo.setCurrentUser(user);
        userVo.setMenuList(menuVoList);

        return OutputResult.buildSucc(userVo);
    }

    @Override
    public OutputResult convertPassWord(String password) {
        return userService.convertPassWord(password);
    }

    @Override
    public OutputResult tradePassword(String password) {
        return userService.tradePassword(password);
    }
}
