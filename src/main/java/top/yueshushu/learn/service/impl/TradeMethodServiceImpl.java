package top.yueshushu.learn.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.yueshushu.learn.assembler.TradeMethodAssembler;
import top.yueshushu.learn.domain.TradeMethodDo;
import top.yueshushu.learn.domainservice.TradeMethodDomainService;
import top.yueshushu.learn.entity.TradeMethod;
import top.yueshushu.learn.enumtype.TradeMethodType;
import top.yueshushu.learn.mode.ro.TradeMethodRo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;
import top.yueshushu.learn.service.TradeMethodService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 交易，包括爬虫所使用的url信息 自定义的
 * </p>
 *
 * @author 岳建立
 * @since 2022-01-02
 */
@Service
@Slf4j
public class TradeMethodServiceImpl implements TradeMethodService {
    @Resource
    private TradeMethodDomainService tradeMethodDomainService;
    @Resource
    private TradeMethodAssembler tradeMethodAssembler;
    @Override
    public TradeMethod getMethod(TradeMethodType tradeMethodType) {
        if(null==tradeMethodType){
            return null;
        }
        return getMethodByCode(tradeMethodType.getCode());
    }

    @Override
    public TradeMethod getMethodByCode(String methodCode) {
        if(!StringUtils.hasText(methodCode)){
            return null;
        }
        return tradeMethodAssembler.doToEntity(
                tradeMethodDomainService.getMethodByCode(methodCode)
        );
    }

    @Override
    public OutputResult pageList(TradeMethodRo tradeMethodRo) {

        Page<Object> pageGithubResult = PageHelper.startPage(tradeMethodRo.getPageNum(), tradeMethodRo.getPageSize());
        List<TradeMethodDo> tradeMethodDoList = tradeMethodDomainService.listByName(tradeMethodRo.getKeyword());
        if (CollectionUtils.isEmpty(tradeMethodDoList)){
            return OutputResult.buildSucc(
                    PageResponse.emptyPageResponse()
            );
        }

        List<TradeMethod> pageResultList = new ArrayList<>(tradeMethodDoList.size());
        tradeMethodDoList.forEach(
                n->{
                    pageResultList.add(
                            tradeMethodAssembler.doToEntity(
                                    n
                            )
                    );
                }
        );
        return OutputResult.buildSucc(new PageResponse<>(pageGithubResult.getTotal(), pageResultList));
    }
}
