package top.yueshushu.learn.mode.ro;

import lombok.Data;
import lombok.NoArgsConstructor;
import top.yueshushu.learn.response.PageRo;

/**
 * @ClassName:StockRo
 * @Description 股票的相关ro
 * @Author 岳建立
 * @Date 2021/11/13 5:50
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
public class StockDayStatRo extends PageRo {
    /**
     * 股票的编码
     */
    private String code;
    /**
     * /**
     * 开始日期
     */
    private String startDate;
    /**
     * 结束日期
     */
    private String endDate;
    /**
     * 月份
     */
    private Integer month;
    /**
     * 开始的天数字
     */
    private Integer startDayNum;
    
    /**
     * 结束的天数字
     */
    private Integer endDayNum;
}
