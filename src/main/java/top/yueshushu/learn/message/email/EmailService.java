package top.yueshushu.learn.message.email;

import java.util.Map;

import top.yueshushu.learn.enumtype.message.VelocityTemplateType;

/**
 * 邮件发送的接口信息
 *
 * @author yuejianli
 * @date 2022-06-09
 */

public interface EmailService {
	/**
	 * 发送普通的文本文件
	 *
	 * @param toArr   发送人， 之间用 ,号分隔
	 * @param subject 发送主题
	 * @param content 发送的内容, 普通文本内容
	 */
	boolean sendSimpleMail(String[] toArr, String subject, String content);
	
	/**
	 * 发送 HTML 文件
	 *
	 * @param toArr   发送人， 之间用 ,号分隔
	 * @param subject 发送主题
	 * @param content 发送的内容 ,html 形式
	 */
	boolean sendHtmlMail(String[] toArr, String subject, String content);
	
	/**
	 * 发送邮件 velocity 模板邮件
	 *
	 * @param toArr                发送人
	 * @param subject              主题
	 * @param velocityTemplateType 邮件类型
	 * @param dataMap              发送模板邮件填充数据
	 */
	boolean sendVelocityMail(String[] toArr, String subject, VelocityTemplateType velocityTemplateType, Map<String, Object> dataMap);
}
