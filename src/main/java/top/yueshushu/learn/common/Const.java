package top.yueshushu.learn.common;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName:Const
 * @Description 定义一些常量
 * @Author 岳建立
 * @Date 2022/1/2 10:51
 * @Version 1.0
 **/
public class Const {
    public static final String SALT = "twoButterfly";
    public static final String RAND_NUMBER_PREFIX = "0.903";
    public static final String Authorization = "Authorization";
    public static final Integer DEFAULT_NO = 0;
    public static final Integer DEFAULT_USER_ID = 1;
    public static final String TRADE_PASSWORD_AES_KEY = "yuezelinyuezelin";

    public static final String CACHE_PUBLIC_KEY_PREFIX = "stock:public:";
    public static final String CACHE_PRIVATE_KEY_PREFIX = "stock:private:";
    public static final String CACHE_WE_CHAT = "stock:wechat";

    public static final String STOCK_PRICE = CACHE_PUBLIC_KEY_PREFIX;
    public static final String STOCK_YES_PRICE = CACHE_PUBLIC_KEY_PREFIX + "yes:";
    public static final String STOCK_BUY_PRICE = CACHE_PUBLIC_KEY_PREFIX + "buy:";
    public static final String STOCK_SELL_PRICE = CACHE_PUBLIC_KEY_PREFIX + "sell:";

    public static final String X_REAL_IP = "x-real-ip";
    public static final int TOKEN_EXPIRE_TIME = 7 * 24 * 3600;

    /**
     * 定义日期的格式
     */
    public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
    public static final String STOCK_DATE_FORMAT = "yyyyMMdd";



    public static final String ASYNC_SERVICE_EXECUTOR_BEAN_NAME = "asyncServiceExecutor";
    public static final String HOLIDAY_CALENDAR_CACHE = "holiday_calendar";

    public static final String KEY_AUTH_USER_ID = "user_id";

    public static final String KEY_AUTH_TOKEN = "auth-token";



    private static final String CACHE_KEY_DATA_PREFIX = CACHE_PUBLIC_KEY_PREFIX + "data:";
    public static final String CACHE_KEY_DATA_STOCK = Const.CACHE_KEY_DATA_PREFIX + "stock";

    private static final String CACHE_KEY_CONFIG_PREFIX = CACHE_PUBLIC_KEY_PREFIX + "config:";
    public static final String CACHE_KEY_CONFIG_ROBOT = Const.CACHE_KEY_CONFIG_PREFIX + "robot";

    private static final String CACHE_KEY_TRADE_PREFIX = CACHE_PUBLIC_KEY_PREFIX + "trade:";
    public static final String CACHE_KEY_TRADE_USER = Const.CACHE_KEY_TRADE_PREFIX + "tradeUser";
    public static final String CACHE_KEY_TRADE_METHOD = Const.CACHE_KEY_TRADE_PREFIX + "tradeMethod";

    public static final String AUTH = "auth:";

    public static final String CACHE_KEY_TRADE_STRATEGY = CACHE_PUBLIC_KEY_PREFIX + "trade:tradeStrategy";

    public static final long DURATION_REDIS_DEFAULT = 3600 * 24 * 2;


    public static final List<String> IgnoreList = Arrays.asList("class", "userId", "method");

    public static String getCacheKeyPrefix(Integer userId){
        return CACHE_PUBLIC_KEY_PREFIX +userId+":";
    }
}
