package top.yueshushu.learn.message.weixin.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @Description 默认的微信配置
 * @Author yuejianli
 * @Date 2022/6/4 17:29
 **/
@Data
@Component
public class DefaultWXProperties implements Serializable {

    @Value("${weixin.corpId}")
    private String corpId;

    @Value("${weixin.coprsecret}")
    private String coprsecret;

    @Value("${weixin.agentId}")
    private Integer agentId;

}
