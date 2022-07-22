package top.yueshushu.learn.extension.business.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import javax.annotation.Resource;

import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.date.DateUtil;
import top.yueshushu.learn.extension.assembler.ExtFastingAssembler;
import top.yueshushu.learn.extension.business.ExtFastingBusiness;
import top.yueshushu.learn.extension.entity.ExtFasting;
import top.yueshushu.learn.extension.model.vo.ExtFastingVo;
import top.yueshushu.learn.extension.service.ExtFastingService;

/**
 * @Description 接口实现应用
 * @Author yuejianli
 * @Date 2022/6/11 15:10
 **/
@Service
public class ExtFastingBusinessImpl implements ExtFastingBusiness {
    @Resource
    private ExtFastingService extFastingService;
    @Resource
    private ExtFastingAssembler extFastingAssembler;

    @Override
    public String fastingJob(Integer interfaceId) {
        //根据接口id ,查询相应的信息.
        //能得到信息.
        Date now = DateUtil.date();
        ChineseDate chineseDate = new ChineseDate(now);
    
        // 获取当前的月索引
        int month = chineseDate.getMonth();
        int day = chineseDate.getDay();
        //是节气
        String term = chineseDate.getTerm();
        ExtFasting extFasting = extFastingService.getByMonthAndDay(month, day, term);
        //转换成 Vo
        ExtFastingVo extFastingVo = extFastingAssembler.entityToVo(extFasting);
    
        String nowDayMessage = null;
        if (extFastingVo != null) {
            nowDayMessage = covertVoToMessage(extFastingVo, now, 0);
        }
        Date morring = DateUtil.offsetDay(now, 1);
        chineseDate = new ChineseDate(morring);
    
        // 获取当前的月索引
        month = chineseDate.getMonth();
        day = chineseDate.getDay();
        //是节气
        term = chineseDate.getTerm();
        extFasting = extFastingService.getByMonthAndDay(month, day, term);
        //转换成 Vo
        extFastingVo = extFastingAssembler.entityToVo(extFasting);
    
        String morringDayMessage = null;
        if (extFastingVo != null) {
            morringDayMessage = covertVoToMessage(extFastingVo, morring, 1);
        }
        String result = "";
        String line = System.lineSeparator();
        if (StringUtils.hasText(nowDayMessage)) {
            result = result.concat(nowDayMessage).concat(line);
        }
        if (StringUtils.hasText(morringDayMessage)) {
            result = result.concat(morringDayMessage).concat(line);
        }
        return result;
    }
    
    /**
     * 将对象转换成微信要发送的消息
     *
     * @param extFastingVo 禁期对象
     * @return 将对象转换成微信要发送的消息
     */
    private String covertVoToMessage(ExtFastingVo extFastingVo, Date date, Integer index) {
        
        if (StringUtils.isEmpty(extFastingVo.getFastingReason())) {
            extFastingVo.setFastingReasonList(Collections.emptyList());
        } else {
            extFastingVo.setFastingReasonList(Arrays.asList(extFastingVo.getFastingReason().split("\\,")));
        }
        if (StringUtils.isEmpty(extFastingVo.getDamage())) {
            extFastingVo.setDamageList(Collections.emptyList());
        } else {
            extFastingVo.setDamageList(Arrays.asList(extFastingVo.getDamage().split("\\,")));
        }
        String dayMessage = index == 0 ? "今天" : "明天";
        ChineseDate chineseDate = new ChineseDate(date);
        StringBuilder stringBuilder = new StringBuilder();
        String line = System.lineSeparator();
        if (extFastingVo.getType() == 1) {
            stringBuilder.append(dayMessage + "是农历").append(chineseDate.getChineseMonth())
                    .append(" ").append(chineseDate.getChineseDay()).append(line);
            if (!CollectionUtils.isEmpty(extFastingVo.getFastingReasonList())) {
                //不为空，往下继续进行.
                int i = 0;
                for (String reason : extFastingVo.getFastingReasonList()) {
                    String damage = " - ";
                    if (extFastingVo.getDamageList().size() > i) {
                        damage = extFastingVo.getDamageList().get(i);
                    }
                    ++i;
                    stringBuilder.append("戒由").append(i).append(reason).append("  ")
                            .append(" 犯者报应：").append(damage)
                            .append(line);
                    
                }
            }
        } else {
            stringBuilder.append(dayMessage + "是").append(extFastingVo.getJieQi()).append(line);
            stringBuilder.append("注意:").append(extFastingVo.getNotes());
        }
        stringBuilder.append(line).append("请注意洁身自好");
        return stringBuilder.toString();
    }
}
