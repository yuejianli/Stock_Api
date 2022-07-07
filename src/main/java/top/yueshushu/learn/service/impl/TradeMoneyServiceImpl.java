package top.yueshushu.learn.service.impl;

import com.alibaba.fastjson.TypeReference;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import top.yueshushu.learn.api.TradeResultVo;
import top.yueshushu.learn.api.request.GetAssetsRequest;
import top.yueshushu.learn.api.response.GetAssetsResponse;
import top.yueshushu.learn.api.responseparse.DefaultResponseParser;
import top.yueshushu.learn.assembler.TradeMoneyAssembler;
import top.yueshushu.learn.assembler.TradeMoneyHistoryAssembler;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.config.TradeClient;
import top.yueshushu.learn.domain.TradeMoneyDo;
import top.yueshushu.learn.domain.TradeMoneyHistoryDo;
import top.yueshushu.learn.domainservice.TradeMoneyDomainService;
import top.yueshushu.learn.domainservice.TradeMoneyHistoryDomainService;
import top.yueshushu.learn.entity.TradeMoney;
import top.yueshushu.learn.enumtype.MockType;
import top.yueshushu.learn.mode.ro.TradeMoneyRo;
import top.yueshushu.learn.mode.vo.TradeMoneyVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.TradeMoneyService;
import top.yueshushu.learn.util.TradeUtil;

/**
 * <p>
 * 资金表 自定义的
 * </p>
 *
 * @author 两个蝴蝶飞
 * @since 2022-01-03
 */
@Service
@Slf4j
public class TradeMoneyServiceImpl implements TradeMoneyService {
    @Resource
    private TradeMoneyDomainService tradeMoneyDomainService;
    @Resource
    private DefaultResponseParser defaultResponseParser;
    @Resource
    private TradeUtil tradeUtil;
    @Resource
    private TradeClient tradeClient;
    @Resource
    private TradeMoneyAssembler tradeMoneyAssembler;
    @Resource
    private TradeMoneyHistoryAssembler tradeMoneyHistoryAssembler;
    @Resource
    private TradeMoneyHistoryDomainService tradeMoneyHistoryDomainService;
    
    
    @Override
    public void updateMoney(TradeMoney tradeMoney) {
        //进行修改
        tradeMoneyDomainService.updateById(tradeMoneyAssembler.entityToDo(tradeMoney));
    }
    
    @Override
    public TradeMoney getByUserIdAndMockType(Integer userId, Integer mockType) {

        TradeMoneyDo tradeMoneyDo = tradeMoneyDomainService.getByUserIdAndMockType(userId,mockType);
        return tradeMoneyAssembler.doToEntity(tradeMoneyDo);
    }
    @Override
    public OutputResult<TradeMoneyVo> mockInfo(TradeMoneyRo tradeMoneyRo) {
        return OutputResult.buildSucc(tradeMoneyAssembler.entityToVo(
                getByUserIdAndMockType(tradeMoneyRo.getUserId(),
                        tradeMoneyRo.getMockType())));
    }

    @Override
    public OutputResult<TradeMoneyVo> realInfo(TradeMoneyRo tradeMoneyRo) {
        //获取响应信息
        TradeResultVo<GetAssetsResponse> tradeResultVo = getAssetsResponse(tradeMoneyRo.getUserId());
        TradeMoneyVo tradeMoneyVo = new TradeMoneyVo();
        if (!tradeResultVo.getSuccess()) {
            return OutputResult.buildAlert(ResultCode.TRADE_MONEY_FAIL);
        }
        List<GetAssetsResponse> data = tradeResultVo.getData();
        GetAssetsResponse response = data.get(0);
        tradeMoneyVo.setUseMoney(new BigDecimal(response.getKyzj()));
        tradeMoneyVo.setMarketMoney(new BigDecimal(response.getDjzj()));
        tradeMoneyVo.setTotalMoney(new BigDecimal(response.getZzc()));
        tradeMoneyVo.setTakeoutMoney(new BigDecimal(response.getKqzj()));
        tradeMoneyVo.setMockType(MockType.REAL.getCode());
        return OutputResult.buildSucc(tradeMoneyVo);
    }
    
    @Override
    public void saveMoneyHistory(Integer userId, MockType mock) {
        //1. 将今天的记录删除掉
        DateTime now = DateUtil.date();
        tradeMoneyHistoryDomainService.deleteByUserIdAndMockTypeAndDate(
                userId, mock.getCode(), now
        );
        //查看当前的持仓信息
        TradeMoneyDo tradeMoneyDo = tradeMoneyDomainService.getByUserIdAndMockType(
                userId, mock.getCode());
        // 进行保存
        if (tradeMoneyDo == null) {
            return;
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        TradeMoneyHistoryDo tradeMoneyHistoryDo =
                tradeMoneyHistoryAssembler.doToHis(tradeMoneyDo);
        tradeMoneyHistoryDo.setCurrDate(localDateTime);
        tradeMoneyHistoryDomainService.save(tradeMoneyHistoryDo);
    }
    
    /**
     * 获取真实的持仓金额信息
     *
     * @param userId 用户编号
     * @return 获取真实的持仓金额信息
     */
    private TradeResultVo<GetAssetsResponse> getAssetsResponse(Integer userId) {
        GetAssetsRequest request = new GetAssetsRequest(userId);
        String url = tradeUtil.getUrl(request);
        Map<String, String> header = tradeUtil.getHeader(request);
        Map<String, Object> params;
        params = tradeUtil.getParams(request);
        log.debug("trade {} request: {}", request.getMethod(), params);
        String content = tradeClient.send(url, params, header);
        log.debug("trade {} response: {}", request.getMethod(), content);
        TradeResultVo<GetAssetsResponse> result= defaultResponseParser.parse(content,
                new TypeReference<GetAssetsResponse>(){});
        return result;
    }
}
