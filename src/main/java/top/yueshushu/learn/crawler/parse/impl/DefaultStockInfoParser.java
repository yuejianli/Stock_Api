package top.yueshushu.learn.crawler.parse.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import top.yueshushu.learn.crawler.entity.BKInfo;
import top.yueshushu.learn.crawler.entity.BKMoneyInfo;
import top.yueshushu.learn.crawler.entity.DownloadStockInfo;
import top.yueshushu.learn.crawler.parse.StockInfoParser;
import top.yueshushu.learn.util.StockUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 股票转换信息实现
 * @author 12905
 */
@Component("defaultStockInfoParser")
public class DefaultStockInfoParser implements StockInfoParser {

    @Override
    public List<DownloadStockInfo> parseStockInfoList(String content) {
        //将内容转换成json
        JSONObject jsonObject = JSONObject.parseObject(content);
        //获取里面的data.diff 内容，是个列表对象
        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("diff");
        //处理内容
        List<DownloadStockInfo> result = new ArrayList<>(32);
        jsonArray.stream().forEach(
                n->{
                    JSONObject tempObject = JSONObject.parseObject(n.toString());
                    DownloadStockInfo downloadStockInfo = new DownloadStockInfo();
                    downloadStockInfo.setCode(tempObject.getString("f12"));
                    downloadStockInfo.setName(tempObject.getString("f14"));

                    //处理类型  1为上海   0为深圳
                    int type=tempObject.getInteger("f13");
                    //进行处理
                    downloadStockInfo.setExchange(type);
                    //设置股票的全称
                    downloadStockInfo.setFullCode(StockUtil.getFullCode(type, downloadStockInfo.getCode()));

                    result.add(downloadStockInfo);
                }
        );
        return result;
    }

    @Override
    public List<BKInfo> parseBkInfoList(String content) {
        //将内容转换成json
        JSONObject jsonObject = JSONObject.parseObject(content);
        //获取里面的data.diff 内容，是个列表对象
        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("diff");
        //处理内容
        List<BKInfo> result = new ArrayList<>(32);
        jsonArray.stream().forEach(
                n -> {
                    JSONObject tempObject = JSONObject.parseObject(n.toString());
                    BKInfo bkInfo = new BKInfo();
                    bkInfo.setCode(tempObject.getString("f12"));
                    bkInfo.setName(tempObject.getString("f14"));
                    result.add(bkInfo);
                }
        );
        return result;
    }

    @Override
    public List<BKMoneyInfo> parseTodayBKMoneyInfoList(String content) {
        //将内容转换成json
        JSONObject jsonObject = JSONObject.parseObject(content);
        //获取里面的data.diff 内容，是个列表对象
        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("diff");
        //处理内容
        Date now = DateUtil.date();
        List<BKMoneyInfo> result = new ArrayList<>(32);
        jsonArray.stream().forEach(
                n -> {
                    JSONObject tempObject = JSONObject.parseObject(n.toString());
                    BKMoneyInfo bkMoneyInfo = new BKMoneyInfo();
                    bkMoneyInfo.setBkCode(tempObject.getString("f12"));
                    bkMoneyInfo.setBkName(tempObject.getString("f14"));
                    bkMoneyInfo.setCurrentDate(now);
                    bkMoneyInfo.setBkNowPrice(tempObject.getString("f2"));
                    bkMoneyInfo.setBkNowProportion(tempObject.getString("f13"));
                    bkMoneyInfo.setMarket(tempObject.getInteger("f13"));
                    bkMoneyInfo.setTodayMainInflow(tempObject.getString("f62"));
                    bkMoneyInfo.setTodayMainInflowProportion(tempObject.getString("f184"));
                    bkMoneyInfo.setTodaySuperInflow(tempObject.getString("f66"));
                    bkMoneyInfo.setTodaySuperInflowProportion(tempObject.getString("f69"));
                    bkMoneyInfo.setTodayMoreInflow(tempObject.getString("f72"));
                    bkMoneyInfo.setTodayMoreInflowProportion(tempObject.getString("f75"));
                    bkMoneyInfo.setTodayMiddleInflow(tempObject.getString("f78"));
                    bkMoneyInfo.setTodayMiddleInflowProportion(tempObject.getString("f81"));
                    bkMoneyInfo.setTodaySmallInflow(tempObject.getString("f84"));
                    bkMoneyInfo.setTodaySmallInflowProportion(tempObject.getString("f87"));
                    bkMoneyInfo.setTodayMainInflowCode(tempObject.getString("f205"));
                    bkMoneyInfo.setTodayMainInflowName(tempObject.getString("f204"));
                    result.add(bkMoneyInfo);
                }
        );
        return result;
    }
}
