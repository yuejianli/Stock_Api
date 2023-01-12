package top.yueshushu.learn.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.yueshushu.learn.message.dingtalk.DingTalkService;

import javax.annotation.Resource;

/**
 * 用途描述
 *
 * @author yuejianli
 * @date 2023-01-11
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class DingTalkTest {

    @Resource
    private DingTalkService dingTalkService;


    @Test
    public void dingTalkTextMessageTest() {
        // 0.设置消息内容
        String content = "<h2>这是一条测试文本消息</h2> <h4>h4标签</h4>";
        //userId为企业用户的id
        Integer userId = 1;
        // 3.发送消息：调用业务类，发送消息
        dingTalkService.sendTextMessage(userId, content);
        dingTalkService.sendTextMessage(null, content);
    }

    @Test
    public void dingTalkMarkdownMessageTest() {
        // 0.设置消息内容
        String content = "业务提醒: <div class=\\\"gray\\\">2016年9月26日</div> <div class=\\\"normal\\\">恭喜你抽中iPhone 7一台，领奖码：xxxx</div><div class=\\\"highlight\\\">请于2016年10月10日前联系行政同事领取</div>";
        //userId为企业用户的id
        Integer userId = 2;
        // 3.发送消息：调用业务类，发送消息
        dingTalkService.sendMarkdownMessage(userId, "业务提醒: 具体的内容", content);
    }

}
