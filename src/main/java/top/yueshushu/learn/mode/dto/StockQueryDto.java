package top.yueshushu.learn.mode.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 股票查询实体
 *
 * @author yuejianli
 * @date 2023-03-23
 */
@Data
public class StockQueryDto implements Serializable {
    private String code;
    private String keyword;
    private Integer canUse = 1;
    private String prefix;
    private Integer exchange;
}
