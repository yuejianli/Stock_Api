package top.yueshushu.learn.crawler.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.crawler.crawler.CrawlerService;
import top.yueshushu.learn.crawler.service.RealTimePriceService;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 新浪接口获取股票的价格
 *
 * @author yuejianli
 * @date 2023-01-12
 */
@Service("realTimePriceService0")
@Slf4j
public class SinaRealTimePriceServiceImpl implements RealTimePriceService {
    @Resource
    private CrawlerService crawlerService;

    @Override
    public BigDecimal getNowPrice(String code, String fullCode, Integer counter) {
        BigDecimal price = crawlerService.sinaGetPrice(fullCode);
        log.info(">>>>> 新浪 获取 股票 {} 的价格为: {}", counter + ":" + code, price);
        return price;
    }
}
