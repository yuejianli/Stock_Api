package top.yueshushu.learn.test;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.yueshushu.learn.helper.DateHelper;

import javax.annotation.Resource;

/**
 * 日期测试
 * @author Yue Jianli
 * @date 2022-05-31
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class TimeDateTest {

    @Resource
    private DateHelper dateHelper;

    @Test
    public void workDayTest(){
        boolean workingDay = dateHelper.isWorkingDay(DateUtil.date());
        log.info("是否是工作日{}",workingDay);
        boolean tradeTime = dateHelper.isTradeTime(DateUtil.date());
        log.info("是否是交易时间{}",tradeTime);
    }
}
