package top.yueshushu.learn.crawler.util;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @ClassName:ImageUtil
 * @Description 图片工具类
 * @Author 岳建立
 * @Date 2021/11/12 22:02
 **/
@Slf4j
public class ImageUtil {
    /**
     * 根据地址获得数据的字节流
     *
     * @param strUrl
     *            网络连接地址
     * @return
     */
    public static byte[] getImageFromNetByUrl(String strUrl) {
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            // 通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
            // 得到图片的二进制数据
            byte[] btImg = readInputStream(inStream);
            return btImg;
        } catch (Exception e) {
            log.error("异常信息", e);
            ;
        }
        return null;
    }
    /**
     * 根据地址获得数据的字节流
     *
     * @param strUrl
     *            本地连接地址
     * @return
     */
    public static byte[] getImageFromLocalByUrl(String strUrl) {
        try {
            File imageFile = new File(strUrl);
            InputStream inStream = new FileInputStream(imageFile);
            // 得到图片的二进制数据
            byte[] btImg = readInputStream(inStream);
            return btImg;
        } catch (Exception e) {
            log.error("异常信息", e);
        }
        return null;
    }
    /**
     * 从输入流中获取数据
     *
     * @param inStream
     *            输入流
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[10240];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }
}
