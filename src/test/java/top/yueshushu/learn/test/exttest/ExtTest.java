package top.yueshushu.learn.test.exttest;

import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.yueshushu.learn.enumtype.EntrustType;
import top.yueshushu.learn.enumtype.ExtJobInfoType;
import top.yueshushu.learn.extension.business.ExtJobInfoBusiness;
import top.yueshushu.learn.extension.domain.ExtCustomerDo;
import top.yueshushu.learn.extension.job.ExtJobSechduler;
import top.yueshushu.learn.extension.model.dto.gaodeweather.WeatherResponse;
import top.yueshushu.learn.extension.model.dto.shanbeici.TranslateResponse;
import top.yueshushu.learn.extension.model.dto.shici.PoemResponse;
import top.yueshushu.learn.extension.model.dto.tianxing.TianXingResponse;
import top.yueshushu.learn.extension.service.ExtFunctionService;

import javax.annotation.Resource;
import java.util.Date;

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
    private ExtFunctionService extFunctionService;
    @Resource
    private ExtJobSechduler extJobSechduler;
    @Resource
    private ExtJobInfoBusiness extJobInfoBusiness;
    private ExtCustomerDo extCustomerDo;
    
    @Before
    public void initCustomerDo() {
        extCustomerDo = new ExtCustomerDo();
        extCustomerDo.setId(1);
        extCustomerDo.setUserAccount("岳泽霖");
        extCustomerDo.setName("岳泽霖");
        extCustomerDo.setWxId("yuejianli");
        extCustomerDo.setSex(1);
        extCustomerDo.setCity("330114");
        extCustomerDo.setFlag(1);
    }
    
    @Test
    public void weatherTest() {
        WeatherResponse weather = extFunctionService.getWeather("522701");
        log.info("获取天气信息:{}", JSONObject.toJSONString(weather));
    }
    
    @Test
    public void translateResponseTest() {
        TranslateResponse weather = extFunctionService.getTranslate();
        log.info("获取每日一句信息:{}", JSONObject.toJSONString(weather));
    }
    
    
    @Test
    public void poemTest() {
        PoemResponse poemResponse = extFunctionService.getPoem();
        log.info("获取一句诗信息:{}", JSONObject.toJSONString(poemResponse));
    }
    
    @Test
    public void coupletsTest() {
        TianXingResponse couplets = extFunctionService.getCouplets();
        log.info("获取对联信息:{}", JSONObject.toJSONString(couplets));
    }
    
    @Test
    public void getClassicalTest() {
        TianXingResponse couplets = extFunctionService.getClassical();
        log.info("获取古典诗句信息:{}", JSONObject.toJSONString(couplets));
    }
    
    @Test
    public void getDialogueTest() {
        TianXingResponse couplets = extFunctionService.getDialogue();
        log.info("获取经典台词信息:{}", JSONObject.toJSONString(couplets));
    }
    
    @Test
    public void getCaiHongPiTest() {
        TianXingResponse caihongPi = extFunctionService.getCaiHongPi();
        log.info("获取彩虹屁信息:{}", JSONObject.toJSONString(caihongPi));
    }
    
    @Test
    public void getBaiKeTiKuTest() {
        TianXingResponse baiKeTiKu = extFunctionService.getBaiKeTiKu();
        log.info("获取百科题库:{}", JSONObject.toJSONString(baiKeTiKu));
    }
    
    @Test
    public void getEnglishTest() {
        TianXingResponse yingYu = extFunctionService.getEnglish();
        log.info("获取英语信息:{}", JSONObject.toJSONString(yingYu));
    }
    
    @Test
    public void getZaoAnTest() {
        TianXingResponse zaoAn = extFunctionService.getZaoAn();
        log.info("获取早安信息:{}", JSONObject.toJSONString(zaoAn));
    }
    
    @Test
    public void getWanAnTest() {
        TianXingResponse wanAn = extFunctionService.getWanAn();
        log.info("获取晚安信息:{}", JSONObject.toJSONString(wanAn));
    }
    
    @Test
    public void getTenWhyTest() {
        TianXingResponse tenWhy = extFunctionService.getTenWhy();
        log.info("获取十万个为什么信息:{}", JSONObject.toJSONString(tenWhy));
    }

    @Test
    public void getZiMiTest() {
        TianXingResponse ziMi = extFunctionService.getZiMi();
        log.info("获取字迷信息:{}", JSONObject.toJSONString(ziMi));
    }

    @Test
    public void getProverbTest() {
        TianXingResponse proverb = extFunctionService.getProverb();
        log.info("获取文化谚语信息:{}", JSONObject.toJSONString(proverb));
    }

    @Test
    public void getChengyuTest() {
        TianXingResponse proverb = extFunctionService.getChengYu();
        log.info("获取成语信息:{}", JSONObject.toJSONString(proverb));
    }

    @Test
    public void getQiaoMenTest() {
        TianXingResponse qiaoMen = extFunctionService.getQiaoMen();
        log.info("获取生活小窍门信息:{}", JSONObject.toJSONString(qiaoMen));
    }

    @Test
    public void getMiYuTest() {
        TianXingResponse miyu = extFunctionService.getMiYu();
        log.info("获取迷语信息:{}", JSONObject.toJSONString(miyu));
    }

    @Test
    public void getQingShiTest() {
        TianXingResponse qingShi = extFunctionService.getQingShi();
        log.info("获取情诗信息:{}", JSONObject.toJSONString(qingShi));
    }

    @Test
    public void getMingYanTest() {
        TianXingResponse mingYan = extFunctionService.getMingYan();
        log.info("获取名言信息:{}", JSONObject.toJSONString(mingYan));
    }

    @Test
    public void getSayLoveTest() {
        TianXingResponse toSayLove = extFunctionService.getSayLove();
        log.info("获取土味情话信息:{}", JSONObject.toJSONString(toSayLove));
    }

    @Test
    public void getHuangLiTest() {
        TianXingResponse huangLi = extFunctionService.getHuangLi();
        log.info("获取黄历信息:{}", JSONObject.toJSONString(huangLi));
    }

    @Test
    public void getXieHouYuTest() {
        TianXingResponse xieHouYu = extFunctionService.getXieHouYu();
        log.info("获取歇后语信息:{}", JSONObject.toJSONString(xieHouYu));
    }

    @Test
    public void getRaoKouLingTest() {
        TianXingResponse raoKouLing = extFunctionService.getRaoKouLing();
        log.info("获取绕口令信息:{}", JSONObject.toJSONString(raoKouLing));
    }

    @Test
    public void nongliTest() {
        //获取农历
        Date now = DateUtil.date();
        ChineseDate chineseDate = new ChineseDate(now);
        // 获取农历月 和农历天
        String chineseMonth = chineseDate.getChineseMonth();
        String chineseDay = chineseDate.getChineseDay();
        log.info(">>>月:{},日:{}", chineseMonth, chineseDay);
    }

    @Test
    public void jobTest() {
        extJobInfoBusiness.execJob(ExtJobInfoType.MORNING, EntrustType.AUTO.getCode());
    }

    @Test
    public void job2Test() {
        extJobInfoBusiness.execJob(ExtJobInfoType.FASTING, EntrustType.AUTO.getCode());
    }
}
