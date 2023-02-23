package top.yueshushu.learn.mode.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 股票版块查询Dto
 *
 * @author yuejianli
 * @date 2023-02-23
 */
@Data
public class StockBkCodeQueryDto implements Serializable {
    private String bkCode;
    private String stockCode;
    private List<String> bkCodeList;
    private List<String> stockCodeList;
}
