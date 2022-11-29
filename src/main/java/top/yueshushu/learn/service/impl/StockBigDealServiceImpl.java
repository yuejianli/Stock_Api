package top.yueshushu.learn.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.domain.StockBigDealDo;
import top.yueshushu.learn.domainservice.StockBigDealDomainService;
import top.yueshushu.learn.helper.DateHelper;
import top.yueshushu.learn.service.StockBigDealService;
import top.yueshushu.learn.service.StockCrawlerService;
import top.yueshushu.learn.service.cache.StockCacheService;
import top.yueshushu.learn.util.BigDecimalUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 用途描述
 *
 * @author yuejianli
 * @date 2022-11-29
 */
@Service
@Slf4j
public class StockBigDealServiceImpl implements StockBigDealService {
    @Resource
    private StockBigDealDomainService stockBigDealDomainService;
    @Resource
    private DateHelper dateHelper;
    @Resource
    private StockCrawlerService stockCrawlerService;
    @Resource
    private StockCacheService stockCacheService;

    //30 万
    private static BigDecimal MIN_MONEY = new BigDecimal(300000);

    @Override
    public void syncBigDeal(List<String> codeList) {
        if (CollectionUtils.isEmpty(codeList)) {
            return;
        }
        DateTime beforeLastWorking = dateHelper.getBeforeLastWorking(DateUtil.date());
        for (String stockCode : codeList) {
            String fullCode = stockCacheService.selectByCode(stockCode).getFullCode();
            // 获取最小的手数.
            Integer minVolume = getMinVolume(MIN_MONEY, stockCode);

            List<StockBigDealDo> data = stockCrawlerService.handleBigDeal(fullCode, minVolume, beforeLastWorking).getData();
            if (CollectionUtils.isEmpty(data)) {
                continue;
            }
            // 先删除之前已经同步过的历史数据
            stockBigDealDomainService.deleteHasAsyncData(fullCode, beforeLastWorking);

            log.info(">>> 股票 {} 共获取大宗数量交易数量是:{}", stockCode, data.size());
            stockBigDealDomainService.saveBatch(data, 100);
        }
        log.info(">>>> 同步大宗交易完成");

    }

    /**
     * 获取手数, 整百
     *
     * @param totalMoney 总金额 元
     * @param stockCode  股票编码
     */
    @Override
    public Integer getMinVolume(BigDecimal totalMoney, String stockCode) {
        // 昨日的价格
        BigDecimal yesterdayCloseCachePrice = stockCacheService.getYesterdayCloseCachePrice(stockCode);

        // 相除，得到手数 /100 得到股票手数。

        BigDecimal handResult = BigDecimalUtil.div(totalMoney, BigDecimalUtil.toBigDecimal(yesterdayCloseCachePrice, BigDecimal.valueOf(100)));

        // bigDecimal 转换成 int 类型

        int intValue = handResult.intValue();

        return intValue;
    }
}
