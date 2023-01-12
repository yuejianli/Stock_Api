package top.yueshushu.learn.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 交易，包括爬虫所使用的url信息
 * </p>
 *
 * @author 岳建立 自定义的
 * @since 2022-01-02
 */
@Data
public class TradeMethod implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String code;
    private String name;
    private String url;
    private String description;
    private Date createTime;
    private Date updateTime;
    private Integer flag;


}
