package top.yueshushu.learn.common;

import top.yueshushu.learn.response.BaseResultCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:ResultCode
 * @Description Tool 工具的错误码信息
 * @Author 岳建立
 * @Date 2022/5/20 20:14
 * @Version 1.0
 **/
public class CrawlerResultCode extends BaseResultCode {
    /**
     * 爬虫 提示信息：  200 10 （编排）+ 001 (三位随机)
     */
    public static final CrawlerResultCode STOCK_ASYNC_FAIL =
            new CrawlerResultCode(true, 20010001, "股票同步失败");
    public static final CrawlerResultCode STOCK_ASYNC_SUCCESS =
            new CrawlerResultCode(true, 20010002, "股票同步成功");
    public static final CrawlerResultCode STOCK_HIS_ASYNC_FAIL =
            new CrawlerResultCode(false, 20010003, "股票历史数据同步失败");
    public static final CrawlerResultCode STOCK_HIS_ASYNC_SUCCESS =
            new CrawlerResultCode(true, 20010004, "股票历史数据同步成功");
    public static final CrawlerResultCode STOCK_CODE_ERROR =
            new CrawlerResultCode(false, 20010005, "股票编码不存在");

    public static final CrawlerResultCode STOCK_KLINE_IS_EMPTY =
            new CrawlerResultCode(false, 20010006, "股票K线类型未选择");
    public static final CrawlerResultCode STOCK_ASYNC_NO_CHANGE =
            new CrawlerResultCode(false, 20010007, "股票信息未发生改变");

    public static final CrawlerResultCode STOCK_ASYNC_NO_START_DATE =
            new CrawlerResultCode(false, 20010008, "同步历史数据未选择开始日期");

    public static final CrawlerResultCode STOCK_ASYNC_NO_END_DATE =
            new CrawlerResultCode(false, 20010009, "同步历史数据未选择结束日期");

    public static final CrawlerResultCode STOCK_EXCHANGE_IS_EMPTY =
            new CrawlerResultCode(true, 20010010, "股票交易所类型不能为空");


    private static final BaseResultCode[] VALUES;

    static {
        VALUES = getStaticFieldValues(CrawlerResultCode.class);
    }

    private CrawlerResultCode(boolean success, int code, String message) {
        super(success, code, message);
    }

    public static BaseResultCode[] values() {
        return VALUES;
    }

    public static BaseResultCode getByCode(int code) {

        for (BaseResultCode typeEnum : values()) {
            if (typeEnum.getCode() == code) {
                return typeEnum;
            }
        }

        return null;
    }

    public static BaseResultCode getResultCodeByMessage(String message) {
        for (BaseResultCode resultCode : VALUES) {
            if (resultCode.getMessage().equals(message)) {
                return resultCode;
            }
        }
        return null;
    }

    public static List<Integer> getCodeList() {
        List<Integer> result = new ArrayList<>();
        for (BaseResultCode resultCode : values()) {
            result.add(resultCode.getCode());
        }
        return result;
    }
}
