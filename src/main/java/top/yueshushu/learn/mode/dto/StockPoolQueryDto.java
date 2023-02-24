package top.yueshushu.learn.mode.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 股票池查询 dto
 *
 * @author yuejianli
 * @date 2023-02-24
 */
@Data
public class StockPoolQueryDto implements Serializable {
    private String keywords;
    private Integer poolType;
    private Date startDate;
    private Date endDate;
}
