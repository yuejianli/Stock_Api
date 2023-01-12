package top.yueshushu.learn.crawler.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 雪球的配置信息
 *
 * @author yuejianli
 * @date 2023-01-12
 */
@Data
public class XueQiuResponseStockInfo implements Serializable {
    private Integer error_code;
    private String error_description;
    private List<XueQiuStockInfo> data;
}
