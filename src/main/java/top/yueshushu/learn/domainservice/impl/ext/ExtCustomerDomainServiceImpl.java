package top.yueshushu.learn.domainservice.impl.ext;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

import top.yueshushu.learn.domain.ext.ExtCustomerDo;
import top.yueshushu.learn.domainservice.ext.ExtCustomerDomainService;
import top.yueshushu.learn.mapper.ext.ExtCustomerDoMapper;

/**
 * 用途描述
 *
 * @author yuejianli
 * @date 2022-06-09
 */
@Service
public class ExtCustomerDomainServiceImpl extends ServiceImpl<ExtCustomerDoMapper, ExtCustomerDo>
		implements ExtCustomerDomainService {
	
}
