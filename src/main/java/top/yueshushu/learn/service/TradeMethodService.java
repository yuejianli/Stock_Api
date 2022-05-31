package top.yueshushu.learn.service;

import top.yueshushu.learn.entity.TradeMethod;
import top.yueshushu.learn.enumtype.TradeMethodType;
import top.yueshushu.learn.domain.TradeMethodDo;
import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.mode.ro.TradeMethodRo;
import top.yueshushu.learn.response.OutputResult;

/**
 * <p>
 * 交易，包括爬虫所使用的url信息 自定义的
 * </p>
 *
 * @author 岳建立
 * @since 2022-01-02
 */
public interface TradeMethodService{
    /**
     * 获取相关的方法信息
      * @param tradeMethodType
     * @return
     */
    TradeMethod getMethod(TradeMethodType tradeMethodType);

    /**
     * 根据方法的code 获取对应的信息
     * @param methodCode
     * @return
     */
    TradeMethod getMethodByCode(String methodCode);

    OutputResult pageList(TradeMethodRo tradeMethodRo);
}
