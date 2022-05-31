package top.yueshushu.learn.mode.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName:CalcBuyMoneyVo
 * @Description TODO
 * @Author 岳建立
 * @Date 2021/11/21 12:11
 * @Version 1.0
 **/
@Data
public class CalcBuyMoneyVo extends BuyPoundageVo implements Serializable {
    /**
     * @param price 买入时的价格
     */
    private String price;
    /**
     * @param number 买入的股票数
     */
    private Integer number;

    /**
     * 交易的金额
     */
    private String dealMoney;
    /**
     * @param buyMoney 买入的金额
     */
    private String buyMoney;



}
