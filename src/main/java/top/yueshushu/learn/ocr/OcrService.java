package top.yueshushu.learn.ocr;

/**
 * Ocr 识别
 *
 * @author Yue Jianli
 * @date 2022-10-17
 */

public interface OcrService {
    String process(String url);

    String processBase64(String base64);
}
