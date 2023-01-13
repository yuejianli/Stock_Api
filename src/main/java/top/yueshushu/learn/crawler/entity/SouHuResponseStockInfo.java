package top.yueshushu.learn.crawler.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 搜狐响应数据
 *
 * @author yuejianli
 * @date 2023-01-13
 */
@Data
public class SouHuResponseStockInfo implements Serializable {
    private Integer status;
    private String code;
    private List<List<String>> hq;
}
