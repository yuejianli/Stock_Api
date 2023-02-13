package top.yueshushu.learn.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ObjectUtils;
import top.yueshushu.learn.service.cache.StockCacheService;
import top.yueshushu.learn.util.RedisUtil;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author yuejianli
 * @date 2022-06-10
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {

    @Resource
    private StockCacheService stockCacheService;
    @Resource
    private RedisUtil redisUtil;

    @Test
    public void ztCodeListTest() {
        List<String> codeList = Arrays.asList("002415", "002212");
        stockCacheService.setYesZtCodeList(codeList);

        List<String> yesZtCodeList = stockCacheService.getYesZtCodeList();

        System.out.println(yesZtCodeList.toString());
    }

    @Test
    public void qsSetListTest() {
        String key = "stock_public:todayQs";
        // 获取到今日的已经发送的强势股票
        Object qsRangeList = redisUtil.sMembers(key);
        Set<String> codeList;
        if (ObjectUtils.isEmpty(qsRangeList)) {
            codeList = Collections.emptySet();
        } else {
            codeList = (Set<String>) qsRangeList;
        }
        System.out.println(codeList);
        List<String> poolList = Arrays.asList("008", "0332", "02", "03", "04", "05");
        List<String> result = new ArrayList<>();
        for (String stockPoolInfo : poolList) {
            // 放置进去
            result.add(stockPoolInfo);
        }
        Set<String> addCodeList = new HashSet<>(result);
        redisUtil.sAdd(key, addCodeList.toArray(new String[]{}));
    }
}
