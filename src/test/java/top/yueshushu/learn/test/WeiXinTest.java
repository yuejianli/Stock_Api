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
	public void wxTextMessageTest() {
		// 0.设置消息内容
		String content = "<h2>这是一条测试文本消息</h2> <h4>h4标签</h4>";
		//userId为企业用户的id
		String userId = "YueJianLi";
		// 3.发送消息：调用业务类，发送消息
		weChatService.sendTextMessage(userId, content);
	}
	
	@Test
	public void wxMarkdownMessageTest() {
		// 0.设置消息内容
		String content = "<div class=\\\"gray\\\">2016年9月26日</div> <div class=\\\"normal\\\">恭喜你抽中iPhone 7一台，领奖码：xxxx</div><div class=\\\"highlight\\\">请于2016年10月10日前联系行政同事领取</div>";
		//userId为企业用户的id
		String userId = "YueJianLi";
		// 3.发送消息：调用业务类，发送消息
		weChatService.sendTextCardMessage(userId, "具体的内容", content);
	}
}
