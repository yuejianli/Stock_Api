package top.yueshushu.learn.extension.domainservice;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yueshushu.learn.extension.domain.ExtCustomerJobDo;

import java.util.List;

/**
 * @Description 功能的操作
 * @Author yuejianli
 * @Date 2022/06/02 23:23
 **/
public interface ExtCustomerJobDomainService extends IService<ExtCustomerJobDo> {
    /**
     * 根据客户id和任务id查询对应的功能列表信息
     *
     * @param extCustomerId 客户id
     * @param extJobId      任务id
     * @return 根据客户id和任务id查询对应的功能列表信息
     */
    List<ExtCustomerJobDo> listByCustomerIdAndJobId(Integer extCustomerId, Integer extJobId);

    /**
     * 根据客户id和任务id 删除以前的 功能列表信息
     *
     * @param extCustomerId 客户id
     * @param extJobId      任务id
     */
    void removeByCustomerIdAndJobId(Integer extCustomerId, Integer extJobId);

    /**
     * 查询一下，当前任务关联的用户和接口信息
     *
     * @param jobId 任务id
     * @return 查询一下，当前任务关联的用户和接口信息
     */
    List<ExtCustomerJobDo> listByJobId(Integer jobId);

    /**
     * 根据用户编号删除对应的接口信息
     *
     * @param customerId 用户id
     */
    void removeByUserId(Integer customerId);
}
