package top.yueshushu.learn.mode.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用途描述
 *
 * @author yuejianli
 * @date 2023-02-23
 */
@Data
public class StockBkTopVo implements Serializable {
    /**
     * 当前日期和详情列表
     */
    private String currDate;
    private List<StockBkTopDetailVo> detailList;
}
