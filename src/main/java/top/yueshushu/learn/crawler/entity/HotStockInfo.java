package top.yueshushu.learn.crawler.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 热点股票信息
 *
 * @author yuejianli
 * @date 2023-01-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotStockInfo implements Serializable {
    private Map<String, List<String>> hotDataList;
}
