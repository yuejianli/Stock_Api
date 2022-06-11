package top.yueshushu.learn.extension.domainservice;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.extension.domain.ExtInterfaceDo;

import java.util.List;

/**
 * @Description 功能的操作
 * @Author yuejianli
 * @Date 2022/06/02 23:23
 **/
public interface ExtInterfaceDomainService extends IService<ExtInterfaceDo> {
    /**
     * 根据接口的关键字查询接口的列表信息
     *
     * @param keyword 关键字
     * @return 根据接口的关键字查询接口的列表信息
     */
    List<ExtInterfaceDo> listByName(String keyword);
}
