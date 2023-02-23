package top.yueshushu.learn.mode.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 股票排行榜详情列表
 *
 * @author yuejianli
 * @date 2023-02-23
 */
@Data
public class StockBkTopDetailVo implements Serializable {
    private String bkCode;
    private String bkName;
    private String bkNowProportion;
    private Integer type;
}
