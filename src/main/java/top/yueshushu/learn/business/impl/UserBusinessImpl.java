package top.yueshushu.learn.business.impl;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.assembler.UserAssembler;
import top.yueshushu.learn.business.UserBusiness;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.entity.TradeMoney;
import top.yueshushu.learn.entity.User;
import top.yueshushu.learn.enumtype.MockType;
import top.yueshushu.learn.mode.ro.UserRo;
import top.yueshushu.learn.mode.vo.AddUserRequestVo;
import top.yueshushu.learn.mode.vo.MenuVo;
import top.yueshushu.learn.mode.vo.UserVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.*;
import top.yueshushu.learn.util.JasypUtil;

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
    private TradeUserService tradeUserService;
    @Resource
    private TradeMoneyService tradeMoneyService;

    @Resource
    private TradeMoneyHistoryService tradeMoneyHistoryService;
    @Resource
    private MenuService menuService;
    @Resource
    private UserAssembler userAssembler;

    @Override
    public OutputResult login(UserRo userRo) {
        OutputResult userValidateResult = userService.validateUserRo(
                userRo
        );
        //如果验证不通过，就招聘相应的提示.
        if (!userValidateResult.getSuccess()) {
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

    @Override
    public OutputResult encrypt(String text) {
        return OutputResult.buildSucc(JasypUtil.encrypt(text));
    }

    @Override
    public OutputResult decrypt(String text) {
        return OutputResult.buildSucc(JasypUtil.decrypt(text));
    }

    @Override
    public OutputResult addUser(AddUserRequestVo addUserRequestVo, User currentUser) {
        // 添加用户前验证。
        if (currentUser.getId() != 1) {
            return OutputResult.buildFail(ResultCode.NO_AUTH);
        }
        // 用户账号不能相同
        User dbUser = userService.getUserByAccount(addUserRequestVo.getAccount());
        if (dbUser != null) {
            return OutputResult.buildFail(ResultCode.USER_EXISTS);
        }

        // 添加用户操作
        User user = userAssembler.addUserToEntity(addUserRequestVo);

        User addUser = userService.operateUser(user);

        // 补充一下 TradeUser 的操作信息.

        tradeUserService.operateTradeUser(null, addUser.getId());

        // 往金额里面放置数据。

        TradeMoney tradeMoney = new TradeMoney();
        tradeMoney.setTotalMoney(addUserRequestVo.getTotalMoney());
        tradeMoney.setUserId(addUser.getId());
        tradeMoney.setMockType(MockType.MOCK.getCode());
        tradeMoneyService.operateMoney(tradeMoney);

        // 保存历史金额
        tradeMoneyService.saveMoneyHistory(addUser.getId(), MockType.MOCK, DateUtil.date());

        return OutputResult.buildSucc();

    }
}
