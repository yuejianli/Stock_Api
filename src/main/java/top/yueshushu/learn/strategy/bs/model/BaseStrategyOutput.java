package top.yueshushu.learn.strategy.bs.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.yueshushu.learn.mode.ro.BuyRo;
import top.yueshushu.learn.mode.ro.SellRo;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseStrategyOutput implements Serializable {
    private BuyRo buyRo;
    private SellRo sellRo;
}
