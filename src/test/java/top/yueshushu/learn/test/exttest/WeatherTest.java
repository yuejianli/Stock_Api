package top.yueshushu.learn.test.exttest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import top.yueshushu.learn.extension.ExtJobSechduler;
import top.yueshushu.learn.extension.ExtJobService;
import top.yueshushu.learn.extension.model.gaodeweather.WeatherResponse;
import top.yueshushu.learn.extension.model.shanbeici.TranslateResponse;
import top.yueshushu.learn.extension.model.shici.PoemResponse;

/**
 * @Description TODO
 * @Author yuejianli
 * @Date 2022/6/4 16:27
 **/
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class WeatherTest {
    @Resource
    private ExtJobService extJobService;
    @Resource
    private ExtJobSechduler extJobSechduler;
    
    @Test
    public void weatherTest() {
        WeatherResponse weather = extJobService.getWeather();
        log.info("获取天气信息:{}", weather);
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
    public void moringTest() {
        extJobSechduler.morning();
    }
    
    @Test
    public void nightTest() {
        extJobSechduler.night();
    }
}
