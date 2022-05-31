package top.yueshushu.learn.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

/**
 * 系统启动后，打印输出
 * @author Yue Jianli
 * @date 2022-05-31
 */

@Component
@Order(1)
@Slf4j
public class InitCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {
        log.info(">>>>Stock 项目启动成功");
    }
}