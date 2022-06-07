package top.yueshushu.learn.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.yueshushu.learn.api.TradeResultVo;
import top.yueshushu.learn.api.request.GetDealDataRequest;
import top.yueshushu.learn.api.request.GetHisDealDataRequest;
import top.yueshushu.learn.api.response.GetDealDataResponse;
import top.yueshushu.learn.api.response.GetHisDealDataResponse;
import top.yueshushu.learn.api.responseparse.DefaultResponseParser;
import top.yueshushu.learn.assembler.TradeDealAssembler;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.config.TradeClient;
import top.yueshushu.learn.domain.TradeDealDo;
import top.yueshushu.learn.domain.TradeEntrustDo;
import top.yueshushu.learn.domainservice.TradeDealDomainService;
import top.yueshushu.learn.enumtype.DataFlagType;
import top.yueshushu.learn.enumtype.MockType;
import top.yueshushu.learn.mode.dto.TradeDealQueryDto;
import top.yueshushu.learn.mode.ro.TradeDealRo;
import top.yueshushu.learn.mode.vo.TradeDealVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;
import top.yueshushu.learn.service.TradeDealService;
import top.yueshushu.learn.util.BigDecimalUtil;
import top.yueshushu.learn.util.PageUtil;
import top.yueshushu.learn.util.StockUtil;
import top.yueshushu.learn.util.TradeUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
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
    private DefaultResponseParser defaultResponseParser;
    @Resource
    private TradeUtil tradeUtil;
    @Resource
    private TradeClient tradeClient;

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
                StockUtil.generateDealCode()
        );
        tradeDealDo.setEntrustCode(tradeEntrustDo.getEntrustCode());
        tradeDealDo.setEntrustType(tradeEntrustDo.getEntrustType());
        tradeDealDo.setUserId(tradeEntrustDo.getUserId());
        tradeDealDo.setMockType(tradeEntrustDo.getMockType());
        tradeDealDo.setFlag(DataFlagType.NORMAL.getCode());
        log.info("用户{},成交买入委托单{},添加成交记录成功", tradeEntrustDo.getUserId(), tradeEntrustDo.getId());
        tradeDealDomainService.save(tradeDealDo);
    }
    /**
     * 正式盘的处理方式
     * @param tradeDealRo
     * @return
     */
    @Override
    public OutputResult<List<TradeDealVo>> realList(TradeDealRo tradeDealRo) {
        //获取响应信息
        TradeResultVo<GetDealDataResponse> tradeResultVo = getDealDataResponse(tradeDealRo.getUserId());
        if (!tradeResultVo.getSuccess()) {
            return OutputResult.buildAlert(ResultCode.TRADE_DEAL_FAIL);
        }
        List<GetDealDataResponse> data = tradeResultVo.getData();

        List<TradeDealVo> tradeDealVoList = new ArrayList<>();
        for(GetDealDataResponse getDealDataResponse:data){
            TradeDealVo tradeDealVo = new TradeDealVo();
            tradeDealVo.setCode(getDealDataResponse.getCjbh());
            tradeDealVoList.add(tradeDealVo);
        }
        return OutputResult.buildSucc(tradeDealVoList);
    }

    /**
     * 获取响应的信息
     * @param userId
     * @return
     */
    private TradeResultVo<GetDealDataResponse> getDealDataResponse(Integer userId) {
        GetDealDataRequest request = new GetDealDataRequest(userId);
        String url = tradeUtil.getUrl(request);
        Map<String, String> header = tradeUtil.getHeader(request);

        List<Map<String, Object>> paramList = null;
        Map<String, Object> params = null;
        params = tradeUtil.getParams(request);
        log.debug("trade {} request: {}", request.getMethod(), params);
        String content = tradeClient.send(url, params, header);
        log.debug("trade {} response: {}", request.getMethod(), content);
        TradeResultVo<GetDealDataResponse> result= defaultResponseParser.parse(content,
                new TypeReference<GetDealDataResponse>(){});
        return result;
    }
    @Override
    public OutputResult mockList(TradeDealRo tradeDealRo) {
        Date now = DateUtil.date();
        DateTime beginNow = DateUtil.beginOfDay(now);


        TradeDealQueryDto tradeDealQueryDto = new TradeDealQueryDto();
        tradeDealQueryDto.setUserId(tradeDealRo.getUserId());
        tradeDealQueryDto.setMockType(tradeDealRo.getMockType());
        tradeDealQueryDto.setDealDate(beginNow);
        //根据用户去查询信息
        List<TradeDealDo> tradeDealDoList = tradeDealDomainService.listByQuery(tradeDealQueryDto);
        if (CollectionUtils.isEmpty(tradeDealDoList)) {
            return OutputResult.buildSucc(Collections.emptyList());
        }
        List<TradeDealVo> tradeDealVoList = tradeDealDoList.stream().map(
                n-> tradeDealAssembler.entityToVo(tradeDealAssembler.doToEntity(n))
        ).collect(Collectors.toList());
        return OutputResult.buildSucc(tradeDealVoList);
    }


    /**
     * 正式盘的处理方式
     * @param tradeDealRo
     * @return
     */
    @Override
    public OutputResult realHistoryList(TradeDealRo tradeDealRo) {
        //获取响应信息
        TradeResultVo<GetHisDealDataResponse> tradeResultVo = getHisDealDataResponse(tradeDealRo.getUserId());
        if (!tradeResultVo.getSuccess()) {
            return OutputResult.buildAlert(ResultCode.TRADE_DEAL_HISTORY_FAIL);
        }
        List<GetHisDealDataResponse> data = tradeResultVo.getData();
        List<TradeDealVo> tradeDealVoList = new ArrayList<>();
        for(GetHisDealDataResponse getDealDataResponse:data){
            TradeDealVo tradeDealVo = new TradeDealVo();
            tradeDealVo.setCode(getDealDataResponse.getCjbh());
            tradeDealVoList.add(tradeDealVo);
        }
        List<TradeDealVo> list = PageUtil.startPage(tradeDealVoList, tradeDealRo.getPageNum(),
                tradeDealRo.getPageSize());
        return OutputResult.buildSucc(new PageResponse<>((long) list.size(),
                list));
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
                    TradeDealDo tradeDealDo=
                            tradeDealAssembler.entityToDo(tradeDealAssembler.voToEntity(n));
                    tradeDealDo.setUserId(userId);
                    tradeDealDo.setMockType(MockType.REAL.getCode());
                    return tradeDealDo;
                }
        ).collect(Collectors.toList());
        tradeDealDomainService.saveBatch(entrustDoList);
    }

    /**
     * 获取响应的信息
     * @param userId
     * @return
     */
    private TradeResultVo<GetHisDealDataResponse> getHisDealDataResponse(Integer userId) {
        GetHisDealDataRequest request = new GetHisDealDataRequest(userId);
        request.setEt(DateUtil.format(new Date(), "yyyy-MM-dd"));
        Date et = new Date();
        et.setTime(et.getTime() - 7 * 24 * 3600 * 1000);
        request.setSt(DateUtil.format(et, "yyyy-MM-dd"));
        String url = tradeUtil.getUrl(request);
        Map<String, String> header = tradeUtil.getHeader(request);
        List<Map<String, Object>> paramList = null;
        Map<String, Object> params = null;
        params = tradeUtil.getParams(request);
        log.debug("trade {} request: {}", request.getMethod(), params);
        String content = tradeClient.send(url, params, header);
        log.debug("trade {} response: {}", request.getMethod(), content);
        TradeResultVo<GetHisDealDataResponse> result= defaultResponseParser.parse(content,
                new TypeReference<GetHisDealDataResponse>(){});
        return result;
    }

    /**
     * 查询虚拟盘的信息
     * @param tradeDealRo
     * @return
     */
    @Override
    public OutputResult mockHistoryList(TradeDealRo tradeDealRo) {
        Page<Object> pageGithubResult = PageHelper.startPage(tradeDealRo.getPageNum(), tradeDealRo.getPageSize());
        DateTime now = DateUtil.date();
        //不包含今天
        DateTime beginNow = DateUtil.beginOfDay(now);
        TradeDealQueryDto tradeDealQueryDto = new TradeDealQueryDto();
        tradeDealQueryDto.setUserId(tradeDealRo.getUserId());
        tradeDealQueryDto.setMockType(tradeDealRo.getMockType());
        tradeDealQueryDto.setDealType(tradeDealRo.getDealType());
        tradeDealQueryDto.setCode(tradeDealRo.getCode());
        if (!StringUtils.hasText(tradeDealRo.getStartDate())){
            //获取14天前的日期
            DateTime before14Day = DateUtil.offsetDay(beginNow,-14);
            tradeDealQueryDto.setStartDealDate(before14Day);
        }else{
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