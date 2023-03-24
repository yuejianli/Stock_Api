package top.yueshushu.learn.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.yueshushu.learn.api.TradeResultVo;
import top.yueshushu.learn.api.response.GetStockListResponse;
import top.yueshushu.learn.api.responseparse.DataObjResponseParser;
import top.yueshushu.learn.assembler.TradePositionAssembler;
import top.yueshushu.learn.common.ResultCode;
import top.yueshushu.learn.domain.TradePositionDo;
import top.yueshushu.learn.domain.TradePositionHistoryDo;
import top.yueshushu.learn.domainservice.TradePositionDomainService;
import top.yueshushu.learn.domainservice.TradePositionHistoryDomainService;
import top.yueshushu.learn.entity.TradePosition;
import top.yueshushu.learn.enumtype.MockType;
import top.yueshushu.learn.enumtype.SelectedType;
import top.yueshushu.learn.exception.TradeUserException;
import top.yueshushu.learn.helper.TradeRequestHelper;
import top.yueshushu.learn.mode.ro.TradePositionRo;
import top.yueshushu.learn.mode.vo.TradePositionVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.TradePositionService;
import top.yueshushu.learn.util.BigDecimalUtil;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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
    private DataObjResponseParser dataObjResponseParser;
    @Resource
    private TradeRequestHelper tradeRequestHelper;

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
    public void savePositionHistory(Integer userId, MockType mock, Date currentDate) {
        //1. 将今天的记录删除掉
        DateTime handlerDate = DateUtil.date(currentDate);
        tradePositionHistoryDomainService.deleteByUserIdAndMockTypeAndDate(
                userId, mock.getCode(), handlerDate
        );
        //查看当前的持仓信息
        List<TradePositionDo> tradePositionDoList = tradePositionDomainService.listByUserIdAndMockTypeAndCode(
                userId, mock.getCode(), null);
        // 进行保存
        if (CollectionUtils.isEmpty(tradePositionDoList)) {
            return;
        }
        Instant instant = currentDate.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
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
    public OutputResult<List<TradePositionVo>> realList(TradePositionRo tradePositionRo) throws TradeUserException {
        //获取响应信息
        TradeResultVo<GetStockListResponse> tradeResultVo = null;
        try {
            tradeResultVo = tradeRequestHelper.findRealPosition(tradePositionRo.getUserId());
        } catch (Exception e) {
            throw new TradeUserException("无权限查询真实的持仓情况");
        }
        if (!tradeResultVo.getSuccess()) {
            return OutputResult.buildAlert(ResultCode.TRADE_POSITION_FAIL);
        }
        log.info(">>>用户{}获取真实的持仓信息成功", tradePositionRo.getUserId());
        List<GetStockListResponse> data = tradeResultVo.getData();
        List<TradePositionVo> tradePositionVoList = new ArrayList<>();
        for (GetStockListResponse getStockListResponse : data) {
            TradePositionVo tradePositionVo = new TradePositionVo();
            tradePositionVo.setCode(getStockListResponse.getZqdm());
            tradePositionVo.setName(getStockListResponse.getZqmc());
            tradePositionVo.setAvgPrice(BigDecimalUtil.toBigDecimal(getStockListResponse.getCbjg()));
            tradePositionVo.setAllAmount(Integer.parseInt(getStockListResponse.getZqsl()));
            tradePositionVo.setUseAmount(Integer.parseInt(getStockListResponse.getKysl()));
            tradePositionVo.setPrice(BigDecimalUtil.toBigDecimal(getStockListResponse.getZxjg()));
            tradePositionVo.setFloatMoney(BigDecimalUtil.toBigDecimal(getStockListResponse.getLjyk()));
            tradePositionVo.setFloatProportion(BigDecimalUtil.toBigDecimal(getStockListResponse.getYkbl()));
            tradePositionVo.setAllMoney(BigDecimalUtil.toBigDecimal(getStockListResponse.getZxsz()));
            tradePositionVo.setTodayMoney(BigDecimalUtil.toBigDecimal(
                    StringUtils.hasText(getStockListResponse.getDryk()) ? getStockListResponse.getDryk() : "0.00"));
            tradePositionVo.setMockType(MockType.REAL.getCode());
            tradePositionVo.setSelectType(SelectedType.POSITION.getCode());
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
    public void syncEasyMoneyToDB(Integer userId, MockType mockType) {
        TradePositionRo tradePositionRo = new TradePositionRo();
        tradePositionRo.setUserId(userId);
        List<TradePositionVo> tradePositionVoList = null;
        try {
            tradePositionVoList = realList(tradePositionRo).getData();
        } catch (TradeUserException e) {
            log.error("异常信息", e);
        }
        // 没有持仓记录
        if (CollectionUtils.isEmpty(tradePositionVoList)) {
            return;
        }

        // 有持仓了。
        syncRealPositionByUserId(userId, tradePositionVoList);

    }

    @Override
    public void updateById(TradePosition tradePosition) {
        tradePositionDomainService.updateById(tradePositionAssembler.entityToDo(tradePosition));
    }

    @Override
    public void clearById(Integer id) {
        tradePositionDomainService.removeById(id);
    }

    @Override
    public void operatePosition(TradePosition tradePosition) {
        // 先查询一下，是否存在.
        TradePosition showTradePosition = getPositionByCode(tradePosition.getUserId(), tradePosition.getMockType(), tradePosition.getCode());
        if (null == showTradePosition) {
            // 进行插入
            TradePositionDo addTradePositionDo = tradePositionAssembler.entityToDo(tradePosition);
            tradePositionDomainService.save(addTradePositionDo);
        } else {
            // 存在的话，进行更新。
            TradePositionDo editTradePositionDo = tradePositionAssembler.entityToDo(tradePosition);
            editTradePositionDo.setId(showTradePosition.getId());
            // 进行更新
            tradePositionDomainService.updateById(editTradePositionDo);
        }
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
