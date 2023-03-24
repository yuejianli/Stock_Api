package top.yueshushu.learn.controller;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yueshushu.learn.annotation.AuthToken;
import top.yueshushu.learn.business.TradeEntrustBusiness;
import top.yueshushu.learn.business.TradeUserBusiness;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.enumtype.MockType;
import top.yueshushu.learn.exception.TradeUserException;
import top.yueshushu.learn.mode.ro.TradeEntrustRo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.util.PageUtil;

import javax.annotation.Resource;

/**
 * <p>
 * 委托表 我是自定义的
 * </p>
 *
 * @author 两个蝴蝶飞
 * @date 2022-01-03
 */
@RestController
@RequestMapping("/tradeEntrust")
@ApiModel("委托信息")
public class TradeEntrustController extends BaseController {
    @Resource
    private TradeEntrustBusiness tradeEntrustBusiness;
    @Resource
    private TradeUserBusiness tradeUserBusiness;

    @PostMapping("/list")
    @ApiOperation("查询今日委托信息")
    @AuthToken
    public OutputResult list(@RequestBody TradeEntrustRo tradeEntrustRo) throws TradeUserException {
        tradeEntrustRo.setUserId(getUserId());
        if (tradeEntrustRo.getMockType() == null) {
            return OutputResult.buildFail(ResultCode.TRADE_MOCK_TYPE_IS_EMPTY);
        }
        MockType mockType = MockType.getMockType(tradeEntrustRo.getMockType());
        if (mockType == null) {
            return OutputResult.buildFail(ResultCode.TRADE_MOCK_TYPE_NOT_EXIST);
        }
        if (!tradeUserBusiness.configTradeUser(getUserId(), mockType)) {
            return OutputResult.buildAlert(ResultCode.TRADE_USER_NO_CONFIG);
        }

        if (MockType.MOCK.equals(mockType)) {
            return tradeEntrustBusiness.mockList(tradeEntrustRo);
        }
        return PageUtil.pageResult(tradeEntrustBusiness.realList(tradeEntrustRo).getData(), tradeEntrustRo.getPageNum(), tradeEntrustRo.getPageSize());
    }

    @PostMapping("/history")
    @ApiOperation("查询历史委托信息")
    @AuthToken
    public OutputResult history(@RequestBody TradeEntrustRo tradeEntrustRo) throws TradeUserException {
        tradeEntrustRo.setUserId(getUserId());
        if (tradeEntrustRo.getMockType() == null) {
            return OutputResult.buildFail(ResultCode.TRADE_MOCK_TYPE_IS_EMPTY);
        }
        MockType mockType = MockType.getMockType(tradeEntrustRo.getMockType());
        if (mockType == null) {
            return OutputResult.buildFail(ResultCode.TRADE_MOCK_TYPE_NOT_EXIST);
        }
        if (!tradeUserBusiness.configTradeUser(getUserId(), mockType)) {
            return OutputResult.buildAlert(ResultCode.TRADE_USER_NO_CONFIG);
        }

        if (MockType.MOCK.equals(mockType)) {
            return tradeEntrustBusiness.mockHistoryList(tradeEntrustRo);
        }
        return tradeEntrustBusiness.realHistoryList(tradeEntrustRo);
    }

    @PostMapping("/getInfo")
    @ApiOperation("根据委托单编号查询委托单详细信息")
    @AuthToken
    public OutputResult getInfo(@RequestBody TradeEntrustRo tradeEntrustRo) {
        tradeEntrustRo.setUserId(getUserId());
        if (tradeEntrustRo.getMockType() == null) {
            return OutputResult.buildFail(ResultCode.TRADE_MOCK_TYPE_IS_EMPTY);
        }
        MockType mockType = MockType.getMockType(tradeEntrustRo.getMockType());
        if (mockType == null) {
            return OutputResult.buildFail(ResultCode.TRADE_MOCK_TYPE_NOT_EXIST);
        }
        if (!StringUtils.hasText(tradeEntrustRo.getEntrustCode())) {
            return OutputResult.buildFail(ResultCode.TRADE_ENTRUST_CODE_EMPTY);
        }
        return tradeEntrustBusiness.getInfoByCondition(tradeEntrustRo);
    }
}
