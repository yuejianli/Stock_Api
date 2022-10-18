package top.yueshushu.learn.ocr;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import top.yueshushu.learn.crawler.util.HttpUtil;

import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractOcrService implements OcrService {

    @Autowired
    protected CloseableHttpClient httpClient;

    @Override
    public String process(String url) {
        try (CloseableHttpResponse response = HttpUtil.sendGetResponse(httpClient, url)) {
            InputStream in = response.getEntity().getContent();
            return process(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String process(InputStream in) throws IOException {
        byte[] buf = FileCopyUtils.copyToByteArray(in);
        String base64 = Base64.encodeBase64String(buf);
        return processBase64(base64);
    }

    protected abstract String processBase64(String base64);

}
