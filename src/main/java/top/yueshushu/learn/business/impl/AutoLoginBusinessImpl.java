package top.yueshushu.learn.business.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.yueshushu.learn.business.AutoLoginBusiness;
import top.yueshushu.learn.business.TradeMethodBusiness;
import top.yueshushu.learn.business.TradeUserBusiness;
import top.yueshushu.learn.entity.User;
import top.yueshushu.learn.enumtype.TradeRealValueType;
import top.yueshushu.learn.helper.DateHelper;
import top.yueshushu.learn.message.dingtalk.DingTalkService;
import top.yueshushu.learn.message.weixin.service.WeChatService;
import top.yueshushu.learn.mode.ro.TradeMoneyRo;
import top.yueshushu.learn.mode.ro.TradeUserRo;
import top.yueshushu.learn.mode.vo.TradeMoneyVo;
import top.yueshushu.learn.ocr.OcrService;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.TradeMoneyService;
import top.yueshushu.learn.service.UserService;
import top.yueshushu.learn.service.cache.TradeCacheService;
import top.yueshushu.learn.util.RedisUtil;
import top.yueshushu.learn.util.ThreadLocalUtils;

import javax.annotation.Resource;
import java.text.MessageFormat;

/**
 * 自动登录
 *
 * @author yuejianli
 * @date 2023-01-10
 */
@Service
@Slf4j
public class AutoLoginBusinessImpl implements AutoLoginBusiness {
    @Resource
    private UserService userService;
    @Resource
    private TradeMoneyService tradeMoneyService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private WeChatService weChatService;
    @Resource
    private DingTalkService dingTalkService;
    @Resource
    private TradeUserBusiness tradeUserBusiness;
    @Resource
    private TradeMethodBusiness tradeMethodBusiness;
    @Resource
    private TradeCacheService tradeCacheService;
    @Resource
    private DateHelper dateHelper;

    @Value("${ocr.service:chaojiyingOcrService}")
    private String ocrServiceName;

    @Override
    public boolean autoLogin(Integer userId) {
        if (!dateHelper.isTradeTime(DateUtil.date())) {
            return false;
        }
        // 先调用一下查询资金的方法，如果查询不出来，则说明过期了。
        tradeCacheService.removeRealEasyMoneyCache(TradeRealValueType.TRADE_MONEY, userId);

        TradeMoneyRo tradeMoneyRo = new TradeMoneyRo();
        tradeMoneyRo.setUserId(userId);
        OutputResult<TradeMoneyVo> tradeMoneyVoOutputResult = tradeMoneyService.realInfo(tradeMoneyRo);
        if (tradeMoneyVoOutputResult.getSuccess()) {
            return true;
        }

        // 不成功，才进行自动登录操作。
        String base64Url = tradeMethodBusiness.yzm(userId).getData();
        if (!StringUtils.hasText(base64Url)) {
            return false;
        }
        OcrService ocrService = SpringUtil.getBean(ocrServiceName, OcrService.class);
        String identifyCode = ocrService.processBase64(base64Url);
        TradeUserRo tradeUserRo = new TradeUserRo();
        tradeUserRo.setId(ThreadLocalUtils.getUserId());
        String randNum = redisUtil.get(userId + "_RAND_NUM");
        tradeUserRo.setRandNum(randNum);
        tradeUserRo.setIdentifyCode(identifyCode);
        boolean flag = tradeUserBusiness.login(tradeUserRo).getSuccess();

        // 运行失败的话，才发消息。
        if (!flag) {
            User user = userService.getById(userId);
            String message = MessageFormat.format(
                    "用户 :{0} 执行自动登录, 运行 {1}",
                    user.getName(), flag == true ? "成功" : "失败"
            );
            weChatService.sendTextMessage(user.getId(), message);
            dingTalkService.sendTextMessage(user.getId(), message);
        }
        return flag;
    }
}
