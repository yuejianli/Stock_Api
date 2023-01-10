package top.yueshushu.learn.api.responseparse;

import com.alibaba.fastjson.TypeReference;
import top.yueshushu.learn.api.TradeResultVo;

/**
 * @ClassName:ResponseParse
 * @Description 默认的响应解析器
 * @Author 岳建立
 * @Date 2022/1/2 21:49
 * @Version 1.0
 **/
public interface ResponseParser {
    /**
     * 将内容进行解析
     * @param content
     * @param responseType
     * @param <T>
     * @return
     */
    <T> TradeResultVo<T> parse(String content, TypeReference<T> responseType);
    /**
     * 解析器的名称
     *
     * @return
     */
    String name();

    int version();
}
