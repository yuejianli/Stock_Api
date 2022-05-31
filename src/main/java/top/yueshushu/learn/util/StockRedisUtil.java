package top.yueshushu.learn.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.common.SystemConst;
import top.yueshushu.learn.mode.dto.StockPriceCacheDto;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @ClassName:StockUtil
 * @Description 股票的信息与缓存的工具类
 * @Author 岳建立
 * @Date 2022/1/8 9:51
 * @Version 1.0
 **/
@Component
public class StockRedisUtil {
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 更新股票的价格信息
     * @param code 股票编码
     * @param price 股票的价钱
     * @return
     */
    public boolean setPrice(String code,BigDecimal price){
        //放置股票的信息
       return setKeyValue(buildKey(code),price);
    }

    private boolean setKeyValue(String key,BigDecimal price){
        return redisUtil.set(key,
                price);
    }
    private BigDecimal getKey(String key){
        //放置股票的信息
        return (BigDecimal) Optional.ofNullable(redisUtil.get(key))
                .orElse(
                        SystemConst.DEFAULT_EMPTY
                );
    }
    /**
     * 获取股票的价格信息
     * @param code
     * @return
     */
    public BigDecimal getPrice(String code){
        //放置股票的信息
        return getKey(buildKey(code));
    }
    /**
     * 移除价钱
     * @param code
     * @return
     */
    public Boolean removePrice(String code){
        //放置股票的信息
        return removeKey(buildKey(code));
    }
    private String buildKey(String code){
        return Const.STOCK_PRICE+code;
    }

    private String buildYesKey(String code){
        return Const.STOCK_YES_PRICE+code;
    }

    public void setYesPrice(String code, BigDecimal price) {
        //放置股票昨天的价格信息
        setKeyValue(buildYesKey(code),price);
    }
    public BigDecimal getYesPrice(String code) {
        return getKey(buildYesKey(code));
    }
    /**
     * 移除价钱
     * @param code
     * @return
     */
    public Boolean removeYesPrice(String code){
        //放置股票的信息
        return removeKey(buildYesKey(code));
    }
    private Boolean removeKey(String key){
        return redisUtil.remove(key);
    }
}
