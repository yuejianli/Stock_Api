package top.yueshushu.learn.crawler.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 实时价格处理
 *
 * @author yuejianli
 * @date 2023-01-12
 */
public interface RealTimePriceService {
    BigDecimal getNowPrice(String code, String fullCode);

    Map<String, BigDecimal> batchGetNowPrice(List<String> codeList, List<String> fullCodeList);

}
