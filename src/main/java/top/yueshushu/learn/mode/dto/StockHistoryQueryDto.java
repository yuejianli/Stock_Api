package top.yueshushu.learn.mode.dto;

import cn.hutool.core.date.DateTime;
import lombok.Data;

import java.io.Serializable;

/**
 * 股票历史记录查询
 *
 * @author Yue Jianli
 * @date 2022-05-26
 */
@Data
public class StockHistoryQueryDto implements Serializable {
    private String code;

    private DateTime startDate;

    private DateTime endDate;
    private Integer startDayNum ;
    private Integer endDayNum ;
}
