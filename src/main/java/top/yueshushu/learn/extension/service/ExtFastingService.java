package top.yueshushu.learn.extension.service;

import top.yueshushu.learn.extension.entity.ExtFasting;

/**
 * 扩展功能使用的service
 *
 * @author Yue Jianli
 * @date 2022-06-10
 */

public interface ExtFastingService {
    /**
     * 根据id编号，查询相应的信息
     *
     * @param id id编号
     * @return 根据id编号，查询相应的信息
     */
    ExtFasting getById(Integer id);

    /**
     * 根据农历的 月，天 或者节气查询，是否存在相应的记录信息。
     *
     * @param month 月
     * @param day   天
     * @param term  节气
     * @return 根据农历的 月，天 或者节气查询，是否存在相应的记录信息。
     */
    ExtFasting getByMonthAndDay(int month, int day, String term);
}
