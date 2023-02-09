package top.yueshushu.learn.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yueshushu.learn.domainservice.StockBkStockDomainService;

import javax.annotation.Resource;

/**
 * 用途描述
 *
 * @author yuejianli
 * @date 2023-02-09
 */
@Service
@Slf4j
public class StockBkStockServiceImpl {
    @Resource
    private StockBkStockDomainService stockBkStockDomainService;
}
