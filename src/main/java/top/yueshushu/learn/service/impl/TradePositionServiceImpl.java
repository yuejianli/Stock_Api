package top.yueshushu.learn.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.api.TradeResultVo;
import top.yueshushu.learn.api.request.GetStockListRequest;
import top.yueshushu.learn.api.response.GetStockListResponse;
import top.yueshushu.learn.api.responseparse.DefaultResponseParser;
import top.yueshushu.learn.assembler.TradePositionAssembler;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.config.TradeClient;
import top.yueshushu.learn.domain.TradePositionDo;
import top.yueshushu.learn.domain.TradePositionHistoryDo;
import top.yueshushu.learn.domainservice.TradePositionDomainService;
import top.yueshushu.learn.domainservice.TradePositionHistoryDomainService;
import top.yueshushu.learn.entity.TradePosition;
import top.yueshushu.learn.enumtype.MockType;
import top.yueshushu.learn.mode.ro.TradePositionRo;
import top.yueshushu.learn.mode.vo.TradePositionVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.TradePositionService;
import top.yueshushu.learn.util.TradeUtil;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 我的持仓表 自定义的
 * </p>
 *
 * @author 两个蝴蝶飞
 * @since 2022-01-03
 */
@Service
@Slf4j(topic = "持仓信息")
public class TradePositionServiceImpl implements TradePositionService {

    @Resource
    private TradePositionHistoryDomainService tradePositionHistoryDomainService;

    @Resource
    private TradePositionDomainService tradePositionDomainService;
    @Resource
    private DefaultResponseParser defaultResponseParser;

    @Resource
    private TradeClient tradeClient;

    @Resource
    private TradeUtil tradeUtil;

    @Resource
    private TradePositionAssembler tradePositionAssembler;

    @Override
    public TradePosition getPositionByCode(Integer userId,
                                           Integer mockType,
                                           String code) {
        //根据用户去查询信息
        List<TradePositionDo> tradePositionDoList = tradePositionDomainService.listByUserIdAndMockTypeAndCode(userId,
                mockType,code);
        if (CollectionUtils.isEmpty(tradePositionDoList)) {
            return null;
        }
        return tradePositionAssembler.doToEntity(tradePositionDoList.get(0));
    }

    @Override
    public void syncUseAmountByXxlJob() {
        //更新所有的数据
        tradePositionDomainService.syncUseAmountByXxlJob();
    }

    @Override
    public void savePositionHistory(Integer userId, MockType mock) {
        //1. 将今天的记录删除掉
        DateTime now = DateUtil.date();
        tradePositionHistoryDomainService.deleteByUserIdAndMockTypeAndDate(
                userId,mock.getCode(),now
        );
        //查看当前的持仓信息
        List<TradePositionDo> tradePositionDoList = tradePositionDomainService.listByUserIdAndMockTypeAndCode(
                userId, mock.getCode(), null);
        // 进行保存
        if (CollectionUtils.isEmpty(tradePositionDoList)){
            return ;
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        List<TradePositionHistoryDo> tradePositionHistoryDoList = tradePositionDoList.stream().map(
                n -> {
                    TradePositionHistoryDo tradePositionHistoryDo =
                            tradePositionAssembler.doToHisDo(n);
                    tradePositionHistoryDo.setCurrDate(localDateTime);
                    return tradePositionHistoryDo;
                }
        ).collect(Collectors.toList());
        tradePositionHistoryDomainService.saveBatch(tradePositionHistoryDoList);
    }
    @Override
    public OutputResult<List<TradePositionVo>> realList(TradePositionRo tradePositionRo) {
        //获取响应信息
        TradeResultVo<GetStockListResponse> tradeResultVo = getStockListResponse(tradePositionRo.getUserId());
        if (!tradeResultVo.getSuccess()) {
            return OutputResult.buildAlert(ResultCode.TRADE_POSITION_FAIL);
        }
        log.info(">>>用户{}获取真实的持仓信息成功",tradePositionRo.getUserId());
        List<GetStockListResponse> data = tradeResultVo.getData();

        List<TradePositionVo> tradePositionVoList = new ArrayList<>();
        for(GetStockListResponse getStockListResponse:data){
            TradePositionVo tradePositionVo = new TradePositionVo();
            tradePositionVo.setCode(getStockListResponse.getKysl());
            tradePositionVo.setMockType(MockType.REAL.getCode());
            tradePositionVoList.add(tradePositionVo);
        }
        log.info(">>>用户{}解析真实的持仓信息成功",tradePositionRo.getUserId());
        return OutputResult.buildSucc(tradePositionVoList);
    }

    @Override
    public void deleteRealByUserId(Integer userId) {
        tradePositionDomainService.deleteByUserIdAndMockType(userId,MockType.REAL.getCode());
    }

    @Override
    public void syncRealPositionByUserId(Integer userId, List<TradePositionVo> tradePositionVoList) {
        //先将当前用户的真实持仓信息全部删除
        deleteRealByUserId(userId);
        if (CollectionUtils.isEmpty(tradePositionVoList)) {
            return ;
        }
        List<TradePositionDo> positionDoList = tradePositionVoList.stream().map(
                n -> {
                    TradePositionDo tradePositionDo =
                            tradePositionAssembler.entityToDo(tradePositionAssembler.voToEntity(n));
                    tradePositionDo.setUserId(userId);
                    tradePositionDo.setMockType(MockType.REAL.getCode());
                    return tradePositionDo;
                }
        ).collect(Collectors.toList());
        tradePositionDomainService.saveBatch(positionDoList);
    }

    @Override
    public void updateById(TradePosition tradePosition) {
        tradePositionDomainService.updateById(tradePositionAssembler.entityToDo(tradePosition));
    }

    @Override
    public void clearById(Integer id) {
         tradePositionDomainService.removeById(id);
    }

    /**
     * 获取真实的股票持仓响应的信息
     * @param userId 用户编号
     * @return 获取真实的股票持仓响应的信息
     */
    private TradeResultVo<GetStockListResponse> getStockListResponse(Integer userId) {
        GetStockListRequest request = new GetStockListRequest(userId);
        String url = tradeUtil.getUrl(request);
        Map<String, String> header = tradeUtil.getHeader(request);
        Map<String, Object> params;
        params = tradeUtil.getParams(request);
        log.debug("trade {} request: {}", request.getMethod(), params);
        String content = tradeClient.send(url, params, header);
        log.debug("trade {} response: {}", request.getMethod(), content);
        return defaultResponseParser.parse(content,
                new TypeReference<GetStockListResponse>(){});
    }

    @Override
    public OutputResult<List<TradePositionVo>> mockList(TradePositionRo tradePositionRo) {

        List<TradePositionDo> tradePositionDoList =
                tradePositionDomainService.listByUserIdAndMockTypeAndCode(tradePositionRo.getUserId(),
                        tradePositionRo.getMockType(),null);
        if (CollectionUtils.isEmpty(tradePositionDoList)){
            return OutputResult.buildSucc(Collections.EMPTY_LIST);
        }
        List<TradePositionVo> tradePositionVoList = tradePositionDoList.stream().map(
                n-> tradePositionAssembler.entityToVo(tradePositionAssembler.doToEntity(n))
        ).collect(Collectors.toList());
        return OutputResult.buildSucc(tradePositionVoList);
    }
}
