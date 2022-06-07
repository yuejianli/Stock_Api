package top.yueshushu.learn.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import top.yueshushu.learn.message.weixin.service.WeChatService;

/**
 * @Description TODO
 * @Author yuejianli
 * @Date 2022/6/4 16:27
 **/
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class WeiXinTest {
    @Resource
    private WeChatService weChatService;
    
    @Test
    public void weixin() {
        // 0.设置消息内容
        String content = "这是一条测试消息";
        //userId为企业用户的id
        String userId = "YueJianLi";
        // 3.发送消息：调用业务类，发送消息
        weChatService.sendMessage(userId, content);
    }
}
