package top.yueshushu.learn.controller;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yueshushu.learn.business.TradeEntrustBusiness;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.enumtype.MockType;
import top.yueshushu.learn.mode.ro.TradeEntrustRo;
import top.yueshushu.learn.response.OutputResult;

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

    @PostMapping("/list")
    @ApiOperation("查询今日委托信息")
    public OutputResult list(@RequestBody TradeEntrustRo tradeEntrustRo){
        tradeEntrustRo.setUserId(getUserId());
        if (tradeEntrustRo.getMockType()==null){
            return OutputResult.buildFail(ResultCode.TRADE_MOCK_TYPE_IS_EMPTY);
        }
        MockType mockType = MockType.getMockType(tradeEntrustRo.getMockType());
        if (mockType == null){
            return OutputResult.buildFail(ResultCode.TRADE_MOCK_TYPE_NOT_EXIST);
        }

        if (MockType.MOCK.equals(mockType)){
            return tradeEntrustBusiness.mockList(tradeEntrustRo);
        }
        return tradeEntrustBusiness.realList(tradeEntrustRo);
    }

    @PostMapping("/history")
    @ApiOperation("查询历史委托信息")
    public OutputResult history(@RequestBody TradeEntrustRo tradeEntrustRo){
        tradeEntrustRo.setUserId(getUserId());
        if (tradeEntrustRo.getMockType()==null){
            return OutputResult.buildFail(ResultCode.TRADE_MOCK_TYPE_IS_EMPTY);
        }
        MockType mockType = MockType.getMockType(tradeEntrustRo.getMockType());
        if (mockType == null){
            return OutputResult.buildFail(ResultCode.TRADE_MOCK_TYPE_NOT_EXIST);
        }

        if (MockType.MOCK.equals(mockType)){
            return tradeEntrustBusiness.mockHistoryList(tradeEntrustRo);
        }
        return tradeEntrustBusiness.realHistoryList(tradeEntrustRo);
    }
}
