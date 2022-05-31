package top.yueshushu.learn.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.yueshushu.learn.assembler.UserAssembler;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.domainservice.UserDomainService;
import top.yueshushu.learn.entity.User;
import top.yueshushu.learn.mode.ro.UserRo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.UserService;
import top.yueshushu.learn.service.cache.UserCacheService;
import top.yueshushu.learn.util.JwtUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 登录用户表 自定义的
 * </p>
 *
 * @author 岳建立
 * @since 2022-01-02
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Resource
    private UserDomainService userDomainService;
    @Resource
    private UserAssembler userAssembler;
    @Resource
    private UserCacheService userCacheService;
    @Resource
    private JwtUtils jwtUtils;
    /**
     * 用户登录
     * @param userRo
     * @return
     */
    @Override
    public User login(UserRo userRo) {
        // 获取登录的用户
        String account = userRo.getAccount();
        User user = getUserByAccount(account);

        // 创建新的token 值
        Map<String, Object> map = new HashMap<>(2,1.0f);
        map.put("timestamp", System.currentTimeMillis());
        String token = jwtUtils.createJwt(user.getId(), account, map);
        log.info("用户账号 {} 生成的 token is {}", account,token);
        String oldToken = user.getToken();
        user.setToken(token);
        //更新员工的信息
        user.setLastLoginTime(DateUtil.date());

        userDomainService.updateUser(
                userAssembler.entityToDo(user)
        );
        log.info("更新用户 {}表信息成功",account);
        //更新用户的缓存信息

        userCacheService.refreshToken(user,oldToken);
        log.info("更新用户{} 的Token缓存信息成功",account);

       return user;
    }

    @Override
    public OutputResult convertPassWord(String password) {
        if(!StringUtils.hasText(password)){
            return OutputResult.buildAlert(
                    ResultCode.PASSWORD_IS_EMPTY
            );
        }
        MD5 md5 = MD5.create();
        md5.setSalt(Const.SALT.getBytes());
        String encryPs = md5.digestHex16(password.getBytes());
        return OutputResult.buildSucc(encryPs);
    }
    @Override
    public OutputResult tradePassword(String password) {
         byte[] key = Const.TRADE_PASSWORD_AES_KEY.getBytes();
         SymmetricCrypto symmetricCrypto = new SymmetricCrypto(SymmetricAlgorithm.AES,key);
         //加密
         return OutputResult.buildSucc(symmetricCrypto.encryptHex(password));
    }
    @Override
    public OutputResult validateUserRo(UserRo userRo) {

        //进行账号验证
        User user = getUserByAccount(userRo.getAccount());
        if(null == user){
            log.info("账号 {} 不存在",userRo.getAccount());
            return OutputResult.buildAlert(ResultCode.ACCOUNT_NOT_EXIST);
        }
        //进行密码验证
        MD5 md5 = MD5.create();
        md5.setSalt(Const.SALT.getBytes());
        String entryPs = md5.digestHex16(userRo.getPassword().getBytes());
        if(!entryPs.equals(user.getPassword())){
            log.info("账号 {} 所填写的密码与数据库的密码不一致",userRo.getAccount());
            return OutputResult.buildAlert(ResultCode.PASSWORD_INCORRECT);
        }
        return OutputResult.buildSucc();
    }

    @Override
    public User getUserByAccount(String account) {
        return userAssembler.doToEntity(
                userDomainService.getByAccount(account)
        );
    }

    @Override
    public List<Integer> listUserId() {
        return userDomainService.listUserId();
    }
}
