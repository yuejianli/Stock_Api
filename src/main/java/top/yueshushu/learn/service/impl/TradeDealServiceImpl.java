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
import top.yueshushu.learn.api.response.GetDealDataResponse;
import top.yueshushu.learn.api.response.GetHisDealDataResponse;
import top.yueshushu.learn.assembler.TradeDealAssembler;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.domain.TradeDealDo;
import top.yueshushu.learn.domain.TradeEntrustDo;
import top.yueshushu.learn.domainservice.TradeDealDomainService;
import top.yueshushu.learn.enumtype.DataFlagType;
import top.yueshushu.learn.enumtype.EntrustType;
import top.yueshushu.learn.enumtype.MockType;
import top.yueshushu.learn.enumtype.TradeRealValueType;
import top.yueshushu.learn.exception.TradeUserException;
import top.yueshushu.learn.helper.TradeRequestHelper;
import top.yueshushu.learn.mode.dto.TradeDealQueryDto;
import top.yueshushu.learn.mode.ro.TradeDealRo;
import top.yueshushu.learn.mode.vo.TradeDealVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;
import top.yueshushu.learn.service.TradeDealService;
import top.yueshushu.learn.service.cache.TradeCacheService;
import top.yueshushu.learn.util.BigDecimalUtil;
import top.yueshushu.learn.util.MyDateUtil;
import top.yueshushu.learn.util.StockUtil;
import top.yueshushu.learn.util.ThreadLocalUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 成交表 自定义的
 * </p>
 *
 * @author 两个蝴蝶飞
 * @since 2022-01-03
 */
@Service
@Slf4j(topic = "tradeDeal")
public class TradeDealServiceImpl implements TradeDealService {
    @Resource
    private TradeDealDomainService tradeDealDomainService;
    @Resource
    private TradeDealAssembler tradeDealAssembler;
    @Resource
    private TradeRequestHelper tradeRequestHelper;
    @Resource
    private TradeCacheService tradeCacheService;

    @Override
    public void addDealRecord(TradeEntrustDo tradeEntrustDo) {
        TradeDealDo tradeDealDo = new TradeDealDo();
        tradeDealDo.setCode(tradeEntrustDo.getCode());
        tradeDealDo.setName(tradeEntrustDo.getName());
        tradeDealDo.setDealDate(DateUtil.date());
        tradeDealDo.setDealType(tradeEntrustDo.getDealType());
        tradeDealDo.setDealNum(tradeEntrustDo.getEntrustNum());
        tradeDealDo.setDealPrice(tradeEntrustDo.getEntrustPrice());
        tradeDealDo.setDealMoney(
                BigDecimalUtil.toBigDecimal(
                        tradeEntrustDo.getEntrustPrice(),
                        new BigDecimal(
                                tradeEntrustDo.getEntrustNum()
                        )
                )
        );
        tradeDealDo.setDealCode(
                Optional.of(tradeEntrustDo.getDealCode()).orElse(StockUtil.generateDealCode())
        );
        tradeDealDo.setEntrustCode(tradeEntrustDo.getEntrustCode());
        tradeDealDo.setEntrustType(tradeEntrustDo.getEntrustType());
        tradeDealDo.setDbType(tradeEntrustDo.getDbType());
        tradeDealDo.setUserId(tradeEntrustDo.getUserId());
        tradeDealDo.setMockType(tradeEntrustDo.getMockType());
        tradeDealDo.setFlag(DataFlagType.NORMAL.getCode());
        log.info("用户{},成交买入委托单{},添加成交记录成功", tradeEntrustDo.getUserId(), tradeEntrustDo.getId());
        tradeDealDomainService.save(tradeDealDo);
    }

    /**
     * 正式盘的处理方式
     *
     * @param tradeDealRo
     * @return
     */
    @Override
    public OutputResult<List<TradeDealVo>> realList(TradeDealRo tradeDealRo) throws TradeUserException {
        //获取响应信息
        TradeResultVo<GetDealDataResponse> tradeResultVo;
        try {
            tradeResultVo = tradeRequestHelper.listRealDeal(tradeDealRo.getUserId());
        } catch (Exception e) {
            throw new TradeUserException("无权限查询真实的 历史委托单信息");
        }
        if (!tradeResultVo.getSuccess()) {
            return OutputResult.buildAlert(ResultCode.TRADE_DEAL_FAIL);
        }
        List<GetDealDataResponse> data = tradeResultVo.getData();

        List<TradeDealVo> tradeDealVoList = new ArrayList<>();
        for (GetDealDataResponse getDealDataResponse : data) {
            TradeDealVo tradeDealVo = new TradeDealVo();
            tradeDealVo.setCode(getDealDataResponse.getZqdm());
            tradeDealVo.setName(getDealDataResponse.getZqmc());
            tradeDealVo.setEntrustCode(getDealDataResponse.getWtbh());
            tradeDealVo.setDealCode(getDealDataResponse.getCjbh());
            tradeDealVo.setDealDate(MyDateUtil.convertToTodayDate(null, getDealDataResponse.getCjsj()));
            tradeDealVo.setDealNum(Integer.parseInt(getDealDataResponse.getWtsl()));
            tradeDealVo.setDealPrice(BigDecimalUtil.toBigDecimal(getDealDataResponse.getCjjg()));
            tradeDealVo.setDealType(getDealDataResponse.toDealType());
            tradeDealVo.setEntrustType(EntrustType.HANDLER.getCode());
            // 看是否成交了
            tradeDealVo.setUserId(ThreadLocalUtils.getUserId());
            tradeDealVo.setMockType(MockType.REAL.getCode());
            tradeDealVo.setFlag(DataFlagType.NORMAL.getCode());
            tradeDealVo.setDealMoney(
                    BigDecimalUtil.toBigDecimal(
                            tradeDealVo.getDealPrice(),
                            new BigDecimal(
                                    tradeDealVo.getDealNum()
                            )
                    )
            );
            tradeDealVoList.add(tradeDealVo);
        }
        return OutputResult.buildSucc(tradeDealVoList);
    }

    @Override
    public OutputResult<PageResponse<TradeDealVo>> mockList(TradeDealRo tradeDealRo) {
        Date now = DateUtil.date();
        DateTime beginNow = DateUtil.beginOfDay(now);
        TradeDealQueryDto tradeDealQueryDto = new TradeDealQueryDto();
        tradeDealQueryDto.setUserId(tradeDealRo.getUserId());
        tradeDealQueryDto.setMockType(tradeDealRo.getMockType());
        tradeDealQueryDto.setDealDate(beginNow);
        Page<Object> page = PageHelper.startPage(tradeDealRo.getPageNum(), tradeDealRo.getPageSize());
        //根据用户去查询信息
        List<TradeDealDo> tradeDealDoList = tradeDealDomainService.listByQuery(tradeDealQueryDto);
        List<TradeDealVo> tradeDealVoList = tradeDealDoList.stream().map(
                n-> tradeDealAssembler.entityToVo(tradeDealAssembler.doToEntity(n))
        ).collect(Collectors.toList());
        return OutputResult.buildSucc(new PageResponse<>(page.getTotal(), tradeDealVoList));
    }


    /**
     * 正式盘的处理方式
     *
     * @param tradeDealRo
     * @return
     */
    @Override
    public List<TradeDealVo> realHistoryList(TradeDealRo tradeDealRo) throws TradeUserException {
        Object realEasyMoneyCache = tradeCacheService.getRealEasyMoneyCache(TradeRealValueType.TRADE_DEAL_HISTORY, tradeDealRo.getUserId());
        if (!ObjectUtils.isEmpty(realEasyMoneyCache)) {
            return (List<TradeDealVo>) realEasyMoneyCache;
        }
        //获取响应信息
        TradeResultVo<GetHisDealDataResponse> tradeResultVo;
        try {
            tradeResultVo = tradeRequestHelper.listRealHistoryDeal(tradeDealRo.getUserId());
        } catch (Exception e) {
            throw new TradeUserException("无权限查询真实的 历史成交单信息");
        }

        if (!tradeResultVo.getSuccess()) {
            return null;
        }
        List<GetHisDealDataResponse> data = tradeResultVo.getData();
        List<TradeDealVo> tradeDealVoList = new ArrayList<>();
        for (GetHisDealDataResponse getDealDataResponse : data) {
            TradeDealVo tradeDealVo = new TradeDealVo();
            tradeDealVo.setCode(getDealDataResponse.getZqdm());
            tradeDealVo.setName(getDealDataResponse.getZqmc());
            tradeDealVo.setEntrustCode(getDealDataResponse.getWtbh());
            tradeDealVo.setDealCode(getDealDataResponse.getCjbh());
            tradeDealVo.setDealDate(MyDateUtil.convertToTodayDate(getDealDataResponse.getCjrq(), getDealDataResponse.getCjsj()));
            tradeDealVo.setDealNum(Integer.parseInt(getDealDataResponse.getWtsl()));
            tradeDealVo.setDealPrice(BigDecimalUtil.toBigDecimal(getDealDataResponse.getCjjg()));
            tradeDealVo.setDealType(getDealDataResponse.toDealType());
            tradeDealVo.setEntrustType(EntrustType.HANDLER.getCode());
            // 看是否成交了
            tradeDealVo.setUserId(ThreadLocalUtils.getUserId());
            tradeDealVo.setMockType(MockType.REAL.getCode());
            tradeDealVo.setFlag(DataFlagType.NORMAL.getCode());
            tradeDealVo.setDealMoney(
                    BigDecimalUtil.toBigDecimal(
                            tradeDealVo.getDealPrice(),
                            new BigDecimal(
                                    tradeDealVo.getDealNum()
                            )
                    )
            );
            tradeDealVoList.add(tradeDealVo);
        }
        tradeCacheService.buildRealEasyMoneyCache(TradeRealValueType.TRADE_DEAL_HISTORY, tradeDealRo.getUserId(), tradeDealVoList, null);
        return tradeDealVoList;
    }

    @Override
    public void syncRealDealByUserId(Integer userId, List<TradeDealVo> tradeDealVoList) {
        //先将当前用户的今日委托信息全部删除
        TradeDealQueryDto tradeDealQueryDto = new TradeDealQueryDto();
        tradeDealQueryDto.setUserId(userId);
        tradeDealQueryDto.setMockType(MockType.REAL.getCode());
        DateTime now = DateUtil.date();
        tradeDealQueryDto.setDealDate(now);
        tradeDealDomainService.deleteToDayByQuery(tradeDealQueryDto);
        if (CollectionUtils.isEmpty(tradeDealVoList)) {
            return ;
        }
        List<TradeDealDo> entrustDoList = tradeDealVoList.stream().map(
                n -> {
                    TradeDealDo tradeDealDo =
                            tradeDealAssembler.entityToDo(tradeDealAssembler.voToEntity(n));
                    tradeDealDo.setUserId(userId);
                    tradeDealDo.setMockType(MockType.REAL.getCode());
                    return tradeDealDo;
                }
        ).collect(Collectors.toList());
        tradeDealDomainService.saveBatch(entrustDoList);
    }

    @Override
    public void syncEasyMoneyToDB(Integer userId, MockType mockType) throws TradeUserException {
        TradeDealRo tradeDealRo = new TradeDealRo();
        tradeDealRo.setUserId(userId);
        List<TradeDealVo> tradeDealVoList = realList(tradeDealRo).getData();

        // 将数据保存下来
        tradeCacheService.buildRealEasyMoneyCache(TradeRealValueType.TRADE_DEAL, tradeDealRo.getUserId(), tradeDealVoList, 36000);
        syncRealDealByUserId(userId, tradeDealVoList);
    }

    /**
     * 查询虚拟盘的信息
     *
     * @param tradeDealRo
     * @return
     */
    @Override
    public OutputResult<PageResponse<TradeDealVo>> mockHistoryList(TradeDealRo tradeDealRo) {
        Page<Object> pageGithubResult = PageHelper.startPage(tradeDealRo.getPageNum(), tradeDealRo.getPageSize());
        DateTime now = DateUtil.date();
        //不包含今天
        DateTime beginNow = DateUtil.beginOfDay(now);
        TradeDealQueryDto tradeDealQueryDto = new TradeDealQueryDto();
        tradeDealQueryDto.setUserId(tradeDealRo.getUserId());
        tradeDealQueryDto.setMockType(tradeDealRo.getMockType());
        tradeDealQueryDto.setDealType(tradeDealRo.getDealType());
        tradeDealQueryDto.setCode(tradeDealRo.getCode());
        if (!StringUtils.hasText(tradeDealRo.getStartDate())) {
            //获取14天前的日期
            DateTime before14Day = DateUtil.offsetDay(beginNow, -14);
            tradeDealQueryDto.setStartDealDate(before14Day);
        } else {
            tradeDealQueryDto.setStartDealDate(DateUtil.parse(tradeDealRo.getStartDate(), Const.SIMPLE_DATE_FORMAT));
        }

        if (!StringUtils.hasText(tradeDealRo.getEndDate())){
            tradeDealQueryDto.setEndDealDate(beginNow);
        }else{
            tradeDealQueryDto.setEndDealDate(
                    DateUtil.parse(tradeDealRo.getEndDate(), Const.SIMPLE_DATE_FORMAT)
            );
        }
        //根据用户去查询信息
        List<TradeDealDo> tradeDealDoList = tradeDealDomainService.listHistoryByQuery(tradeDealQueryDto);
        if (CollectionUtils.isEmpty(tradeDealDoList)){
            return OutputResult.buildSucc(
                    PageResponse.emptyPageResponse()
            );
        }
        List<TradeDealVo> pageResultList = tradeDealDoList.stream().map(
                n-> tradeDealAssembler.entityToVo(tradeDealAssembler.doToEntity(n))
        ).collect(Collectors.toList());
        PageInfo pageInfo=new PageInfo<>(pageResultList);
        return OutputResult.buildSucc(new PageResponse<>(
                pageGithubResult.getTotal(),pageInfo.getList()
        ));
    }
}
