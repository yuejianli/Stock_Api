package top.yueshushu.learn.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yueshushu.learn.annotation.AuthToken;
import top.yueshushu.learn.business.TradeDealBusiness;
import top.yueshushu.learn.business.TradeUserBusiness;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.enumtype.MockType;
import top.yueshushu.learn.mode.ro.TradeDealRo;
import top.yueshushu.learn.mode.vo.TradeDealVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.util.PageUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 成交表 我是自定义的
 * </p>
 *
 * @author 两个蝴蝶飞
 * @date 2022-01-03
 */
@RestController
@RequestMapping("/tradeDeal")
public class TradeDealController extends BaseController {
    @Resource
    private TradeDealBusiness tradeDealBusiness;
    @Resource
    private TradeUserBusiness tradeUserBusiness;

    @PostMapping("/list")
    @ApiOperation("查询今日成交")
    @AuthToken
    public OutputResult list(@RequestBody TradeDealRo tradeDealRo) {
        tradeDealRo.setUserId(getUserId());
        if (tradeDealRo.getMockType() == null) {
            return OutputResult.buildFail(ResultCode.TRADE_MOCK_TYPE_IS_EMPTY);
        }
        MockType mockType = MockType.getMockType(tradeDealRo.getMockType());
        if (mockType == null) {
            return OutputResult.buildFail(ResultCode.TRADE_MOCK_TYPE_NOT_EXIST);
        }
        if (!tradeUserBusiness.configTradeUser(getUserId(), mockType)) {
            return OutputResult.buildAlert(ResultCode.TRADE_USER_NO_CONFIG);
        }
        if (MockType.MOCK.equals(mockType)) {
            return tradeDealBusiness.mockList(tradeDealRo);
        }
        OutputResult<List<TradeDealVo>> listOutputResult = tradeDealBusiness.realList(tradeDealRo);
        return PageUtil.pageResult(listOutputResult.getData(), tradeDealRo.getPageNum(), tradeDealRo.getPageSize());
    }
    @PostMapping("/history")
    @ApiOperation("查询历史成交")
    @AuthToken
    public OutputResult history(@RequestBody TradeDealRo tradeDealRo) {
        tradeDealRo.setUserId(getUserId());
        if (tradeDealRo.getMockType() == null) {
            return OutputResult.buildFail(ResultCode.TRADE_MOCK_TYPE_IS_EMPTY);
        }
        MockType mockType = MockType.getMockType(tradeDealRo.getMockType());
        if (mockType == null) {
            return OutputResult.buildFail(ResultCode.TRADE_MOCK_TYPE_NOT_EXIST);
        }
        if (!tradeUserBusiness.configTradeUser(getUserId(), mockType)) {
            return OutputResult.buildAlert(ResultCode.TRADE_USER_NO_CONFIG);
        }

        if (MockType.MOCK.equals(mockType)) {
            return tradeDealBusiness.mockHistoryList(tradeDealRo);
        }
        return tradeDealBusiness.realHistoryList(tradeDealRo);
    }
}
