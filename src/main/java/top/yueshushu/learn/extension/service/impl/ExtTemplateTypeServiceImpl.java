package top.yueshushu.learn.extension.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.enumtype.message.ExtInterfaceTemplateType;
import top.yueshushu.learn.extension.model.dto.gaodeweather.WeatherResponse;
import top.yueshushu.learn.extension.model.dto.shanbeici.TranslateResponse;
import top.yueshushu.learn.extension.model.dto.shici.PoemResponse;
import top.yueshushu.learn.extension.model.dto.tianxing.*;
import top.yueshushu.learn.extension.service.ExtFunctionService;
import top.yueshushu.learn.extension.service.ExtTemplateTypeService;
import top.yueshushu.learn.message.weixin.service.WeChatService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Description TODO
 * @Author yuejianli
 * @Date 2022/6/11 19:44
 **/
@Service
@Slf4j
public class ExtTemplateTypeServiceImpl implements ExtTemplateTypeService {
    @Resource
    private ExtFunctionService extFunctionService;
    private VelocityEngine velocityEngine;
    @Resource
    private WeChatService weChatService;

    @Value("${spring.velocity.FILE_RESOURCE_LOADER_PATH}")
    private String fileResourceLoaderPath;

    @PostConstruct
    public void initVelocityEngine() {
        velocityEngine = new VelocityEngine();
        Properties p = new Properties();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init(p);
    }


    @Override
    public Map<String, String> getResponseByType(ExtInterfaceTemplateType extInterfaceTemplateType) {
        if (null == extInterfaceTemplateType) {
            return Collections.emptyMap();
        }
        Map<String, String> resultMap = new HashMap<>(2, 1.0f);
        resultMap.put(extInterfaceTemplateType.getCode(), execInterfacTemplate(extInterfaceTemplateType));
        return resultMap;
    }


    /**
     * 执行任务，进行调用
     *
     * @param extInterfaceTemplateType 调用的任务
     */
    private String execInterfacTemplate(ExtInterfaceTemplateType extInterfaceTemplateType) {
        //将对应转换成 map
        Map<String, Object> interfaceResultMap = Collections.emptyMap();
        switch (extInterfaceTemplateType) {
            case WEATHER: {
                //获取天气
                WeatherResponse weather = extFunctionService.getWeather("330114");
                //将对应转换成 map
                interfaceResultMap = BeanUtil.beanToMap(weather.getForecasts().get(0));
                break;
            }
            case TRANSLATE: {
                TranslateResponse translateResponse = extFunctionService.getTranslate();
                //将对应转换成 map
                interfaceResultMap = BeanUtil.beanToMap(translateResponse);
                break;
            }
            case POEM: {
                PoemResponse poemResponse = extFunctionService.getPoem();
                //将对应转换成 map
                interfaceResultMap = BeanUtil.beanToMap(poemResponse);
                break;
            }
            case COUPLETS: {
                TianXingResponse<TianXingInfo> tianXingInfoTianXingResponse = extFunctionService.getCouplets();
                //将对应转换成 map
                interfaceResultMap = BeanUtil.beanToMap(convertContent(tianXingInfoTianXingResponse));
                break;
            }
            case CLASSICAL: {
                TianXingResponse<TianXingInfo> tianXingInfoTianXingResponse = extFunctionService.getClassical();
                //将对应转换成 map
                interfaceResultMap = BeanUtil.beanToMap(convertContent(tianXingInfoTianXingResponse));
                break;
            }
            case DIALOGUE: {
                TianXingResponse<TianXingInfo> tianXingInfoTianXingResponse = extFunctionService.getDialogue();
                //将对应转换成 map
                interfaceResultMap = BeanUtil.beanToMap(convertContent(tianXingInfoTianXingResponse));
                break;
            }
            case CAIHONGPI: {
                TianXingResponse<TianXingInfo> tianXingInfoTianXingResponse = extFunctionService.getCaiHongPi();
                //将对应转换成 map
                interfaceResultMap = BeanUtil.beanToMap(convertContent(tianXingInfoTianXingResponse));
                break;
            }
            case BAIKETIKU: {
                TianXingResponse<BaiKeTiKuInfo> tianXingInfoTianXingResponse = extFunctionService.getBaiKeTiKu();
                //将对应转换成 map
                interfaceResultMap = BeanUtil.beanToMap(convertBaiKeContent(tianXingInfoTianXingResponse));
                break;
            }
            case ZAOAN: {
                TianXingResponse<TianXingInfo> tianXingInfoTianXingResponse = extFunctionService.getZaoAn();
                //将对应转换成 map
                interfaceResultMap = BeanUtil.beanToMap(convertContent(tianXingInfoTianXingResponse));
                interfaceResultMap.put("name", "我的朋友");
                break;
            }
            case WANAN: {
                TianXingResponse<TianXingInfo> tianXingInfoTianXingResponse = extFunctionService.getWanAn();
                //将对应转换成 map
                interfaceResultMap = BeanUtil.beanToMap(convertContent(tianXingInfoTianXingResponse));
                interfaceResultMap.put("name", "我的朋友");
                break;
            }
            case TENWHY: {
                TianXingResponse<TianXingInfo> tianXingInfoTianXingResponse = extFunctionService.getTenWhy();
                //将对应转换成 map
                interfaceResultMap = BeanUtil.beanToMap(convertContent(tianXingInfoTianXingResponse));
                break;
            }
            case ZIMI: {
                TianXingResponse<ZiMiInfo> tianXingInfoTianXingResponse = extFunctionService.getZiMi();
                //将对应转换成 map
                interfaceResultMap = BeanUtil.beanToMap(convertZiMiContent(tianXingInfoTianXingResponse));
                break;
            }
            case PROVERB: {
                TianXingResponse<ProverbInfo> tianXingInfoTianXingResponse = extFunctionService.getProverb();
                //将对应转换成 map
                interfaceResultMap = BeanUtil.beanToMap(convertProverbContent(tianXingInfoTianXingResponse));
                break;
            }
            case CHENG_YU: {
                TianXingResponse<ChengYuInfo> tianXingInfoTianXingResponse = extFunctionService.getChengYu();
                //将对应转换成 map
                interfaceResultMap = BeanUtil.beanToMap(convertChengYuContent(tianXingInfoTianXingResponse));
                break;
            }
            case QIAOMEN: {
                TianXingResponse<TianXingInfo> tianXingInfoTianXingResponse = extFunctionService.getQiaoMen();
                //将对应转换成 map
                interfaceResultMap = BeanUtil.beanToMap(convertContent(tianXingInfoTianXingResponse));
                break;
            }
            case MIYU: {
                TianXingResponse<ZiMiInfo> tianXingInfoTianXingResponse = extFunctionService.getMiYu();
                //将对应转换成 map
                interfaceResultMap = BeanUtil.beanToMap(convertZiMiContent(tianXingInfoTianXingResponse));
                break;
            }
            case MINGYAN: {
                TianXingResponse<TianXingInfo> tianXingInfoTianXingResponse = extFunctionService.getMingYan();
                //将对应转换成 map
                interfaceResultMap = BeanUtil.beanToMap(convertContent(tianXingInfoTianXingResponse));
                break;
            }
            case QINGSHI: {
                TianXingResponse<TianXingInfo> tianXingInfoTianXingResponse = extFunctionService.getQingShi();
                //将对应转换成 map
                interfaceResultMap = BeanUtil.beanToMap(convertContent(tianXingInfoTianXingResponse));
                break;
            }
            case SAYLOVE: {
                TianXingResponse<TianXingInfo> tianXingInfoTianXingResponse = extFunctionService.getSayLove();
                //将对应转换成 map
                interfaceResultMap = BeanUtil.beanToMap(convertContent(tianXingInfoTianXingResponse));
                break;
            }
            case HUANGLI: {
                TianXingResponse<HuangLiInfo> tianXingInfoTianXingResponse = extFunctionService.getHuangLi();
                //将对应转换成 map
                interfaceResultMap = BeanUtil.beanToMap(convertHuangLiContent(tianXingInfoTianXingResponse));
                break;
            }
            case XIEHOUYU: {
                TianXingResponse<XieHouYuInfo> tianXingInfoTianXingResponse = extFunctionService.getXieHouYu();
                //将对应转换成 map
                interfaceResultMap = BeanUtil.beanToMap(convertXieHouYuContent(tianXingInfoTianXingResponse));
                break;
            }
            case RAOKOULING: {
                TianXingResponse<TianXingInfo> tianXingInfoTianXingResponse = extFunctionService.getRaoKouLing();
                //将对应转换成 map
                interfaceResultMap = BeanUtil.beanToMap(convertContent(tianXingInfoTianXingResponse));
                //之间的 content
                String oldContent = (String) (interfaceResultMap.get("content"));
                String line = System.lineSeparator();
                interfaceResultMap.put("content", oldContent.replaceAll("<br/>", line));
                break;
            }
            default: {
                break;
            }
        }
        String line = System.lineSeparator();
        interfaceResultMap.put("line", line);

        // 将信息组合后发布

        //获取信息
        String info = getVelocityMailText(extInterfaceTemplateType, interfaceResultMap);
        return info;
    }


    private String getVelocityMailText(ExtInterfaceTemplateType extInterfaceTemplateType, Map<String, Object> dataMap) {
        VelocityContext velocityContext = new VelocityContext(dataMap);
        StringWriter writer = new StringWriter();
        String templateLocation = "interface_" + extInterfaceTemplateType.getCode() + ".vm";
        velocityEngine.mergeTemplate(templateLocation, "UTF-8", velocityContext, writer);
        return writer.toString();
    }

    /**
     * 将信息对象转换
     *
     * @param tianXingInfoTianXingResponse 对象转换
     */
    private TianXingInfo convertContent(TianXingResponse<TianXingInfo> tianXingInfoTianXingResponse) {
        return JSON.parseObject(JSON.toJSONString(tianXingInfoTianXingResponse.getNewslist().get(0)), TianXingInfo.class);
    }

    /**
     * 将信息对象转换
     *
     * @param tianXingInfoTianXingResponse 对象转换
     */
    private ZiMiInfo convertZiMiContent(TianXingResponse<ZiMiInfo> tianXingInfoTianXingResponse) {
        return JSON.parseObject(JSON.toJSONString(tianXingInfoTianXingResponse.getNewslist().get(0)), ZiMiInfo.class);
    }

    /**
     * 将信息对象转换
     *
     * @param tianXingInfoTianXingResponse 对象转换
     */
    private ProverbInfo convertProverbContent(TianXingResponse<ProverbInfo> tianXingInfoTianXingResponse) {
        return JSON.parseObject(JSON.toJSONString(tianXingInfoTianXingResponse.getNewslist().get(0)), ProverbInfo.class);
    }

    private BaiKeTiKuInfo convertBaiKeContent(TianXingResponse<BaiKeTiKuInfo> tianXingInfoTianXingResponse) {
        return JSON.parseObject(JSON.toJSONString(tianXingInfoTianXingResponse.getNewslist().get(0)), BaiKeTiKuInfo.class);
    }

    private ChengYuInfo convertChengYuContent(TianXingResponse<ChengYuInfo> tianXingInfoTianXingResponse) {
        return JSON.parseObject(JSON.toJSONString(tianXingInfoTianXingResponse.getNewslist().get(0)), ChengYuInfo.class);
    }

    private HuangLiInfo convertHuangLiContent(TianXingResponse<HuangLiInfo> tianXingInfoTianXingResponse) {
        return JSON.parseObject(JSON.toJSONString(tianXingInfoTianXingResponse.getNewslist().get(0)), HuangLiInfo.class);
    }

    private XieHouYuInfo convertXieHouYuContent(TianXingResponse<XieHouYuInfo> tianXingInfoTianXingResponse) {
        return JSON.parseObject(JSON.toJSONString(tianXingInfoTianXingResponse.getNewslist().get(0)), XieHouYuInfo.class);
    }
}
