package top.yueshushu.learn.test.exttest;

import com.alibaba.fastjson.JSONObject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import top.yueshushu.learn.domain.ext.ExtCustomerDo;
import top.yueshushu.learn.extension.ExtJobSechduler;
import top.yueshushu.learn.extension.ExtJobService;
import top.yueshushu.learn.extension.business.ExtJobBusiness;
import top.yueshushu.learn.extension.model.gaodeweather.WeatherResponse;
import top.yueshushu.learn.extension.model.shanbeici.TranslateResponse;
import top.yueshushu.learn.extension.model.shici.PoemResponse;
import top.yueshushu.learn.extension.model.tianxing.TianXingResponse;

/**
 * @Description TODO
 * @Author yuejianli
 * @Date 2022/6/4 16:27
 **/
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class ExtTest {
    @Resource
    private ExtJobService extJobService;
    @Resource
    private ExtJobSechduler extJobSechduler;
    @Resource
    private ExtJobBusiness extJobBusiness;
    
    private ExtCustomerDo extCustomerDo;
    
    @Before
    public void initCustomerDo() {
        extCustomerDo = new ExtCustomerDo();
        extCustomerDo.setId(1);
        extCustomerDo.setUserAccount("岳泽霖");
        extCustomerDo.setName("岳泽霖");
        extCustomerDo.setUserId("yuejianli");
        extCustomerDo.setSex(1);
        extCustomerDo.setCity("330114");
        extCustomerDo.setFlag(1);
    }
    
    @Test
    public void weatherTest() {
        WeatherResponse weather = extJobService.getWeather("522701");
        log.info("获取天气信息:{}", JSONObject.toJSONString(weather));
    }
    
    @Test
    public void translateResponseTest() {
        TranslateResponse weather = extJobService.getTranslate();
        log.info("获取每日一句信息:{}", weather);
    }
    
    
    @Test
    public void poemTest() {
        PoemResponse poemResponse = extJobService.getPoem();
        log.info("获取一句诗信息:{}", poemResponse);
    }
    
    @Test
    public void coupletsTest() {
        TianXingResponse couplets = extJobService.getCouplets();
        log.info("获取对联信息:{}", couplets);
    }
    
    @Test
    public void getClassicalTest() {
        TianXingResponse couplets = extJobService.getClassical();
        log.info("获取古典诗句信息:{}", couplets);
    }
    
    @Test
    public void getDialogueTest() {
        TianXingResponse couplets = extJobService.getDialogue();
        log.info("获取经典台词信息:{}", couplets);
    }
    
    @Test
    public void getCaiHongPiTest() {
        TianXingResponse caihongPi = extJobService.getCaiHongPi();
        log.info("获取彩虹屁信息:{}", caihongPi);
    }
    
    @Test
    public void getBaiKeTiKuTest() {
        TianXingResponse baiKeTiKu = extJobService.getBaiKeTiKu();
        log.info("获取百科题库:{}", baiKeTiKu);
    }
    
    @Test
    public void getEnglishTest() {
        TianXingResponse yingYu = extJobService.getEnglish();
        log.info("获取英语信息:{}", yingYu);
    }
    
    @Test
    public void getZaoAnTest() {
        TianXingResponse zaoAn = extJobService.getZaoAn();
        log.info("获取早安信息:{}", zaoAn);
    }
    
    @Test
    public void getWanAnTest() {
        TianXingResponse wanAn = extJobService.getWanAn();
        log.info("获取晚安信息:{}", wanAn);
    }
    
    @Test
    public void getTenWhyTest() {
        TianXingResponse tenWhy = extJobService.getTenWhy();
        log.info("获取十万个为什么信息:{}", tenWhy);
    }
    
    @Test
    public void getZiMiTest() {
        TianXingResponse ziM = extJobService.getZiMi();
        log.info("获取字迷信息:{}", ziM);
    }
    
    @Test
    public void getProverbTest() {
        TianXingResponse proverb = extJobService.getProverb();
        log.info("获取文化谚语信息:{}", proverb);
    }
    
    @Test
    public void moringTest() {
        extJobBusiness.morning(extCustomerDo);
    }
    
    @Test
    public void nightTest() {
        extJobSechduler.night();
    }
}
