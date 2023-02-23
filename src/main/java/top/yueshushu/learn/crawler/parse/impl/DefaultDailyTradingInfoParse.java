package top.yueshushu.learn.crawler.parse.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.crawler.entity.StockHistoryCsvInfo;
import top.yueshushu.learn.crawler.entity.TxStockHistoryInfo;
import top.yueshushu.learn.crawler.parse.DailyTradingInfoParse;
import top.yueshushu.learn.crawler.util.MyCsvUtil;
import top.yueshushu.learn.mode.vo.EasyMoneyHistoryVo;
import top.yueshushu.learn.util.BigDecimalUtil;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName:DefaultDailyTradingInfoParse
 * @Description 默认的每天交易信息处理器
 * @Author 岳建立
 * @Date 2021/11/10 21:15
 * @Version 1.0
 **/
@Service("defaultDailyTradingInfoParse")
@Slf4j
public class DefaultDailyTradingInfoParse implements DailyTradingInfoParse {

    public static final String TX_NO_MATCH_RESULT = "v_pv_none_match";
    @Value("${uploadFilePath:D:/upload/}")
    private String uploadFilePath;

    @Override
    public List<StockHistoryCsvInfo> parseStockHistoryList(InputStream inputStream) {
        //定义一个默认的文件路径
        Long fileName = DateUtil.date().getTime();
        if (!(uploadFilePath.endsWith("/") || uploadFilePath.endsWith("\\"))) {
            uploadFilePath = uploadFilePath + File.separator;
        }
        File downloadFile = new File(uploadFilePath + fileName + ".csv");
        //将数据保存到文件里面
        FileUtil.writeFromStream(inputStream, downloadFile);
        //将文件写入进去
        List<StockHistoryCsvInfo> stockHistoryCsvInfos = null;
        try {
            stockHistoryCsvInfos = MyCsvUtil.readFile(downloadFile, StockHistoryCsvInfo.class);

            stockHistoryCsvInfos = stockHistoryCsvInfos.stream().filter(n -> StringUtils.hasText(n.getCode())).collect(Collectors.toList());

            if (CollectionUtils.isEmpty(stockHistoryCsvInfos)) {
                return stockHistoryCsvInfos;
            }

            stockHistoryCsvInfos.stream().forEach(
                    n -> {
                        n.setCode(n.getCode().replace("'", ""));
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        //删除文件
        downloadFile.delete();
        return stockHistoryCsvInfos;
    }

    @Override
    public List<StockHistoryCsvInfo> parseEasyMoneyHistory(String content, List<String> codeList, DateTime beforeLastWorking) {
        char[] chArr = content.toCharArray();
        char[] newCharArr = new char[chArr.length];
        int i = 0;
        for (char ch : chArr) {
            if (ch == ' ') {
                continue;
            }
            if (ch == 'Ａ') {
                ch = 'A';
            } else if (ch == 'Ｂ') {
                ch = 'B';
            }
            newCharArr[i++] = ch;
        }

        EasyMoneyHistoryVo.StockResultVo stockResultVo = JSON.parseObject(new String(newCharArr, 0, i),
                EasyMoneyHistoryVo.StockResultVo.class);
        /**
         * f2: 3299 收盘价 /100, 当前价
         * f3: -138 涨跌福 /100,
         * f4: -46 涨跌额  /100,
         * f5: 545259 成交量 *100,
         * f6: 1797334016 成交金额,基本相同，不处理,
         * f12: "002415" 股票编码,
         * f14 海康威视 股票的名称
         * f15: 3336 最高价 /100,
         * f16: 3273 最低价 /100,
         * f17: 3332 开盘价 /100,
         * f18: 3345 昨天收盘价
         */
        String currDate = DateUtil.format(beforeLastWorking, Const.SIMPLE_DATE_FORMAT);
        List<StockHistoryCsvInfo> result= new ArrayList<>(codeList.size());
        stockResultVo.getData().getDiff().forEach(v -> {
            if (!codeList.contains(v.getF12())) {
                return;
            }
            StockHistoryCsvInfo stockHistoryCsvInfo = new StockHistoryCsvInfo();
            stockHistoryCsvInfo.setCode(v.getF12());
            stockHistoryCsvInfo.setName(v.getF14());
            stockHistoryCsvInfo.setCurrDate(currDate);
            stockHistoryCsvInfo.setClosingPrice(new BigDecimal(v.getF2()).movePointLeft(2));
            stockHistoryCsvInfo.setNowPrice(new BigDecimal(v.getF2()).movePointLeft(2));
            stockHistoryCsvInfo.setHighestPrice(new BigDecimal(v.getF15()).movePointLeft(2));
            stockHistoryCsvInfo.setLowestPrice(new BigDecimal(v.getF16()).movePointLeft(2));
            stockHistoryCsvInfo.setOpeningPrice(new BigDecimal(v.getF17()).movePointLeft(2));
            stockHistoryCsvInfo.setYesClosingPrice(new BigDecimal(v.getF18()).movePointLeft(2));
            stockHistoryCsvInfo.setAmplitude(new BigDecimal(v.getF4()).movePointLeft(2));
            stockHistoryCsvInfo.setAmplitudeProportion(new BigDecimal(v.getF3()).movePointLeft(2));
            stockHistoryCsvInfo.setTradingVolume(v.getF5() * 100);
            stockHistoryCsvInfo.setTradingValue(BigDecimalUtil.toBigDecimal(v.getF6()));
            result.add(stockHistoryCsvInfo);
        });
        return result;
    }

    @Override
    public List<TxStockHistoryInfo> parseTxMoneyHistory(String content, List<String> codeList, DateTime beforeLastWorking) {
        /**
         * v_sz002812="51~恩捷股份~002812~175.76~176.50~179.50~64869~32300~32569~175.76~11~175.75~8~175.73~1~175.72~10~175.70~9~175.78~1~175.80~8~175.81~12~175.82~36~175.84~84~~20221014161418~-0.74~-0.42~179.50~171.06~175.76/64869/1129392874~64869~112939~0.87~42.54~~179.50~171.06~4.78~1304.21~1568.50~10.09~194.15~158.85~1.15~-102~174.10~38.83~57.72~~~1.18~112939.2874~0.0000~0~
         * ~GP-A~-29.72~0.94~0.17~23.64~12.38~313.43~161.70~-1.62~-5.11~-26.46~742041973~892409275~-56.67~-29.87~742041973~~~-33.55~-0.02~";
         * v_sz002415="51~海康威视~002415~30.28~29.50~29.73~544783~294255~250528~30.28~194~30.27~620~30.26~750~30.25~712~30.24~290~30.29~1341~30.30~2889~30.31~328~30.32~353~30.33~296~~20221014161427~0.78~2.64~30.60~29.35~30.28/544783/1639917704~544783~163992~0.59~17.77~~30.60~29.35~4.24~2778.39~2856.38~4.61~32.45~26.55~1.41~-2641~30.10~24.80~17.00~~~0.99~163991.7704~0.0000~0~
         * ~GP-A~-41.11~-0.46~2.97~25.93~16.01~55.62~28.10~4.23~5.69~-7.51~9175672118~9433208719~-33.98~-37.37~9175672118~~~-45.33~-0.03~";
         */
        //1. 先按照分号进行拆分
        if (content.startsWith(TX_NO_MATCH_RESULT)) {
            return Collections.emptyList();
        }
        String[] splitStrArr = content.split("\\;");
        List<TxStockHistoryInfo> result = new ArrayList<>(splitStrArr.length);

        for (String codeHistoryContent : splitStrArr) {
            codeHistoryContent = codeHistoryContent.replace("\\n", "");
            if (!StringUtils.hasText(codeHistoryContent)) {
                continue;
            }
            TxStockHistoryInfo txStockHistoryInfo = singleTxMoneyHistoryParse(codeHistoryContent, beforeLastWorking);
            if (null != txStockHistoryInfo.getClosingPrice()) {
                result.add(txStockHistoryInfo);
            } else {
                log.info(" 股票 {} ,名称 {} 退市了", txStockHistoryInfo.getCode(), txStockHistoryInfo.getName());
            }
        }
        return result;
    }

    /**
     * @param codeHistoryContent 单条历史记录
     * @param beforeLastWorking  最近的一个工作日日期
     * @return 返回腾讯接口解析的数据信息
     */
    private TxStockHistoryInfo singleTxMoneyHistoryParse(String codeHistoryContent, DateTime beforeLastWorking) {
        TxStockHistoryInfo result = new TxStockHistoryInfo();

        /**
         * v_sz002812="51~恩捷股份~002812~175.76~176.50~179.50~64869~32300~32569~175.76~11~175.75~8~175.73~1~175.72~10~175.70~9~175.78~1~175.80~8~175.81~12~175.82~36~175.84~84~~20221014161418~-0.74~-0.42~179.50~171.06~175.76/64869/1129392874~64869~112939~0.87~42.54~~179.50~171.06~4.78~1304.21~1568.50~10.09~194.15~158.85~1.15~-102~174.10~38.83~57.72~~~1.18~112939.2874~0.0000~0~
         * ~GP-A~-29.72~0.94~0.17~23.64~12.38~313.43~161.70~-1.62~-5.11~-26.46~742041973~892409275~-56.67~-29.87~742041973~~~-33.55~-0.02~";
         */

        /**
         * 序号	返回值	含义
         * 1	1	代表交易所，200-美股（us），100-港股（hk），51-深圳（sz），1-上海（sh）
         * 2	贵州茅台	股票名字
         * 3	600519	股票代码
         * 4	2076.91	当前价格
         * 5	2033.00	昨收
         * 6	2000.00	开盘
         * 7	39811	成交量
         * 8	20166	外盘
         * 9	19646	内盘
         * 10	2076.03	买一
         * 11	8	买一量
         * 12	2076.02	买二
         * 13	6	买二量
         * 14	2076.01	买三
         * 15	2	买三量
         * 16	2076.00	买四
         * 17	7	买四量
         * 18	2075.90	买五
         * 19	2	买五量
         * 20	2076.05	卖一
         * 21	3	卖一量
         * 22	2076.82	卖二
         * 23	1	卖二量
         * 24	2076.91	卖三
         * 25	1	买三量
         * 26	2076.97	卖四
         * 27	1	卖四量
         * 28	0	卖五
         * 29	0	卖五量
         * 30	20210305110527	请求时间
         * 31	43.91	涨跌
         * 32	2.16	涨跌%
         * 33	2088.00	最高
         * 34	1988.00	最低
         * 35	2076.91/39811/8132289410	最新价/成交量（手）/成交额（元）
         * 36	39811	成交量
         * 37	813229	成交额
         * 38	0.32	换手率
         * 39	58.53	ttm市盈率
         * 40	-	-
         * 42	2088.00	最高
         * 43	1988.00	最低
         * 43	4.92	振幅
         * 44	26090.10	流通市值
         * 45	26090.10	总市值
         * 46	17.57	lf市净率
         * 47	2236.30	涨停价
         * 48	1829.70	跌停价
         * 49	1.65	量比
         * 50	9	-
         * 51	2042.72	均价
         * 52	57.85	动态市盈率
         * 53	63.32	静态市盈率
         * 54	-	-
         * 55	-	-
         * 56	1.09	-
         * 57	813228.9410	成交额
         * 58	0.0000
         * 59	0
         * 60	-
         * 61	GP-A
         * 62	3.95
         * 63	-
         * 64	0.82
         * 65	30.03
         * 66	25.68
         */
        //去掉开头的内容
        codeHistoryContent = codeHistoryContent.substring("v_sz002812=\"".length());
        //去掉结尾的两个字符信息
        codeHistoryContent = codeHistoryContent.substring(0, codeHistoryContent.length() - 2);

        // 对字符串进行拆分，按照 ~
        String[] splitArr = codeHistoryContent.split("\\~");
        result.setCode(splitArr[2]);
        result.setCurrDate(DateUtil.format(beforeLastWorking, Const.SIMPLE_DATE_FORMAT));
        result.setName(splitArr[1]);
        if (!StringUtils.hasText(splitArr[38]) && !StringUtils.hasText(splitArr[39])) {
            return result;
        }
        result.setClosingPrice(BigDecimalUtil.toBigDecimal(splitArr[3]));
        result.setHighestPrice(BigDecimalUtil.toBigDecimal(splitArr[41]));
        result.setLowestPrice(BigDecimalUtil.toBigDecimal(splitArr[42]));
        result.setOpeningPrice(BigDecimalUtil.toBigDecimal(splitArr[5]));
        result.setYesClosingPrice(BigDecimalUtil.toBigDecimal(splitArr[4]));
        result.setNowPrice(BigDecimalUtil.toBigDecimal(splitArr[3]));
        result.setAmplitude(BigDecimalUtil.toBigDecimal(splitArr[31]));
        result.setAmplitudeProportion(BigDecimalUtil.toBigDecimal(splitArr[32]));
        // 175.76/64869/1129392874  最新价/成交量（手）/成交额（元）
        result.setTradingVolume(Long.parseLong(splitArr[36]) * 100);
        result.setTradingValue(BigDecimalUtil.toBigDecimalIfNull(splitArr[37]));
        ;

        // 扩展的几个
        result.setOutDish(Integer.parseInt(Optional.ofNullable(splitArr[7]).orElse("0")));
        result.setInnerDish(Integer.parseInt(Optional.ofNullable(splitArr[8]).orElse("0")));
        result.setChangingProportion(BigDecimalUtil.toBigDecimalIfNull(splitArr[38]));
        result.setThan(BigDecimalUtil.toBigDecimalIfNull(splitArr[49]));
        result.setAvgPrice(BigDecimalUtil.toBigDecimalIfNull(splitArr[51]));
        result.setStaticPriceRatio(BigDecimalUtil.toBigDecimalIfNull(splitArr[53]));
        result.setDynamicPriceRatio(BigDecimalUtil.toBigDecimalIfNull(splitArr[52]));
        result.setTtmPriceRatio(BigDecimalUtil.toBigDecimalIfNull(splitArr[39]));

        result.setBuyHand(calcBuyHand(splitArr));
        result.setSellHand(calcSellHand(splitArr));
        result.setAppointThan(calcAppointThan(result.getBuyHand(), result.getSellHand()));
        return result;

    }

    /**
     * 计算委比
     *
     * @param buyHand  买的数目
     * @param sellHand 卖的数目
     * @return 计算委比
     */
    private String calcAppointThan(Integer buyHand, Integer sellHand) {
        Integer subNum = buyHand - sellHand;
        Integer sumNum = buyHand + sellHand;
        if (sumNum == 0 || subNum == 0) {
            return "0";
        }
        return BigDecimalUtil.divPattern(
                new BigDecimal(subNum), new BigDecimal(sumNum)
        );
    }

    /**
     * 获取卖的前五手数目
     *
     * @param splitArr 内容
     * @return 获取卖的前五手数目
     */
    private Integer calcSellHand(String[] splitArr) {
        List<Integer> sellIndexList = Arrays.asList(20, 22, 24, 26, 28);
        return calcHand(splitArr, sellIndexList);
    }

    /**
     * 获取买的前五手数目
     *
     * @param splitArr 内容
     * @return 获取买的前五手数目
     */
    private Integer calcBuyHand(String[] splitArr) {
        List<Integer> buyIndexList = Arrays.asList(10, 12, 14, 16, 18);
        return calcHand(splitArr, buyIndexList);
    }

    private Integer calcHand(String[] splitArr, List<Integer> indexList) {
        Integer sum = 0;
        for (Integer index : indexList) {
            sum += Integer.parseInt(splitArr[index]);
        }
        return sum;
    }
}
