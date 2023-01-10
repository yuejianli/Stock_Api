package top.yueshushu.learn.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.util.RSAUtil;
import top.yueshushu.learn.util.RedisUtil;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;

/**
 * 用途描述
 *
 * @author yuejianli
 * @date 2022-10-13
 */
@Component
@Slf4j
public class InitDataMethods {
    @Resource
    private RedisUtil redisUtil;

    public static final String PUBLIC_KEY_STRING = "publicKeyString";
    public static final String PRIVATE_KEY_STRING = "privateKeyString";

    @PostConstruct
    public void initUserId() {
        redisUtil.set(Const.KEY_AUTH_USER_ID, 1);
        log.info(">>>>初始化用户编号信息 成功");
    }

    @PostConstruct
    public void initRsaKey() {
        try {
            // 如果没有公钥，私钥 才进行生成.
            if (!ObjectUtils.isEmpty(redisUtil.get(Const.RSA_PUBLIC_KEY))) {
                return;
            }
            //生成密钥对
            Map<String, String> keys = RSAUtil.generateKeyPair1();
            //得到公钥
            String publicKeyStr = keys.get(PUBLIC_KEY_STRING).replaceAll("[\\s*\t\r\n]", "");
            //得到私钥
            String privateKeyStr = keys.get(PRIVATE_KEY_STRING).replaceAll("[\\s*\t\r\n]", "");

            // 将公钥和私钥中的某个字符进行修改，避免拿到公钥和私钥后进行解密。
            publicKeyStr = publicKeyStr.replaceFirst("X", "X+");
            privateKeyStr = privateKeyStr.replaceFirst("X", "X+");

            redisUtil.set(Const.RSA_PUBLIC_KEY, publicKeyStr);
            redisUtil.set(Const.RSA_PRIVATE_KEY, privateKeyStr);
        } catch (Exception e) {
            log.error(">>>> 初始化公钥私钥错误");
        }
    }
}
