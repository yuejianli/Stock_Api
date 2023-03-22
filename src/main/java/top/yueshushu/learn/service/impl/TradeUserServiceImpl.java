package top.yueshushu.learn.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.yueshushu.learn.api.response.AuthenticationResponse;
import top.yueshushu.learn.assembler.TradeUserAssembler;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.domain.TradeUserDo;
import top.yueshushu.learn.domainservice.TradeUserDomainService;
import top.yueshushu.learn.entity.TradeUser;
import top.yueshushu.learn.enumtype.DataFlagType;
import top.yueshushu.learn.helper.TradeRequestHelper;
import top.yueshushu.learn.init.InitDataMethods;
import top.yueshushu.learn.mode.ro.TradeUserRo;
import top.yueshushu.learn.mode.vo.TradeUserVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.TradeUserService;
import top.yueshushu.learn.util.RSAUtil;
import top.yueshushu.learn.util.RedisUtil;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 * 交易用户信息 自定义的
 * </p>
 *
 * @author 岳建立
 * @since 2022-01-02
 */
@Service
@Slf4j
public class TradeUserServiceImpl implements TradeUserService {
    @Resource
    private TradeUserDomainService tradeUserDomainService;
    @Resource
    private TradeUserAssembler tradeUserAssembler;

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private TradeRequestHelper tradeRequestHelper;
    @Resource
    private InitDataMethods initDataMethods;

    @Override
    public OutputResult login(TradeUserRo tradeUserRo) {
        //根据id 去查询对应的交易账户
        TradeUser tradeUser = tradeUserAssembler.doToEntity(
                tradeUserDomainService.getByUserId(tradeUserRo.getId())
        );
        if (null == tradeUser) {
            return OutputResult.buildFail(ResultCode.TRADE_USER_NO_RELATION);
        }
        if (!StringUtils.hasText(tradeUser.getAccount())) {
            return OutputResult.buildFail(ResultCode.TRADE_USER_NO_CONFIG);
        }
        //关联的用户
        String oldPassword = tradeUser.getPassword();
        String oldAccount = tradeUser.getAccount();
        tradeUser.setPassword(encodePassword(oldPassword));
        tradeUser.setAccount(encodeAccount(oldAccount));
        tradeUser.setRandNum(tradeUserRo.getRandNum());
        tradeUser.setIdentifyCode(tradeUserRo.getIdentifyCode());
        // 对数据进行验证
        if (!StringUtils.hasText(tradeUser.getPassword()) || !StringUtils.hasText(tradeUser.getAccount())) {
            log.error("系统用户{} 构建用户名或者密码信息失败", tradeUser.getUserId());
            return OutputResult.buildFail(ResultCode.PASSWORD_INCORRECT);
        }
        AuthenticationResponse response = tradeRequestHelper.login(tradeUser);
        if (null == response) {
            log.error("系统用户{}登录请求失败", tradeUser.getUserId());
            return OutputResult.buildFail(ResultCode.EASY_MONEY_LOGIN_ERROR);
        }
        tradeUser.setCookie(response.getCookie());
        tradeUser.setValidateKey(response.getValidateKey());
        tradeUser.setUpdateTime(LocalDateTime.now());
        //将密码还原
        tradeUser.setPassword(oldPassword);
        tradeUser.setAccount(oldAccount);
        tradeUserDomainService.updateById(
                tradeUserAssembler.entityToDo(
                        tradeUser
                )
        );
        log.info("系统用户{}更新cookie和validateKey成功", tradeUser.getUserId());
        TradeUserVo tradeUserVo = new TradeUserVo();
        tradeUserVo.setUserId(tradeUserRo.getId());
        return OutputResult.buildSucc(tradeUserVo);
    }
    @Override
    public void operateTradeUser(TradeUser tradeUser, Integer userId) {
        // 先查询一下，是否存在.

        TradeUserDo tradeUserDo = tradeUserDomainService.getByUserId(userId);
        if (null == tradeUserDo) {
            // 进行插入
            tradeUserDo = new TradeUserDo();
            tradeUserDo.setAccount("");
            tradeUserDo.setPassword("");
            tradeUserDo.setUserId(userId);
            tradeUserDo.setCreateTime(LocalDateTime.now());
            tradeUserDo.setUpdateTime(LocalDateTime.now());
            tradeUserDo.setFlag(DataFlagType.NORMAL.getCode());
            // 进行保存
            tradeUserDomainService.save(tradeUserDo);
        } else {
            // 存在的话，进行更新。

            TradeUserDo editTradeUserDo = tradeUserAssembler.entityToDo(tradeUser);
            editTradeUserDo.setId(tradeUserDo.getId());
            // 进行更新
            tradeUserDomainService.updateById(editTradeUserDo);
        }

    }

    @Override
    public void editInfo(TradeUserRo tradeUserRo) {
        TradeUserDo tradeUserDo = tradeUserDomainService.getByUserId(tradeUserRo.getId());
        if (null == tradeUserDo) {
            return;
        }
        TradeUserDo editTradeUserDo = new TradeUserDo();
        editTradeUserDo.setId(tradeUserDo.getId());
        editTradeUserDo.setAccount(tradeUserRo.getAccount());
        editTradeUserDo.setPassword(tradeUserRo.getPassword());
        // 进行更新
        tradeUserDomainService.updateById(editTradeUserDo);
    }

    @Override
    public boolean configTradeUser(Integer userId) {
        //根据id 去查询对应的交易账户
        TradeUser tradeUser = tradeUserAssembler.doToEntity(tradeUserDomainService.getByUserId(userId));
        if (null == tradeUser) {
            return false;
        }
        if (!StringUtils.hasText(tradeUser.getAccount())) {
            return false;
        }
        return true;
    }

    /**
     * 密码转换
     *
     * @param password 数据库密码
     * @return 东方财富加密前的密码
     */
    private String encodePassword(String password) {
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDHdsyxT66pDG4p73yope7jxA92\n" +
                "c0AT4qIJ/xtbBcHkFPK77upnsfDTJiVEuQDH+MiMeb+XhCLNKZGp0yaUU6GlxZdp\n" +
                "+nLW8b7Kmijr3iepaDhcbVTsYBWchaWUXauj9Lrhz58/6AE/NF0aMolxIGpsi+ST\n" +
                "2hSHPu3GSXMdhPCkWQIDAQAB";
        try {
            // 将密码进行解密
            String redisPrivateKey = redisUtil.get(Const.RSA_PRIVATE_KEY);
            if (!StringUtils.hasText(redisPrivateKey)) {
                initDataMethods.initRsaKey();
                redisPrivateKey = redisUtil.get(Const.RSA_PRIVATE_KEY);
            }
            // 进行替换
            redisPrivateKey = redisPrivateKey.replaceFirst("X\\+", "X");

            String originPassword = RSAUtil.decryptByPrivateKey(redisPrivateKey, password);
            return RSAUtil.encryptByPublicKey(publicKey, originPassword);
        } catch (Exception e) {
            log.error("公钥加密密码错误", e);
            return password;
        }
    }

    /**
     * 账户转换
     *
     * @param account 数据库的账号
     * @return 东方财富加密前的账号
     */
    private String encodeAccount(String account) {
        try {
            // 将密码进行解密
            String redisPrivateKey = redisUtil.get(Const.RSA_PRIVATE_KEY);
            if (!StringUtils.hasText(redisPrivateKey)) {
                initDataMethods.initRsaKey();
                redisPrivateKey = redisUtil.get(Const.RSA_PRIVATE_KEY);
            }
            // 进行替换
            redisPrivateKey = redisPrivateKey.replaceFirst("X\\+", "X");
            return RSAUtil.decryptByPrivateKey(redisPrivateKey, account);
        } catch (Exception e) {
            log.error("私钥解密账号错误", e);
            return account;
        }
    }
}
