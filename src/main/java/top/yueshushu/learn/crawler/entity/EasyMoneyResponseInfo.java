package top.yueshushu.learn.crawler.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 东方财富响应 最外层的信息
 *
 * @author yuejianli
 * @date 2023-02-02
 */
@Data
public class EasyMoneyResponseInfo<T> implements Serializable {
    private String rc;
    private String rt;
    private String svr;
    private String lt;
    private String full;
    private String dlmkts;
    private EasyMoneyResponseData<T> data;
}
