package top.yueshushu.learn.util;

import cn.hutool.core.util.RandomUtil;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import top.yueshushu.learn.common.SystemConst;
import top.yueshushu.learn.enumtype.ExchangeMarketType;
import top.yueshushu.learn.enumtype.ExchangeType;
import top.yueshushu.learn.mode.dto.PoundageDto;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName:StockUtil
 * @Description 股票的工具类
 * @Author 岳建立
 * @Date 2022/1/8 10:13
 * @Version 1.0
 **/
public class StockUtil {
    /**
     * 获取当前的市值，即总的价钱
     * @param allAmount 可用数量
     * @param price 当前价格
     * @return
     */
    public static BigDecimal allMoney(int allAmount,BigDecimal price){
        return BigDecimalUtil.toBigDecimal(
                new BigDecimal(allAmount),
                price
        );
    }

    /**
     * 获取当前的浮动盈亏
     * @param avgPrice 平均价格
     * @param price 当前价格
     * @param allAmount 当前数量
     * @return
     */
    public static BigDecimal floatMoney(BigDecimal avgPrice,
                                        BigDecimal price,
                                        int allAmount){
        return BigDecimalUtil.toBigDecimal(
                BigDecimalUtil.subBigDecimal(
                        price,avgPrice
                ),
                new BigDecimal(allAmount)
        );
    }

    /**
     * 获取当前的浮动盈亏比例
     * @param avgPrice 平均价格
     * @param price 当前价格
     * @param allAmount 当前数量
     * @return
     */
    public static BigDecimal floatProportion(BigDecimal avgPrice,
                                        BigDecimal price,
                                        int allAmount){
       //拿数量 除以 总的金额数
        return MathUtil.getPattern(
             allMoney(
                     allAmount,avgPrice
             ),
             allMoney(
                     allAmount,price
             )
        );
    }

    /**
     * 获取买入时所需要的金额
     * @param amount
     * @param price
     * @param tranPrice
     * @return
     */
    public static BigDecimal getBuyMoney(Integer amount, BigDecimal price, BigDecimal tranPrice) {
        //获取买入的价格
        BigDecimal buyMoney = allMoney(
                amount,
                price
        );
        //买入的手续费
        BigDecimal buyHandMoney = getBuyHandMoney(amount,price,tranPrice);
        //相加，即为总的费用信息
        return BigDecimalUtil.addBigDecimal(
                buyMoney,
                buyHandMoney
        );
    }
    /**
     * 获取买入时所需要的手续费
     * @param amount
     * @param price
     * @param tranPrice
     * @return
     */
    public static BigDecimal getBuyHandMoney(Integer amount, BigDecimal price, BigDecimal tranPrice) {
        PoundageDto poundageDto = new PoundageDto();
        poundageDto.setBuyNumber(amount);
        poundageDto.setBuyPrice(price);
        poundageDto.setPlatformFee(convertToPlatformFee(tranPrice));
        return PoundageCalcUtil.calcBuyCharge(poundageDto);
    }

    /**
     * 转换手续费
     * 如 万3 ， 数据库里是  3.0  转换成  0.03
     * @param bigDecimal
     * @return
     */
    public static BigDecimal convertToPlatformFee(BigDecimal bigDecimal){
        return BigDecimalUtil.div(
                bigDecimal,
                SystemConst.DEFAULT_FULL
        );
    }

    /**
     * 获取卖出时可得的金额
     * @param amount
     * @param price
     * @param tranPrice
     * @return
     */
    public static BigDecimal getSellMoney(Integer amount, BigDecimal price, BigDecimal tranPrice) {
        //获取买入的价格
        BigDecimal sellMoney = allMoney(
                amount,
                price
        );
        //买入的手续费
        BigDecimal sellHandMoney = getSellHandMoney(amount,price,tranPrice);
        //相加，即为总的费用信息
        return BigDecimalUtil.subBigDecimal(
                sellMoney,
                sellHandMoney
        );
    }
    /**
     * 获取卖出时所需要的手续费
     * @param amount
     * @param price
     * @param tranPrice
     * @return
     */
    public static BigDecimal getSellHandMoney(Integer amount, BigDecimal price, BigDecimal tranPrice) {
        PoundageDto poundageDto = new PoundageDto();
        poundageDto.setSellNumber(amount);
        poundageDto.setSellPrice(price);
        poundageDto.setPlatformFee(convertToPlatformFee(tranPrice));
        return BigDecimalUtil.addBigDecimal(
                PoundageCalcUtil.calcSellStampDuty(poundageDto),
                PoundageCalcUtil.calcSellCharge(poundageDto)
        );
    }

    /**
     * 生成交易单号
     * @return
     */
    public static String generateEntrustCode() {
        return System.currentTimeMillis()+ RandomUtil.randomLong(999999)+"";
    }
    /**
     * 生成交易成交单号
     * @return
     */
    public static String generateDealCode() {
        return System.currentTimeMillis()+ RandomUtil.randomLong(999999)+"";
    }

    /**
     * 股票买入后，计算其平均价格
     * @param beforeAmount
     * @param beforeAvgPrice
     * @param addTotalMoney
     * @param addAmount
     * @return
     */
    public static BigDecimal calcBuyAvgPrice(Integer beforeAmount, BigDecimal beforeAvgPrice,
                                             BigDecimal addTotalMoney,
                                             Integer addAmount) {
        //获取总数量
        Integer totalNum = beforeAmount +addAmount;
        //获取总的金额数
        BigDecimal totalMoney = BigDecimalUtil.addBigDecimal(
                BigDecimalUtil.toBigDecimal(
                        beforeAvgPrice,
                        new BigDecimal(beforeAmount)
                ),
                addTotalMoney
        );
        // 计算新的成本
        return BigDecimalUtil.div(
                totalMoney,
                new BigDecimal(totalNum)
        );
    }

    /**
     * 股票卖出后，计算其平均价格
     * @param beforeAmount
     * @param beforeAvgPrice
     * @param subTotalMoney
     * @param subAmount
     * @return
     */
    public static BigDecimal calcSellAvgPrice(Integer beforeAmount, BigDecimal beforeAvgPrice,
                                             BigDecimal subTotalMoney,
                                             Integer subAmount) {
        //获取总数量
        Integer totalNum = beforeAmount - subAmount;
        //获取总的金额数
        BigDecimal totalMoney = BigDecimalUtil.subBigDecimal(
                BigDecimalUtil.toBigDecimal(
                        beforeAvgPrice,
                        new BigDecimal(beforeAmount)
                ),
                subTotalMoney
        );
        // 计算新的成本
        return BigDecimalUtil.div(
                totalMoney,
                new BigDecimal(totalNum)
        );
    }

    private static List<String> shList;
    private static List<String> shThreeList;
    static{
        shList= Arrays.asList("5","6","9");
        shThreeList=Arrays.asList("009","126","110","201","202","203","204");
    }

    public static String getFullCode(Integer type,String code){
        Assert.notNull(type,"类型不能为空");
        Assert.notNull(code,"股票代码不能为空");
        ExchangeType exchangeType = ExchangeType.getExchangeType(type);
        if(exchangeType==null){
            return code;
        }
        return exchangeType.getDesc()+code;
    }
    public static String getFullCode(String stockCode){
        if (!StringUtils.hasText(stockCode)||stockCode.length()<3) {
            return stockCode;
        }
        String one = stockCode.substring(0, 1);
        String three = stockCode.substring(0, 3);
        if (shList.contains(one)) {
            return ExchangeType.SH.getDesc()+stockCode;
        } else {
            if (shThreeList.contains(three)) {
                return ExchangeType.SH.getDesc()+stockCode;
            } else {
                return ExchangeType.SZ.getDesc()+stockCode;
            }
        }
    }
    public static String getStockMarket(String stockCode) {
        String exchange = getExchange(stockCode);
        if (exchange == null) {
            return null;
        }
        return ExchangeMarketType.getExchangeType(exchange).getDesc();
    }

    private static final List<String> CODES_SH_A = Arrays.asList("600", "601", "603", "605", "688", "689");
    private static final List<String> CODES_SH_INDEX = Collections.singletonList("000001");
    private static final List<String> CODES_SH_ETF = Arrays.asList("51", "56", "58");
    private static final List<String> CODES_SH_CB = Arrays.asList("100", "110");

    private static final List<String> CODES_SZ_A = Arrays.asList("000", "001", "002", "003", "004", "300", "301");
    private static final List<String> CODES_SZ_INDEX = Arrays.asList("399001", "399006");
    private static final List<String> CODES_SZ_ETF = Collections.singletonList("15");
    private static final List<String> CODES_SZ_CB = Arrays.asList("12");

    private static final List<String> CODES_BJ_A = Arrays.asList("83", "87", "43");
    private static final List<String> CODES_BJ_INDEX = Collections.emptyList();
    private static final List<String> CODES_BJ_ETF = Collections.emptyList();
    private static final List<String> CODES_BJ_CB = Collections.emptyList();

    public static String getExchange(String code) {
        if (!StringUtils.hasLength(code)) {
            return null;
        }
        if (isCodeStart(code, CODES_SH_A, CODES_SH_ETF, CODES_SH_CB)) {
            return ExchangeMarketType.SH.getCode();
        }
        if (isCodeStart(code, CODES_SZ_A, CODES_SZ_ETF, CODES_SZ_CB)) {
            return ExchangeMarketType.SZ.getCode();
        }
        if (isCodeStart(code, CODES_BJ_A, CODES_BJ_ETF, CODES_BJ_CB)) {
            return ExchangeMarketType.BJ.getCode();
        }
        return null;
    }

    public static boolean isOriName(String name) {
        for (String namePrefix : Arrays.asList("N", "XD", "XR", "DR")) {
            if (name.startsWith(namePrefix)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isCodeStart(String code, List<String> list) {
        return list.stream().anyMatch(code::startsWith);
    }

    private static boolean isCodeStart(String code, List<String> list01, List<String> list02, List<String> list03) {
        return isCodeStart(code, list01) || isCodeStart(code, list02) || isCodeStart(code, list03);
    }

}
