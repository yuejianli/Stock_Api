package top.yueshushu.learn.mode.dto;

import cn.hutool.core.date.DateTime;
import lombok.Data;

import java.util.List;

/**
 * @Description 股票交易查询对象
 * @Author yuejianli
 * @Date 2022/5/28 20:06
 **/
@Data
public class TradeEntrustQueryDto {
    private Integer userId;
    private Integer mockType;
    private DateTime entrustDate;
    private Integer entrustStatus;
    private String code;
    /**
     * 开始委托时间
     */
    private DateTime startEntrustDate;
    /**
     * 结束委托时间
     */
    private DateTime endEntrustDate;

    private List<Integer> inEntrustStatus;
}
