package top.yueshushu.learn.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.yueshushu.learn.assembler.TradeMoneyHistoryAssembler;
import top.yueshushu.learn.common.Const;
import top.yueshushu.learn.domain.TradeMoneyHistoryDo;
import top.yueshushu.learn.domainservice.TradeMoneyHistoryDomainService;
import top.yueshushu.learn.mode.ro.TradeMoneyRo;
import top.yueshushu.learn.mode.vo.TradeMoneyHistoryVo;
import top.yueshushu.learn.response.OutputResult;
import top.yueshushu.learn.response.PageResponse;
import top.yueshushu.learn.service.TradeMoneyHistoryService;
import top.yueshushu.learn.service.TradeMoneyService;

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
public class TradeMoneyHistoryServiceImpl implements TradeMoneyHistoryService {
    @Resource
    private TradeMoneyHistoryDomainService tradeMoneyHistoryDomainService;
    @Resource
    private TradeMoneyHistoryAssembler tradeMoneyHistoryAssembler;
    @Resource
    private TradeMoneyService tradeMoneyService;

    @Override
    public OutputResult pageHistory(TradeMoneyRo tradeMoneyRo) {
        Page<Object> pageGithubResult = PageHelper.startPage(tradeMoneyRo.getPageNum(), tradeMoneyRo.getPageSize());

        List<TradeMoneyHistoryDo> tradeMoneyHistoryDoList = tradeMoneyHistoryDomainService.listMoneyHistory(tradeMoneyRo.getUserId(), tradeMoneyRo.getMockType()
                , DateUtil.parse(
                        tradeMoneyRo.getStartDate(), Const.SIMPLE_DATE_FORMAT
                ),
                DateUtil.parse(
                        tradeMoneyRo.getEndDate(), Const.SIMPLE_DATE_FORMAT
                ));
        
        if (CollectionUtils.isEmpty(tradeMoneyHistoryDoList)) {
            return OutputResult.buildSucc(
                    PageResponse.emptyPageResponse()
            );
        }
        List<TradeMoneyHistoryVo> pageResultList = new ArrayList<>(tradeMoneyHistoryDoList.size());
        tradeMoneyHistoryDoList.forEach(
                n -> {
                    pageResultList.add(
                            tradeMoneyHistoryAssembler.entityToVo(
                                    tradeMoneyHistoryAssembler.doToEntity(n)
                            )
                    );
                }
        );
        PageInfo pageInfo = new PageInfo<>(pageResultList);
        return OutputResult.buildSucc(new PageResponse<>(
                pageGithubResult.getTotal(), pageInfo.getList()
        ));
    }
}
