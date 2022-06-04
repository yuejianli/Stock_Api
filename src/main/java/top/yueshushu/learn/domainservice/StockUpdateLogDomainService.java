package top.yueshushu.learn.domainservice;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.domain.StockUpdateLogDo;

import java.util.List;

/**
 * @Description 股票历史变动表
 * @Author yuejianli
 * @Date 2022/6/4 10:06
 **/
public interface StockUpdateLogDomainService extends IService<StockUpdateLogDo> {
    /**
     * 查询股票的更新日志记录
     * @param code 股票编码
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 查询股票的更新日志记录
     */
    List<StockUpdateLogDo> listStockLogAndDate(String code, DateTime startDate, DateTime endDate);
}
