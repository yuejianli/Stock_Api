package top.yueshushu.learn.mode.ro;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import top.yueshushu.learn.response.PageRo;

import java.io.Serializable;

/**
 * 股票更新日志
 *
 * @author yuejianli
 * @date 2022-06-02
 */
@Data
@ApiModel("股票更新日志Ro")
public class StockUpdateLogRo extends PageRo implements Serializable {
    /**
     * 股票的编码
     */
    private String code;

    /**
     * 开始日期
     */
    private String startDate;
    /**
     * 结束日期
     */
    private String endDate;
}
