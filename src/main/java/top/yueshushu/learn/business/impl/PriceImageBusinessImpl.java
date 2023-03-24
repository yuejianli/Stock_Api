package top.yueshushu.learn.business.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import top.yueshushu.learn.business.PriceImageBusiness;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.crawler.service.CrawlerStockService;
import top.yueshushu.learn.domainservice.StockDomainService;
import top.yueshushu.learn.enumtype.DBStockType;
import top.yueshushu.learn.enumtype.KType;
import top.yueshushu.learn.helper.DateHelper;
import top.yueshushu.learn.service.StockSelectedService;
import top.yueshushu.learn.service.StockService;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 价格分时图保存
 *
 * @author yuejianli
 * @date 2023-03-17
 */
@Service
@Slf4j
public class PriceImageBusinessImpl implements PriceImageBusiness {
    @Resource
    private StockSelectedService stockSelectedService;
    @Resource
    private CrawlerStockService crawlerStockService;
    @Resource
    private StockService stockService;
    @SuppressWarnings("all")
    @Resource(name = Const.SIMPLE_ASYNC_SERVICE_EXECUTOR_BEAN_NAME)
    private AsyncTaskExecutor executor;
    @Resource
    private StockDomainService stockDomainService;
    @Value("${uploadFilePath}")
    private String uploadFilePath;
    @Resource
    private DateHelper dateHelper;

    @Override
    public void batchSavePriceImage(List<String> codeList, Boolean asyncPool) {
        //1. 查询自选列表
        if (CollectionUtils.isEmpty(codeList)) {
            codeList = stockSelectedService.findCodeList(null);
        }
        if (CollectionUtils.isEmpty(codeList)) {
            log.info(">>> 没有自选列表,不进行保存股票分时图列表");
            return;
        }
        List<String> selectedCodeList = saveImageSelected(codeList);
        if (asyncPool) {
            syncPoolHistory(selectedCodeList, DBStockType.SH_SZ_CY);
        }
    }

    @Override
    public void batchSavePriceImageByFullCode(List<String> fullCodeList, Boolean asyncPool) {
        if (CollectionUtils.isEmpty(fullCodeList)) {
            return;
        }
        getAndSaveImage(fullCodeList, fullCodeList);
    }

    private void syncPoolHistory(List<String> codeSelectedList, DBStockType dbStockType) {
        //1. 查询出所有的股票和对应的股票 full code 列表
        List<String> allCodeList = stockDomainService.listCodeByType(dbStockType);
        // 对股票 编码进行筛选
        List<String> filterCodeList = new ArrayList<>();
        for (String allCode : allCodeList) {
            if (codeSelectedList.contains(allCode)) {
                continue;
            }
            filterCodeList.add(allCode);
        }
        if (CollectionUtils.isEmpty(filterCodeList)) {
            return;
        }
        // 对股票进行分组处理.
        int size = filterCodeList.size();
        TimeInterval timeInterval = DateUtil.timer();
        log.info("开始同步全量的股票价格数据 图片，共需要同步{}条", size);
        //进行分批保存, 最多是 20*99,不到2K条
        int BATCH_NUMBER = 20;
        for (int i = 0; i < size; i = i + BATCH_NUMBER) {
            int maxIndex = Math.min(i + BATCH_NUMBER, size);
            saveImageSelected(filterCodeList.subList(i, maxIndex));
        }
        log.info("总同步时间是:" + timeInterval.intervalSecond());
    }

    private List<String> saveImageSelected(List<String> codeList) {
        List<String> resultCodeList = codeList;
        // 15点之前
        executor.submit(
                () -> {
                    getAndSaveImage(resultCodeList, stockService.listFullCode(resultCodeList));
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (Exception e) {
                    }
                }
        );
        return resultCodeList;
    }

    private void getAndSaveImage(List<String> resultCodeList, List<String> listFullCode) {
        //2. 如果没有的话，创建文件夹
        String date = DateUtil.format(dateHelper.getBeforeLastWorking(new Date()), Const.STOCK_DATE_FORMAT);
        File imageDirection = new File(uploadFilePath + File.separator + "images" + File.separator + date);
        if (!imageDirection.exists()) {
            imageDirection.mkdirs();
        }
        // 2. 获取分时图的 base64
        for (String fullCode : listFullCode) {
            Object data = crawlerStockService.getCrawlerLineByFullCode(fullCode, KType.MIN.getCode()).getData();
            if (ObjectUtils.isEmpty(data)) {
                log.error("获取股票 {} 价格分时图出错", fullCode);
                continue;
            }
            byte[] decode = Base64.getDecoder().decode(data.toString());
            File newFile = new File(imageDirection, fullCode + ".png");
            if (newFile.exists()) {
                continue;
            }
            FileUtil.writeBytes(decode, newFile);
        }
        log.info(">>>>> 定时 获取股票 {}  价格分时图成功", resultCodeList);
    }
}
