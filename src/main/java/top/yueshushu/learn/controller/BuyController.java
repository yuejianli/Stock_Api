package top.yueshushu.learn.controller;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yueshushu.learn.business.BuyBusiness;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.mode.ro.BuyRo;
import top.yueshushu.learn.response.OutputResult;

import javax.annotation.Resource;

/**
 * <p>
 * 买入委托
 * </p>
 *
 * @author 两个蝴蝶飞
 * @date 2022-01-03
 */
@RestController
@RequestMapping("/buy")
@ApiModel("买入股票处理")
public class BuyController extends BaseController {
    @Resource
    private BuyBusiness buyBusiness;
    @PostMapping("/buy")
    @ApiOperation("买入股票信息")
    public OutputResult buy(@RequestBody BuyRo buyRo){
        buyRo.setUserId(getUserId());
        if(!StringUtils.hasText(buyRo.getCode())){
            return OutputResult.buildAlert(ResultCode.STOCK_CODE_IS_EMPTY);
        }
        if(buyRo.getAmount() ==null){
            return OutputResult.buildAlert(ResultCode.TOOL_NUMBER_IS_EMPTY);
        }
        if (buyRo.getAmount()%100 !=0){
            return OutputResult.buildAlert(
                    ResultCode.TOOL_NUMBER_IS_HUNDREDS
            );
        }
        if(buyRo.getPrice() ==null){
            return OutputResult.buildAlert(ResultCode.TOOL_PRICE_IS_EMPTY);
        }
        return buyBusiness.buy(buyRo);
    }


}
