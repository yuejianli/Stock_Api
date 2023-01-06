package top.yueshushu.learn.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanMap;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.yueshushu.learn.api.TradeResultVo;
import top.yueshushu.learn.api.request.AuthenticationRequest;
import top.yueshushu.learn.api.request.BaseTradeRequest;
import top.yueshushu.learn.api.response.AuthenticationResponse;
import top.yueshushu.learn.api.responseparse.DataListResponseParser;
import top.yueshushu.learn.assembler.TradeMethodAssembler;
import top.yueshushu.learn.assembler.TradeUserAssembler;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.config.TradeClient;
import top.yueshushu.learn.domain.TradeMethodDo;
import top.yueshushu.learn.domain.TradeUserDo;
import top.yueshushu.learn.domainservice.TradeMethodDomainService;
import top.yueshushu.learn.domainservice.TradeUserDomainService;
import top.yueshushu.learn.entity.TradeMethod;
import top.yueshushu.learn.entity.TradeUser;
import top.yueshushu.learn.enumtype.DataFlagType;
import top.yueshushu.learn.enumtype.TradeMethodType;
import top.yueshushu.learn.mode.ro.TradeUserRo;
import top.yueshushu.learn.mode.vo.TradeUserVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.TradeUserService;
import top.yueshushu.learn.util.RSAUtil;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
    private TradeMethodAssembler tradeMethodAssembler;
    @Resource
    private TradeMethodDomainService tradeMethodDomainService;

    @Resource
    private TradeClient tradeClient;
    @Resource
    private DataListResponseParser dataListResponseParser;

    //    @Value(value = "${emSecSecurityServerUrl}")
    private String emSecSecurityServerUrl;

    @Override
    public OutputResult login(TradeUserRo tradeUserRo) {
        //根据id 去查询对应的交易账户
        TradeUser tradeUser = tradeUserAssembler.doToEntity(
                tradeUserDomainService.getByUserId(tradeUserRo.getId())
        );
        if (null == tradeUser) {
            return OutputResult.buildFail(ResultCode.TRADE_USER_NO_RELATION);
        }

        //关联的用户
        AuthenticationRequest request = new AuthenticationRequest(tradeUser.getUserId());
        request.setPassword(encodePassword(tradeUser.getPassword()));
        request.setIdentifyCode(tradeUserRo.getIdentifyCode());
        request.setRandNumber(tradeUserRo.getRandNum());
        setRequestSecInfo(request);
        Map<String, String> header = getHeader(request);
        if (!StringUtils.hasLength(request.getSecInfo())) {
            log.info("authentication use mac User-Agent");
            header.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/536.66");
        }

        //获取登录验证的方法
        TradeMethod tradeMethod = tradeMethodAssembler.doToEntity(
                tradeMethodDomainService.getMethodByCode(request.getMethod())
        );
        Map<String, Object> params = getParams(request);
        params.put("userId", tradeUser.getAccount());
        TradeResultVo<AuthenticationResponse> resultVo = null;
        log.info("系统用户{}登录时成功构建了登录请求信息", tradeUser.getUserId());
        try {
            tradeClient.openSession();
            String content = tradeClient.sendNewInstance(tradeMethod.getUrl(), params, header);
            resultVo = dataListResponseParser.parse(content, new TypeReference<AuthenticationResponse>() {
            });
            if (resultVo.getSuccess()) {
                log.info("系统用户{}登录请求成功", tradeUser.getUserId());
                TradeMethodDo authCheckTradeMethodDo =
                        tradeMethodDomainService.getMethodByCode(TradeMethodType.AuthenticationCheckRequest.getCode());

                String content2 = tradeClient.sendNewInstance(authCheckTradeMethodDo.getUrl(), new HashMap<>(2), header);
                String validateKey = getValidateKey(content2);

                AuthenticationResponse response = new AuthenticationResponse();
                response.setCookie(tradeClient.getCurrentCookie());
                response.setValidateKey(validateKey);
                resultVo.setData(Arrays.asList(response));
                log.info("系统用户{}响应数据成功",tradeUser.getUserId());
            }else{
                log.error("系统用户{}登录请求失败",tradeUser.getUserId(),resultVo.getMessage());
                return OutputResult.buildFail(ResultCode.EASY_MONEY_LOGIN_ERROR);
            }
        } catch (Exception e){
            log.error("系统用户{}登录请求失败",tradeUser.getUserId(),e);
            return OutputResult.buildFail();
        }finally {
           tradeClient.destoryCurrentSession();
        }
        TradeUserVo tradeUserVo = new TradeUserVo();
        if (!resultVo.getSuccess()) {
            log.info("系统用户{}未成功登录",tradeUser.getUserId());
            return OutputResult.buildFail();
        }
        AuthenticationResponse response = resultVo.getData().get(0);
        tradeUser.setCookie(response.getCookie());
        tradeUser.setValidateKey(response.getValidateKey());
        tradeUser.setUpdateTime(LocalDateTime.now());
        tradeUserDomainService.updateById(
                tradeUserAssembler.entityToDo(
                        tradeUser
                )
        );
        log.info("系统用户{}更新cookie和validateKey成功", tradeUser.getUserId());
        tradeUserVo.setUserId(tradeUserRo.getId());
        return OutputResult.buildSucc(tradeUserVo);
    }

    private void setRequestSecInfo(AuthenticationRequest request) {
        if (emSecSecurityServerUrl == null) {
            return;
        }

        try {
            tradeClient.openSession();
            String content = tradeClient.sendNewInstance(emSecSecurityServerUrl + request.getIdentifyCode(), null, null);
            @SuppressWarnings("unchecked")
            Map<String, String> map = JSON.parseObject(content, Map.class);
            String userInfo = map.get("userInfo");
            request.setSecInfo(userInfo);
        } catch (Exception e) {
            log.info("not code get from EM SecSecurity Server");
        } finally {
            tradeClient.destoryCurrentSession();
        }
    }

    private Map<String, String> getHeader(BaseTradeRequest request) {
        //根据id 去查询对应的交易账户
        TradeUser tradeUser = tradeUserAssembler.doToEntity(
                tradeUserDomainService.getByUserId(request.getUserId())
        );
        HashMap<String, String> header = new HashMap<>();
        if (!(request instanceof AuthenticationRequest)) {
            header.put("cookie", tradeUser.getCookie());
        }
        header.put("gw_reqtimestamp", System.currentTimeMillis() + "");
        header.put("X-Requested-With", "XMLHttpRequest");
        return header;
    }

    @Override
    public void operateTradeUser(TradeUser tradeUser, Integer userId) {
        // 先查询一下，是否存在.

        TradeUserDo tradeUserDo = tradeUserDomainService.getByUserId(userId);

        if (null == tradeUserDo) {
            // 进行插入
            tradeUserDo = new TradeUserDo();
            tradeUserDo.setAccount("证券交易系统账号");
            tradeUserDo.setPassword("证券交易系统密码,已加密");
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

    /**
     * 密码转换
     *
     * @param password 数据库密码
     * @return 东方财富加密前的密码
     */
    private String encodePassword(String password) {
        if (password.length() != 6) {
            return password;
        }
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDHdsyxT66pDG4p73yope7jxA92\n" +
                "c0AT4qIJ/xtbBcHkFPK77upnsfDTJiVEuQDH+MiMeb+XhCLNKZGp0yaUU6GlxZdp\n" +
                "+nLW8b7Kmijr3iepaDhcbVTsYBWchaWUXauj9Lrhz58/6AE/NF0aMolxIGpsi+ST\n" +
                "2hSHPu3GSXMdhPCkWQIDAQAB";
        return RSAUtil.encodeWithPublicKey(password, publicKey);
    }

    /**
     * 组装参数
     * @param request
     * @return
     */
    private Map<String, Object> getParams(Object request) {
        Map<Object, Object> beanMap = new BeanMap(request);
        HashMap<String, Object> params = new HashMap<>();
        beanMap.entrySet().stream().filter(entry -> !TradeApiServiceImpl.IgnoreList.contains(String.valueOf(entry.getKey())))
                .forEach(entry -> params.put(String.valueOf(entry.getKey()), entry.getValue()));
        return params;
    }

    /**
     * 获取验证信息
     * @param content
     * @return
     */
    private String getValidateKey(String content) {
        String key = "input id=\"em_validatekey\" type=\"hidden\" value=\"";
        int inputBegin = content.indexOf(key) + key.length();
        int inputEnd = content.indexOf("\" />", inputBegin);
        String validateKey = content.substring(inputBegin, inputEnd);
        return validateKey;
    }
}
