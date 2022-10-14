package top.yueshushu.learn.mode.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 用途描述
 *
 * @author yuejianli
 * @date 2022-10-14
 */
@Data
public class TradePositionShowVo implements Serializable {
    private List<TradePositionVo> dateList;
    private BigDecimal todayMoney;
}
