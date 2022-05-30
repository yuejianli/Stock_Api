package top.yueshushu.learn.mode.ro;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName:StatTen10Ro
 * @Description 最近十天的交易信息
 * @Author 岳建立
 * @Date 2022/1/3 17:00
 * @Version 1.0
 **/
@Data
@ApiModel("最近十天的交易信息")
public class StatTen10Ro extends TradeRo implements Serializable {

}
