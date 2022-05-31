package top.yueshushu.learn.mode.dto;

import cn.hutool.core.date.DateTime;
import lombok.Data;

/**
 * @Description 股票交易查询对象
 * @Author yuejianli
 * @Date 2022/5/28 20:06
 **/
@Data
public class TradeDealQueryDto {
    private Integer userId;
    private Integer mockType;
    private DateTime dealDate;
    private Integer dealType;
    /**
     * 开始成交时间
     */
    private DateTime startDealDate;
    /**
     * 结束成交时间
     */
    private DateTime endDealDate;
    /**
     * 股票的编码
     */
    private String code;
}
