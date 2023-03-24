package top.yueshushu.learn.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yueshushu.learn.annotation.AuthToken;
import top.yueshushu.learn.business.TradePositionBusiness;
import top.yueshushu.learn.business.TradeUserBusiness;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.enumtype.MockType;
import top.yueshushu.learn.exception.TradeUserException;
import top.yueshushu.learn.mode.ro.TradePositionRo;
import top.yueshushu.learn.mode.vo.AddPositionVo;
import top.yueshushu.learn.mode.vo.TradePositionShowVo;
import top.yueshushu.learn.mode.vo.TradePositionVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.util.BigDecimalUtil;
import top.yueshushu.learn.util.ThreadLocalUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 我的持仓表 我是自定义的
 * </p>
 *
 * @author 两个蝴蝶飞
 * @date 2022-01-03
 */
@RestController
@RequestMapping("/tradePosition")
@Api("持仓信息")
public class TradePositionController extends BaseController {
    @Resource
    private TradePositionBusiness tradePositionBusiness;
    @Resource
    private TradeUserBusiness tradeUserBusiness;

    @PostMapping("/list")
    @ApiOperation("查询当前我的持仓")
    @AuthToken
    public OutputResult list(@RequestBody TradePositionRo tradePositionRo) throws TradeUserException {
        tradePositionRo.setUserId(getUserId());
        if (tradePositionRo.getMockType() == null) {
            return OutputResult.buildFail(ResultCode.TRADE_MOCK_TYPE_IS_EMPTY);
        }
        MockType mockType = MockType.getMockType(tradePositionRo.getMockType());
        if (mockType == null) {
            return OutputResult.buildFail(ResultCode.TRADE_MOCK_TYPE_NOT_EXIST);
        }
        if (!tradeUserBusiness.configTradeUser(getUserId(), mockType)) {
            return OutputResult.buildAlert(ResultCode.TRADE_USER_NO_CONFIG);
        }

        List<TradePositionVo> data;
        if (MockType.MOCK.equals(mockType)) {
            data = (List<TradePositionVo>) tradePositionBusiness.mockList(tradePositionRo).getData();
        } else {
            data = (List<TradePositionVo>) tradePositionBusiness.realList(tradePositionRo).getData();
        }

        TradePositionShowVo tradePositionShowVo = new TradePositionShowVo();
        tradePositionShowVo.setDataList(data);
        BigDecimal todayMoneySum = new BigDecimal(0);
        if (!CollectionUtils.isEmpty(data)) {
            for (TradePositionVo tradePositionVo : data) {
                todayMoneySum = BigDecimalUtil.addBigDecimal(
                        todayMoneySum,
                        Optional.ofNullable(tradePositionVo.getTodayMoney()).orElse(BigDecimal.ZERO)
                );
            }
        }
        tradePositionShowVo.setTodayMoney(todayMoneySum);
        tradePositionShowVo.setTotal(data.size());
        return OutputResult.buildSucc(tradePositionShowVo);
    }

    @PostMapping("/addPosition")
    @ApiOperation("保存当前的持仓信息")
    @AuthToken
    public OutputResult addPosition(@RequestBody AddPositionVo addPositionVo) {
        return tradePositionBusiness.addPosition(addPositionVo, ThreadLocalUtils.getUser());
    }
}
