package top.yueshushu.learn.crawler.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 东方财富响应第二层数据
 *
 * @author yuejianli
 * @date 2023-02-02
 */
@Data
public class EasyMoneyResponseData<T> implements Serializable {
    private Integer total;
    private List<T> diff;
}
