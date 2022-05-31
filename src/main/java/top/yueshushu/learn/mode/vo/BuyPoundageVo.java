package top.yueshushu.learn.mode.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName:BuyPoundageVo
 * @Description TODO
 * @Author 岳建立
 * @Date 2021/11/21 13:27
 * @Version 1.0
 **/
@Data
public class BuyPoundageVo implements Serializable {
    /**
     * @param buyCharge 买入手续费
     */
    private String buyCharge;

    /**
     * @param buyTransferFee 买入过户费
     */
    private String buyTransferFee;

    /**
     * @param buyCommunications 买入通讯费
     */
    private String buyCommunications;

    /**
     * @param totalBuyCharge 总的买入费用
     */
    private String totalBuyCharge;
}
