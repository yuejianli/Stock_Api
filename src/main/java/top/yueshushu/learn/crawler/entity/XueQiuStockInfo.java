package top.yueshushu.learn.crawler.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 腾讯股票历史记录信息
 * @Author yuejianli
 * @Date 2022/10/16 9:14
 **/
@Data
public class XueQiuStockInfo implements Serializable {
    private String symbol;
    private String current;
    // 其它的不重要的属性信息
}
