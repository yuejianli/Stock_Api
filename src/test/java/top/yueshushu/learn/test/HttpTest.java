package top.yueshushu.learn.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Test;
import org.springframework.util.FileCopyUtils;
import top.yueshushu.learn.crawler.util.HttpUtil;
import top.yueshushu.learn.ocr.OcrService;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

/**
 * 用途描述
 *
 * @author yuejianli
 * @date 2022-10-17
 */
@Slf4j
public class HttpTest extends BaseTest {

    @Resource
    private CloseableHttpClient httpClient;
    @Resource(name = "txOcrService")
    private OcrService ocrService;

    @Test
    public void yzmTest() {
        String url = "https://jywg.18.cn/Login/YZM?randNum=0.9031652239783832&_ra=0.48314645419752167";
        try (CloseableHttpResponse response = HttpUtil.sendGetResponse(httpClient, url)) {
            InputStream in = response.getEntity().getContent();
            process(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void process(InputStream in) throws IOException {
        byte[] buf = FileCopyUtils.copyToByteArray(in);
        String base64 = Base64.encodeBase64String(buf);
        log.info(">>> base64 :{}", base64);
    }

    @Test
    public void parseYzmTest() {
        String url = "https://jywg.18.cn/Login/YZM?randNum=0.9031652239783832&_ra=0.48314645419752167";
        String result = ocrService.process(url);
        log.info(">>> 解析数据: {}", result);
    }


}
