package top.yueshushu.learn.test;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.yueshushu.learn.crawler.entity.TxStockHistoryInfo;
import top.yueshushu.learn.crawler.parse.DailyTradingInfoParse;
import top.yueshushu.learn.helper.DateHelper;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @Description TODO
 * @Author yuejianli
 * @Date 2022/10/16 9:24
 **/
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class DailyTradingInfoParseTest {

    @Resource
    private DailyTradingInfoParse dailyTradingInfoParse;
    @Resource
    private DateHelper dateHelper;

    @Test
    public void parseTxMoneyHistoryTest() {
        List<String> codeList = Arrays.asList("sz002812", "sz002415");
        DateTime beforeLastWorking = dateHelper.getBeforeLastWorking(DateUtil.offsetDay(DateUtil.date(), -1));

        //内容处理

        String content = "v_sz002812=\"51~恩捷股份~002812~175.76~176.50~179.50~64869~32300~32569~175.76~11~175.75~8~175.73~1~175.72~10~175.70~9~175.78~1~175.80~8~175.81~12~175.82~36~175.84~84~~20221014161418~-0.74~-0.42~179.50~171.06~175.76/64869/1129392874~64869~112939~0.87~42.54~~179.50~171.06~4.78~1304.21~1568.50~10.09~194.15~158.85~1.15~-102~174.10~38.83~57.72~~~1.18~112939.2874~0.0000~0~\n" +
                "~GP-A~-29.72~0.94~0.17~23.64~12.38~313.43~161.70~-1.62~-5.11~-26.46~742041973~892409275~-56.67~-29.87~742041973~~~-33.55~-0.02~\";\n" +
                "v_sz002415=\"51~海康威视~002415~30.28~29.50~29.73~544783~294255~250528~30.28~194~30.27~620~30.26~750~30.25~712~30.24~290~30.29~1341~30.30~2889~30.31~328~30.32~353~30.33~296~~20221014161427~0.78~2.64~30.60~29.35~30.28/544783/1639917704~544783~163992~0.59~17.77~~30.60~29.35~4.24~2778.39~2856.38~4.61~32.45~26.55~1.41~-2641~30.10~24.80~17.00~~~0.99~163991.7704~0.0000~0~\n" +
                "~GP-A~-41.11~-0.46~2.97~25.93~16.01~55.62~28.10~4.23~5.69~-7.51~9175672118~9433208719~-33.98~-37.37~9175672118~~~-45.33~-0.03~\";";

        List<TxStockHistoryInfo> txStockHistoryInfos = dailyTradingInfoParse.parseTxMoneyHistory(content, codeList, beforeLastWorking);

        if (CollectionUtil.isEmpty(txStockHistoryInfos)) {
            log.info(">>> 未解析出数据");
            return;
        }
        txStockHistoryInfos.forEach(
                n -> {
                    log.info("解析股票数据 {}", n);
                }
        );

    }


}
