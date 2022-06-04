package top.yueshushu.learn.message.weixin;

import top.yueshushu.learn.message.weixin.service.WeChatService;

/**
 * @Description TODO
 * @Author yuejianli
 * @Date 2022/6/4 16:27
 **/
public class WeiXinTest {
    public static void main(String[] args) {
        // 0.设置消息内容
        String content = "这是一条测试消息";
        //userId为企业用户的id
        String userId = "YueJianLi";
        WeChatService weChatService = new WeChatService();
        // 3.发送消息：调用业务类，发送消息
        weChatService.sendMessage(userId, content);
    }
}
