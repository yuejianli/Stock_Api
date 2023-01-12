package top.yueshushu.learn.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.api.TradeResultVo;
import top.yueshushu.learn.api.request.GetAssetsRequest;
import top.yueshushu.learn.api.response.GetAssetsResponse;
import top.yueshushu.learn.api.responseparse.DataObjResponseParser;
import top.yueshushu.learn.api.responseparse.ResponseParser;
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
import top.yueshushu.learn.service.cache.StockCacheService;
import top.yueshushu.learn.util.TradeUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    private DataObjResponseParser dataObjResponseParser;
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
    @Resource
    private StockCacheService stockCacheService;

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
        tradeMoneyVo.setMarketMoney(new BigDecimal(response.getZxsz()));
        tradeMoneyVo.setTotalMoney(new BigDecimal(response.getZzc()));
        tradeMoneyVo.setTakeoutMoney(new BigDecimal(response.getKqzj()));
        tradeMoneyVo.setProfitMoney(new BigDecimal(response.getLjyk()));
        tradeMoneyVo.setMockType(MockType.REAL.getCode());
        return OutputResult.buildSucc(tradeMoneyVo);
    }

    @Override
    public void saveMoneyHistory(Integer userId, MockType mock, Date currentDate) {
        //1. 将今天的记录删除掉
        DateTime handlerDate = DateUtil.date(currentDate);
        tradeMoneyHistoryDomainService.deleteByUserIdAndMockTypeAndDate(
                userId, mock.getCode(), handlerDate
        );
        //查看当前的持仓信息
        TradeMoneyDo tradeMoneyDo = tradeMoneyDomainService.getByUserIdAndMockType(userId, mock.getCode());
        // 进行保存
        if (tradeMoneyDo == null) {
            return;
        }
        Instant instant = currentDate.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        TradeMoneyHistoryDo tradeMoneyHistoryDo =
                tradeMoneyHistoryAssembler.doToHis(tradeMoneyDo);
        tradeMoneyHistoryDo.setCurrDate(localDateTime);
        tradeMoneyHistoryDomainService.save(tradeMoneyHistoryDo);
    }

    @Override
    public void syncEasyMoneyToDB(Integer userId, MockType mockType) {
        if (!MockType.REAL.equals(mockType)) {
            return;
        }
        // 用户编号
        TradeMoneyRo tradeMoneyRo = new TradeMoneyRo();
        tradeMoneyRo.setUserId(userId);
        tradeMoneyRo.setMockType(MockType.REAL.getCode());
        // 获取真实的数据
        TradeMoneyVo tradeMoneyVo = realInfo(tradeMoneyRo).getData();

        TradeMoneyDo editTradeMoneyDo = tradeMoneyAssembler.entityToDo(tradeMoneyAssembler.voToEntity(tradeMoneyVo));
        // 为空， 不为空。
        if (editTradeMoneyDo == null) {
            // 设置为空的数据，进行保存。
            editTradeMoneyDo = new TradeMoneyDo();
            editTradeMoneyDo.setUseMoney(BigDecimal.ZERO);
            editTradeMoneyDo.setTotalMoney(BigDecimal.ZERO);
            editTradeMoneyDo.setTakeoutMoney(BigDecimal.ZERO);
            editTradeMoneyDo.setMarketMoney(BigDecimal.ZERO);
            editTradeMoneyDo.setProfitMoney(BigDecimal.ZERO);
            editTradeMoneyDo.setMockType(MockType.REAL.getCode());
            editTradeMoneyDo.setUserId(userId);
            // 进行保存
            tradeMoneyDomainService.save(editTradeMoneyDo);
        } else {
            // 能查询出来。
            editTradeMoneyDo.setUserId(userId);
            editTradeMoneyDo.setMockType(MockType.REAL.getCode());
            TradeMoney tradeMoneyDBInfo = getByUserIdAndMockType(userId, MockType.REAL.getCode());
            if (null == tradeMoneyDBInfo) {
                tradeMoneyDomainService.save(editTradeMoneyDo);
            } else {
                // 不为空，有值.
                editTradeMoneyDo.setId(tradeMoneyDBInfo.getId());
                tradeMoneyDomainService.updateById(editTradeMoneyDo);
            }
        }
    }

    @Override
    public void updateToDayMoney(Integer userId, MockType mockType, BigDecimal todayMoneySum) {
        DateTime now = DateUtil.date();
        //获取昨天的金额信息,如果没有记录,则 返回 0
        TradeMoneyHistoryDo tradeHistoryLastDo = Optional.ofNullable(tradeMoneyHistoryDomainService.getLastRecordProfit(userId, mockType.getCode(), now)).orElse(new TradeMoneyHistoryDo());

        // 昨天的,与今天的相加,就是今天的亏损信息.
        TradeMoneyDo tradeMoneyDo = tradeMoneyDomainService.getByUserIdAndMockType(
                userId, mockType.getCode());
        if (tradeMoneyDo == null) {
            return;
        }
        // 进行保存
        tradeMoneyDo.setProfitMoney(todayMoneySum.add(Optional.ofNullable(tradeHistoryLastDo.getProfitMoney()).orElse(tradeMoneyDo.getProfitMoney())));
        tradeMoneyDo.setTotalMoney(todayMoneySum.add(Optional.ofNullable(tradeHistoryLastDo.getTotalMoney()).orElse(tradeMoneyDo.getTotalMoney())));
        tradeMoneyDo.setMarketMoney(todayMoneySum.add(Optional.ofNullable(tradeHistoryLastDo.getMarketMoney()).orElse(tradeMoneyDo.getMarketMoney())));
        // 更新今天的亏损信息
        tradeMoneyDomainService.updateById(tradeMoneyDo);
    }

    @Override
    public void operateMoney(TradeMoney tradeMoney) {
        // 先查询一下，是否存在.
        TradeMoneyDo tradeMoneyDo = tradeMoneyDomainService.getByUserIdAndMockType(tradeMoney.getUserId(), tradeMoney.getMockType());
        if (null == tradeMoneyDo) {
            // 进行插入
            tradeMoneyDo = new TradeMoneyDo();
            tradeMoneyDo.setTotalMoney(tradeMoney.getTotalMoney());
            tradeMoneyDo.setUseMoney(tradeMoney.getTotalMoney());
            tradeMoneyDo.setMarketMoney(new BigDecimal(0));
            tradeMoneyDo.setTakeoutMoney(tradeMoney.getTotalMoney());
            tradeMoneyDo.setProfitMoney(new BigDecimal(0));
            tradeMoneyDo.setUserId(tradeMoney.getUserId());
            tradeMoneyDo.setMockType(tradeMoney.getMockType());
            tradeMoneyDomainService.save(tradeMoneyDo);
        } else {
            // 存在的话，进行更新。
            TradeMoneyDo editTradeMoneyDo = tradeMoneyAssembler.entityToDo(tradeMoney);
            editTradeMoneyDo.setId(tradeMoneyDo.getId());
            // 进行更新
            tradeMoneyDomainService.updateById(editTradeMoneyDo);
        }
    }

    /**
     * 获取真实的持仓金额信息
     *
     * @param userId 用户编号
     * @return 获取真实的持仓金额信息
     */
    private TradeResultVo<GetAssetsResponse> getAssetsResponse(Integer userId) {
        GetAssetsRequest request = new GetAssetsRequest(userId);
        ResponseParser responseParse = tradeUtil.getResponseParser(request);

        String url = tradeUtil.getUrl(request);
        log.debug("trade {} url: {}", request.getMethod(), url);
        Map<String, String> header = tradeUtil.getHeader(request);

        Map<String, Object> params = tradeUtil.getParams(request);
        log.debug("trade {} request: {}", request.getMethod(), params);
        String content = tradeClient.send(url, params, header);
        log.debug("trade {} response: {}", request.getMethod(), content);
        return responseParse.parse(content, new TypeReference<GetAssetsResponse>() {
        });
    }
}
