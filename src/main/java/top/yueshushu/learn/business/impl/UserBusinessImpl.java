package top.yueshushu.learn.business.impl;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.assembler.UserAssembler;
import top.yueshushu.learn.business.UserBusiness;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.domainservice.UserDomainService;
import top.yueshushu.learn.entity.TradeMoney;
import top.yueshushu.learn.entity.User;
import top.yueshushu.learn.enumtype.DataFlagType;
import top.yueshushu.learn.enumtype.MockType;
import top.yueshushu.learn.mode.ro.QueryUserRo;
import top.yueshushu.learn.mode.ro.UserRo;
import top.yueshushu.learn.mode.vo.AdminOperateUserRequestVo;
import top.yueshushu.learn.mode.vo.MenuVo;
import top.yueshushu.learn.mode.vo.UserVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;
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
    private MenuService menuService;
    @Resource
    private UserAssembler userAssembler;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private UserDomainService userDomainService;

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

        // 对一些重要的数据,进行隐藏
        user.setDingUserId(null);
        user.setWxUserId(null);
        user.setRebootId(null);
        user.setRoleName(null);
        user.setRoleId(null);

        userVo.setCurrentUser(user);
        userVo.setMenuList(menuVoList);

        return OutputResult.buildSucc(userVo);
    }

    @Override
    public OutputResult convertPassWord(String password) {
        return userService.convertPassWord(password);
    }

    @Override
    public OutputResult tradeUserText(String text) {
        return userService.tradeUserText(text);
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
    public OutputResult addUser(AdminOperateUserRequestVo adminOperateUserRequestVo, User currentUser) {
        if (!isSuperUser(currentUser)) {
            return OutputResult.buildFail(ResultCode.NO_AUTH);
        }
        // 用户账号不能相同
        User dbUser = userService.getUserByAccount(adminOperateUserRequestVo.getAccount());
        if (dbUser != null) {
            return OutputResult.buildFail(ResultCode.USER_EXISTS);
        }

        // 添加用户操作
        User user = userAssembler.addUserToEntity(adminOperateUserRequestVo);

        User addUser = userService.operateUser(user);

        // 对角色进行处理。
        if (adminOperateUserRequestVo.getRoleId() != null) {
            userRoleService.configRole(addUser.getId(), adminOperateUserRequestVo.getRoleId());
        }

        // 补充一下 TradeUser 的操作信息.

        tradeUserService.operateTradeUser(null, addUser.getId());

        // 往金额里面放置数据。

        TradeMoney tradeMoney = new TradeMoney();
        tradeMoney.setTotalMoney(adminOperateUserRequestVo.getTotalMoney());
        tradeMoney.setUserId(addUser.getId());
        tradeMoney.setMockType(MockType.MOCK.getCode());
        tradeMoneyService.operateMoney(tradeMoney);

        // 保存历史金额
        tradeMoneyService.saveMoneyHistory(addUser.getId(), MockType.MOCK, DateUtil.date());

        return OutputResult.buildSucc();

    }

    @Override
    public OutputResult updateUser(AdminOperateUserRequestVo adminOperateUserRequestVo, User currentUser) {
        if (!isSuperUser(currentUser) && !currentUser.getAccount().equals(adminOperateUserRequestVo.getAccount())) {
            return OutputResult.buildFail(ResultCode.NO_AUTH);
        }
        // 添加用户操作
        User user = userAssembler.addUserToEntity(adminOperateUserRequestVo);
        userService.operateUser(user);

        // 对角色进行处理。
        User roleUser = userService.getById(adminOperateUserRequestVo.getId());
        if (adminOperateUserRequestVo.getRoleId() != null && !adminOperateUserRequestVo.getRoleId().equals(roleUser.getRoleId())) {
            userRoleService.configRole(adminOperateUserRequestVo.getId(), adminOperateUserRequestVo.getRoleId());
        }

        return OutputResult.buildSucc();
    }

    @Override
    public OutputResult deleteUser(Integer id, User currentUser) {
        if (!isSuperUser(currentUser)) {
            return OutputResult.buildFail(ResultCode.NO_AUTH);
        }
        User changeUser = userService.getById(id);
        if (null == changeUser) {
            return OutputResult.buildAlert(ResultCode.INVALID_PARAM);
        }
        userDomainService.removeById(id);
        return OutputResult.buildSucc();
    }

    @Override
    public boolean isSuperUser(User user) {
        if (user == null) {
            return false;
        }
        // 添加用户前验证。
        return 1 == user.getId();
    }

    @Override
    public OutputResult list(QueryUserRo queryUserRo, User user) {
        if (!isSuperUser(user)) {
            return OutputResult.buildFail(ResultCode.NO_AUTH);
        }
        // 进行查询
        OutputResult<PageResponse<User>> pageResponseOutputResult = userService.pageList(queryUserRo);
        pageResponseOutputResult.getData().getList().forEach(
                n -> {
                    n.setPassword("");
                }
        );
        return pageResponseOutputResult;
    }

    @Override
    public OutputResult changeStatus(Integer id, DataFlagType statusType, User user) {
        if (!isSuperUser(user)) {
            return OutputResult.buildFail(ResultCode.NO_AUTH);
        }
        User changeUser = userService.getById(id);
        if (null == changeUser) {
            return OutputResult.buildAlert(ResultCode.INVALID_PARAM);
        }
        User editUser = new User();
        editUser.setAccount(changeUser.getAccount());
        editUser.setStatus(statusType.getCode());
        userService.operateUser(editUser);
        return OutputResult.buildSucc();
    }

    @Override
    public OutputResult getInfo(String account, User user) {
        if (!user.getAccount().equals(account)) {
            return OutputResult.buildAlert(ResultCode.NO_AUTH);
        }
        return OutputResult.buildSucc(userService.getUserByAccount(account));
    }
}
