package top.yueshushu.learn.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.assembler.StockAssembler;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.domain.StockDo;
import top.yueshushu.learn.domainservice.StockDomainService;
import top.yueshushu.learn.entity.Stock;
import top.yueshushu.learn.mode.ro.StockRo;
import top.yueshushu.learn.mode.vo.StockVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;
import top.yueshushu.learn.service.StockService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 股票信息基本表 自定义的
 * </p>
 *
 * @author 岳建立
 * @since 2022-01-02
 */
@Service
@Slf4j
public class StockServiceImpl implements StockService {
    @Resource
    private StockDomainService stockDomainService;
    @Resource
    private StockAssembler stockAssembler;

    @Override
    public OutputResult pageStock(StockRo stockRo) {
        Page<Object> pageGithubResult = PageHelper.startPage(stockRo.getPageNum(), stockRo.getPageSize());
        List<StockDo> stockDoInfoList = stockDomainService.selectByKeyword(stockRo.getKeyword());
        if (CollectionUtils.isEmpty(stockDoInfoList)){
            return OutputResult.buildSucc(
                    PageResponse.emptyPageResponse()
            );
        }

        List<StockVo> pageResultList = new ArrayList<>(stockDoInfoList.size());
        stockDoInfoList.forEach(
                n->{
                    pageResultList.add(
                            stockAssembler.entityToVo(
                                    stockAssembler.doToEntity(
                                            n
                                    )
                            )
                    );
                }
        );
        PageInfo pageInfo=new PageInfo<StockVo>(pageResultList);
        return OutputResult.buildSucc(new PageResponse<StockVo>(pageGithubResult.getTotal(),
                pageInfo.getList()));
    }
    @Override
    public Stock selectByCode(String code) {
       return stockAssembler.doToEntity(
               stockDomainService.getByCode(code)
       );
    }

    @Override
    public boolean existStockCode(String code) {
        Stock stock = stockAssembler.doToEntity(
                stockDomainService.getByCode(code)
        );
        return stock!=null;
    }

    @Override
    public OutputResult<StockVo> getStockInfo(String code) {
        Stock stock = stockAssembler.doToEntity(
                stockDomainService.getByCode(
                        code
                )
        );
        if (null == stock){
            return OutputResult.buildAlert(
                ResultCode.STOCK_CODE_NO_EXIST
            );
        }
        return OutputResult.buildSucc(
                stockAssembler.entityToVo(stock)
        );
    }
}