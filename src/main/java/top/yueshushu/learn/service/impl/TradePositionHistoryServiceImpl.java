package top.yueshushu.learn.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.assembler.TradePositionHistoryAssembler;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.domain.StockHistoryDo;
import top.yueshushu.learn.domain.TradePositionHistoryDo;
import top.yueshushu.learn.domainservice.TradePositionHistoryDomainService;
import top.yueshushu.learn.mode.ro.TradePositionRo;
import top.yueshushu.learn.mode.vo.StockHistoryVo;
import top.yueshushu.learn.mode.vo.TradePositionHistoryVo;
import top.yueshushu.learn.response.PageResponse;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.service.TradePositionHistoryService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 持仓历史记录表
 * </p>
 *
 * @author 岳建立
 * @since 2022-01-02
 */
@Service
@Slf4j
public class TradePositionHistoryServiceImpl implements TradePositionHistoryService {
    @Resource
    private TradePositionHistoryDomainService tradePositionHistoryDomainService;
    @Resource
    private TradePositionHistoryAssembler tradePositionHistoryAssembler;
    @Override
    public OutputResult pageHistory(TradePositionRo tradePositionRo) {
        Page<Object> pageGithubResult = PageHelper.startPage(tradePositionRo.getPageNum(), tradePositionRo.getPageSize());

        List<TradePositionHistoryDo> positionHistoryDoList= tradePositionHistoryDomainService.listPositionHistoryAndDate(tradePositionRo.getCode()
                , DateUtil.parse(
                        tradePositionRo.getStartDate(), Const.SIMPLE_DATE_FORMAT
                ),
                DateUtil.parse(
                        tradePositionRo.getEndDate(),Const.SIMPLE_DATE_FORMAT
                ));

        if (CollectionUtils.isEmpty(positionHistoryDoList)){
            return OutputResult.buildSucc(
                    PageResponse.emptyPageResponse()
            );
        }
        List<TradePositionHistoryVo> pageResultList = new ArrayList<>(positionHistoryDoList.size());
        positionHistoryDoList.forEach(
                n->{
                    pageResultList.add(
                            tradePositionHistoryAssembler.entityToVo(
                                    tradePositionHistoryAssembler.doToEntity(n)
                            )
                    );
                }
        );

        PageInfo pageInfo=new PageInfo<>(pageResultList);
        return OutputResult.buildSucc(new PageResponse<>(
                pageGithubResult.getTotal(),pageInfo.getList()
        ));
    }
}
