package top.yueshushu.learn.ocr.impl;

import com.baidu.aip.ocr.AipOcr;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;
import top.yueshushu.learn.ocr.AbstractOcrService;
import top.yueshushu.learn.util.ThreadLocalUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

@Service("baiduOcrService")
@Slf4j
public class BaiduOcrServiceImpl extends AbstractOcrService {

    @Value("${ocr.third.baidu.app_id}")
    private String appId;

    @Value("${ocr.third.baidu.app_key}")
    private String appKey;

    @Value("${ocr.third.baidu.secret_key}")
    private String secretKey;

    @Value("${ocr.path:/usr/app/ocr/}")
    private String filePath;

    @Override
    protected String processBase64(String base64) {
        AipOcr aipOcr = new AipOcr(appId, appKey, secretKey);
        //建立连接的超时时间（单位：毫秒）
        aipOcr.setConnectionTimeoutInMillis(2000);
        //通过打开的连接传输数据的超时时间（单位：毫秒）
        aipOcr.setSocketTimeoutInMillis(60000);

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("language_type", "CHN_ENG");//识别语言类型，默认为中英文混合
        options.put("detect_direction", "true");//是否检查图片朝向，默认false不检查
        options.put("detect_language", "true");//是否检查语言，默认false不检查
        options.put("probability", "true");//是否返回识别结果中每一行的置信度

        // 调用接口，返回JSON格式数据
        String saveFilePath = filePath + "yzm_" + ThreadLocalUtils.getUserId() + ".png";
        base64ToImage(base64, saveFilePath);
        System.out.println("result: ");
        return null;
    }

    public static boolean base64ToImage(String imgBase64, String filePath) {

        File pngFile = new File(filePath);

        if (!pngFile.getParentFile().exists()) {
            pngFile.getParentFile().mkdirs();
        }

        BASE64Decoder decoder = new BASE64Decoder();
        //Base64解码
        byte[] b = new byte[0];
        try {
            b = decoder.decodeBuffer(imgBase64);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    //调整异常数据
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(filePath);
            out.write(b);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            //失败
            return false;
        }
        //成功
        return true;
    }
}
