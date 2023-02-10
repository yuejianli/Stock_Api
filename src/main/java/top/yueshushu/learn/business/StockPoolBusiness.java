package top.yueshushu.learn.business;

import java.util.Date;

/**
 * 股票池处理
 *
 * @author Yue Jianli
 * @date 2023-02-10
 */

public interface StockPoolBusiness {
    /**
     * 处理股票池信息
     *
     * @param date 日期
     */
    void handlerPool(Date date);
}
