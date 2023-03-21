package top.yueshushu.learn.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.yueshushu.learn.annotation.AuthToken;
import top.yueshushu.learn.business.UserBusiness;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.enumtype.DataFlagType;
import top.yueshushu.learn.mode.ro.QueryUserRo;
import top.yueshushu.learn.mode.ro.UserRo;
import top.yueshushu.learn.mode.vo.AdminOperateUserRequestVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.util.RedisUtil;
import top.yueshushu.learn.util.SelectConditionUtil;
import top.yueshushu.learn.util.ThreadLocalUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 登录用户表 我是自定义的
 * </p>
 *
 * @author 岳建立
 * @date 2022-01-02
 */
@RestController
@RequestMapping("/user")
@Api("用户信息")
public class UserController extends BaseController {
    @Resource
    private UserBusiness userBusiness;
    @Resource
    private RedisUtil redisUtil;

    @PostMapping("/login")
    @ApiOperation("用户登录信息")
    public OutputResult login(@RequestBody UserRo userRo) {
        // 对数据进行 check
        if (!StringUtils.hasText(userRo.getAccount())) {
            return OutputResult.buildAlert(ResultCode.ACCOUNT_IS_EMPTY);
        }
        if (!StringUtils.hasText(userRo.getPassword())) {
            return OutputResult.buildAlert(ResultCode.PASSWORD_IS_EMPTY);
        }

        if (SelectConditionUtil.intIsNullOrZero(userRo.getReadAgreement())) {
            return OutputResult.buildAlert(ResultCode.READ_AGREEMENT_TRUE);
        }
        return userBusiness.login(userRo);
    }

    @GetMapping("/convertPassWord")
    @ApiOperation("转换登录用户的密码")
    @AuthToken
    public OutputResult convertPassWord(String password) {
        return userBusiness.convertPassWord(password);
    }

    @GetMapping("/tradeUserText")
    @ApiOperation("转换交易用户的信息")
    @AuthToken
    public OutputResult tradeUserText(String text) {
        return userBusiness.tradeUserText(text);
    }

    @GetMapping("/encrypt")
    @ApiOperation("配置文件敏感信息加密")
    @AuthToken
    public OutputResult encrypt(String text) {
        return userBusiness.encrypt(text);
    }

    @GetMapping("/decrypt")
    @ApiOperation("配置文件敏感信息解密")
    @AuthToken
    public OutputResult decrypt(String text) {
        return userBusiness.decrypt(text);
    }

    @GetMapping("/{userId}")
    @ApiOperation("切换用户信息")
    @AuthToken
    public OutputResult setUserId(@PathVariable("userId") Integer userId) {
        redisUtil.set(Const.KEY_AUTH_USER_ID, userId);
        return OutputResult.buildSucc("切换用户成功");
    }

    @PostMapping("/addUser")
    @ApiOperation("添加用户信息")
    @AuthToken
    public OutputResult addUser(@RequestBody AdminOperateUserRequestVo adminOperateUserRequestVo) {
        return userBusiness.addUser(adminOperateUserRequestVo, ThreadLocalUtils.getUser());
    }

    @PostMapping("/updateUser")
    @ApiOperation("更新用户信息")
    @AuthToken
    public OutputResult updateUser(@RequestBody AdminOperateUserRequestVo adminOperateUserRequestVo) {
        return userBusiness.updateUser(adminOperateUserRequestVo, ThreadLocalUtils.getUser());
    }

    @PostMapping("/deleteUser")
    @ApiOperation("删除用户")
    @AuthToken
    public OutputResult deleteUser(@RequestBody AdminOperateUserRequestVo adminOperateUserRequestVo) {
        return userBusiness.deleteUser(adminOperateUserRequestVo.getId(), ThreadLocalUtils.getUser());
    }

    @PostMapping("/enable")
    @ApiOperation("启用用户")
    @AuthToken
    public OutputResult enable(@RequestBody AdminOperateUserRequestVo adminOperateUserRequestVo) {
        return userBusiness.changeStatus(adminOperateUserRequestVo.getId(), DataFlagType.NORMAL, ThreadLocalUtils.getUser());
    }

    @PostMapping("/disable")
    @ApiOperation("禁用用户")
    @AuthToken
    public OutputResult disable(@RequestBody AdminOperateUserRequestVo adminOperateUserRequestVo) {
        return userBusiness.changeStatus(adminOperateUserRequestVo.getId(), DataFlagType.DELETE, ThreadLocalUtils.getUser());
    }

    @PostMapping("/list")
    @ApiOperation("查询用户列表")
    @AuthToken
    public OutputResult list(@RequestBody QueryUserRo queryUserRo) {
        return userBusiness.list(queryUserRo, ThreadLocalUtils.getUser());
    }

    @PostMapping("/getInfo")
    @ApiOperation("获取当前用户信息")
    @AuthToken
    public OutputResult getInfo(@RequestBody QueryUserRo queryUserRo) {
        return userBusiness.getInfo(queryUserRo.getAccount(), ThreadLocalUtils.getUser());
    }


}
