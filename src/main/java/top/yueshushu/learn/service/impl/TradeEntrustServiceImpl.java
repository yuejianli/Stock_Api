package top.yueshushu.learn.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import top.yueshushu.learn.api.TradeResultVo;
import top.yueshushu.learn.api.response.GetHisOrdersDataResponse;
import top.yueshushu.learn.api.response.GetOrdersDataResponse;
import top.yueshushu.learn.assembler.TradeEntrustAssembler;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.domain.TradeEntrustDo;
import top.yueshushu.learn.domainservice.TradeEntrustDomainService;
import top.yueshushu.learn.entity.TradeEntrust;
import top.yueshushu.learn.enumtype.*;
import top.yueshushu.learn.exception.TradeUserException;
import top.yueshushu.learn.helper.TradeRequestHelper;
import top.yueshushu.learn.mode.dto.TradeEntrustQueryDto;
import top.yueshushu.learn.mode.ro.TradeEntrustRo;
import top.yueshushu.learn.mode.vo.TradeEntrustVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;
import top.yueshushu.learn.service.TradeEntrustService;
import top.yueshushu.learn.service.cache.TradeCacheService;
import top.yueshushu.learn.util.BigDecimalUtil;
import top.yueshushu.learn.util.MyDateUtil;
import top.yueshushu.learn.util.StockUtil;
import top.yueshushu.learn.util.ThreadLocalUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 委托表 自定义的
 * </p>
 *
 * @author 两个蝴蝶飞
 * @since 2022-01-03
 */
@Service
@Slf4j
public class TradeEntrustServiceImpl implements TradeEntrustService {
    @Resource
    private TradeEntrustDomainService tradeEntrustDomainService;
    @Resource
    private TradeEntrustAssembler tradeEntrustAssembler;
    @Resource
    private TradeRequestHelper tradeRequestHelper;
    @Resource
    private TradeCacheService tradeCacheService;

    @Override
    public List<TradeEntrust> listNowRunEntrust(Integer userId, Integer mockType) {

        TradeEntrustQueryDto tradeEntrustQueryDto = new TradeEntrustQueryDto();
        tradeEntrustQueryDto.setUserId(userId);
        tradeEntrustQueryDto.setMockType(mockType);
        DateTime now = DateUtil.date();
        tradeEntrustQueryDto.setEntrustDate(now);
        tradeEntrustQueryDto.setEntrustStatus(EntrustStatusType.ING.getCode());
        //根据用户去查询信息
        List<TradeEntrustDo> tradeEntrustDoList = tradeEntrustDomainService.listByQuery(tradeEntrustQueryDto);
        if (CollectionUtils.isEmpty(tradeEntrustDoList)) {
            return Collections.emptyList();
        }
        return tradeEntrustDoList.stream().map(n -> tradeEntrustAssembler.doToEntity(n))
                .collect(Collectors.toList());
    }

    /**
     * 正式盘的处理方式
     *
     * @param tradeEntrustRo
     * @return
     */
    @Override
    public OutputResult<List<TradeEntrustVo>> realList(TradeEntrustRo tradeEntrustRo) throws TradeUserException {
        //获取响应信息
        TradeResultVo<GetOrdersDataResponse> tradeResultVo;
        try {
            tradeResultVo = tradeRequestHelper.findRealEntrust(tradeEntrustRo.getUserId());
        } catch (Exception e) {
            throw new TradeUserException("无权限查询真实的 今日委托单信息");
        }
        if (!tradeResultVo.getSuccess()) {
            return OutputResult.buildAlert(ResultCode.TRADE_ENTRUST_FAIL);
        }
        List<GetOrdersDataResponse> data = tradeResultVo.getData();

        List<TradeEntrustVo> tradeEntrustVoList = new ArrayList<>();
        for (GetOrdersDataResponse getOrdersDataResponse : data) {
            TradeEntrustVo tradeEntrustVo = new TradeEntrustVo();
            tradeEntrustVo.setCode(getOrdersDataResponse.getZqdm());
            tradeEntrustVo.setName(getOrdersDataResponse.getZqmc());
            tradeEntrustVo.setEntrustCode(getOrdersDataResponse.getWtbh());
            tradeEntrustVo.setEntrustDate(MyDateUtil.convertToTodayDate(null, getOrdersDataResponse.getWtsj()));
            tradeEntrustVo.setEntrustNum(Integer.parseInt(getOrdersDataResponse.getWtsl()));
            tradeEntrustVo.setEntrustPrice(BigDecimalUtil.toBigDecimal(getOrdersDataResponse.getWtjg()));
            tradeEntrustVo.setDealType(getOrdersDataResponse.toDealType());
            tradeEntrustVo.setEntrustStatus(getOrdersDataResponse.toEntrustStatus());
            tradeEntrustVo.setEntrustType(EntrustType.HANDLER.getCode());
            // 看是否成交了
            tradeEntrustVo.setUserId(ThreadLocalUtils.getUserId());
            tradeEntrustVo.setMockType(MockType.REAL.getCode());
            tradeEntrustVo.setFlag(DataFlagType.NORMAL.getCode());

            // 处理金额
            tradeEntrustVo.setUseMoney(BigDecimal.ZERO);
            tradeEntrustVo.setTakeoutMoney(BigDecimal.ZERO);
            tradeEntrustVo.setEntrustMoney(
                    StockUtil.allMoney(
                            tradeEntrustVo.getEntrustNum(),
                            tradeEntrustVo.getEntrustPrice()
                    )
            );
            tradeEntrustVo.setHandMoney(
                    StockUtil.getBuyHandMoney(
                            tradeEntrustVo.getEntrustNum(),
                            tradeEntrustVo.getEntrustPrice(),
                            BigDecimalUtil.toBigDecimal(tradeEntrustVo.getCode())
                    )
            );
            //获取对应的手续费
            BigDecimal buyMoney = getBuyMoney(
                    tradeEntrustVo.getEntrustNum(),
                    tradeEntrustVo.getEntrustPrice(),
                    BigDecimalUtil.toBigDecimal(tradeEntrustVo.getCode())
            );
            tradeEntrustVo.setTotalMoney(buyMoney);


            tradeEntrustVoList.add(tradeEntrustVo);
        }
        return OutputResult.buildSucc(tradeEntrustVoList);
    }

    /**
     * 获取买入，总共需要的手续费
     *
     * @param amount
     * @param price
     * @param tranPrice
     * @return
     */
    private BigDecimal getBuyMoney(Integer amount, BigDecimal price, BigDecimal tranPrice) {
        return StockUtil.getBuyMoney(
                amount,
                price,
                tranPrice
        );
    }

    @Override
    public void syncRealEntrustByUserId(Integer userId, List<TradeEntrustVo> tradeEntrustVoList) {
        //先将当前用户的今日委托信息全部删除
        TradeEntrustQueryDto tradeEntrustQueryDto = new TradeEntrustQueryDto();
        tradeEntrustQueryDto.setUserId(userId);
        tradeEntrustQueryDto.setMockType(MockType.REAL.getCode());
        DateTime now = DateUtil.date();
        tradeEntrustQueryDto.setEntrustDate(now);
        tradeEntrustDomainService.deleteToDayByQuery(tradeEntrustQueryDto);
        if (CollectionUtils.isEmpty(tradeEntrustVoList)) {
            return ;
        }
        List<TradeEntrustDo> entrustDoList = tradeEntrustVoList.stream().map(
                n -> {
                    TradeEntrustDo tradeEntrustDo =
                            tradeEntrustAssembler.entityToDo(tradeEntrustAssembler.voToEntity(n));
                    tradeEntrustDo.setUserId(userId);
                    tradeEntrustDo.setMockType(MockType.REAL.getCode());
                    return tradeEntrustDo;
                }
        ).collect(Collectors.toList());
        tradeEntrustDomainService.saveBatch(entrustDoList);
    }

    @Override
    public OutputResult<PageResponse<TradeEntrustVo>> mockList(TradeEntrustRo tradeEntrustRo) {
        DateTime now = DateUtil.date();
        //根据用户去查询信息
        TradeEntrustQueryDto tradeEntrustQueryDto = new TradeEntrustQueryDto();
        tradeEntrustQueryDto.setUserId(tradeEntrustRo.getUserId());
        tradeEntrustQueryDto.setMockType(tradeEntrustRo.getMockType());
        tradeEntrustQueryDto.setEntrustDate(now);
        Page<Object> pageInfo = PageHelper.startPage(tradeEntrustRo.getPageNum(), tradeEntrustRo.getPageSize());
        List<TradeEntrustDo> tradeEntrustDoList = tradeEntrustDomainService.listByQuery(
                tradeEntrustQueryDto
        );
        List<TradeEntrustVo> tradePositionVoList = tradeEntrustDoList.stream().map(
                n -> tradeEntrustAssembler.entityToVo(tradeEntrustAssembler.doToEntity(n))
        ).collect(Collectors.toList());
        return OutputResult.buildSucc(new PageResponse<>(pageInfo.getTotal(), tradePositionVoList));
    }

    @Override
    public OutputResult<TradeEntrustVo> getInfoByCondition(TradeEntrustRo tradeEntrustRo) {
        TradeEntrustQueryDto tradeEntrustQueryDto = new TradeEntrustQueryDto();
        tradeEntrustQueryDto.setUserId(tradeEntrustRo.getUserId());
        tradeEntrustQueryDto.setMockType(tradeEntrustRo.getMockType());
        tradeEntrustQueryDto.setEntrustCode(tradeEntrustRo.getEntrustCode());
        List<TradeEntrustDo> tradeEntrustDoList = tradeEntrustDomainService.listByQuery(
                tradeEntrustQueryDto
        );
        List<TradeEntrustVo> tradePositionVoList = tradeEntrustDoList.stream().map(
                n -> tradeEntrustAssembler.entityToVo(tradeEntrustAssembler.doToEntity(n))
        ).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(tradePositionVoList)) {
            return OutputResult.buildAlert(ResultCode.TRADE_ENTRUST_CODE_EMPTY);
        }
        return OutputResult.buildSucc(tradePositionVoList.get(0));
    }

    /**********对历史记录的处理*************/
    /**
     * 正式盘的历史处理方式
     *
     * @param tradeEntrustRo
     * @return
     */
    @Override
    public List<TradeEntrustVo> realHistoryList(TradeEntrustRo tradeEntrustRo) throws TradeUserException {
        Object realEasyMoneyCache = tradeCacheService.getRealEasyMoneyCache(TradeRealValueType.TRADE_ENTRUST_HISTORY, tradeEntrustRo.getUserId());
        if (!ObjectUtils.isEmpty(realEasyMoneyCache)) {
            return (List<TradeEntrustVo>) realEasyMoneyCache;
        }

        //获取响应信息
        TradeResultVo<GetHisOrdersDataResponse> tradeResultVo =
                null;
        try {
            tradeResultVo = tradeRequestHelper.findRealHistoryEntrust(tradeEntrustRo.getUserId());
        } catch (Exception e) {
            throw new TradeUserException("无权限查询真实的 历史委托单信息");
        }
        if (!tradeResultVo.getSuccess()) {
            return Collections.emptyList();
        }
        List<GetHisOrdersDataResponse> data = tradeResultVo.getData();

        List<TradeEntrustVo> tradeEntrustVoList = new ArrayList<>();
        for (GetHisOrdersDataResponse getOrdersDataResponse : data) {
            TradeEntrustVo tradeEntrustVo = new TradeEntrustVo();
            tradeEntrustVo.setCode(getOrdersDataResponse.getZqdm());
            tradeEntrustVo.setName(getOrdersDataResponse.getZqmc());
            tradeEntrustVo.setEntrustCode(getOrdersDataResponse.getWtbh());
            tradeEntrustVo.setEntrustDate(MyDateUtil.convertToTodayDate(getOrdersDataResponse.getWtrq(),
                    getOrdersDataResponse.getWtsj().substring(0, getOrdersDataResponse.getWtsj().length() - 2)));
            tradeEntrustVo.setEntrustNum(Integer.parseInt(getOrdersDataResponse.getWtsl()));
            tradeEntrustVo.setEntrustPrice(BigDecimalUtil.toBigDecimal(getOrdersDataResponse.getWtjg()));
            tradeEntrustVo.setDealType(getOrdersDataResponse.toDealType());
            tradeEntrustVo.setEntrustStatus(getOrdersDataResponse.toEntrustStatus());
            tradeEntrustVo.setEntrustType(EntrustType.HANDLER.getCode());
            // 看是否成交了
            tradeEntrustVo.setUserId(ThreadLocalUtils.getUserId());
            tradeEntrustVo.setMockType(MockType.REAL.getCode());
            tradeEntrustVo.setFlag(DataFlagType.NORMAL.getCode());

            // 处理金额
            tradeEntrustVo.setUseMoney(BigDecimal.ZERO);
            tradeEntrustVo.setTakeoutMoney(BigDecimal.ZERO);
            tradeEntrustVo.setEntrustMoney(
                    StockUtil.allMoney(
                            tradeEntrustVo.getEntrustNum(),
                            tradeEntrustVo.getEntrustPrice()
                    )
            );
            tradeEntrustVo.setHandMoney(
                    StockUtil.getBuyHandMoney(
                            tradeEntrustVo.getEntrustNum(),
                            tradeEntrustVo.getEntrustPrice(),
                            BigDecimalUtil.toBigDecimal(tradeEntrustVo.getCode())
                    )
            );
            //获取对应的手续费
            BigDecimal buyMoney = getBuyMoney(
                    tradeEntrustVo.getEntrustNum(),
                    tradeEntrustVo.getEntrustPrice(),
                    BigDecimalUtil.toBigDecimal(tradeEntrustVo.getCode())
            );
            tradeEntrustVo.setTotalMoney(buyMoney);
            tradeEntrustVoList.add(tradeEntrustVo);
        }
        tradeCacheService.buildRealEasyMoneyCache(TradeRealValueType.TRADE_ENTRUST_HISTORY, tradeEntrustRo.getUserId(), tradeEntrustVoList, null);
        return tradeEntrustVoList;
    }

    @Override
    public void syncEasyMoneyToDB(Integer userId, MockType mockType) {
        TradeEntrustRo tradeEntrustRo = new TradeEntrustRo();
        tradeEntrustRo.setUserId(userId);
        try {
            syncRealEntrustByUserId(userId, realHistoryList(tradeEntrustRo));
        } catch (TradeUserException e) {
            log.error("异常信息", e);
        }
    }
    @Override
    public OutputResult<PageResponse<TradeEntrustVo>> mockHistoryList(TradeEntrustRo tradeEntrustRo) {

        Page<Object> pageGithubResult = PageHelper.startPage(tradeEntrustRo.getPageNum(), tradeEntrustRo.getPageSize());

        Date now = DateUtil.date();
        //不包含今天
        DateTime beginNow = DateUtil.beginOfDay(now);
        TradeEntrustQueryDto tradeEntrustQueryDto = new TradeEntrustQueryDto();
        tradeEntrustQueryDto.setUserId(tradeEntrustRo.getUserId());
        tradeEntrustQueryDto.setMockType(tradeEntrustRo.getMockType());
        if (!StringUtils.hasText(tradeEntrustRo.getStartDate())) {
            //获取14天前的日期
            DateTime before14Day = DateUtil.offsetDay(beginNow, -14);
            tradeEntrustQueryDto.setStartEntrustDate(
                    before14Day
            );
        } else {
            tradeEntrustQueryDto.setStartEntrustDate(
                    DateUtil.parse(tradeEntrustRo.getStartDate(), Const.SIMPLE_DATE_FORMAT)
            );
        }
        tradeEntrustQueryDto.setEndEntrustDate(beginNow);
        tradeEntrustQueryDto.setInEntrustStatus(tradeEntrustRo.getStatusList());
        //根据用户去查询信息
        List<TradeEntrustDo> tradeEntrustDoList = tradeEntrustDomainService.listHistoryByQuery(tradeEntrustQueryDto);
        if (CollectionUtils.isEmpty(tradeEntrustDoList)){
            return OutputResult.buildSucc(
                    PageResponse.emptyPageResponse()
            );
        }
        List<TradeEntrustVo> pageResultList = tradeEntrustDoList.stream().map(
                n-> tradeEntrustAssembler.entityToVo(tradeEntrustAssembler.doToEntity(n))
        ).collect(Collectors.toList());
        PageInfo pageInfo=new PageInfo<>(pageResultList);
        return OutputResult.buildSucc(new PageResponse<>(
                pageGithubResult.getTotal(),pageInfo.getList()
        ));
    }
}
