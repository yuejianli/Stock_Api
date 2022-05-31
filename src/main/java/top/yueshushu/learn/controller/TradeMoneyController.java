package top.yueshushu.learn.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yueshushu.learn.business.TradeMoneyBusiness;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.enumtype.MockType;
import top.yueshushu.learn.mode.ro.TradeMoneyRo;
import top.yueshushu.learn.response.OutputResult;

import javax.annotation.Resource;

/**
 * <p>
 * 资金表 我是自定义的
 * </p>
 *
 * @author 两个蝴蝶飞
 * @date 2022-01-03
 */
@RestController
@RequestMapping("/tradeMoney")
@Api("交易资产信息")
public class TradeMoneyController extends BaseController{
    @Resource
    private TradeMoneyBusiness tradeMoneyBusiness;

    @PostMapping("/info")
    @ApiOperation("查询资金信息")
    public OutputResult list(@RequestBody TradeMoneyRo tradeMoneyRo){
        tradeMoneyRo.setUserId(getUserId());
        if (tradeMoneyRo.getMockType()==null){
            return OutputResult.buildFail(ResultCode.TRADE_MOCK_TYPE_IS_EMPTY);
        }
        MockType mockType = MockType.getMockType(tradeMoneyRo.getMockType());
        if (mockType == null){
            return OutputResult.buildFail(ResultCode.TRADE_MOCK_TYPE_NOT_EXIST);
        }
        if (MockType.MOCK.equals(mockType)){
            return tradeMoneyBusiness.mockInfo(tradeMoneyRo);
        }
        return tradeMoneyBusiness.realInfo(tradeMoneyRo);
    }
}
