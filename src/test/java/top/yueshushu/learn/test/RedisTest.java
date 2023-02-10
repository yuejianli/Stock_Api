package top.yueshushu.learn.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.yueshushu.learn.service.cache.StockCacheService;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author yuejianli
 * @date 2022-06-10
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {

    @Resource
    private StockCacheService stockCacheService;

    @Test
    public void ztCodeListTest() {
        List<String> codeList = Arrays.asList("002415", "002212");
        stockCacheService.setYesZtCodeList(codeList);

        List<String> yesZtCodeList = stockCacheService.getYesZtCodeList();

        System.out.println(yesZtCodeList.toString());
    }

}
