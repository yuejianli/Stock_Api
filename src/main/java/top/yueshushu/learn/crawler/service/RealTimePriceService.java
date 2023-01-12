package top.yueshushu.learn.crawler.service;

import java.math.BigDecimal;

/**
 * 实时价格处理
 *
 * @author yuejianli
 * @date 2023-01-12
 */
public interface RealTimePriceService {
    BigDecimal getNowPrice(String code, String fullCode);
}
