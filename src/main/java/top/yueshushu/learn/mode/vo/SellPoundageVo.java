package top.yueshushu.learn.mode.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName:SellPoundageVo
 * @Description TODO
 * @Author 岳建立
 * @Date 2021/11/21 13:28
 * @Version 1.0
 **/
@Data
public class SellPoundageVo implements Serializable {
    /**
     * @param sellStampDuty 卖出印花税
     */
    private String sellStampDuty;

    /**
     * @param sellCharge 卖出手续费
     */
    private String sellCharge;

    /**
     * @param sellTransferFee 卖出过户费
     */
    private String sellTransferFee;

    /**
     * @param sellCommunications 卖出通讯费
     */
    private String sellCommunications;
}
