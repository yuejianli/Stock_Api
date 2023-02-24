package top.yueshushu.learn.mode.ro;

import lombok.Data;
import top.yueshushu.learn.enumtype.StockPoolType;
import top.yueshushu.learn.response.PageRo;

import java.io.Serializable;

/**
 * 股票池统计信息
 *
 * @author yuejianli
 * @date 2023-02-24
 */
@Data
public class StockPoolRo extends PageRo implements Serializable {

    /**
     * @param startDate 股票的查询开始日期
     */
    private String startDate;
    /**
     * @param endDate 股票的结束日期
     */
    private String endDate;

    private String keywords;

    private Integer poolType = StockPoolType.ZT.getCode();
}
