package top.yueshushu.learn.domainservice;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.domain.StockHistoryDo;
import top.yueshushu.learn.domain.StockSelectedDo;
import top.yueshushu.learn.domain.UserDo;

import java.util.List;

/**
 * @Description 用户自选的操作
 * @Author yuejianli
 * @Date 2022/5/20 23:23
 **/
public interface StockSelectedDomainService extends IService<StockSelectedDo> {
    /**
     *根据用户和关键字筛选出相应的自选记录信息
     * @param userId 系统用户Id
     * @param keyword 关键字
     * @return 根据用户和关键字筛选出相应的自选记录信息
     */
    List<StockSelectedDo> listByUserIdAndKeyword(Integer userId, String keyword);

    /**
     * 根据用户信息，股票信息和状态查询相应的股票记录.最多只查询出一条记录
     * 只查询一个，所以 stockCode 和 userId必填写
     * @param userId 用户编号
     * @param stockCode 股票编码
     * @param status 状态 0为禁用 1为正常
     * @return 根据用户信息，股票信息和状态查询相应的股票记录.最多只查询出一条记录
     */
    StockSelectedDo getByUserIdAndCodeAndStatus(Integer userId, String stockCode, Integer status);

    /**
     * 根据用户信息，股票信息和状态查询相应的股票记录.
       stockCode 可以为空
     * @param userId 用户编号
     * @param stockCode 股票编码
     * @param status 状态 0为禁用 1为正常
     * @return 根据用户信息，股票信息和状态查询相应的股票记录
     */
    List<StockSelectedDo> listByUserIdAndCodeAndStatus(Integer userId, String stockCode, Integer status);

    /**
     * 根据用户信息和状态，获取相应的数量
     * @param userId 用户编号
     * @param status 状态 0为禁用 1为正常
     * @return 根据用户信息和状态，获取相应的数量
     */
    int countByUserIdAndStatus(Integer userId, Integer status);

    /**
     * 查询该用户下的自选股票编码信息
     * @param userId 用户编号，可为空
     * @return 查询该用户下的自选股票编码信息
     */
    List<String> findCodeList(Integer userId);
}
