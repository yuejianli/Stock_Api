package top.yueshushu.learn.mode.vo.stock;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName:StockWeekStatVo
 * @Description 股票周统计展示信息
 * @Author 岳建立
 * @Date 2021/11/27 10:37
 * @Version 1.0
 **/
@Data
public class StockWeekStatVo implements Serializable {
    /**
     * 周展示信息
     */
    private List<StockWeekStatInfoVo>  weekStatInfoList;
}
