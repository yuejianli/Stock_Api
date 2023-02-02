package top.yueshushu.learn.crawler.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @ClassName:EasyMoneyProperties
 * @Description 扩展的配置信息
 * @Author 岳建立
 * @Date 2021/11/7 10:35
 * @Version 1.0
 **/
@Data
@Component
public class ExtendProperties implements Serializable {
    /**
     * 热点图 hotmap
     */
    @Value("${extend.hotmap}")
    private String hotMapUrl;

    /**
     * 主力资金今日注入
     */
    @Value("${extend.todayPlatMoney}")
    private String todayPlatMoneyUrl;

}
