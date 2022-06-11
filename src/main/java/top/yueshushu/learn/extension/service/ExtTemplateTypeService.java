package top.yueshushu.learn.extension.service;

import top.yueshushu.learn.enumtype.message.ExtInterfaceTemplateType;

import java.util.Map;

/**
 * @Description 根据接口类型，获取对应的响应信息
 * @Author yuejianli
 * @Date 2022/6/11 19:44
 **/
public interface ExtTemplateTypeService {
    /**
     * 根据类型，获取响应信息
     *
     * @param extInterfaceTemplateType 接口类型
     * @return 根据类型，获取响应信息，转换成字符串
     */
    Map<String, String> getResponseByType(ExtInterfaceTemplateType extInterfaceTemplateType);
}
