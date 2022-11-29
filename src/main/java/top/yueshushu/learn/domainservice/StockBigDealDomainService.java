package top.yueshushu.learn.domainservice;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.domain.StockBigDealDo;

/**
 * 大宗交易信息
 *
 * @author yuejianli
 * @date 2022-11-29
 */

public interface StockBigDealDomainService extends IService<StockBigDealDo> {
    /**
     * 删除已经同步过的数据
     *
     * @param fullCode    股票编码
     * @param currentDate 日期
     */
    void deleteHasAsyncData(String fullCode, DateTime currentDate);


}
