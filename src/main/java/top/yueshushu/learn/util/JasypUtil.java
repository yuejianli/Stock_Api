package top.yueshushu.learn.util;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import top.yueshushu.learn.common.Const;

/**
 * @Description 配置文件加密，解密工具
 * @Author yuejianli
 * @Date 2022/6/5 6:45
 **/
public class JasypUtil {
    /**
     * 加密
     *
     * @param sourceText 要加密的源文本
     * @return 返回加密后的信息
     */
    public static String encrypt(String sourceText) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(Const.Jasypt_KEY);
        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor.encrypt(sourceText);
    }

    /**
     * 解密
     *
     * @param encryptText 要解密的加密文本
     * @return 返回解密后的信息
     */
    public static String decrypt(String encryptText) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(Const.Jasypt_KEY);
        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor.decrypt(encryptText);
    }
}
