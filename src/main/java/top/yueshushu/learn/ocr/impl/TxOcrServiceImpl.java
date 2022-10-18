package top.yueshushu.learn.ocr.impl;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import com.tencentcloudapi.ocr.v20181119.models.TextDetectRequest;
import com.tencentcloudapi.ocr.v20181119.models.TextDetectResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;
import top.yueshushu.learn.ocr.AbstractOcrService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service("txOcrService")
@Slf4j
public class TxOcrServiceImpl extends AbstractOcrService {

    @Value("${ocr.third.tx.app_id}")
    private String appId;

    @Value("${ocr.third.tx.secret_id}")
    private String secretId;

    @Value("${ocr.third.tx.secret_key}")
    private String secretKey;

    @Value("${ocr.path:/usr/app/ocr/}")
    private String filePath;

    @Override
    protected String processBase64(String base64) {
        try {
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
            // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
            Credential cred = new Credential(secretId, secretKey);
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("ocr.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            OcrClient client = new OcrClient(cred, "ap-beijing", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            // 实例化一个请求对象,每个接口都会对应一个request对象
            TextDetectRequest req = new TextDetectRequest();
            req.setImageBase64(base64);
            // 返回的resp是一个TextDetectResponse的实例，与请求对象对应
            TextDetectResponse resp = client.TextDetect(req);
            // 输出json格式的字符串回包
            String result = TextDetectResponse.toJsonString(resp);
            return result;
        } catch (TencentCloudSDKException e) {
            log.info(">>> tx ocr error :{}", e);
            return null;
        }
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
