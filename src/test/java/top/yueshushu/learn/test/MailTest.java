package top.yueshushu.learn.test;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import top.yueshushu.learn.enumtype.message.VelocityTemplateType;
import top.yueshushu.learn.message.email.EmailService;

/**
 * 用途描述
 *
 * @author yuejianli
 * @date 2022-06-10
 */
@Slf4j
public class MailTest extends BaseTest {
	@Resource
	private EmailService emailService;
	
	@Test
	public void simpleEmailTest() {
		String[] toArr = new String[]{"1290513799@qq.com"};
		emailService.sendSimpleMail(toArr, "发送测试简单文件", "一封简单的测试文件");
		log.info(">>>发送邮件成功");
	}
	
	@Test
	public void htmlEmailTest() {
		String[] toArr = new String[]{"1290513799@qq.com"};
		emailService.sendHtmlMail(toArr, "发送测试HTML文件", "<a href='https://wwww.baidu.com'>百度</a>");
		log.info(">>>发送邮件成功");
	}
	
	@Test
	public void velocityTest() {
		String[] toArr = new String[]{"1290513799@qq.com"};
		Map<String, Object> dataMap = new HashMap<>();
		emailService.sendVelocityMail(toArr, "发送velocity -test 文件", VelocityTemplateType.TEST, dataMap);
		log.info(">>>发送测试邮件成功");
	}
}
