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
import top.yueshushu.learn.api.request.GetHisOrdersDataRequest;
import top.yueshushu.learn.api.request.GetOrdersDataRequest;
import top.yueshushu.learn.api.response.GetHisOrdersDataResponse;
import top.yueshushu.learn.api.response.GetOrdersDataResponse;
import top.yueshushu.learn.api.responseparse.DataObjResponseParser;
import top.yueshushu.learn.assembler.TradeEntrustAssembler;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.config.TradeClient;
import top.yueshushu.learn.domain.TradeEntrustDo;
import top.yueshushu.learn.domainservice.TradeEntrustDomainService;
import top.yueshushu.learn.entity.TradeEntrust;
import top.yueshushu.learn.enumtype.EntrustStatusType;
import top.yueshushu.learn.enumtype.MockType;
import top.yueshushu.learn.mode.dto.TradeEntrustQueryDto;
import top.yueshushu.learn.mode.ro.TradeEntrustRo;
import top.yueshushu.learn.mode.vo.TradeEntrustVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;
import top.yueshushu.learn.service.TradeEntrustService;
import top.yueshushu.learn.util.TradeUtil;

import javax.annotation.Resource;
import java.util.*;
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
    private DataObjResponseParser dataObjResponseParser;
    @Resource
    private TradeUtil tradeUtil;
    @Resource
    private TradeClient tradeClient;

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
     * @param tradeEntrustRo
     * @return
     */
    @Override
    public OutputResult<List<TradeEntrustVo>> realList(TradeEntrustRo tradeEntrustRo) {
        //获取响应信息
        TradeResultVo<GetOrdersDataResponse> tradeResultVo = getOrdersDataResponse(tradeEntrustRo.getUserId());
        if (!tradeResultVo.getSuccess()) {
            return OutputResult.buildAlert(ResultCode.TRADE_ENTRUST_FAIL);
        }
        List<GetOrdersDataResponse> data = tradeResultVo.getData();

        List<TradeEntrustVo> tradeEntrustVoList = new ArrayList<>();
        for(GetOrdersDataResponse getOrdersDataResponse:data){
            TradeEntrustVo tradeEntrustVo = new TradeEntrustVo();
            tradeEntrustVo.setCode(getOrdersDataResponse.getMmlb());
            tradeEntrustVoList.add(tradeEntrustVo);
        }
        return OutputResult.buildSucc(tradeEntrustVoList);
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

    /**
     * 获取委托的信息
     * @param userId 用户编号
     * @return 获取委托的信息
     */
    private TradeResultVo<GetOrdersDataResponse> getOrdersDataResponse(Integer userId) {
        GetOrdersDataRequest request = new GetOrdersDataRequest(userId);
        String url = tradeUtil.getUrl(request);
        Map<String, String> header = tradeUtil.getHeader(request);
        Map<String, Object> params = null;
        params = tradeUtil.getParams(request);
        log.debug("trade {} request: {}", request.getMethod(), params);
        String content = tradeClient.send(url, params, header);
        log.debug("trade {} response: {}", request.getMethod(), content);
        TradeResultVo<GetOrdersDataResponse> result = dataObjResponseParser.parse(content,
                new TypeReference<GetOrdersDataResponse>() {
                });
        return result;
    }

    @Override
    public OutputResult mockList(TradeEntrustRo tradeEntrustRo) {
        DateTime now = DateUtil.date();
        //根据用户去查询信息
        TradeEntrustQueryDto tradeEntrustQueryDto = new TradeEntrustQueryDto();
        tradeEntrustQueryDto.setUserId(tradeEntrustRo.getUserId());
        tradeEntrustQueryDto.setMockType(tradeEntrustRo.getMockType());
        tradeEntrustQueryDto.setEntrustDate(now);
        List<TradeEntrustDo> tradeEntrustDoList = tradeEntrustDomainService.listByQuery(
                tradeEntrustQueryDto
        );
        if (CollectionUtils.isEmpty(tradeEntrustDoList)) {
           return OutputResult.buildSucc(Collections.EMPTY_LIST);
        }
        List<TradeEntrustVo> tradePositionVoList = tradeEntrustDoList.stream().map(
                n-> tradeEntrustAssembler.entityToVo(tradeEntrustAssembler.doToEntity(n))
        ).collect(Collectors.toList());
        return OutputResult.buildSucc(tradePositionVoList);
    }


    /**********对历史记录的处理*************/
    /**
     * 正式盘的历史处理方式
     * @param tradeEntrustRo
     * @return
     */
    @Override
    public OutputResult realHistoryList(TradeEntrustRo tradeEntrustRo) {
        //获取响应信息
        TradeResultVo<GetHisOrdersDataResponse> tradeResultVo =
                getHistoryOrdersDataResponse(tradeEntrustRo.getUserId());
        if (!tradeResultVo.getSuccess()) {
            return OutputResult.buildAlert(ResultCode.TRADE_ENTRUST_HISTORY_FAIL);
        }
        List<GetHisOrdersDataResponse> data = tradeResultVo.getData();

        List<TradeEntrustVo> tradeEntrustVoList = new ArrayList<>();
        for(GetHisOrdersDataResponse getOrdersDataResponse:data){
            TradeEntrustVo tradeEntrustVo = new TradeEntrustVo();
            tradeEntrustVo.setCode(getOrdersDataResponse.getMmlb());
            tradeEntrustVoList.add(tradeEntrustVo);
        }
        return OutputResult.buildSucc(tradeEntrustVoList);
    }

    /**
     * 获取历史响应信息
     * @param userId
     * @return
     */
    private TradeResultVo<GetHisOrdersDataResponse> getHistoryOrdersDataResponse(Integer userId) {
        GetHisOrdersDataRequest request = new GetHisOrdersDataRequest(userId);
        request.setEt(DateUtil.format(new Date(), "yyyy-MM-dd"));
        Date et = new Date();
        et.setTime(et.getTime() - 7 * 24 * 3600 * 1000);
        request.setSt(DateUtil.format(et, "yyyy-MM-dd"));
        String url = tradeUtil.getUrl(request);
        Map<String, String> header = tradeUtil.getHeader(request);
        Map<String, Object> params = null;
        params = tradeUtil.getParams(request);
        log.debug("trade {} request: {}", request.getMethod(), params);
        String content = tradeClient.send(url, params, header);
        log.debug("trade {} response: {}", request.getMethod(), content);
        TradeResultVo<GetHisOrdersDataResponse> result = dataObjResponseParser.parse(content,
                new TypeReference<GetHisOrdersDataResponse>() {
                });
        return result;
    }
    @Override
    public OutputResult mockHistoryList(TradeEntrustRo tradeEntrustRo) {

        Page<Object> pageGithubResult = PageHelper.startPage(tradeEntrustRo.getPageNum(), tradeEntrustRo.getPageSize());

        Date now = DateUtil.date();
        //不包含今天
        DateTime beginNow = DateUtil.beginOfDay(now);
        TradeEntrustQueryDto tradeEntrustQueryDto = new TradeEntrustQueryDto();
        tradeEntrustQueryDto.setUserId(tradeEntrustRo.getUserId());
        tradeEntrustQueryDto.setMockType(tradeEntrustRo.getMockType());
        if (!StringUtils.hasText(tradeEntrustRo.getStartDate())){
            //获取14天前的日期
            DateTime before14Day = DateUtil.offsetDay(beginNow,-14);
            tradeEntrustQueryDto.setStartEntrustDate(
                    before14Day
            );
        }else{
            tradeEntrustQueryDto.setStartEntrustDate(
                    DateUtil.parse(tradeEntrustRo.getStartDate(), Const.SIMPLE_DATE_FORMAT)
            );
        }
        tradeEntrustQueryDto.setEndEntrustDate(beginNow);

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
