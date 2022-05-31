package top.yueshushu.learn.mode.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName:CalcSellMoneyVo
 * @Description TODO
 * @Author 岳建立
 * @Date 2021/11/21 12:11
 * @Version 1.0
 **/
@Data
public class CalcSellMoneyVo extends SellPoundageVo implements Serializable {
    /**
     * @param price 卖出时的价格
     */
    private String price;
    /**
     * @param number 卖出的股票数
     */
    private Integer number;

    /**
     * @param buyMoney 卖出的金额
     */
    private String sellMoney;

    /**
     * @param totalSellCharge 总的卖出费用
     */
    private String totalSellCharge;
}
