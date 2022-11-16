package top.yueshushu.learn;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

/**
 * @ClassName:StockApplication
 * @Description 股票小工具
 * @Author 岳建立
 * @Date 2021/4/26 12:01
 * @Version 1.0
 **/
@SpringBootApplication
@MapperScan({"top.yueshushu.learn.mapper", "top.yueshushu.learn.extension.mapper"})
@Slf4j
//开启缓存
@EnableCaching
@EnableAsync
@EnableScheduling
public class StockApplication {
    public static void main(String[] args) {
        // 设置为 +8 时区
        TimeZone china = TimeZone.getTimeZone("GMT+:08:00");
        TimeZone.setDefault(china);
        SpringApplication.run(StockApplication.class, args);
    }
}
