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
    private Integer bkCodeType1;
    private String bkContent1;

    private String bkCode2;
    private String bkCodeName2;
    private Integer bkCodeType2;
    private String bkContent2;

    private String bkCode3;
    private String bkCodeName3;
    private Integer bkCodeType3;
    private String bkContent3;

    private String bkCode4;
    private String bkCodeName4;
    private Integer bkCodeType4;
    private String bkContent4;
}
