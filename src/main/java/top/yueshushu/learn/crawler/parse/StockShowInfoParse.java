package top.yueshushu.learn.crawler.parse;
import org.jsoup.select.Elements;
import top.yueshushu.learn.mode.info.StockShowInfo;

/**
 * @Description 股票元素数据处理
 * @Author 岳建立
 * @Date 2021/11/10 22:40
 **/
public interface StockShowInfoParse {
    /**
     * 解析元素信息
     * @param elements 元素信息
     * @return 解析元素信息
     */
    default StockShowInfo parse(Elements elements){
        return new StockShowInfo();
    };

    /**
     * 将接口内容转换成股票展示信息，是接口方式
     * @param content 内容
     * @return 将接口内容转换成股票展示信息，是接口方式
     */
    default StockShowInfo parse(String content){
        return new StockShowInfo();
    };
}
