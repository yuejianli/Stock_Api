package top.yueshushu.learn.crawler.service.impl;

import lombok.extern.slf4j.Slf4j;
import top.yueshushu.learn.crawler.crawler.CrawlerService;
import top.yueshushu.learn.crawler.service.RealTimePriceService;
import top.yueshushu.learn.enumtype.ExchangeType;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 新浪接口获取股票的价格
 *
 * @author yuejianli
 * @date 2023-01-12
 */
// @Service("realTimePriceService4")
@Slf4j
public class WangYiRealTimePriceServiceImpl implements RealTimePriceService {
    @Resource
    private CrawlerService crawlerService;

    @Override
    public BigDecimal getNowPrice(String code, String fullCode, Integer counter) {
        if (fullCode.startsWith(ExchangeType.SH.getDesc())) {
            fullCode = "0" + code;
        } else {
            fullCode = "1" + code;
        }
        BigDecimal price = crawlerService.wangYiGetPrice(fullCode);
        log.info(">>>>> 网易 获取 股票 {} 的价格为: {}", counter + ":" + code, price);
        return price;
    }
}
