package top.yueshushu.learn.mode.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 股票概念对应信息
 *
 * @author yuejianli
 * @date 2023-02-23
 */
@Data
public class StockBkStockVo implements Serializable {
    private String code;
    private String name;

    private String bkCode1;
    private String bkCodeName1;

    private String bkCode2;
    private String bkCodeName2;


    private String bkCode3;
    private String bkCodeName3;

    private String bkCode4;
    private String bkCodeName4;
}
