package top.yueshushu.learn.mode.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName:CalcMoneyVo
 * @Description 计算金额所用的Vo
 * @Author 岳建立
 * @Date 2021/11/21 12:09
 * @Versio 1.0
 **/
@Data
public class CalcMoneyVo implements Serializable {
    /**
     * @param code 股票的代码
     */
    private String code;
    /**
     * @param name 股票的名称
     */
    private String name;
    /**
     * 股票的买入相关记录信息
     */
    private List<CalcBuyMoneyVo> calcBuyMoneyVoList;
    /**
     * 股票的卖出相关信息
     */
    private List<CalcSellMoneyVo> calcSellMoneyVoList;
    /**
     * @param totalBuyCharge 总的买入费用
     */
    private String totalBuyCharge;
    /**
     * @param totalSellCharge 总的卖出费用
     */
    private String totalSellCharge;
    /**
     * @param totalCharge 总的手续费用
     */
    private String totalCharge;

    /**
     * 总的数量
     */
    private int totalNum;
    /**
     * 总的金额
     */
    private String totalMoney;
    /**
     * 平均价格
     */
    private String avgPrice;
    /**
     * 本次操作的价格
     */
    private String nowOperationPrice;
}