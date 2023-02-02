package top.yueshushu.learn.crawler.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 响应 Map
 *
 * @author yuejianli
 * @date 2023-01-31
 */
@Data
public class HotStockResponseInfo implements Serializable {
    private List<HotStockInfo> data;
}
