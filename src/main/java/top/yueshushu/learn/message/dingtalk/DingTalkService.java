package top.yueshushu.learn.message.dingtalk;

import cn.snowheart.dingtalk.robot.starter.client.DingTalkRobotClient;
import cn.snowheart.dingtalk.robot.starter.entity.DingTalkResponse;
import cn.snowheart.dingtalk.robot.starter.entity.MarkdownMessage;
import cn.snowheart.dingtalk.robot.starter.entity.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.domain.RebootDo;
import top.yueshushu.learn.domain.UserDo;
import top.yueshushu.learn.domainservice.RebootDomainService;
import top.yueshushu.learn.domainservice.UserDomainService;
import top.yueshushu.learn.enumtype.DataFlagType;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 钉钉发送消息
 *
 * @author yuejianli
 * @date 2023-01-11
 */
@Service
@Slf4j
public class DingTalkService {

    @Resource
    private UserDomainService userDomainService;
    @Resource
    private RebootDomainService rebootDomainService;

    @Autowired
    @Qualifier("dingTalkRobotClient")
    private DingTalkRobotClient dingTalkRebotClient;

    /**
     * 通过钉钉发送普通消息
     *
     * @param userId  微信用户标识
     * @param content 发送的内容
     * @return 通过微信企业号发送消息
     */
    public String sendTextMessage(Integer userId, String content) {
        if (userId == null) {
            RebootDo rebootDo = rebootDomainService.getById(Const.DEFAULT_REBOOT_ID);
            if (null == rebootDo || !DataFlagType.NORMAL.getCode().equals(rebootDo.getStatus())) {
                return null;
            }
            return sendTextMessageBySign(rebootDo.getWebhook(), null, rebootDo.getParam() + content);
        }

        UserDo userDo = userDomainService.getById(userId);
        if (null == userDo) {
            return null;
        }
        String dingUserId = userDo.getDingUserId();
        if (!StringUtils.hasText(dingUserId) || userDo.getRebootId() == null) {
            return null;
        }
        // 查询一下 reboot 配置
        RebootDo rebootDo = rebootDomainService.getById(userDo.getRebootId());
        if (null == rebootDo || !DataFlagType.NORMAL.getCode().equals(rebootDo.getStatus())) {
            return null;
        }
        return sendTextMessageBySign(rebootDo.getWebhook(), dingUserId, rebootDo.getParam() + content);
    }

    /**
     * 通过钉钉发送普通消息
     *
     * @param webhook    标识
     * @param dingUserId 钉钉用户标识
     * @param content    发送的内容
     * @return 通过微信企业号发送消息
     */
    public String sendTextMessageBySign(String webhook, String dingUserId, String content) {
        // 动态的设置 webhook
        TextMessage textMessage;
        if (StringUtils.hasText(dingUserId)) {
            List<String> userSignList = new ArrayList<>();
            userSignList.add(dingUserId);
            String[] userArr = userSignList.toArray(new String[userSignList.size()]);
            textMessage = new TextMessage(content, userArr);
        } else {
            textMessage = new TextMessage(content);
        }
        DingTalkResponse response = dingTalkRebotClient.sendMessageByURL(webhook, textMessage);
        long code = response.getErrcode().longValue();
        return code == 0 ? "1" : "0";
    }

    /**
     * 通过钉钉发送 markdown 消息
     *
     * @param userId  微信用户标识
     * @param title   标题
     * @param content 发送的内容，支持 html 格式
     * @return 通过微信企业号发送消息
     */
    public String sendMarkdownMessage(Integer userId, String title, String content) {
        if (userId == null) {
            RebootDo rebootDo = rebootDomainService.getById(Const.DEFAULT_REBOOT_ID);
            if (null == rebootDo || !DataFlagType.NORMAL.getCode().equals(rebootDo.getStatus())) {
                return null;
            }
            return sendMarkdownMessageBySign(rebootDo.getWebhook(), null, rebootDo.getParam() + title, rebootDo.getParam() + content);
        }
        UserDo userDo = userDomainService.getById(userId);
        if (null == userDo) {
            return null;
        }
        String dingUserId = userDo.getDingUserId();
        if (!StringUtils.hasText(dingUserId) || userDo.getRebootId() == null) {
            return null;
        }
        // 查询一下 reboot 配置
        RebootDo rebootDo = rebootDomainService.getById(userDo.getRebootId());
        if (null == rebootDo || !DataFlagType.NORMAL.getCode().equals(rebootDo.getStatus())) {
            return null;
        }
        return sendMarkdownMessageBySign(rebootDo.getWebhook(), dingUserId, rebootDo.getParam() + title, rebootDo.getParam() + content);
    }


    /**
     * 通过钉钉发送 markdown 消息
     *
     * @param webhook    标识
     * @param dingUserId 微信用户标识
     * @param title      标题
     * @param content    发送的内容，支持 html 格式
     * @return 通过微信企业号发送消息
     */
    public String sendMarkdownMessageBySign(String webhook, String dingUserId, String title, String content) {

        // 动态的设置 webhook
        MarkdownMessage markdownMessage;
        if (StringUtils.hasText(dingUserId)) {
            List<String> userSignList = new ArrayList<>();
            userSignList.add(dingUserId);
            String[] userArr = userSignList.toArray(new String[userSignList.size()]);
            markdownMessage = new MarkdownMessage(title, content, userArr);
        } else {
            markdownMessage = new MarkdownMessage(title, content);
        }
        DingTalkResponse response = dingTalkRebotClient.sendMessageByURL(webhook, markdownMessage);
        long code = response.getErrcode().longValue();
        return code == 0 ? "1" : "0";
    }
}
