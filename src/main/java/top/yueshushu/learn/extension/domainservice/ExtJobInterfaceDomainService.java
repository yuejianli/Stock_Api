package top.yueshushu.learn.extension.domainservice;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.extension.domain.ExtJobInterfaceDo;

import java.util.List;

/**
 * @Description 功能的操作
 * @Author yuejianli
 * @Date 2022/06/02 23:23
 **/
public interface ExtJobInterfaceDomainService extends IService<ExtJobInterfaceDo> {
    /**
     * 根据任务id查询对应的全部可选功能
     *
     * @param extJobId 任务id
     * @return 根据任务id查询对应的全部可选功能
     */
    List<ExtJobInterfaceDo> listByJobId(Integer extJobId);
}
