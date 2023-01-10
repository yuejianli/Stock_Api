package top.yueshushu.learn.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import top.yueshushu.learn.util.RSAUtil;

import java.time.LocalTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 简单的方法测试
 *
 * @author yuejianli
 * @date 2022-11-16
 */
@Slf4j
public class SimpleTest {

    private LocalTime STOCK_PRICE_START_TIME = LocalTime.parse("14:59:00");
    private LocalTime STOCK_PRICE_END_TIME = LocalTime.parse("15:01:00");
    public static final String PUBLIC_KEY_STRING = "publicKeyString";
    public static final String PRIVATE_KEY_STRING = "privateKeyString";

    @Test
    public void timeTest() throws Exception {
        // 进行延迟， 如果时间在 14:59 之后，则睡眠 1分钟。
        log.info(">>>>>执行开始");
        LocalTime now = LocalTime.now();
        if (now.isAfter(STOCK_PRICE_START_TIME) && now.isBefore(STOCK_PRICE_END_TIME)) {
            // 进行休眠一分钟
            TimeUnit.SECONDS.sleep(5);
        }
        log.info(">>>>>执行结束");
    }

    @Test
    public void rsaTest() throws Exception {

        //生成密钥对
        Map<String, String> keys = RSAUtil.generateKeyPair1();
        //得到公钥
        String publicKeyStr = keys.get(PUBLIC_KEY_STRING).replaceAll("[\\s*\t\r\n]", "");
        //得到私钥
        String privateKeyStr = keys.get(PRIVATE_KEY_STRING).replaceAll("[\\s*\t\r\n]", "");

        // 将公钥和私钥中的某个字符进行修改，避免拿到公钥和私钥后进行解密。
        publicKeyStr = publicKeyStr.replaceFirst("X", "X+");
        privateKeyStr = privateKeyStr.replaceFirst("X", "X+");

        log.info(">>> 保存到缓存中的: {}, {}", publicKeyStr, privateKeyStr);

        // 对公钥加密
        publicKeyStr = publicKeyStr.replaceFirst("X\\+", "X");
        privateKeyStr = privateKeyStr.replaceFirst("X\\+", "X");

        log.info(">>> 获取使用的: {}, {}", publicKeyStr, privateKeyStr);

        String scode = RSAUtil.encryptByPublicKey(publicKeyStr, "123456");

        // 将其进行解密
        String originCode = RSAUtil.decryptByPrivateKey(privateKeyStr, scode);

        // 验证
        Assert.assertEquals("123456", originCode);


    }
}
