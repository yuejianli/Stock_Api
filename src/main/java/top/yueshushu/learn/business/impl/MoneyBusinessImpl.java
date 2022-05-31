package top.yueshushu.learn.business.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.business.MoneyBusiness;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.entity.Stock;
import top.yueshushu.learn.mode.ro.MoneyRo;
import top.yueshushu.learn.mode.vo.CalcMoneyVo;
import top.yueshushu.learn.mode.vo.MoneyVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.MoneyService;
import top.yueshushu.learn.service.StockService;

import javax.annotation.Resource;

/**
 * @Description 股票小工具编排层实现
 * @Author yuejianli
 * @Date 2022/5/22 9:35
 **/
@Slf4j
@Service
public class MoneyBusinessImpl implements MoneyBusiness {
    @Resource
    private StockService stockService;
    @Resource
    private MoneyService moneyService;

    @Override
    public OutputResult clearMoney(MoneyRo moneyRo) {
        OutputResult validateResult = validateStock(moneyRo.getCode());
        if (!validateResult.getSuccess()) {
            return validateResult;
        }
        Stock stock = (Stock) validateResult.getData();
        moneyRo.setCode(
                stock.getFullCode()
        );

        // 处理价格
        Double makePrice = moneyService.handlerClearPrice(moneyRo);

        moneyRo.setMakePrice(makePrice);
        //3. 获取数据
        MoneyVo moneyVo = moneyService.assemblyClearMoneyVo(moneyRo);
        moneyVo.setName(
                stock.getName()
        );
        return OutputResult.buildSucc(moneyVo);
    }

    @Override
    public OutputResult coverMoney(MoneyRo moneyRo) {
        OutputResult validateResult = validateStock(moneyRo.getCode());
        if (!validateResult.getSuccess()) {
            return validateResult;
        }
        Stock stock = (Stock) validateResult.getData();
        moneyRo.setCode(
                stock.getFullCode()
        );

        //2. 组装数据,处理预期的价格,也就是卖的价格.
        Double makePrice=moneyService.handlerCovertPrice(moneyRo);
        moneyRo.setSecPrice(makePrice);
        //3. 获取数据
        CalcMoneyVo calcMoneyVo= moneyService.coverCalcMoneyVo(moneyRo);
        calcMoneyVo.setName(
                stock.getName()
        );
        return OutputResult.buildSucc(calcMoneyVo);
    }

    @Override
    public OutputResult reduceMoney(MoneyRo moneyRo) {
        OutputResult validateResult = validateStock(moneyRo.getCode());
        if (!validateResult.getSuccess()) {
            return validateResult;
        }
        Stock stock = (Stock) validateResult.getData();
        moneyRo.setCode(
                stock.getFullCode()
        );

        //2. 组装数据,处理预期的价格,也就是卖的价格.
        Double makePrice= moneyService.handlerReducePrice(moneyRo);
        moneyRo.setSecPrice(makePrice);
        //3. 获取数据
        CalcMoneyVo calcMoneyVo= moneyService.reduceCalcMoneyVo(moneyRo);
        calcMoneyVo.setName(
                stock.getName()
        );
        return OutputResult.buildSucc(calcMoneyVo);
    }

    /**
     * 验证股票的编码是否正确，如果正确，返回股票的对象
     *
     * @param code 股票的编码
     * @return 验证股票的编码是否正确，如果正确，返回股票的对象
     */
    private OutputResult validateStock(String code) {
        Stock stock = stockService.selectByCode(code);
        if (null == stock) {
            return OutputResult.buildAlert(
                    ResultCode.STOCK_CODE_NO_EXIST
            );
        }
        return OutputResult.buildSucc(stock);
    }
}
