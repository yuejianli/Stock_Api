package top.yueshushu.learn.controller;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import top.yueshushu.learn.business.MoneyBusiness;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.mode.ro.MoneyRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * @ClassName:MoneyController
 * @Description 小工具--清仓计算器
 * @Author 岳建立
 * @Date 2021/11/6 10:38
 * @Version 1.0
 **/
@RestController
@RequestMapping("/money")
@Api("股票金额计算小工具")
public class MoneyController {
    @Resource
    private MoneyBusiness moneyBusiness;
    @PostMapping("/clearMoney")
    @ApiOperation("股票清仓")
    public OutputResult clearMoney(@RequestBody MoneyRo moneyRo){
        //进行相关参数的验证
        OutputResult validateParamResult = validateClearParam(moneyRo);
        if (!validateParamResult.getSuccess()){
            return validateParamResult;
        }
        return moneyBusiness.clearMoney(moneyRo);
    }

    @ApiOperation("股票补仓")
    @PostMapping("/coverMoney")
    public OutputResult coverMoney(@RequestBody MoneyRo moneyRo){
        //进行相关参数的验证
        OutputResult validateParamResult = validateCovertParam(moneyRo);
        if (!validateParamResult.getSuccess()){
            return validateParamResult;
        }
        return moneyBusiness.coverMoney(moneyRo);
    }

    @ApiOperation("股票减仓")
    @PostMapping("/reduceMoney")
    public OutputResult reduceMoney(@RequestBody MoneyRo moneyRo){
        //进行相关参数的验证
        OutputResult validateParamResult = validateReduceParam(moneyRo);
        if (!validateParamResult.getSuccess()){
            return validateParamResult;
        }
        return moneyBusiness.reduceMoney(moneyRo);
    }

    /**
     * 股票工具,减仓时验证传入的参数
     * @param moneyRo 股票工具验证对象
     * @return 股票工具,减仓时验证传入的参数
     */
    private OutputResult validateReduceParam(MoneyRo moneyRo){
        if (moneyRo.getType() == null){
            return OutputResult.buildAlert(
                    ResultCode.TOOL_TYPE_IS_EMPTY
            );
        }
        if (moneyRo.getType() ==1){
            // 对交易进行验证
            if (moneyRo.getSecPrice() ==null){
                return OutputResult.buildAlert(
                        ResultCode.TOOL_REDUCE_PRICE_IS_EMPTY
                );
            }
        }else if (moneyRo.getType() ==2){
            if (moneyRo.getMakePrice() ==null){
                return OutputResult.buildAlert(
                        ResultCode.TOOL_MAKE_PRICE_IS_EMPTY
                );
            }
        }else{
            return OutputResult.buildAlert(
                    ResultCode.TOOL_TYPE_NOT_SUPPORT
            );
        }
        // 股票编码不能为空
        if (!StringUtils.hasText(moneyRo.getCode())){
            return OutputResult.buildAlert(
                    ResultCode.STOCK_CODE_IS_EMPTY
            );
        }
        // 对交易进行验证
        if (moneyRo.getPrice() ==null){
            return OutputResult.buildAlert(
                    ResultCode.TOOL_PRICE_IS_EMPTY
            );
        }
        //数量验证
        if (moneyRo.getNumber() ==null){
            return OutputResult.buildAlert(
                    ResultCode.TOOL_NUMBER_IS_EMPTY
            );
        }
        if (moneyRo.getNumber()%100 !=0){
            return OutputResult.buildAlert(
                    ResultCode.TOOL_NUMBER_IS_HUNDREDS
            );
        }
        //地区验证
        if (moneyRo.getTradingArea() ==null){
            return OutputResult.buildAlert(
                    ResultCode.TOOL_AREA_IS_EMPTY
            );
        }
        //手续费验证
        if (moneyRo.getPlatformFee() == null) {
            return OutputResult.buildAlert(
                    ResultCode.TOOL_FEE_IS_EMPTY
            );
        }
        if (moneyRo.getPlatformFee() < 0.00 || moneyRo.getPlatformFee() > 0.03) {
            return OutputResult.buildAlert(
                    ResultCode.TOOL_FEE_NOT_SUPPORT
            );
        }
        //数量验证
        if (moneyRo.getSecNumber() ==null){
            return OutputResult.buildAlert(
                    ResultCode.TOOL_REDUCE_NUMBER_IS_EMPTY
            );
        }
        if (moneyRo.getSecNumber()%100 !=0){
            return OutputResult.buildAlert(
                    ResultCode.TOOL_REDUCE_NUMBER_IS_HUNDREDS
            );
        }

        return OutputResult.buildSucc();
    }


    /**
     * 股票工具,清仓时验证传入的参数
     * @param moneyRo 股票工具验证对象
     * @return 股票工具,清仓时验证传入的参数
     */
    private OutputResult validateClearParam(MoneyRo moneyRo){
        if (moneyRo.getType() == null){
            return OutputResult.buildAlert(
                    ResultCode.TOOL_TYPE_IS_EMPTY
            );
        }
        if (moneyRo.getType() ==1){
            if (moneyRo.getMakeMoney() ==null){
                return OutputResult.buildAlert(
                        ResultCode.TOOL_MAKE_MONEY_IS_EMPTY
                );
            }
        }else if (moneyRo.getType() ==2){
            if (moneyRo.getMakePrice() ==null){
                return OutputResult.buildAlert(
                        ResultCode.TOOL_MAKE_PRICE_IS_EMPTY
                );
            }
        }else if (moneyRo.getType() ==3){
            if (moneyRo.getMakeProportion() ==null){
                return OutputResult.buildAlert(
                        ResultCode.TOOL_MAKE_PROPORTION_IS_EMPTY
                );
            }
        }else{
            return OutputResult.buildAlert(
                    ResultCode.TOOL_TYPE_NOT_SUPPORT
            );
        }
        // 股票编码不能为空
        if (!StringUtils.hasText(moneyRo.getCode())){
            return OutputResult.buildAlert(
                    ResultCode.STOCK_CODE_IS_EMPTY
            );
        }
        // 对交易进行验证
        if (moneyRo.getPrice() ==null){
            return OutputResult.buildAlert(
                    ResultCode.TOOL_PRICE_IS_EMPTY
            );
        }
        //数量验证
        if (moneyRo.getNumber() ==null){
            return OutputResult.buildAlert(
                    ResultCode.TOOL_NUMBER_IS_EMPTY
            );
        }
        if (moneyRo.getNumber()%100 !=0){
            return OutputResult.buildAlert(
                    ResultCode.TOOL_NUMBER_IS_HUNDREDS
            );
        }
        //地区验证
        if (moneyRo.getTradingArea() ==null){
            return OutputResult.buildAlert(
                    ResultCode.TOOL_AREA_IS_EMPTY
            );
        }
        //手续费验证
        if (moneyRo.getPlatformFee() == null) {
            return OutputResult.buildAlert(
                    ResultCode.TOOL_FEE_IS_EMPTY
            );
        }
        if (moneyRo.getPlatformFee() < 0.00 || moneyRo.getPlatformFee() > 0.03) {
            return OutputResult.buildAlert(
                    ResultCode.TOOL_FEE_NOT_SUPPORT
            );
        }
        return OutputResult.buildSucc();
    }



    /**
     * 股票工具,补仓时验证传入的参数
     * @param moneyRo 股票工具验证对象
     * @return 股票工具,补仓时验证传入的参数
     */
    private OutputResult validateCovertParam(MoneyRo moneyRo){
        if (moneyRo.getType() == null){
            return OutputResult.buildAlert(
                    ResultCode.TOOL_TYPE_IS_EMPTY
            );
        }
        if (moneyRo.getType() ==1){
            // 对交易进行验证
            if (moneyRo.getSecPrice() ==null){
                return OutputResult.buildAlert(
                        ResultCode.TOOL_CONVERT_PRICE_IS_EMPTY
                );
            }
        }else if (moneyRo.getType() ==2){
            if (moneyRo.getMakePrice() ==null){
                return OutputResult.buildAlert(
                        ResultCode.TOOL_MAKE_PRICE_IS_EMPTY
                );
            }
        }else{
            return OutputResult.buildAlert(
                    ResultCode.TOOL_TYPE_NOT_SUPPORT
            );
        }
        // 股票编码不能为空
        if (!StringUtils.hasText(moneyRo.getCode())){
            return OutputResult.buildAlert(
                    ResultCode.STOCK_CODE_IS_EMPTY
            );
        }
        // 对交易进行验证
        if (moneyRo.getPrice() ==null){
            return OutputResult.buildAlert(
                    ResultCode.TOOL_PRICE_IS_EMPTY
            );
        }
        //数量验证
        if (moneyRo.getNumber() ==null){
            return OutputResult.buildAlert(
                    ResultCode.TOOL_NUMBER_IS_EMPTY
            );
        }
        if (moneyRo.getNumber()%100 !=0){
            return OutputResult.buildAlert(
                    ResultCode.TOOL_NUMBER_IS_HUNDREDS
            );
        }
        //地区验证
        if (moneyRo.getTradingArea() ==null){
            return OutputResult.buildAlert(
                    ResultCode.TOOL_AREA_IS_EMPTY
            );
        }
        //手续费验证
        if (moneyRo.getPlatformFee() == null) {
            return OutputResult.buildAlert(
                    ResultCode.TOOL_FEE_IS_EMPTY
            );
        }
        if (moneyRo.getPlatformFee() < 0.00 || moneyRo.getPlatformFee() > 0.03) {
            return OutputResult.buildAlert(
                    ResultCode.TOOL_FEE_NOT_SUPPORT
            );
        }
        //数量验证
        if (moneyRo.getSecNumber() ==null){
            return OutputResult.buildAlert(
                    ResultCode.TOOL_CONVERT_NUMBER_IS_EMPTY
            );
        }
        if (moneyRo.getSecNumber()%100 !=0){
            return OutputResult.buildAlert(
                    ResultCode.TOOL_CONVERT_NUMBER_IS_HUNDREDS
            );
        }




        return OutputResult.buildSucc();
    }
}
