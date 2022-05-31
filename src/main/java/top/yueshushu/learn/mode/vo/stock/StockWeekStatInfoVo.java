package top.yueshushu.learn.mode.vo.stock;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName:StockWeekStatInfoVo
 * @Description TODO
 * @Author 岳建立
 * @Date 2021/11/27 10:38
 * @Version 1.0
 **/
@Data
public class StockWeekStatInfoVo implements Serializable {
    /**
     * 类型
     */
    private Integer type;
    /**
     * 类型
     */
    private String typeName;
    /**
     * 价格
     */
    private String rangePrice;
    /**
     * 比例
     */
    private String rangeProportion;
}
