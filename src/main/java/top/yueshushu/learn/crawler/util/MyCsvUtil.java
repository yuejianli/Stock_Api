package top.yueshushu.learn.crawler.util;

import cn.hutool.core.text.csv.CsvReadConfig;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvUtil;
import top.yueshushu.learn.crawler.entity.StockHistoryCsvInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @ClassName:MyCsvUtil
 * @Description csv工具转换
 * @Author 岳建立
 * @Date 2021/11/14 10:42
 * @Version 1.0
 **/
public class MyCsvUtil {
    public static <T> List<T>readFile(InputStream inputStream, Class<T> clazz ) throws Exception{
        //2. 进行配置
        CsvReadConfig csvReadConfig=new CsvReadConfig();
        csvReadConfig.setSkipEmptyRows(true);
        csvReadConfig.setContainsHeader(true);
        //构建 CsvReader 对象
        CsvReader csvReader = CsvUtil.getReader();
        //直接读取，封装成 Bean
        return csvReader.read(
                new InputStreamReader(inputStream, "GBK"), clazz);
    }

    public static List<StockHistoryCsvInfo> readFile(File downloadFile, Class<StockHistoryCsvInfo> clazz)
    throws Exception{
        //2. 进行配置
        CsvReadConfig csvReadConfig=new CsvReadConfig();
        csvReadConfig.setSkipEmptyRows(true);
        csvReadConfig.setContainsHeader(true);
        //构建 CsvReader 对象
        CsvReader csvReader = CsvUtil.getReader();
        //直接读取，封装成 Bean
        return csvReader.read(
                new InputStreamReader(
                        new FileInputStream(downloadFile), "GBK"), clazz);

    }
}
