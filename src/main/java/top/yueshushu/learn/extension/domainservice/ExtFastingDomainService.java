package top.yueshushu.learn.extension.domainservice;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.extension.domain.ExtFastingDo;

/**
 * @Description 斋戒日期
 * @Author yuejianli
 * @Date 2022/06/02 23:23
 **/
public interface ExtFastingDomainService extends IService<ExtFastingDo> {

    /**
     * 根据农历的 月，天 或者节气查询，是否存在相应的记录信息。
     *
     * @param month 月
     * @param day   天
     * @param term  节气
     * @return 根据农历的 月，天 或者节气查询，是否存在相应的记录信息。
     */
    ExtFastingDo getByMonthAndDay(int month, int day, String term);
}
