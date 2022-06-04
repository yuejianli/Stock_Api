package top.yueshushu.learn.crawler.parse.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.crawler.entity.StockHistoryCsvInfo;
import top.yueshushu.learn.crawler.parse.DailyTradingInfoParse;
import top.yueshushu.learn.crawler.util.MyCsvUtil;
import top.yueshushu.learn.mode.dto.EasyMoneyHistoryVo;
import top.yueshushu.learn.mode.info.StockInfo;
import top.yueshushu.learn.util.BigDecimalUtil;
import top.yueshushu.learn.util.StockUtil;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName:DefaultDailyTradingInfoParse
 * @Description 默认的每天交易信息处理器
 * @Author 岳建立
 * @Date 2021/11/10 21:15
 * @Version 1.0
 **/
@Service("defaultDailyTradingInfoParse")
public class DefaultDailyTradingInfoParse implements DailyTradingInfoParse {

    @Value("${uploadFilePath:D:/upload/}")
    private String uploadFilePath;

    @Override
    public List<StockHistoryCsvInfo> parseStockHistoryList(InputStream inputStream) {
        //定义一个默认的文件路径
        Long fileName = DateUtil.date().getTime();
        if (!(uploadFilePath.endsWith("/") || uploadFilePath.endsWith("\\"))) {
            uploadFilePath = uploadFilePath + File.separator;
        }
        File downloadFile = new File(uploadFilePath + fileName + ".csv");
        //将数据保存到文件里面
        FileUtil.writeFromStream(inputStream, downloadFile);
        //将文件写入进去
        List<StockHistoryCsvInfo> stockHistoryCsvInfos = null;
        try {
            stockHistoryCsvInfos = MyCsvUtil.readFile(downloadFile, StockHistoryCsvInfo.class);
            stockHistoryCsvInfos.stream().forEach(
                    n -> {
                        n.setCode(n.getCode().replace("'", ""));
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        //删除文件
        downloadFile.delete();
        return stockHistoryCsvInfos;
    }

    @Override
    public List<StockHistoryCsvInfo> parseEasyMoneyHistory(String content, List<String> codeList, DateTime beforeLastWorking) {
        char[] chArr = content.toCharArray();
        char[] newCharArr = new char[chArr.length];
        int i = 0;
        for (char ch : chArr) {
            if (ch == ' ') {
                continue;
            }
            if (ch == 'Ａ') {
                ch = 'A';
            } else if (ch == 'Ｂ') {
                ch = 'B';
            }
            newCharArr[i++] = ch;
        }

        EasyMoneyHistoryVo.StockResultVo stockResultVo = JSON.parseObject(new String(newCharArr, 0, i),
                EasyMoneyHistoryVo.StockResultVo.class);
        /**
         * f2: 3299 收盘价 /100,
         * f3: -138 涨跌福 /100,
         * f4: -46 涨跌额  /100,
         * f5: 545259 成交量 *100,
         * f6: 1797334016 成交金额,基本相同，不处理,
         * f12: "002415" 股票编码,
         * f14 海康威视 股票的名称
         * f15: 3336 最高价 /100,
         * f16: 3273 最低价 /100,
         * f17: 3332 开盘价 /100,
         * f18: 3345 昨天收盘价
         */
        String currDate = DateUtil.format(beforeLastWorking, Const.SIMPLE_DATE_FORMAT);
        List<StockHistoryCsvInfo> result= new ArrayList<>(codeList.size());
        stockResultVo.getData().getDiff().forEach(v -> {
            if (!codeList.contains(v.getF12())) {
                return;
            }
            StockHistoryCsvInfo stockHistoryCsvInfo = new StockHistoryCsvInfo();
            stockHistoryCsvInfo.setCode(v.getF12());
            stockHistoryCsvInfo.setName(v.getF14());
            stockHistoryCsvInfo.setCurrDate(currDate);
            stockHistoryCsvInfo.setClosingPrice(new BigDecimal(v.getF2()).movePointLeft(2));
            stockHistoryCsvInfo.setHighestPrice(new BigDecimal(v.getF15()).movePointLeft(2));
            stockHistoryCsvInfo.setLowestPrice(new BigDecimal(v.getF16()).movePointLeft(2));
            stockHistoryCsvInfo.setOpeningPrice(new BigDecimal(v.getF17()).movePointLeft(2));
            stockHistoryCsvInfo.setYesClosingPrice(new BigDecimal(v.getF18()).movePointLeft(2));
            stockHistoryCsvInfo.setAmplitude(new BigDecimal(v.getF4()).movePointLeft(2));
            stockHistoryCsvInfo.setAmplitudeProportion(new BigDecimal(v.getF3()).movePointLeft(2));
            stockHistoryCsvInfo.setTradingVolume(v.getF5() *100);
            stockHistoryCsvInfo.setTradingValue(BigDecimalUtil.toBigDecimal(v.getF6()));
            result.add(stockHistoryCsvInfo);
        });
        return result;
    }
}
