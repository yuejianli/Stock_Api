package top.yueshushu.learn.message.weixin.model;

import lombok.Data;

/**
 * @Description 文本是否加密的信息
 * @Author yuejianli
 * @Date 2022/6/4 16:17
 **/
@Data
public class MarkDownMessage extends BaseMessage {
	/**
	 * 文本
	 */
	private WxMarkdown markdown;
}
