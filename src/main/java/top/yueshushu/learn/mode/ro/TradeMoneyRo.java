package top.yueshushu.learn.mode.ro;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @ClassName:PoistionRo
 * @Description TODO
 * @Author 岳建立
 * @Date 2022/1/3 17:00
 * @Version 1.0
 **/
@Data
@ApiModel("我的持仓展示信息")
public class TradeMoneyRo extends TradeRo implements Serializable {
	
	/**
	 * 开始日期
	 */
	private String startDate;
	/**
	 * 结束日期
	 */
	private String endDate;
}
